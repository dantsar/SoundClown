import { useParams } from "react-router-dom";
import useFetch from './useFetch';
import User from './User';
import Cookies from "js-cookie";
import {useEffect, useState} from "react";

const TrackDetails = (props) => {
    const username = Cookies.get("username");
    const { track_id } = useParams();
    const [liked, setLiked] = useState(false);
    const { data: track, error, isPending } = useFetch("http://localhost:8080/get/track/" + track_id);

    useEffect(() => {
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
                props.setTrack(track._track_path)
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
    return (
        <div className="track-details" style={{ marginTop: '20px', marginBottom: '20px', padding: '20px', border: '1px solid #ff5500', borderRadius: '5px' }}>
            {isPending && <div>Loading...</div>}
            {error && <div>{error}</div>}
            {track && (
                <article>
                    <h2 style={{ marginBottom: '10px', color: '#ff5500' }}>Track: {track._track_name}</h2>
                    <div style={{ marginBottom: '10px',  overflow: 'hidden' }}>
                        <p style={{ float: "left" }}>Artist:&nbsp;</p>
                        <User user_id={track._artist_id} />
                    </div>
                    <p style={{ marginBottom: '10px' }}>Description: {track._description}</p>
                    <p style={{ marginBottom: '10px' }}>Plays: {track._plays}</p>
                    <div className="button-container">
                        <button
                            onClick={handlePlayButtonClick}
                            style={{
                                backgroundColor: '#ff5500',
                                border: 'none',
                                color: 'white',
                                padding: '10px 20px',
                                textAlign: 'center',
                                textDecoration: 'none',
                                display: 'inline-block',
                                fontSize: '16px',
                                borderRadius: '5px',
                                cursor: 'pointer',
                                marginRight: '10px',
                            }}
                        >
                            Play
                        </button>
                        {!liked && (
                            <button
                                className="like-button"
                                onClick={handleLikeButtonClick}
                                style={{
                                    backgroundColor: '#2196F3',
                                    border: 'none',
                                    color: 'white',
                                    padding: '10px 20px',
                                    textAlign: 'center',
                                    textDecoration: 'none',
                                    display: 'inline-block',
                                    fontSize: '16px',
                                    borderRadius: '5px',
                                    cursor: 'pointer',
                                    marginRight: '10px',
                                }}
                            >
                                Add this track to your likes!
                            </button>
                        )}
                        {liked && (
                            <button
                                className="unlike-button"
                                onClick={handleUnlikeButtonClick}
                                style={{
                                    backgroundColor: '#f44336',
                                    border: 'none',
                                    color: 'white',
                                    padding: '10px 20px',
                                    textAlign: 'center',
                                    textDecoration: 'none',
                                    display: 'inline-block',
                                    fontSize: '16px',
                                    borderRadius: '5px',
                                    cursor: 'pointer',
                                    marginRight: '10px',
                                }}
                            >
                                Remove this track from your likes!
                            </button>
                        )}
                    </div>
                </article>
            )}
        </div>
    );
}

export default TrackDetails;
