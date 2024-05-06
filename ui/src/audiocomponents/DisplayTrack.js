import { useEffect } from 'react';

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
            { props.currentTrack && <div>{props.currentTrack._track_name}</div> }
        </div>
    );
};
export default DisplayTrack;
