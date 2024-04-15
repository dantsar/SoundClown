import { NavLink } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="links">
                <div className="navbar-left">
                    <NavLink to="/" style={{
                        backgroundColor: "black",
                    }}>SoundClown</NavLink>
                    <NavLink to="/">Home</NavLink>
                </div>
                <div className="navbar-right">
                    <NavLink to="/upload">Upload</NavLink>
                    <NavLink to="/login">Login</NavLink>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;
