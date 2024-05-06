import { useEffect, useState, useRef, useCallback } from 'react';

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
    
    const playAnimationRef = useRef();

    const repeat = useCallback(() => {
        const currentTime = props.audioRef.current.currentTime;
        props.setTimeProgress(currentTime);
        props.progressBarRef.current.value = currentTime;
        props.progressBarRef.current.style.setProperty(
            '--range-progress',
            `${(props.progressBarRef.current.value / props.duration) * 100}%`
        );

        playAnimationRef.current = requestAnimationFrame(repeat);
    }, [props.audioRef, props.duration, props.progressBarRef, props.setTimeProgress]);

    useEffect(() => {
        const storedTrack = sessionStorage.getItem('currentTrack');
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
            playAnimationRef.current = requestAnimationFrame(repeat);
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
    }, [props.isPlaying, repeat]);

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
