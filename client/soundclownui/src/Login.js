import { useState } from 'react';
import { NavLink, useNavigate} from "react-router-dom";
import Cookies from "js-cookie";

const Login = () => {
    const userCookie = Cookies.get("username");
    const [user_name, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isPending, setIsPending] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const user = {user_name, password};
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            'Content-Type': 'application/json',
            body: JSON.stringify(user)
        });

        if (!response.ok) {
             setError("true");
        } else {
            console.log("Setting cookie")
            Cookies.set('username', user_name);
            navigate("/");
        }
        window.location.reload();
    }

    return (
        <div className="login">
            <h1>Welcome to SoundClown!</h1>
            <h2>Enter your login credentials</h2>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username:
                </label>
                <input
                    type="text"
                    id="user_name"
                    value={user_name}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter your Username"
                    required
                />

                <label htmlFor="password">
                    Password:
                </label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your Password"
                    required
                />
                <button> Submit </button>
            </form>
            {error && <p style={{color: 'red'}}>Incorrect username or password</p>}
            <p>Not registered?</p>
            <nav className="register-link">
                <NavLink to="/create-user">
                    <p>Create an Account</p>
                </NavLink>
            </nav>
        </div>
    );
}

export default Login;
