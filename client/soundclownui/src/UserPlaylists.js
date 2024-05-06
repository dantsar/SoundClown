import PlaylistList from './PlaylistList';
import useFetchUserPlaylists from './useFetchUserPlaylists';
import { useNavigate, useParams } from 'react-router-dom';
import Cookies from 'js-cookie';
import React, {useEffect, useState} from "react";

const UserPlaylists = () => {
    const {username} = useParams()
    const myUsername = Cookies.get("username");
    const {data: playlists, isPlaylistsPending, error} = useFetchUserPlaylists('http://localhost:8080/get/user/playlists/' + username);
    // const [playlist, setPlaylist] = useState('');
    // const [deleteStatus, setDeleteStatus] = useState(null);
    const navigate = useNavigate();

    const handleCreatePlaylistOnClick = () => {
        navigate('/create-playlist');
    };

    return (
        <div className='my-playlists'>
            { error && <div>{ error }</div> }
            { isPlaylistsPending && <div>Loading...</div> }
            { playlists && <PlaylistList playlists={playlists} title={username + "'s playlists!"}/> }
            {username === myUsername && <button onClick={handleCreatePlaylistOnClick}>
                Create a Playlist
            </button>}
        </div>
    );
};
export default UserPlaylists;
