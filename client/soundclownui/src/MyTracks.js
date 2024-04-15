import UserList from './UserList';
import TrackList from './TrackList';
import useFetch from './useFetch';

const MyTracks = () => {
    const { data: tracks, tracksIsPending, error } = useFetch('http://localhost:8080/get/user/tracks');

    // console.log("Users");
    // console.log(users);
    // console.log("Tracks");
    // console.log(tracks);
    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { tracksIsPending && <div>Loading...</div> }
            {/* users && <UserList users={users} title="All Users!"/>  */}
            { tracks && <TrackList tracks={tracks} title="My Tracks!"/> }
        </div>
    );
}

export default MyTracks;
