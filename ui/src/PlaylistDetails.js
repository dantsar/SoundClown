import useFetch from './useFetch';
import TrackList from './TrackList';
import { useParams } from "react-router-dom";
import User from './User';
import Cookies from "js-cookie";
import {useEffect, useState} from "react";
import handleAddTrack from './handleAddTrack';

const PlaylistDetails = () => {
    const username = Cookies.get("username");
    const { playlist_id } = useParams();
    const [errorMsg, setErrorMsg] = useState("");
    const { data: playlist, error, isPending } = useFetch("http://localhost:8080/get/user/playlist/" + playlist_id);

     return (
        <div className='playlist-details'>
            {isPending && <div>Loading...</div>}
            {error && <div>{error}</div>}
            {playlist && (
                <>
                    <div>
                        <div>
                            <h1> {playlist._playlist_name} </h1>
                            <p> Created by {playlist._user.username} </p>
                            <p> Description: {playlist._description} </p>
                        </div>
                        <TrackList tracks={playlist._tracks} title="" />
                    </div>
                </>
            )}
        </div>
    );
};
export default PlaylistDetails;
