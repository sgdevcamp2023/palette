from datetime import datetime
from elasticsearch import Elasticsearch, helpers
from tweet_generator import get_tweet_info
import pandas as pd

def delete_all_data(es, index):
    es.delete_by_query(
        index=index,
        body={
            "query": {
                "match_all": {}
            }
        }
    )

def extract_hashtags(data):
    return [item['hashtag'] for item in data]

def extract_mentions(data):
    return [item['uid'] for item in data]

def get_usernames(uid_list, users):
    return users.loc[users['Uid'].isin(uid_list), 'Username'].tolist()

def fetch_data_from_es(es, index, size):
    res = es.search(index=index, size=size)
    for doc in res['hits']['hits']:
        print(doc['_source'])

def make_index(es, index_name):
    if es.indices.exists(index=index_name):
        es.indices.delete(index=index_name)
    print(es.indices.create(index=index_name))

if __name__ == "__main__":
    es = Elasticsearch("http://localhost:9200/")
    # make_index(es, "search_paint")

    # delete_all_data(es, 'search_paint')

    user_df = pd.read_csv('user.csv')

    paints = get_tweet_info()
    es_paints = []

    for paint in paints:
        doc = {
            'id': paint['pid'],
            'authorId': paint['authorUid'],
            'authorUsername': user_df.loc[user_df['Uid'] == paint['authorUid'], 'Username'].values[0],
            'authorNickname': user_df.loc[user_df['Uid'] == paint['authorUid'], 'Nickname'].values[0],
            'text': paint['text'],
            'hashtagRecords': extract_hashtags(paint['hashtags']),
            'mentionRecords': get_usernames(extract_mentions(paint['mentions']), user_df),
            'createdAt': paint['createdAt'].strftime('%Y-%m-%dT%H:%M:%S'),
        }
        es_paints.append(doc)

    # Elasticsearch에 데이터 저장
    actions = [
        {
            "_index": "search_paint",
            "_id": doc['id'],
            "_source": doc
        }
        for doc in es_paints
    ]

    try:
        helpers.bulk(es, actions)
    except Exception as e:
        print(f"Error occurred while indexing documents: {e}")
        
    # fetch_data_from_es(es, 'search_paint', 5)
