import psycopg2
from psycopg2 import sql

import random

def create_db(db_name, user, password, host, port):
    # Connect to postgresql database first
    print(f"Created {db_name} database")
    connection = psycopg2.connect(dbname=db_name, user=user, password=password, host=host, port=port)
    connection.autocommit = True # Allows us to operate outside of a transaction (basically create database)
    cursor = connection.cursor()

    # Switch to db to soundclown and create it
    db_name = "soundclown"
    cursor.execute(sql.SQL(f"CREATE DATABASE {db_name}"))
    connection.close()
    connection = psycopg2.connect(dbname=db_name, user=user, password=password, host=host, port=port)
    connection.autocommit = True # Allows us to operate outside of a transaction (basically create database)
    cursor = connection.cursor()
    print("Created {db_name} database")
    return connection, cursor


def create_table(cursor):
    # Create tables
    cursor.execute('''CREATE TABLE users (
                        user_id serial,
                        user_name varchar(50) NOT NULL,
                        password varchar(50) NOT NULL,
                        PRIMARY KEY (user_id),
                        UNIQUE(user_name, password)
                    )''')

    cursor.execute('''CREATE TABLE artists (
                        artist_id serial,
                        artist_name varchar(50) UNIQUE NOT NULL,
                        PRIMARY KEY(artist_id)
                    )''')
    cursor.execute('''CREATE TABLE tracks (
                        track_id serial,
                        track_name varchar(50) UNIQUE NOT NULL,
                        description varchar(500),
                        artist_name varchar(50) NOT NULL,
                        plays integer DEFAULT 0 NOT NULL,
                        track_path varchar(500) NOT NULL,
                        PRIMARY KEY (track_id),
                        FOREIGN KEY (artist_name) REFERENCES artists(artist_name)
                    )''')

    print("Finished creating tables")


def insert_items(cursor):
    for i in range(1, 101):
        user     = f"user{i}"
        password = f"pw_{i}"
        cursor.execute(f'''INSERT into users (user_name, password)
                           VALUES('{user}', '{password}')''')


    # Add inserting tracks and artists once I know what they are


if __name__ == "__main__":
    db_name  = "postgres"
    user     = "postgres"
    password = "password"
    host     = "localhost"
    port     = "5432"
    connection, cursor = create_db(db_name, user, password, host, port)
    create_table(cursor)
    connection.close()
    print("Done")
