import Navbar from './Navbar';
import Home from './Home';
import CreateUser from './CreateUser';
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
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
