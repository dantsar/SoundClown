import { Link } from 'react-router-dom';

const UserList = ({users, title}) => {
    return (
        <div className="user-list">
            <h2>{ title }</h2>
            {users.map((user) => (
                <div className="user-preview" key={user._user_id}>
                    <Link to ={`/user/${user._user_id}`}>
                        <p>ID: {user._user_id} Username: { user._user_name }</p>
                    </Link>
                </div>
            ))}
        </div>
    );
}

export default UserList;
