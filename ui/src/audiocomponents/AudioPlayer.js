import { useState } from 'react';

import DisplayTrack from './DisplayTrack'
import Controls from './Controls'
import ProgressBar from './ProgressBar'

const AudioPlayer = (props) => {
    return (
        <div className="audioplayer">
            <div className="inner">
                <DisplayTrack currentTrack={props.currentTrack} audioRef={props.audioRef}/>
                <Controls audioRef={props.audioRef}/>
                <ProgressBar />
            </div>
        </div>
    )
}

export default AudioPlayer;
