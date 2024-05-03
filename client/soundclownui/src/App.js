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
import AudioPlayer from './audiocomponents/AudioPlayer';
import { BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import { useRef, useState, useCallback } from 'react';
// My imports
import Cookies from 'js-cookie';
import User from './User.js';

function App() {
    const [currentTrack, setCurrentTrack] = useState('');
    const audioRef = useRef(null);

    const userCookie = Cookies.get(User);
    console.log(userCookie);

    // const setTrack = (path) => {
    //     setCurrentTrack(path);
    //     if(audioRef.current) {
    //         audioRef.current.play();
    //     }
    //     console.log(path);
    // };

    const setTrack = useCallback((path) => {
        setCurrentTrack(path);
        if(audioRef.current) {
            audioRef.current.play();
        }
    });

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
                            path="my-tracks"
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
                            element={<TrackDetails setTrack={setTrack}/>}
                        />
                        <Route
                            path="whoami"
                            element={<WhoAmI />}
                        />
                    </Routes>
                </div>
                <AudioPlayer currentTrack={currentTrack} audioRef={audioRef} />
            </div>
        </Router>
    );
}

export default App;
