import TrackList from './TrackList';
import LikedTrackList from './LikedTrackList';
import useFetch from './useFetch';
import Cookies from 'js-cookie';
import user from "./User";

const Home = () => {
    const username= Cookies.get('username');
    const { data: users, isPending, error } = useFetch('http://localhost:8080/get/allusers');
    const { data: tracks, tracksIsPending } = useFetch('http://localhost:8080/get/alltracks');
    const { data: likedTracks, likedTracksIsPending } = useFetch('http://localhost:8080/get/liked/tracks/' + username);

    // console.log("Users");
    // console.log(users);
    // console.log("Tracks");
    // console.log(tracks);
    return (
        <div className="home">
            { error && <div>{ error }</div> }
            { isPending && <div>Loading...</div> }
            { tracks && <TrackList tracks={tracks} title="All Tracks!"/> }
            { likedTracks && <div style={{ marginTop: '20px' }}><LikedTrackList likedTracks={likedTracks} title="Liked Tracks!"/></div> }
        </div>
    );
}

export default Home;
