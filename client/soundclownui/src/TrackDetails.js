import { useParams } from "react-router-dom";
import useFetch from './useFetch';
import User from './User';

const TrackDetails = (props) => {
    const { track_id } = useParams();
    const { data: track, error, isPending } = useFetch("http://localhost:8080/get/track/" + track_id);

    console.log("Track Details");
    console.log(track);

    const handlePlayButtonClick = async () => {
        try {
            const response = await fetch('http://localhost:8080/play/track/' + track_id, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers if needed
                },
                body: JSON.stringify({
                    track_id: track_id,
                    // Add any other data to be sent in the request body
                }),
            });

            // Handle the response
            if (response.ok) {
                props.setTrack(track._track_path)
            } else {
                // Handle error response
            }
        } catch (error) {
            // Handle network error or any other errors
            console.error('Error:', error);
        }
    };

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
                    <User user_id={track._artist_id}/>
                    <p>Description: {track._description}</p>
                    <p>Plays: {track._plays}</p>
                    <button onClick={handlePlayButtonClick}>Play</button>
                </article>
            )}
        </div>
    );
}

export default TrackDetails;
