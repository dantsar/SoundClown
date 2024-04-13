import { NavLink } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="links">
                <NavLink to="/" style={{
                    backgroundColor: "black",
                }}>SoundClown</NavLink>
                <NavLink to="/">Home</NavLink>
                <NavLink to="/login">Login</NavLink>
            </div>
        </nav>
    );
}

export default Navbar;
