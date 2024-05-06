import useFetch from './useFetch';
import TrackList from './TrackList';
import { useParams } from "react-router-dom";
import User from './User';
import Cookies from "js-cookie";
import {useEffect, useState} from "react";

const PlaylistDetails = () => {
    const username = Cookies.get("username");
    const { playlist_id } = useParams();
    const [errorMsg, setErrorMsg] = useState("");
    const { data: playlist, error, isPending } = useFetch("http://localhost:8080/get/user/playlist/" + playlist_id);

    const handleAddTrack = async () => {
        if (username == null) {
            return;
        }
        try {
            const response = await fetch('http://localhost:8080/addtrack/playlist', {
                method: 'POST',
                headers: {
                    'dataType': 'text',
                    // Add any additional headers if needed
                },
                credentials: 'include', // Include credentials for cookie support
                body: JSON.stringify({
                    playlist_id: playlist_id,
                    track_id: "42",
                }),
            });

            // Handle the response
            if (response.ok) {
            } else {
                // Handle error response
            }
        } catch (error) {
            // Handle network error or any other errors
            console.error('Error:', error);
        }
        window.location.reload();
    };

     return (
        <div className='playlist-details'>
            {isPending && <div>Loading...</div>}
            {error && <div>{error}</div>}
            {playlist && (
                <>
                    <div>
                        <div>
                            <h1> {playlist._playlist_name} </h1>
                            <p> Created by {playlist._user.username} </p>
                        </div>
                        {username === playlist._user.username && (
                            <button onClick={handleAddTrack}>Add Track</button>
                        )}
                        <TrackList tracks={playlist._tracks} title="" />
                    </div>
                </>
            )}
        </div>
    );
};
export default PlaylistDetails;
