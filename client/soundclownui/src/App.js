import Navbar from './Navbar';
import Home from './Home';
import CreateUser from './CreateUser';
import UserDetails from './UserDetails';
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
                            path="create-user"
                            element={<CreateUser />}
                        />
                        <Route
                            path="user/:user_id"
                            element={<UserDetails />}
                        />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
