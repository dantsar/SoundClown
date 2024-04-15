import { useState } from 'react';
import { NavLink, useNavigate} from "react-router-dom";

const Login = () => {

    const [user_name, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isPending, setIsPending] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = { user_name, password };

        console.log(user);

        setIsPending(true);

        fetch('http://localhost:8080/login', {
            method: 'POST',
            'Content-Type': 'application/json',
            credentials: 'include', // Include credentials for cookie support
            body: JSON.stringify(user)
        }).then(() => {
            setIsPending(false);
            navigate("/");
        });
    }

    return (
        <div className="login">
            <h1>Welcome to SoundClown!</h1>
            <h2>Enter your login credentials</h2>
            <form>
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
            <p>Not registered? </p>
            <nav className="register-link">
                <NavLink to="/create-user">
                    Create an Account
                </NavLink>
            </nav>
        </div>
    );
}

export default Login;
