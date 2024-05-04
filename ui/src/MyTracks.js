import UserList from './UserList';
import TrackList from './TrackList';
import useFetchUserTracks from './useFetchUserTracks';
import Cookies from 'js-cookie';
import User from './User';

const MyTracks = () => {
    const { data: tracks, tracksIsPending, error } = useFetchUserTracks('http://localhost:8080/get/user/tracks');

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
