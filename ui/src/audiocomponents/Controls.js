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
        if (props.audioRef.current) {
            if (props.isPlaying) {
                props.audioRef.current.play();
            } else {
                props.audioRef.current.pause();
            }
        }
    }, [props.isPlaying, props.audioRef]);

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
