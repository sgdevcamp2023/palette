import pandas as pd
import random, re, json, tweet_image_list
from neo4j.time import DateTime
from datetime import timedelta

def get_tweet_info():
    user_df = pd.read_csv('user.csv')

    kr_limit = 2000   # 0 ~ 9000
    en_limit = 2000   # 0 ~ 31962

    df = pd.read_csv('tweet_kr.csv')
    df = df.head(kr_limit) # 상위 n개
    # df = df.tail(-100) # 상위 100개를 제외한 100개
    tweets_kr = df['tokens'].tolist()

    df = pd.read_csv('tweet_us.csv')
    df = df.head(en_limit)
    tweets_en = df['tweet'].tolist()

    tweets = tweets_kr + tweets_en
    random.shuffle(tweets)

    image_list = tweet_image_list.tweet_image_list * 14
    random.shuffle(image_list)

    start_date = DateTime(2013, 1, 1, 0, 0, 0, 789123456)
    end_date = DateTime(2023, 12, 31, 23, 59, 59, 789123456)
    timestamps = []
    for _ in range(kr_limit + en_limit):
        random_date = start_date + timedelta(days=random.randint(0, (end_date - start_date).days))
        random_timestamp = random_date + timedelta(hours=random.randint(0, 23), minutes=random.randint(0, 59), seconds=random.randint(0, 59))
        timestamps.append(random_timestamp)
    timestamps.sort()

    tweets_info = []
    for idx, tweet in enumerate(tweets):
        if not isinstance(tweet, str):
            tweet = str(tweet)

        tweet_info = {}
        tweet_info['pid'] = idx
        tweet_info['createdAt'] = timestamps[idx]
        tweet_info['isReplyTo'] = idx - 1 if idx > 0 and random.random() < 0.2 else None
        tweet_info['quotes'] = random.randint(0, idx - 1) if idx > 0 and random.random() < 0.2 else None
        tweet_info['medias'] = [image_list.pop()] if idx > 0 and random.random() < 0.6 else []
        tweet_info['views'] = random.randint(0, 958573)

        # filtered_users = user_df[user_df['CreatedAt'] < timestamps[idx]]
        tweet_info['authorUid'] = random.choice(user_df['Uid'].tolist())
        tweet_info['taggedUsers'] = random.sample(user_df['Uid'].tolist(), random.randint(0, 3))

        mention_info_list = []
        replaced_tweet = ""
        i = 0
        while i < len(replaced_tweet + tweet):
            if tweet[i:i+5] == "@user":
                rand_index = random.randint(0, 5007)
                user_info = user_df.loc[rand_index]

                replaced_tweet += "@" + user_info['Username']
                mention_info = {
                    'uid': int(user_info['Uid']),
                    'start': i + 1,
                    'end': i + len(user_info['Username']) + 1,
                    'username': user_info['Username']
                }
                mention_info_list.append(mention_info)
                i += 5
            else:
                if i < len(tweet):
                    replaced_tweet += tweet[i]
                i += 1

        tweet_info['text'] = replaced_tweet
        tweet_info['mentions'] = mention_info_list

        hashtags_info = []
        hashtags = re.finditer(r'(#\w+)', replaced_tweet)
        for hashtag in hashtags:
            hashtag_info = {
                'hashtag': hashtag.group()[1:],
                'start': hashtag.start() + 1,  # 샵 다음 인덱스
                'end': hashtag.end()  # 샵 이후의 첫번째 띄어쓰기 인덱스
            }
            hashtags_info.append(hashtag_info)

        tweet_info['hashtags'] = hashtags_info
        tweets_info.append(tweet_info)
    
    return tweets_info


def save_to_json():
    with open('tweets_info.json', 'w', encoding='utf-8') as f:
        json.dump(get_tweet_info(), f, ensure_ascii=False, indent=4)
