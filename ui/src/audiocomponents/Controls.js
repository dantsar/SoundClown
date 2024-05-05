import { useEffect, useState } from 'react';

// icons
import {
    IoPlayBackSharp,
    IoPlayForwardSharp,
    IoPlaySkipBackSharp,
    IoPlaySkipForwardSharp,
    IoPlaySharp,
    IoPauseSharp,
} from 'react-icons/io5';

const Controls = (props) => {

    const togglePlayPause = () => {
        props.setIsPlaying((prev) => !prev);
    };

    useEffect(() => {
        const storedTrack = sessionStorage.getItem('currentTrack');
        console.log(storedTrack)
        if (storedTrack) {
            const track = JSON.parse(storedTrack);
            props.setTrack(track);
        }
    }, [props.setTrack]);

    useEffect(() => {
        const handleCanPlayThrough = () => {
            if (props.isPlaying) {
                props.audioRef.current.play();
            } else {
                props.audioRef.current.pause();
            }
        };

        if (props.audioSrc && props.audioRef.current) {
            const audioElement = props.audioRef.current;

            audioElement.addEventListener('canplaythrough', handleCanPlayThrough);

            // If audio is already loaded, handle play/pause immediately
            if (audioElement.readyState >= 3) {
                handleCanPlayThrough();
            }
        }

        return () => {
            // Clean up event listener when component unmounts or dependencies change
            if (props.audioRef.current) {
                props.audioRef.current.removeEventListener('canplaythrough', handleCanPlayThrough);
            }
        };
    }, [props.isPlaying]);
    return (
        <div className="controls-wrapper">
            <div className="controls">
                <button>
                    <IoPlaySkipBackSharp />
                </button>
                <button>
                    <IoPlayBackSharp />
                </button>
                <button onClick = {togglePlayPause}>
                    {props.isPlaying ? <IoPauseSharp /> : <IoPlaySharp />}
                </button>
                <button>
                    <IoPlayForwardSharp />
                </button>
                <button>
                    <IoPlaySkipForwardSharp />
                </button>
            </div>
        </div>
    );
};

export default Controls;
