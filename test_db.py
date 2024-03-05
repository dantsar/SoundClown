import requests

# If cant stop database using WITH (force)
def create_three():
    url       = "http://localhost:8080/create/user"
    users     = ["andy", "daniel", "fred"]
    passwords = ["123", "456", "789"]

    for i in range(len(users)):
        response = requests.post(url, json={"user_name": f"{users[i]}", "password":f"{passwords[i]}"})


def get_update_three():
    url       = "http://localhost:8080/get/user"
    users     = ["andy", "daniel", "fred"]
    passwords = ["123", "456", "789"]

    response = requests.get("http://localhost:8080/get/user/{users[0]}")
    response_json = response.json()
    print(response_json)


# Note limited to max 100 users
def get_users():
    for i in range(1, 51):
        user = f"user{i}"
        url  = f"http://localhost:8080/get/user/{user}"
        response = requests.get(url)
        response_json = response.json()
        print(response_json)


if __name__ == "__main__":
#    get_users()
    create_three()
    get_update_three()
