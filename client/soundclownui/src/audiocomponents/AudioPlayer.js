import { useState, useEffect, useRef } from 'react';

import DisplayTrack from './DisplayTrack'
import Controls from './Controls'
import ProgressBar from './ProgressBar'
import useFetchAudio from '../useFetchAudio';

const AudioPlayer = (props) => {
    const { audioSrc, isLoading, error, resetError } = useFetchAudio(props.currentTrack._track_path ? 'http://18.222.225.165:8080/' + props.currentTrack._track_path : null);
    const [timeProgress, setTimeProgress] = useState(0);
    const [duration, setDuration] = useState(0);
    const [isMouseDown, setIsMouseDown] = useState(false);
    const [sliderValue, setSliderValue] = useState(0);
    const audioRef = useRef();
    const progressBarRef = useRef();

    return (
        <div className="audioplayer">
            <div className="inner">
                <Controls
                    audioRef={audioRef}
                    audioSrc = {audioSrc}
                    isPlaying={props.isPlaying}
                    setIsPlaying={props.setIsPlaying}
                    currentTrack={props.currentTrack} 
                    setTrack={props.setTrack}
                    resetError={resetError}
                    duration={duration}
                    setTimeProgress={setTimeProgress}
                    progressBarRef={progressBarRef}
                    isMouseDown={isMouseDown}
                    sliderValue={sliderValue}
                />
                <ProgressBar 
                    audioRef={audioRef}
                    duration={duration}
                    timeProgress={timeProgress}
                    progressBarRef={progressBarRef}
                    isMouseDown={isMouseDown}
                    setIsMouseDown={setIsMouseDown}
                    setSliderValue={setSliderValue}
                />
                <DisplayTrack 
                    audioRef={audioRef}
                    audioSrc={audioSrc}
                    setIsPlaying={props.setIsPlaying}
                    currentTrack={props.currentTrack} 
                    isLoading={isLoading}
                    error={error}
                    resetError={resetError}
                    setDuration={setDuration}
                    progressBarRef={progressBarRef}
                />
            </div>
        </div>
    )
}

export default AudioPlayer;
