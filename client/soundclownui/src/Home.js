import TrackList from './TrackList';
import LikedTrackList from './LikedTrackList';
import useFetch from './useFetch';
import Cookies from 'js-cookie';
import React, {useState} from "react";
import RecTrackList from "./RecTrackList";

const Home = () => {
    const username= Cookies.get('username');
    const { data: likedTracks, likedTracksIsPending } = useFetch('http://localhost:8080/get/liked/tracks/' + username);
    const { data: recTracks, recTracksIsPending} = useFetch('http://localhost:8080/get/recommended/tracks');
    const [tracks, setTracks] = useState([]);
    const [trackName, setTrackName] = useState('');
    const [tracksIsPending, setTracksIsPending] = useState(false);

    const searchClick = async () => {
        console.log("Sending post");
        try {
            setTracksIsPending(true);
            const response = await fetch('http://localhost:8080/get/tracks/track_name/' + trackName, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any additional headers if needed
                },
            });

            // Handle the response
            if (response.ok) {
                const data = await response.json();
                setTracks(data);
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

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            searchClick();
        }
    };

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
                    onKeyPress={handleKeyPress}
                />
                <button onClick={searchClick} className="search-button">Search</button>
            </div>
            <div className="search-results">
                {tracksIsPending && <div>Loading...</div>}
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
