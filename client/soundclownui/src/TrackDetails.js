import { useParams } from "react-router-dom";
import useFetchTrackDetails from './useFetchTrackDetails';
import useFetch from './useFetch';
import User from './User';

import { useState, useEffect } from 'react';

const TrackDetails = (props) => {
    const { track_id } = useParams();
    const { data: track, error, isPending } = useFetch("http://localhost:8080/get/track/" + track_id);
    // const { data: track, error, isPending } = useFetchTrackDetails(`http://localhost:8080/get/track/${track_id}`);

    useEffect(() => {
      console.log('TrackDetails rendered with track_id:', track_id);
    }, [track_id]);


    console.log(`TrackDetails.js: ${track_id}`);
    console.log(track);

    return (
        <div className="track-details">
            <p> Test </p>
        </div>
    );
}

export default TrackDetails;

// { isPending && <div>Loading...</div> }
// { error && <div>{ error }</div> }
// { track &&(
//     <article>
//         <h2>{track._track_name}</h2>
//         <p style={{
//             float: "left",
//         }}>Artist:&nbsp;</p>
//         <User user_id={track._artist_id} />
//         <p>Description: {track._description}</p>
//         {/* <button onClick={() => props.setTrack(track._track_path)}>Play</button> */}
//     </article>
// )}
