import UserList from './UserList';
import TrackList from './TrackList';
import useFetch from './useFetch';

const Home = () => {
    const { data: users, usersIsPending, error } = useFetch('http://localhost:8080/get/allusers');
    const { data: tracks, tracksIsPending } = useFetch('http://localhost:8080/get/alltracks');

    console.log("Users");
    console.log(users);
    console.log("Tracks");
    console.log(tracks);
    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { usersIsPending && <div>Loading...</div> }
            { users && <UserList users={users} title="User List"/>  }
            { tracks && !tracksIsPending && <TrackList tracks={tracks} title="Track List"/> }
        </div>
    );
}

export default Home;
