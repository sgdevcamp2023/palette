import pandas as pd
from tweet_generator import get_tweet_info
from neo4j import GraphDatabase
from neo4j.time import DateTime

import datetime

class PaintNodeWithRelation:
    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()

    def create_paints(self, paints):
        with self.driver.session() as session:
            session.execute_write(self._create_and_return_paints, paints)
    
    def create_replies_relationships(self, replies):
        with self.driver.session() as session:
            session.execute_write(self._create_replies_relationships, replies)

    def create_quotes_relationships(self, quotes):
        with self.driver.session() as session:
            session.execute_write(self._create_quotes_relationships, quotes)

    def create_creates_relationships(self, creates):
        with self.driver.session() as session:
            session.execute_write(self._create_creates_relationships, creates)

    def create_uses_relationships(self, uses):
        with self.driver.session() as session:
            session.execute_write(self._create_uses_relationships, uses)

    def create_tags_relationships(self, tags):
        with self.driver.session() as session:
            session.execute_write(self._create_tags_relationships, tags)
            
    def create_mentions_relationships(self, mentions):
        with self.driver.session() as session:
            session.execute_write(self._create_mentions_relationships, mentions)

    def create_tags_user_relationships(self, tags_user):
        with self.driver.session() as session:
            session.execute_write(self._create_tags_user_relationships, tags_user)


    @staticmethod
    def _create_and_return_paints(tx, paints):
        query = """
        UNWIND $paints AS paint
        CREATE (p:Paint) 
        SET p = paint
        RETURN p
        """
        result = tx.run(query, paints=paints)
        paint_count = len(result.values())
        print(f"{paint_count} Paints created.")
        return paint_count
    
    @staticmethod
    def _create_replies_relationships(tx, replies):
        query = """
        UNWIND $replies AS reply
        MATCH (p1:Paint {pid: reply.pid1})
        MATCH (p2:Paint {pid: reply.pid2})
        CREATE (p1)-[:REPLIES]->(p2)
        """
        tx.run(query, replies=replies)
    
    @staticmethod
    def _create_quotes_relationships(tx, quotes):
        query = """
        UNWIND $quotes AS quote
        MATCH (p1:Paint {pid: quote.pid1})
        MATCH (p2:Paint {pid: quote.pid2})
        CREATE (p1)-[:QUOTES]->(p2)
        """
        tx.run(query, quotes=quotes)

    @staticmethod
    def _create_creates_relationships(tx, creates):
        query = """
        UNWIND $creates AS c
        MATCH (u:User {uid: c.uid})
        MATCH (p:Paint {pid: c.pid})
        CREATE (u)-[:CREATES]->(p)
        """
        tx.run(query, creates=creates)
    
    @staticmethod
    def _create_uses_relationships(tx, uses):
        query = """
        UNWIND $uses AS u
        MATCH (p:Paint {pid: u.pid})
        MERGE (m:Media {path: u.path})
        CREATE (p)-[:USES]->(m)
        """
        tx.run(query, uses=uses)

    @staticmethod
    def _create_mentions_relationships(tx, mentions):
        query = """
        UNWIND $mentions AS men
        MATCH (p:Paint {pid: men.pid})
        MERGE (u:User {uid: men.uid})
        CREATE (p)-[:MENTIONS {start: men.start, end: men.end}]->(u)
        """
        tx.run(query, mentions=mentions)

    @staticmethod
    def _create_tags_relationships(tx, tags):
        query = """
        UNWIND $tags AS t
        MATCH (p:Paint {pid: t.pid})
        MERGE (h:Hashtag {tag: t.tag})
        CREATE (p)-[:TAGS {start: t.start, end: t.end}]->(h)
        """
        tx.run(query, tags=tags)

    @staticmethod
    def _create_tags_user_relationships(tx, tags_user):
        query = """
        UNWIND $tags_user AS tu
        MATCH (p:Paint {pid: tu.pid})
        MERGE (u:User {uid: tu.uid})
        CREATE (p)-[:TAGS_USER]->(u)
        """
        tx.run(query, tags_user=tags_user)

if __name__ == "__main__":
    paint_node_with_relation = PaintNodeWithRelation("bolt://localhost:7687", "neo4j", "palette1203")

    paints = get_tweet_info()

    formatted_paints = []
    creates = []
    replies = []
    quotes = []
    uses = []
    mentions = []
    tags = []
    tags_user = []

    for paint in paints:
        formatted_paint = {
            'pid': int(paint['pid']),
            'content': paint['text'],
            'hasQuote': paint.get('quotes') is not None,
            'views': int(paint['views']),
            'createdAt': paint['createdAt']
        }
        formatted_paints.append(formatted_paint)

        create = {
            'uid': int(paint['authorUid']),
            'pid': int(paint['pid']),
        }
        creates.append(create)

        if paint.get('isReplyTo') is not None:
            reply = {
                'pid1': int(paint['pid']),
                'pid2': int(paint['isReplyTo'])
            }
            replies.append(reply)

        if paint.get('quotes') is not None:
            quote = {
                'pid1': int(paint['pid']),
                'pid2': int(paint['quotes'])
            }
            quotes.append(quote)

        for media in paint['medias']:
            md = {
                'pid': int(paint['pid']),
                'path': media
            }
            uses.append(md)
        
        for mention in paint['mentions']:
            mnt = {
                'pid': int(paint['pid']),
                'start': int(mention['start']),
                'end': int(mention['end']),
                'uid': mention['uid']
            }
            mentions.append(mnt)

        for hashtag in paint['hashtags']:
            ht = {
                'pid': int(paint['pid']),
                'start': int(hashtag['start']),
                'end': int(hashtag['end']),
                'tag': hashtag['hashtag']
            }
            tags.append(ht)
        
        for tu in paint['taggedUsers']:
            taged = {
                'pid': int(paint['pid']),
                'uid': int(tu)
            }
            tags_user.append(taged)

    df_paints = pd.DataFrame(formatted_paints)
    df_paints.rename(columns={'pid': 'pid', 'content': 'content', 'hasQuote': 'hasQuote', 'views': 'views', 'createdAt': 'createdAt'}, inplace=True)
    paint_node_with_relation.create_paints(df_paints.to_dict('records'))

    paint_node_with_relation.create_creates_relationships(creates)
    if replies:
        paint_node_with_relation.create_replies_relationships(replies)
    if quotes:
        paint_node_with_relation.create_quotes_relationships(quotes)
    paint_node_with_relation.create_uses_relationships(uses)
    paint_node_with_relation.create_mentions_relationships(mentions)
    paint_node_with_relation.create_tags_relationships(tags)
    paint_node_with_relation.create_tags_user_relationships(tags_user)

    paint_node_with_relation.close()