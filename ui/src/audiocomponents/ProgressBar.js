import React, { useState, useEffect } from 'react';
import '../styles/progress-bar.css';

const ProgressBar = (props) => {
    const handleProgressChange = () => {
        props.audioRef.current.currentTime = props.progressBarRef.current.value;
    };

    const formatTime = (time) => {
        if (time && !isNaN(time)) {
            const minutes = Math.floor(time / 60);
            const formatMinutes =
                minutes < 10 ? `0${minutes}` : `${minutes}`;
            const seconds = Math.floor(time % 60);
            const formatSeconds =
                seconds < 10 ? `0${seconds}` : `${seconds}`;
            return `${formatMinutes}:${formatSeconds}`;
        }
        return '00:00';
    };
    return (
        <div className="progress">
            <span className="time current">{formatTime(props.timeProgress)}</span>
            <input
                type="range"
                ref={props.progressBarRef}
                defaultValue="0"
                onChange={handleProgressChange}
            />
            <span className="time">{formatTime(props.duration)}</span>
        </div>
    );
};

export default ProgressBar;
