import pandas as pd
import requests
import random
from tweet_generator import get_tweet_info

def change_key_name(list_of_dicts):
    new_list = [
        { 'userId' if k=='uid' else k : v for k, v in d.items() }
        for d in list_of_dicts
    ]
    return new_list

def create_post_requests(tweets):
    post_requests = []

    for tweet in tweets:
        post_data = {
            "text": tweet['text'],
            # "inReplyToPaintId": tweet['isReplyTo'],
            "medias": [{"path": path, "type": "image"} for path in tweet['medias']],
            "taggedUserIds": tweet['taggedUsers'],
            # "quotePaintId": tweet['quotes'],
            "mentions": change_key_name(tweet['mentions']),
            "hashtags": tweet['hashtags']
        }

        post_requests.append(post_data)

    return post_requests


if __name__ == "__main__":
    users = pd.read_csv('user.csv').to_dict('records')
    paints = get_tweet_info()
    # print(paints)

    post_requests = create_post_requests(paints)

    for post_data in post_requests:
        response = requests.post(
            'http://localhost:10003/paints', 
            json=post_data,
            headers={'Authorization': 'eyJ1c2VySW5mbyI6eyJpZCI6NDA5NywiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIiwibmlja25hbWUiOiJuaWNrbmFtZSIsInVzZXJuYW1lIjoidXNlcm5hbWUiLCJyb2xlIjoicm9sZSIsImlzQWN0aXZhdGVkIjp0cnVlLCJhY2Nlc3NlZEF0IjoiYWNjZXNzZWRBdCIsImNyZWF0ZWRBdCI6ImNyZWF0ZWRBdCIsImRlbGV0ZWRBdCI6ImRlbGV0ZWRBdCJ9LCJpbnRlZ3JpdHlLZXkiOiJXNG40bnhRQi83YVpHSk1QbzBuUjcraDNkQXZmNE8wSGZ2VlRqaENsb2RBPSJ9'}
        )
        if response.status_code != 201:
            print(f'Request failed with status code {response.status_code}')
            