import { useEffect } from 'react';

const DisplayTrack = (props) => {
    useEffect(() => {
        const audioElement = props.audioRef.current;

        const handlePlay = () => {
            props.setIsPlaying(true);
        };

        const handlePause = () => {
            props.setIsPlaying(false);
        };

        const handleError = () => {
            props.setIsPlaying(false);
            props.resetError();
        };

        if (audioElement) {
            audioElement.addEventListener('play',handlePlay);
            audioElement.addEventListener('pause',handlePause);
            audioElement.addEventListener('error',handleError);

            return () => {
            audioElement.removeEventListener('play',handlePlay);
            audioElement.removeEventListener('pause',handlePause);
            audioElement.removeEventListener('error',handleError);
            };
        }
    }, [props.audioRef, props.audioSrc]);

    useEffect(() => {
        props.resetError(null);
    }, [props.currentTrack]);


    return (
        <div>
            { props.isLoading && <div>Loading...</div> }
            { props.error && <div>Unable to load track</div> }
            { props.audioSrc && <audio ref={props.audioRef} src={props.audioSrc} type="audio/mpeg" /> }
            { props.currentTrack && <div>{props.currentTrack._track_name}</div> }
        </div>
    );
};
export default DisplayTrack;
