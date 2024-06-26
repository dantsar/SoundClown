import PlaylistList from './PlaylistList';
import useFetchUserPlaylists from './useFetchUserPlaylists';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import React, {useEffect, useState} from "react";

const MyPlaylists = () => {
    const username = Cookies.get("username");
    const {data: playlists, isPlaylistsPending, error} = useFetchUserPlaylists('http://18.222.225.165:8080/get/user/playlists/' + username);
    // const [playlist, setPlaylist] = useState('');
    // const [deleteStatus, setDeleteStatus] = useState(null);
    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        if (username == null) {
            setErrorMsg("Not signed in!");
        } else {
            setErrorMsg(null);
        }
    }, []);


    const handleCreatePlaylistOnClick = () => {
        navigate('/create-playlist');
    };

    return (
        <div className='my-playlists'>
            { isPlaylistsPending && <div>Loading...</div> }
            { playlists && <PlaylistList playlists={playlists} title="My Playlists!"/> }
            <button onClick={handleCreatePlaylistOnClick}>
                Create a Playlist
            </button>
        </div>
    );
};
export default MyPlaylists;
