const UserList = (props) => {
    const users = props.users;

    return (
        <div className="user-list">
            {users.map((user) => (
                <div className="user-preview" key={user._user_id}>
                    <h2>Username: { user._user_name }</h2>
                    <h2>Password: { user._password }</h2>
                </div>
            ))}
        </div>
    );
}

export default UserList;
