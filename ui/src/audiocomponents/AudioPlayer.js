import { useState } from 'react';

import DisplayTrack from './DisplayTrack'
import Controls from './Controls'
import ProgressBar from './ProgressBar'

const AudioPlayer = (props) => {
    return (
        <div className="audioplayer">
            <div className="inner">
                <DisplayTrack 
                    currentTrack={props.currentTrack} 
                    audioRef={props.audioRef}
                    setIsPlaying={props.setIsPlaying}
                />
                <Controls
                    audioRef={props.audioRef}
                    isPlaying={props.isPlaying}
                    setIsPlaying={props.setIsPlaying}
                />
                <ProgressBar />
            </div>
        </div>
    )
}

export default AudioPlayer;
