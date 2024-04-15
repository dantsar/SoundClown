import { useEffect } from 'react';
import useFetchAudio from '../useFetchAudio';

const DisplayTrack = (props) => {
    const { audioSrc, isLoading, error } = useFetchAudio('http://localhost:8080/' + props.currentTrack);
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


    return (
        <div>
            {audioSrc && <audio ref={props.audioRef} src={audioSrc} type="audio/mpeg" controls />}
        </div>
    );
};
export default DisplayTrack;
