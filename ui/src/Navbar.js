import { NavLink } from 'react-router-dom';
import logo from "./assets/soundclown.png"
import Cookies from 'js-cookie';
import User from './User.js';
import user from "./User.js";

const Navbar = () => {
    const username = Cookies.get("username");
    return (
        <nav className="navbar">
            <div className="links">
                <div className="navbar-left">
                    <NavLink to="/">
                        <img src={logo} className="logo"></img>
                    </NavLink>
                    <NavLink to="/">Home</NavLink>
                    <NavLink to="/all-users">All Users</NavLink>
                    <NavLink to="/all-tracks">All Tracks</NavLink>
                </div>
                <div className="navbar-right">
                    <NavLink to="/my-tracks">My Tracks</NavLink>
                    <NavLink to="/upload">Upload</NavLink>
                    {username ? (
                        <NavLink to="/whoami">{username}</NavLink>
                    ) : (
                        <NavLink to="/login">Login</NavLink>
                    )}
                </div>
            </div>
        </nav>
    );
}

export default Navbar;
