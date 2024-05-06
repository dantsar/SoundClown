import useFetch from './useFetch';
import TrackList from './TrackList';
import {Link, useParams} from "react-router-dom";
import User from './User';
import Cookies from "js-cookie";
import React, {useEffect, useState} from "react";

const PlaylistDetails = () => {
    const username = Cookies.get("username");
    const { playlist_id } = useParams();
    const [errorMsg, setErrorMsg] = useState("");
    const { data: playlist, error, isPending } = useFetch("http://localhost:8080/get/user/playlist/" + playlist_id);
    const [tracks, setTracks] = useState([]);
    const [trackName, setTrackName] = useState('');
    const [tracksIsPending, setTracksIsPending] = useState(false);

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

    const searchClick = async () => {
        console.log("Sending post");
        try {
            setTracksIsPending(true);
            const response = await fetch('http://localhost:8080/get/tracks/track_name/' + trackName, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers if needed
                },
            });

            // Handle the response
            if (response.ok) {
                const data = await response.json();
                setTracks(data);
            } else {
                // Handle error response
            }
        } catch (error) {
            // Handle network error or any other errors
            console.error('Error:', error);
        } finally {
            setTracksIsPending(false);
        }
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            searchClick();
        }
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
                            <>
                                <div className="Search" style={{marginBottom: '10px'}}>
                                    <h2>Add a Track!</h2>
                                </div>
                                <div className="search-input-container">
                                    <input
                                        type="text"
                                        value={trackName}
                                        onChange={(e) => setTrackName(e.target.value)}
                                        placeholder="Enter track name..."
                                        className="search-input"
                                        onKeyPress={handleKeyPress}
                                    />
                                    <button onClick={searchClick} className="search-button">Search</button>
                                </div>
                                <div>
                                    <div className="track-list">
                                        {tracks.map((track, index) => (
                                            <>
                                            <div className="track-preview" key={`${track._track_id}_${index}`}>
                                                <h2>{track._track_name}</h2>
                                                <div>
                                                    Uploaded by <User user_id={track._artist_id}/>
                                                </div>
                                            </div>
                                            <button onClick={handleAddTrack}>Add Track</button>
                                            </>
                                        ))}
                                    </div>
                                </div>
                            </>
                        )}
                    </div>
                </>
            )}
        </div>
     );
};
export default PlaylistDetails;
