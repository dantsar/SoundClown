import { Link } from 'react-router-dom';
import User from './User';
import TrackImage from './TrackImage';

const RecTrackList = ({recTracks, title}) => {
    return (
        <div className='track-wrapper'>
            <h2> {title} </h2>
            <div className="track-list">
                {recTracks.map((recTrack) => (
                    <div className="track-preview" key={recTrack._track_id}>
                        <Link to ={`/track/${recTrack._track_id}`}>
                            <h2>{recTrack._track_name }</h2>
                            <div>
                                Uploaded by <User user_id={recTrack._artist_id} />
                            </div>
                            {recTrack._image_path && <TrackImage image_path={recTrack._image_path} />}
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default RecTrackList;
