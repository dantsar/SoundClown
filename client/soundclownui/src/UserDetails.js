import { useNavigate, useParams } from "react-router-dom";
import PlaylistList from './PlaylistList';
import useFetch from './useFetch';
import useFetchUserPlaylists from './useFetchUserPlaylists';
import useFetchUserTracks from './useFetchUserTracks';
import Cookies from 'js-cookie';
import TrackList from "./TrackList";

const UserDetails = () => {
    const { username } = useParams();
    const myUsername = Cookies.get("username");
    const { data: user, error, isPending } = useFetch("http://18.222.225.165:8080/get/username/" + username);
    const {data: playlists, isPlaylistsPending, userPlaylistsError} = useFetchUserPlaylists('http://18.222.225.165:8080/get/user/playlists/' + username);
    const {data: tracks, isTracksPending, tracksError} = useFetchUserTracks('http://18.222.225.165:8080/get/user/tracks/' + username);
    const navigate = useNavigate();

    const handleCreatePlaylistOnClick = () => {
        navigate('/create-playlist');
    };

    return (
        <div className="user-details">
            { isPending && <div>Loading...</div> }
            { error && <div>{ error }</div> }
            { user && (
                <article>
                    <h2>{username}</h2>
                </article>
            )}
            <div className='user-playlists'>
                { error && <div>{ error }</div> }
                { isPlaylistsPending && <div>Loading...</div> }
                { playlists && <PlaylistList playlists={playlists} title={"playlists:"}/> }
                {username === myUsername && <button onClick={handleCreatePlaylistOnClick}>
                    Create a Playlist
                </button>}
            </div>
            <div className='user-tracks'>
                { tracks && <TrackList tracks={tracks} title={"tracks:"}/> }
            </div>
        </div>
    );
}

export default UserDetails;
