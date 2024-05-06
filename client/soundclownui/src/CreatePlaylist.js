import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

const CreatePlaylist = () => {
    const username= Cookies.get("username");
    const [errorMsg, setErrorMsg] = useState("");
    const [playlist_name, setPlaylistName] = useState('');
    const [description, setDescription] = useState('');
    const [error, setError] = useState(null);
    const [isPending, setIsPending] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (username == null) {
            setErrorMsg("Not signed in!");
        } else {
            setErrorMsg(null);
        }
    }, []);


    const handleSubmit = (e) => {
        e.preventDefault();
        const playlist = {
            playlist_name,
            description,
            username,
        };
        setIsPending(true);
        if (username == null) {
            if (username == null) {
                setIsPending(false);
                setError("Not signed in!");
                return; // Prevent further execution of the function
            }
        }
        fetch('http://localhost:8080/create/playlist', {
            method: 'POST',
            headers: { "dataType": "text" },
            body: JSON.stringify(playlist)
        })
        .then(res => {
            if (!res.ok) {
                throw Error("could not fetch the data for that resource");
            }
            setIsPending(false);
            setError(null);
            navigate('/')
        })
        .catch(err => {
            if (err.name === 'AbortError') {
                //fetch aborted
            } else {
                setIsPending(false);
                setError(err.message);
            }
        })
    };

    return  (
        <div className='create-playlist'>
            <h1>Create a Playlist</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="playlistname">
                    Playlist Name:
                </label>
                <input type="text"
                       id="playlistname"
                       name="playlistname"
                       placeholder="Enter the playlist name"
                       required
                       value={playlist_name}
                       onChange={(e) => setPlaylistName(e.target.value)}
                />

                <label htmlFor="description">
                    Description:
                </label>
                <textarea
                    id="description"
                    name="description"
                    placeholder="Enter a description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                >
                </textarea>
                <button>Create Playlist</button>
            </form>
        </div>
    );
};
export default CreatePlaylist;
