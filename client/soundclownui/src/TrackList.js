import { Link } from 'react-router-dom';
import User from './User';

const TrackList = ({tracks, title}) => {
    return (
        <div className='track-wrapper'>
            <div className="track-list">
                {tracks.map((track) => (
                    <div className="track-preview" key={track._track_id}>
                        <Link to ={`/track/${track._track_id}`}>
                            <p>{ track._track_name }</p>
                            <User user_id={track._artist_id} />
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default TrackList;
