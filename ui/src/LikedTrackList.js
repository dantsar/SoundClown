import { Link } from 'react-router-dom';
import User from './User';

const LikedTrackList = ({ likedTracks, title }) => {
    return (
        <div className='track-wrapper'>
            <h2>{title}</h2>
            <div className="track-list">
                {likedTracks.map((likedTrack) => (
                    <div className="track-preview" key={likedTrack._like_id}>
                        <Link to={`/track/${likedTrack._track._track_id}`}>
                            <h2>{likedTrack._track._track_name}</h2>
                            <div>
                                Uploaded by <User user_id={likedTrack._track._artist_id} />
                            </div>
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default LikedTrackList;
