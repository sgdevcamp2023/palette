import pandas as pd
from neo4j import GraphDatabase
import random

class UserNodeExample:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()

    def create_users(self, users):
        with self.driver.session() as session:
            session.execute_write(self._create_and_return_users, users)

    @staticmethod
    def _create_and_return_users(tx, users):
        query = """
        UNWIND $users AS user
        CREATE (a:User) 
        SET a = user
        RETURN a
        """
        result = tx.run(query, users=users)
        print(f"{len(result.values())} Users created.")

    def create_random_follows(self, users):
        with self.driver.session() as session:
            all_follows = []
            for user in users:
                follow_count = random.randint(0, 50)
                follows = random.sample(users, k=min(follow_count, len(users)))
                for follow in follows:
                    all_follows.append({'uid': user['uid'], 'follow_uid': follow['uid']})

            session.execute_write(self._create_and_return_follows, all_follows)

    @staticmethod
    def _create_and_return_follows(tx, all_follows):
        query = """
        UNWIND $follows AS follow
        MATCH (a:User),(b:User)
        WHERE a.uid = follow.uid AND b.uid = follow.follow_uid
        CREATE (a)-[r:FOLLOWS]->(b)
        RETURN r
        """
        result = tx.run(query, follows=all_follows)
        print(f"{len(result.values())} Follow relationships created.")

if __name__ == "__main__":
    user_node_example = UserNodeExample("bolt://localhost:7687", "neo4j", "palette1203")

    user_df = pd.read_csv('user.csv')
    users = user_df.to_dict('records')

    for user in users:
        user['uid'] = user.pop('Uid')
        user['nickname'] = user.pop('Nickname')
        user['username'] = user.pop('Username')
        user['imagePath'] = user.pop('ImagePath')
        user['isActive'] = True

    user_node_example.create_users(users)
    user_node_example.create_random_follows(users)
    user_node_example.close()
