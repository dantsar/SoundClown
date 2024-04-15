import { useEffect } from 'react';
import useFetchAudio from '../useFetchAudio';

const DisplayTrack = (props) => {
    const { audioSrc, isLoading, error, resetError } = useFetchAudio(props.currentTrack ? 'http://localhost:8080/' + props.currentTrack : null);
    useEffect(() => {
        const audioElement = props.audioRef.current;

        const handleCanPlay = () => {
            audioElement.play();
        };

        if (audioElement) {
            audioElement.addEventListener('canplay', handleCanPlay);

            return () => {
                audioElement.removeEventListener('canplay', handleCanPlay);
            };
        }
    }, [props.audioRef, audioSrc]);

    useEffect(() => {
        resetError(null);
    }, [props.currentTrack]);


    return (
        <div>
            { isLoading && <div>Loading...</div> }
            { error && <div>Unable to load track</div> }
            {audioSrc && <audio ref={props.audioRef} src={audioSrc} type="audio/mpeg" controls />}
        </div>
    );
};
export default DisplayTrack;
