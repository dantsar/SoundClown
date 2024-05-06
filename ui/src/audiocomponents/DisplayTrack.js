import { useEffect } from 'react';
import TrackImage from '../TrackImage'
import { Link } from 'react-router-dom';

const DisplayTrack = (props) => {
    const onLoadedMetadata = () => {
        const seconds = props.audioRef.current.duration;
        props.setDuration(seconds);
        props.progressBarRef.current.max = seconds;
    };


    useEffect(() => {
        props.resetError(null);
    }, [props.currentTrack]);


    return (
        <div className='displaytrack'>
            { props.isLoading && <div>Loading...</div> }
            { props.error && <div>Unable to load track</div> }
            { props.audioSrc && <audio 
                ref={props.audioRef} 
                src={props.audioSrc} 
                type="audio/mpeg" 
                onLoadedMetadata={onLoadedMetadata}
            /> }
            {props.currentTrack && (
                <div>
                    <Link to={`/track/${props.currentTrack._track_id}`}>
                        <div>
                            <div>{props.currentTrack._track_name} by {props.currentTrack._artist.username}</div>
                        </div>
                        <div>
                            <TrackImage image_path={props.currentTrack._image_path} />
                        </div>
                    </Link>
                </div>
            )}
        </div>
    );
};
export default DisplayTrack;
