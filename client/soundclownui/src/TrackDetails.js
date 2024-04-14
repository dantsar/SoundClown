import { useParams } from "react-router-dom";
import useFetch from './useFetch';
import User from './User';

const TrackDetails = () => {
    const { track_id } = useParams();
    const { data: track, error, isPending } = useFetch("http://localhost:8080/get/track/" + track_id);

    return (
        <div className="track-details">
            { isPending && <div>Loading...</div> }
            { error && <div>{ error }</div> }
            { track &&(
                <article>
                    <h2>{track._track_name}</h2>
                    <p style={{
                        float: "left",
                    }}>Artist:&nbsp;</p>
                    <User user_id={track._artist_id} />
                </article>
            )}
        </div>
    );
}

export default TrackDetails;
