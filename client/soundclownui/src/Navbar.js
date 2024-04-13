const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="links">
                <a style={{
                    backgroundColor: "black",
                }}>SoundClown</a>
                <a href="/" style={{
                    backgroundColor: "#232323",
                }}>Home</a>
                <a href="/create-user">Create User</a>
            </div>
        </nav>
    );
}

export default Navbar;
