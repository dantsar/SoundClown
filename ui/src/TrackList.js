import { Link } from 'react-router-dom';
import User from './User';

const TrackList = ({tracks, title}) => {
    return (
        <div className='track-wrapper'>
            <h2> {title} </h2>
            <div className="track-list">
                {tracks.map((track, index) => (
                    <div className="track-preview" key={`${track._track_id}_${index}`}>
                        <Link to ={`/track/${track._track_id}`}>
                            <h2>{ track._track_name }</h2>
                            <div>
                                Uploaded by <User user_id={track._artist_id} />
                            </div>
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default TrackList;
