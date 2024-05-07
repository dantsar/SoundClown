import useFetch from './useFetch';
import TrackList from './TrackList';
import { useNavigate, useParams } from "react-router-dom";
import User from './User';
import Cookies from "js-cookie";
import {useEffect, useState} from "react";
import handleAddTrack from './handleAddTrack';

const PlaylistDetails = () => {
    const username = Cookies.get("username");
    const { playlist_id } = useParams();
    const [errorMsg, setErrorMsg] = useState("");
    const [showConfirmation, setShowConfirmation] = useState(false); // State for showing confirmation dialog
    const { data: playlist, error, isPending } = useFetch("http://18.222.225.165:8080/get/user/playlist/" + playlist_id);
    const navigate = useNavigate();

    const handleDeleteConfirmation = () => {
        // Show confirmation dialog
        setShowConfirmation(true);
    };

    const handleDeletePlaylist = () => {
        // Call delete endpoint
        fetch(`http://18.222.225.165:8080/delete/playlist/${playlist_id}`, {
            method: 'POST',
        })
        .then(response => {
            // Handle success response
            console.log('Playlist deleted successfully');
            navigate("/you/playlists");
            // Optionally, you can redirect or update state after deletion
        })
        .catch(error => {
            // Handle error
            console.error('Error deleting playlist:', error);
        });
    };

    const handleRemoveTrack = (trackId) => {
        fetch('http://18.222.225.165:8080/removetrack/playlist', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                playlist_id: String(playlist_id),
                track_id: String(trackId)
            })
        })
        .then(response => {
            if (response.ok) {
                // Track removed successfully
                console.log('Track removed from playlist');
                window.location.reload();
                // Reset state
            } else {
                // Error handling
                console.error('Failed to remove track from playlist');
            }
        })
        .catch(error => {
            console.error('Error removing track from playlist:', error);
        });
    }

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
                            <p> Description: {playlist._description} </p>
                        </div>

                        <div>
                            {playlist._tracks.length > 0 ? (
                                <>
                                    <TrackList 
                                        tracks={playlist._tracks} 
                                        title="" 
                                        onRemove={(trackId) => handleRemoveTrack(trackId)} 
                                        isEditing={username === playlist._user.username}
                                    />
                                </>
                            ) : (
                                <>
                                    <p>This playlist is so empty (ￗ﹏ￗ ) maybe you should populate it you clown</p>
                                </>
                            )}
                        </div>

                        {username === playlist._user.username && (
                            <>
                                <button onClick={handleDeleteConfirmation}>Delete this playlist</button>
                                {/* Confirmation dialog */}
                                {showConfirmation && (
                                    <div className='confirm'>
                                        <p>Are you sure you want to delete this playlist?</p>
                                        <button onClick={handleDeletePlaylist}>Confirm</button>
                                        <button onClick={() => setShowConfirmation(false)}>Cancel</button>
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </>
            )}
        </div>
    );
};
export default PlaylistDetails;
