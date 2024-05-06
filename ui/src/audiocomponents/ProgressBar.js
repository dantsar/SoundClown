import '../styles/progress-bar.css';

const ProgressBar = (props) => {
    const handleProgressChange = () => {
        if (props.isMouseDown) {
            props.setSliderValue(props.progressBarRef.current.value);
        }
    };

    const handleMouseDown = () => {
        props.setIsMouseDown(true);
    };

    const handleMouseUp = (event) => {
        props.audioRef.current.currentTime = event.target.value;
        props.setIsMouseDown(false);
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
                onChange={handleProgressChange}
                onMouseDown={handleMouseDown}
                onMouseUp={handleMouseUp}
            />
            <span className="time">{formatTime(props.duration)}</span>
        </div>
    );
};

export default ProgressBar;
