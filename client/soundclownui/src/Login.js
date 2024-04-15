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
            body: JSON.stringify(user)
        }).then(() => {
            setIsPending(false);
            navigate("/");
        });
    }

    return (
        <div className="login">
            <h1>SoundClown</h1>
            <h3>Enter your login credentials</h3>
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
            <p>Not registered?
                <NavLink to="/create-user"  style={{
                    textDecoration: "none",
                }}>
                    Create an Account
                </NavLink>
            </p>
        </div>
    );
}

export default Login;
