import { Link } from 'react-router-dom';

const UserList = ({users, title}) => {
    return (
        <div className='user-wrapper'>
            <h2>{ title }</h2>
            <div className="user-list">
                {users.map((user) => (
                    <div className="user-preview" key={user._user_id}>
                        <Link to ={`/user/${user._user_name}`}>
                            <h2>{ user._user_name } </h2>
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default UserList;
