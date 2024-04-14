import { Link } from 'react-router-dom';
import User from './User';

const TrackList = ({tracks, title}) => {
    return (
        <div className="track-list">
            <h2>{ title }</h2>
            {tracks.map((track) => (
                <div className="track-preview" key={track._track_id}>
                    <Link to ={`/track/${track._track_id}`}>
                        <p>Track Name: { track._track_name }</p>
                        <p style={{
                            float: "left",
                        }}>Track Artist:&nbsp;</p> <User user_id={track._artist_id} />
                    </Link>
                </div>
            ))}
        </div>
    );
}

export default TrackList;
