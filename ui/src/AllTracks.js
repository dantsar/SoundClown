import TrackList from './TrackList';
import useFetch from './useFetch';
import Cookies from 'js-cookie';
import React from "react";

const AllTracks = () => {
    const username= Cookies.get('username');
    const { data: tracks, tracksIsPending } = useFetch('http://localhost:8080/get/alltracks');

    return (
        <div className="all-tracks">
            {tracks && tracks.length > 0 ? (
                <>
                    <h2 style={{marginTop: '10px', marginBottom: '10px'}}>All Tracks!</h2>
                    <TrackList tracks={tracks}/>
                </>
            ) : (
                <>
                    <h2 style={{marginTop: '10px', marginBottom: '10px'}}>All Tracks!</h2>
                    <p style={{marginTop: '10px', marginBottom: '10px', fontWeight: 'bold'}}>No Tracks :(</p>
                </>
            )}
        </div>
    );
}

export default AllTracks;