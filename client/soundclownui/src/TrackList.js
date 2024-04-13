import { Link } from 'react-router-dom';

const TrackList = ({tracks, title}) => {
    return (
        <div className="track-list">
            <h2>{ title }</h2>
            {tracks.map((track) => (
                <div className="track-preview" key={track._track_id}>
                    <Link to ={`/track/${track._track_id}`}>
                        <p>Track Name: { track._track_name }</p>
                        <p>Track Path: { track._track_path }</p>
                    </Link>
                </div>
            ))}
        </div>
    );
}

export default UserList;
