import Navbar from './Navbar';
import Home from './Home';
import AllUsers from './AllUsers';
import Login from './Login';
import CreateUser from './CreateUser';
import UserDetails from './UserDetails';
import TrackDetails from './TrackDetails';
import WhoAmI from './WhoAmI';
import Upload from './Upload';
import MyTracks from './MyTracks';
import AllTracks from './AllTracks';
import MyPlaylists from './MyPlaylists';
import CreatePlaylist from './CreatePlaylist';
import PlaylistDetails from './PlaylistDetails';
import AudioPlayer from './audiocomponents/AudioPlayer';
import { BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import { useRef, useState, useCallback } from 'react';
// My imports
import Cookies from 'js-cookie';
import User from './User.js';

function App() {
    const username = Cookies.get("username");
    const [currentTrack, setCurrentTrack] = useState('');
    const [isPlaying, setIsPlaying] = useState(false);

    const setTrack = useCallback((track) => {
        setCurrentTrack(track);
        sessionStorage.setItem('currentTrack', JSON.stringify(track));
    }, [setCurrentTrack]);

    return (
        <Router>
            <div className="App">
                <Navbar />
                <div className="content">
                    <Routes>
                        <Route
                            path="/"
                            element={<Home />}
                        />
                        <Route
                            path="all-users"
                            element={<AllUsers />}
                        />
                        <Route
                            path="all-tracks"
                            element={<AllTracks />}
                        />
                        <Route
                            path="you/tracks"
                            element={<MyTracks />}
                        />
                        <Route
                            path="login"
                            element={<Login />}
                        />
                        <Route
                            path="create-user"
                            element={<CreateUser />}
                        />
                        <Route
                            path="upload"
                            element={<Upload />}
                        />
                        <Route
                            path="user/:user_id"
                            element={<UserDetails />}
                        />
                        <Route
                            path="track/:track_id"
                            element={<TrackDetails setTrack={setTrack} setIsPlaying={setIsPlaying}/>}
                        />
                        <Route
                            path="whoami"
                            element={<WhoAmI />}
                        />
                        <Route
                            path="you/playlists" element={<MyPlaylists />}
                        />
                        <Route
                            path="create-playlist"
                            element={<CreatePlaylist />}
                        />
                        <Route
                            path="playlist/:playlist_id"
                            element={<PlaylistDetails />}
                        />
                    </Routes>
                </div>
                <AudioPlayer 
                    currentTrack={currentTrack} 
                    isPlaying={isPlaying}
                    setIsPlaying={setIsPlaying}
                    setTrack={setTrack}
                />
            </div>
        </Router>
    );
}

export default App;
