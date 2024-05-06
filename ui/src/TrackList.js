import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import User from './User';

const TrackList = ({ tracks, title, onRemove, isEditing }) => {
    const [trackToRemove, setTrackToRemove] = useState(null);
    const [showRemoveButtons, setShowRemoveButtons] = useState(Array(tracks.length).fill(false));

    const handleMore = (trackId, index) => {
        // Toggle the visibility of the remove button for the specific track
        setShowRemoveButtons(prevState => {
            const newState = [...prevState];
            newState[index] = !newState[index];
            return newState;
        });
        // Set the track to remove
        setTrackToRemove(trackId);
    };

    const handleRemove = () => {
        // Call the onRemove function with the track to remove
        onRemove(trackToRemove);
        // Reset state
        setTrackToRemove(null);
        setShowRemoveButtons(Array(tracks.length).fill(false));
    };

    return (
        <div className='track-wrapper'>
            <h2> {title} </h2>
            <div className="track-list">
                {tracks.map((track, index) => (
                    <div className="track-preview" key={`${track._track_id}_${index}`}>
                        <Link to={`/track/${track._track_id}`}>
                            <h2>{track._track_name}</h2>
                            <div>
                                Uploaded by <User user_id={track._artist_id} />
                            </div>
                        </Link>
                        {isEditing && (
                            <div className="action-buttons">
                                <button onClick={() => handleMore(track._track_id, index)}>...</button>
                                {showRemoveButtons[index] && trackToRemove === track._track_id && (
                                    <div className="confirmation-dialog">
                                        <button onClick={handleRemove}>Remove from this playlist</button>
                                    </div>
                                )}
                            </div>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default TrackList;
