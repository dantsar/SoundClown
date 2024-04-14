import Navbar from './Navbar';
import Home from './Home';
import Login from './Login';
import CreateUser from './CreateUser';
import UserDetails from './UserDetails';
import TrackDetails from './TrackDetails';
import AudioPlayer from './audiocomponents/AudioPlayer';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { useRef, useState } from 'react';

function App() {
    const [currentTrack, setCurrentTrack] = useState('');
    const audioRef = useRef(null);

    const setTrack = (path) => {
        setCurrentTrack(path);
        if(audioRef.current) {
            audioRef.current.play();
        }
        console.log(path);
    };

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
                            path="login"
                            element={<Login />}
                        />
                        <Route
                            path="create-user"
                            element={<CreateUser />}
                        />
                        <Route
                            path="user/:user_id"
                            element={<UserDetails />}
                        />
                        <Route
                            path="track/:track_id"
                            element={<TrackDetails setTrack={setTrack}/>}
                        />
                    </Routes>
                </div>
                <AudioPlayer currentTrack={currentTrack} audioRef={audioRef} />
            </div>
        </Router>
    );
}

export default App;
