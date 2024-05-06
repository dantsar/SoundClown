import TrackList from './TrackList';
import LikedTrackList from './LikedTrackList';
import useFetch from './useFetch';
import Cookies from 'js-cookie';
import React, {useState, useEffect} from "react";
import RecTrackList from "./RecTrackList";

const Home = () => {
    const username= Cookies.get('username');
    const { data: likedTracks, likedTracksIsPending } = useFetch('http://localhost:8080/get/liked/tracks/' + username);
    const { data: recTracks, recTracksIsPending} = useFetch('http://localhost:8080/get/recommended/tracks');
    const [tracks, setTracks] = useState([]);
    const [trackName, setTrackName] = useState('');
    const [tracksIsPending, setTracksIsPending] = useState(false);

    useEffect(() => {
        const searchTracks = async () => {
            console.log("Sending post");
            try {
                setTracksIsPending(true);
                const response = await fetch('http://localhost:8080/get/alltracks', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        // Add any additional headers if needed
                    },
                });
                // Handle the response
                if (response.ok) {
                    const allTracks = await response.json();
                    console.log(allTracks)

                    const filteredTracks = allTracks.filter(track => 
                        track._track_name.toLowerCase().includes(trackName.toLowerCase())
                    );
                    setTracks(filteredTracks);
                } else {
                    // Handle error response
                }
            } catch (error) {
                // Handle network error or any other errors
                console.error('Error:', error);
            } finally {
                setTracksIsPending(false);
            }
        };
        // Only perform search if trackName is not empty
        if (trackName.trim() !== '') {
            searchTracks();
        } else {
            // If trackName is empty, reset tracks
            setTracks([]);
        }
    }, [trackName]);

    return (
        <div className="home">
            <div className="Search" style={{marginBottom: '10px'}}>
                <h2>Search</h2>
            </div>
            <div className="search-input-container">
                <input
                    type="text"
                    value={trackName}
                    onChange={(e) => setTrackName(e.target.value)}
                    placeholder="Enter track name..."
                    className="search-input"
                />
            </div>
            <div className="search-results">
                {tracks.length > 0 && <TrackList tracks={tracks}/>}
            </div>
            {likedTracks && likedTracks.length > 0 ? (
                <>
                    <h2 style={{marginTop: '10px', marginBottom: '10px'}}>Liked Tracks!</h2>
                    <LikedTrackList likedTracks={likedTracks}/>
                </>
            ) : (
                <>
                    <h2 style={{marginTop: '10px', marginBottom: '10px'}}>Liked Tracks!</h2>
                    <p style={{marginTop: '10px', marginBottom: '10px'}}>So empty...</p>
                </>
            )}
            {recTracks && recTracks.length > 0 ? (
                <div className="recommended-tracks" style={{marginBottom: '10px', marginTop: '20px'}}>
                    <h2>Recommended Tracks</h2>
                    <RecTrackList recTracks={recTracks}/>
                </div>
            ) : (
                <>
                    <h2>Recommended Tracks</h2>
                    <p style={{marginTop: '10px', marginBottom: '10px'}}>Need more tracks to make a suggestion.</p>
                </>
            )}
        </div>
    );
}

export default Home;
