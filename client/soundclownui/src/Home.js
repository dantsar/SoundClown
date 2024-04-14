import UserList from './UserList';
import TrackList from './TrackList';
import useFetch from './useFetch';
 
const Home = () => {
    const { data: users, isPending, error } = useFetch('http://localhost:8080/get/allusers');
    const { data: tracks } = useFetch('http://localhost:8080/get/alltracks');

    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { isPending && <div>Loading...</div> }
            { users && <UserList users={users} title="User List"/>  }
            { tracks && <TrackList tracks={tracks} title="Track List"/> }
        </div>
    );
}

export default Home;
