import UserList from './UserList';
import TrackList from './TrackList';
import useFetchUserTracks from './useFetchUserTracks';
import Cookies from 'js-cookie';
import {useState} from "react";

const MyTracks = () => {
    const username = Cookies.get("username");
    const { data: tracks, tracksIsPending, error } = useFetchUserTracks('http://localhost:8080/get/user/tracks');
    const [track, setTrack] = useState('');
    const [deleteStatus, setDeleteStatus] = useState(null);

    const handleDeleteButtonClick = async () => {
        try {
            const response = await fetch('http://localhost:8080/delete/track/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    user_name:username,
                    track_name:track,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to delete');
            }
            setDeleteStatus('Track deleted successfully');
        } catch (error) {
            console.error('Error:', error);
            setDeleteStatus('Failed to delete track');
        }
        // There has to be a better way than reloading
        //window.location.reload();
    };

    return (
        <div className="home">
            {tracksIsPending && <div>Loading...</div>}
            {/* users && <UserList users={users} title="All Users!"/>  */}
            {tracks && <TrackList tracks={tracks} title="My Tracks!"/>}
            <div className="delete-track-container">
                <div className="delete-input">
                    <input
                        type="text"
                        value={track}
                        onChange={(e) => setTrack(e.target.value)}
                        placeholder="Enter the track to delete"
                    />
                    <button onClick={handleDeleteButtonClick}>Delete Track</button>
                </div>
                {deleteStatus && (
                    <div className={`delete-status ${deleteStatus.startsWith('Failed') ? 'error' : 'success'}`}>
                        {deleteStatus}
                    </div>
                )}
            </div>
        </div>
    );
}

export default MyTracks;
