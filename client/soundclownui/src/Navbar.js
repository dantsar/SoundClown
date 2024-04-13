import { Link } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="links">
                <a style={{
                    backgroundColor: "black",
                }}>SoundClown</a>
                <Link to="/" style={{
                    backgroundColor: "#232323",
                }}>Home</Link>
                <Link to="/create-user">Create User</Link>
            </div>
        </nav>
    );
}

export default Navbar;
