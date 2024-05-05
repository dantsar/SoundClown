import UserList from './UserList';
import TrackList from './TrackList';
import useFetch from './useFetch';

const AllUsers = () => {
    const { data: users, isPending, error } = useFetch('http://localhost:8080/get/allusers');

    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { isPending && <div>Loading...</div> }
            { users && <UserList users={users} title="All Users!"/>  }
        </div>
    );
}

export default AllUsers;
