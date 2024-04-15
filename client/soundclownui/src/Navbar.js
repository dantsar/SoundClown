import { NavLink } from 'react-router-dom';
import logo from "./assets/soundclown.png"

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="links">
                <div className="navbar-left">
                    <NavLink to="/">
                        <img src={logo} className="logo"></img>
                    </NavLink>
                    <NavLink to="/">Home</NavLink>
                    <NavLink to="/users">Users</NavLink>
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
