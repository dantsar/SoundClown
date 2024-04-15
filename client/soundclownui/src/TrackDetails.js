import { useParams } from "react-router-dom";
import useFetch from './useFetch';
import User from './User';

const TrackDetails = (props) => {
    const { track_id } = useParams();
    const { data: track, error, isPending } = useFetch("http://localhost:8080/get/track/" + track_id);

    console.log("Track Details");
    console.log(track);

    return (
        <div className="track-details">
            { isPending && <div>Loading...</div> }
            { error && <div>{ error }</div> }
            { track &&(
                <article>
                    <h2>Track: {track._track_name}</h2>
                    <p style={{
                        float: "left",
                    }}>Artist:&nbsp;</p>
                    <User user_id={track._artist_id} />
                    <p>Description: {track._description}</p>
                    <button onClick={() => props.setTrack(track._track_path)}>Play</button>
                </article>
            )}
        </div>
    );
}

export default TrackDetails;
