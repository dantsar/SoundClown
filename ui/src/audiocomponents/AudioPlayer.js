import { useState, useEffect, useRef } from 'react';

import DisplayTrack from './DisplayTrack'
import Controls from './Controls'
import ProgressBar from './ProgressBar'
import useFetchAudio from '../useFetchAudio';

const AudioPlayer = (props) => {
    const { audioSrc, isLoading, error, resetError } = useFetchAudio(props.currentTrack._track_path ? 'http://localhost:8080/' + props.currentTrack._track_path : null);
    const audioRef = useRef();

    return (
        <div className="audioplayer">
            <div className="inner">
                <DisplayTrack 
                    currentTrack={props.currentTrack} 
                    audioRef={audioRef}
                    setIsPlaying={props.setIsPlaying}
                    audioSrc={audioSrc}
                    isLoading={isLoading}
                    error={error}
                    resetError={resetError}
                />
                <Controls
                    audioRef={audioRef}
                    audioSrc = {audioSrc}
                    isPlaying={props.isPlaying}
                    setIsPlaying={props.setIsPlaying}
                    currentTrack={props.currentTrack} 
                    setTrack={props.setTrack}
                />
                <ProgressBar />
            </div>
        </div>
    )
}

export default AudioPlayer;
