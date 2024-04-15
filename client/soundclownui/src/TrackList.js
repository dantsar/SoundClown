import { Link } from 'react-router-dom';
import User from './User';

const TrackList = ({tracks, title}) => {
    return (
        <div className='track-wrapper'>
            <h2> {title} </h2>
            <div className="track-list">
                {tracks.map((track) => (
                    <div className="track-preview" key={track._track_id}>
<<<<<<< HEAD
                        <Link to ={`track/${track._track_id}`}>
                            <p>{ track._track_name }</p>
                            <User user_id={track._artist_id} />
=======
                        <Link to ={`/track/${track._track_id}`}>
                            <h2>{ track._track_name }</h2>
                            <div>
                                Uploaded by <User user_id={track._artist_id} />
                            </div>
>>>>>>> fred13kim/dev
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default TrackList;
