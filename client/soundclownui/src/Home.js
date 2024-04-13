import UserList from './UserList';
import useFetch from './useFetch';
 
const Home = () => {
    const { data: users, isPending, error } = useFetch('http://localhost:8080/get/allusers');

    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { isPending && <div>Loading...</div> }
            { users && <UserList users={users} title="User List"/>  }
        </div>
    );
}

export default Home;
