# 한글 이름 넣기
# import csv
# import random
# import pandas as pd
# from korean_name_generator import namer

# # 5000명의 이름 생성 (2500명의 남자 이름과 2500명의 여자 이름)
# names = []
# for _ in range(2504):
#     male_name = namer.generate(True)
#     female_name = namer.generate(False)

#     # 60% 확률로 첫 글자를 떼어냄
#     if random.random() < 0.6:
#         male_name = male_name[1:]
#         female_name = female_name[1:]

#     names.append(male_name)
#     names.append(female_name)

# # 생성한 이름 목록을 무작위로 섞음
# random.shuffle(names)

# # csv 파일 읽기
# df = pd.read_csv('user.csv')

# # Nickname 컬럼을 생성한 한국 이름으로 교체
# # 만약 생성한 이름이 csv 파일의 행보다 적다면 에러가 발생할 수 있으니, 주의하세요.
# df['Nickname'] = names

# # 변경된 데이터프레임을 다시 csv 파일로 저장
# df.to_csv('user.csv', index=False)

##########################################################3

# 영어이름으로 덮어쓰기
# import pandas as pd
# import numpy as np

# # csv 파일 읽기
# df = pd.read_csv('user.csv')

# # 행의 60%를 무작위로 선택
# num_rows = int(0.5 * len(df))
# random_indices = np.random.choice(df.index, size=num_rows, replace=False)

# # 선택한 행의 Nickname을 Username과 동일하게 설정
# df.loc[random_indices, 'Nickname'] = df.loc[random_indices, 'Username']

# # 변경된 데이터프레임을 다시 csv 파일로 저장
# df.to_csv('user.csv', index=False)

#################################################
# 숫자 넣기
# import pandas as pd
# import random

# # csv 파일 읽기
# df = pd.read_csv('user.csv')

# for i in df.index:
#     # 50%의 확률로 숫자를 끝이나 사이에 추가
#     if random.random() < 0.5:
#         random_number = str(random.randint(0, 999))
#         # 랜덤한 0~999의 숫자를 끝이나 사이에 넣을 위치 선택 (0: 끝, 1: 사이)
#         position = random.choice([0, 1])
#         if position == 0:  # 끝
#             df.loc[i, 'Username'] += random_number
#         else:  # 사이
#             username = df.loc[i, 'Username']
#             insert_index = random.randint(1, len(username) - 1)
#             df.loc[i, 'Username'] = username[:insert_index] + random_number + username[insert_index:]

# # 변경된 데이터프레임을 다시 csv 파일로 저장
# df.to_csv('user.csv', index=False)


#############################################333
# 태어난 시간조정
# import pandas as pd
# import random
# from datetime import datetime, timedelta

# # csv 파일 읽기
# df = pd.read_csv('user.csv')

# # 2005년부터 2023년까지의 날짜 범위 생성
# start_date = datetime(2005, 1, 1)
# end_date = datetime(2023, 12, 31)
# date_range = [start_date + timedelta(days=x) for x in range((end_date-start_date).days + 1)]

# # 랜덤하게 날짜 선택하고 오래된 순으로 정렬
# random_dates = random.sample(date_range, len(df))
# random_dates.sort()

# # 각 날짜에 랜덤한 시간, 분, 초 추가
# random_timestamps = [date + timedelta(hours=random.randint(0, 23), minutes=random.randint(0, 59), seconds=random.randint(0, 59)) for date in random_dates]

# # CreatedAt 컬럼에 랜덤한 timestamp 할당
# df['CreatedAt'] = [timestamp.strftime("%Y-%m-%dT%H:%M:%S") for timestamp in random_timestamps]

# # 변경된 데이터프레임을 다시 csv 파일로 저장
# df.to_csv('user.csv', index=False)

#########################
# 기본 배경 이미지 변경
# import pandas as pd

# # csv 파일 읽기
# df = pd.read_csv('user.csv')

# # ImagePath 컬럼에 "/default" 값 할당
# df['BackgroundImagePath'] = "background/msqoll4kckvhw5gfgqgx"

# # 변경된 데이터프레임을 다시 csv 파일로 저장
# df.to_csv('user.csv', index=False)


#########################
# 이미지 변경
# import pandas as pd
# import user_images
# import random

# images = user_images.images * 5
# random.shuffle(images)

# # csv 파일 읽기
# df = pd.read_csv('user.csv')

# # ImagePath 컬럼에 "/default" 값 할당
# df['ImagePath'] = images[:5008]

# # 변경된 데이터프레임을 다시 csv 파일로 저장
# df.to_csv('user.csv', index=False)

#############################
# import pandas as pd

# # CSV 파일 읽기
# df = pd.read_csv('NationalNames.csv')

# # 'Name' 열에서 유일한 항목만 선택
# unique_names = df['Name'].unique()

# # 유일한 이름 수 확인
# num_unique_names = len(unique_names)

# # 유일한 이름이 5100개 이상이라면, 5100개만 무작위로 선택
# if num_unique_names >= 5100:
#     selected_names = pd.Series(unique_names).sample(5100).tolist()
# else:
#     # 유일한 이름이 5100개 미만이라면, 모든 유일한 이름을 사용
#     selected_names = unique_names.tolist()

# print(selected_names)


###############################################
# import pandas as pd

# # CSV 파일 읽기
# df = pd.read_csv('NationalNames.csv')

# # 'Name' 열에서 유일한 항목만 선택
# unique_names = df['Name'].unique()

# # user.csv 읽기
# user_df = pd.read_csv('user.csv')

# selected_names = pd.Series(unique_names).sample(len(user_df)).tolist()

# # 'Username' 열에 유일한 이름들 덮어쓰기
# user_df['Username'] = selected_names

# # 변경된 데이터를 새 CSV 파일로 저장
# user_df.to_csv('updated_user.csv', index=False)

##################################################
import pandas as pd

# 데이터 불러오기
user_df = pd.read_csv('user.csv')

# 'Uid' 컬럼의 모든 값에 1 더하기
user_df['Uid'] = user_df['Uid'] + 1

# 변경된 데이터프레임을 새로운 CSV 파일에 저장
user_df.to_csv('user.csv', index=False)