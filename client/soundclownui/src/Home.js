import { useState } from 'react';
import UserList from './UserList';
 
const Home = () => {
    const [users, setUsers] = useState([
        {_user_name: "andy", _password: "mypassword", _user_id: 1},
        {_user_name: "fred", _password: "mypassword", _user_id: 34},
    ]);
    return (
        <div className="home">
            <UserList users={users} />
        </div>
    );
}

export default Home;
