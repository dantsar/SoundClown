import { useParams } from "react-router-dom";
import useFetch from './useFetch';
import useFetchUserPlaylists from './useFetch';
import handleAddTrack from "./handleAddTrack";
import User from './User';
import Cookies from "js-cookie";
import {useEffect, useState} from "react";
import PopupMessage from './PopupMessage';
import user from "./User";

const TrackDetails = (props) => {
    const username = Cookies.get("username");
    const { track_id } = useParams();
    const [liked, setLiked] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");
    const [selectedPlaylistId, setSelectedPlaylistId] = useState('');
    const [popupMessage, setPopupMessage] = useState(null);
    const [showPopup, setShowPopup] = useState(false);
    const { data: track, error, isPending } = useFetch("http://localhost:8080/get/track/" + track_id);
    const {data: playlists, isPlaylistsPending, playlistError} = useFetchUserPlaylists('http://localhost:8080/get/user/playlists/' + username);

    useEffect(() => {
        if (username == null) {
            return;
        }
        const fetchLikedStatus = async () => {
            try {
                const response = await fetch('http://localhost:8080/get/liked/track/', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        track_id: String(track._track_id),
                        user_name: username
                    }),
                });

                if (response.ok) {
                    setLiked(true);
                } else {
                    console.log("Failed to fetch liked status");
                    setLiked(false);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        };

        if (track) {
            fetchLikedStatus();
        }
    }, [track, username]);

    const handlePlayButtonClick = async () => {
        try {
            const response = await fetch('http://localhost:8080/play/track/' + track_id, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers if needed
                },
                body: JSON.stringify({
                    track_id: track_id,
                    // Add any other data to be sent in the request body
                }),
            });

            // Handle the response
            if (response.ok) {
                props.setTrack(track);
                props.setIsPlaying(true);
            } else {
                // Handle error response
            }
        } catch (error) {
            // Handle network error or any other errors
            console.error('Error:', error);
        }
        //window.location.reload();
    };

    const handleLikeButtonClick = async () => {
        if (username == null) {
            setErrorMsg("Not signed in!");
            return;
        }
        try {
            const response = await fetch('http://localhost:8080/like/track/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers if needed
                },
                body: JSON.stringify({
                    track_id: String(track._track_id),
                    user_name: username
                }),
            });

            // Handle the response
            if (response.ok) {
                console.log("like track request sent")
            } else {
                // Handle error response
                console.log("Failed to send track like")
            }
        } catch (error) {
            // Handle network error or any other errors
            console.error('Error:', error);
        }
        window.location.reload();
    };

    const handleUnlikeButtonClick= async () => {
        if (username == null) {
            setErrorMsg("Not signed in!");
            return;
        }
        try {
            const response = await fetch('http://localhost:8080/unlike/track/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers if needed
                },
                body: JSON.stringify({
                    track_id: String(track._track_id),
                    user_name: username
                }),
            });

            // Handle the response
            if (response.ok) {
                console.log("unlike track request sent")
            } else {
                // Handle error response
                console.log("Failed to send track unlike")
            }
        } catch (error) {
            // Handle network error or any other errors
            console.error('Error:', error);
        }
        window.location.reload();
    };

    const handlePlaylistChange = (event) => {
        setSelectedPlaylistId(event.target.value);
    };

    const handleClosePopup = () => {
        setShowPopup(false);
    }

    const handleAddTrackCallback = (message) => {
        setShowPopup(true);
        setPopupMessage(message);
    }

    const handleAddToPlaylist = () => {
        if (selectedPlaylistId && track && username) {
            console.log(selectedPlaylistId)
            console.log(track._track_id)
            handleAddTrack(selectedPlaylistId,track._track_id, username, handleAddTrackCallback);
        }
    };




    return (
        <div className="track-details-container">
            {isPending && <div>Loading...</div>}
            {error && <div>{error}</div>}
            {track && (
                <article>
                    <h2 className="track-name">Track: {track._track_name}</h2>
                    <div className="artist-details">
                        <p className="artist-label">Artist:&nbsp;</p>
                        <User user_id={track._artist_id} />
                    </div>
                    <p className="track-description">Description: {track._description}</p>
                    <p className="track-plays">Plays: {track._plays}</p>
                    <div className="button-container">
                        <button
                            onClick={handlePlayButtonClick}
                            className="action-button"
                        >
                            Play
                        </button>
                        {!liked ? (
                            <button
                                onClick={handleLikeButtonClick}
                                className="action-button like-button"
                            >
                                Add this track to your likes!
                            </button>
                        ) : (
                                <button
                                    onClick={handleUnlikeButtonClick}
                                    className="action-button unlike-button"
                                >
                                    Remove this track from your likes!
                                </button>
                            )}
                        {playlists && (
                            <div className="select-container">
                                <select onChange={handlePlaylistChange}>
                                    <option value="">Select playlist</option>
                                    {playlists.map(playlist => (
                                        <option key={playlist._playlist_id} value={playlist._playlist_id}>{playlist._playlist_name}</option>
                                    ))}
                                </select>
                                <button onClick={handleAddToPlaylist}>Add to playlist</button>
                            </div>
                        )}
                    </div>
                </article>
            )}
            {errorMsg && <p className="error">{errorMsg}</p>}
            {popupMessage && showPopup && <PopupMessage message={popupMessage} onClose={handleClosePopup}/>}
        </div>
    );
}

export default TrackDetails;
