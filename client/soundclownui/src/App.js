import Navbar from './Navbar';
import Home from './Home';
import Login from './Login';
import CreateUser from './CreateUser';
import UserDetails from './UserDetails';
import TrackDetails from './TrackDetails';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
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
                            element={<TrackDetails />}
                        />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
