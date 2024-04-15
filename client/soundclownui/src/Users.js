import UserList from './UserList';
import useFetch from './useFetch';

const Users = () => {
    const { data: users, isPending, error } = useFetch('http://localhost:8080/get/allusers');
    // console.log("Users");
    // console.log(users);
    // console.log("Tracks");
    // console.log(tracks);
    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { isPending && <div>Loading...</div> }
            { users && <UserList users={users} title="All Users!"/> }
        </div>
    );
}

export default Users;
