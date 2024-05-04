import UserList from './UserList';
import TrackList from './TrackList';
import useFetch from './useFetch';

const AllUsers = () => {
    const { data: users, isPending, error } = useFetch('http://localhost:8080/get/allusers');
    //const { data: tracks, tracksIsPending } = useFetch('http://localhost:8080/get/alltracks');

    // console.log("Users");
    // console.log(users);
    // console.log("Tracks");
    // console.log(tracks);
    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { isPending && <div>Loading...</div> }
            { users && <UserList users={users} title="All Users!"/>  }
        </div>
    );
}

export default AllUsers;
