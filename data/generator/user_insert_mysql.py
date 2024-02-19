import csv


class CSVToSQLConverter:

    def __init__(self, csv_file_path, sql_file_path):
        self.csv_file_path = csv_file_path
        self.sql_file_path = sql_file_path

    def convert(self):
        with open(self.csv_file_path, 'r') as csv_file:
            reader = csv.DictReader(csv_file)

            with open(self.sql_file_path, 'w') as sql_file:
                values = []

                # Iterate through each row in the csv file
                for row in reader:
                    value = f"""(
                        '{row['Nickname']}',
                        '{row['Username']}',
                        '{row['ImagePath']}',
                        '{row['CreatedAt']}',
                        '{row['BackgroundImagePath']}',
                        '{row['Username'].lower()}@gmail.com',
                        '{row['Username'].lower()}',
                        '',
                        ''
                    )"""
                    values.append(value)

                # Create a bulk insert statement
                statement = f"""
                    INSERT INTO user (nickname, username, profile_image_path, created_at, background_image_path, email, password, dm_pin, paint_pin)
                    VALUES {', '.join(values)};
                """

                # Write the SQL insert statement to the file
                sql_file.write(statement)


# Create an instance of the converter and use it
converter = CSVToSQLConverter('user.csv', 'user_mysql_insert.sql')
converter.convert()
