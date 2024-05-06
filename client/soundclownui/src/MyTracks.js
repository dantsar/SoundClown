import TrackList from './TrackList';
import useFetchUserTracks from './useFetchUserTracks';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import React, {useEffect, useState} from "react";

const MyTracks = () => {
    const username = Cookies.get("username");
    const {data: tracks, tracksIsPending, error} = useFetchUserTracks('http://localhost:8080/get/user/tracks/' + username);
    const [track, setTrack] = useState('');
    const [deleteStatus, setDeleteStatus] = useState(null);
    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        if (username == null) {
            setErrorMsg("Not signed in!");
        } else {
            setErrorMsg(null);
        }
    }, []);
    const handleDeleteButtonClick = async () => {
        try {
            const response = await fetch('http://localhost:8080/delete/track/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    user_name: username,
                    track_name: track,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to delete');
            }
            setDeleteStatus('Track deleted successfully');
            window.location.reload();
        } catch (error) {
            console.error('Error:', error);
            setDeleteStatus("Can't delete another user's track!");
        }
        // There has to be a better way than reloading
    };

    const handleUploadClick = () => {
        navigate('/upload'); // Navigate to the upload page when clicked
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleDeleteButtonClick();
        }
    };

    return (
        <div className="my-tracks">
            {errorMsg ? (
                <>
                    <p className="error" style={{ fontWeight: 'bold' }}>{errorMsg}</p>
                    <button onClick={() => navigate('/login')}
                            style={{
                                        backgroundColor: '#ff5500',
                                        border: 'none',
                                        color: 'white',
                                        padding: '5px 10px',
                                        textAlign: 'center',
                                        textDecoration: 'none',
                                        display: 'inline-block',
                                        fontSize: '16px',
                                        borderRadius: '4px',
                                        marginTop: '10px'
                                    }}>Login</button>
                </>
            ) : (
                tracks && tracks.length > 0 ? (
                    <>
                        {tracks && <TrackList tracks={tracks} title="My Tracks!"/>}
                        <div className="delete-track-container">
                            <div className="delete-input">
                                <input
                                    type="text"
                                    value={track}
                                    onChange={(e) => setTrack(e.target.value)}
                                    placeholder="Enter the track to delete"
                                    onKeyPress={handleKeyPress}
                                />
                                <button onClick={handleDeleteButtonClick}>Delete Track</button>
                            </div>
                            {deleteStatus && (
                                <div className={`delete-status ${deleteStatus.startsWith('Failed') ? 'error' : 'success'}`}>
                                    {deleteStatus}
                                </div>
                            )}
                        </div>
                    </>
                ) : (
                    <>
                        <h2 style={{marginTop: '10px', marginBottom: '10px'}}>My Tracks!</h2>
                        <div style={{marginTop: '10px', marginBottom: '10px'}}>
                            <button
                                onClick={handleUploadClick}
                                style={{
                                    backgroundColor: '#ff5500',
                                    border: 'none',
                                    color: 'white',
                                    padding: '5px 10px',
                                    textAlign: 'center',
                                    textDecoration: 'none',
                                    display: 'inline-block',
                                    fontSize: '16px',
                                    borderRadius: '4px',
                                }}
                            >
                                Upload
                            </button>
                        </div>
                    </>
                )
            )}
        </div>
    );

}

export default MyTracks;
