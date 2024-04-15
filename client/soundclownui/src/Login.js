import { NavLink } from "react-router-dom";

const Login = () => {
    return (
        <div className="login">
            <h1>Welcome to SoundClown!</h1>
            <h2>Enter your login credentials</h2>
            <form>
                <label htmlFor="username">
                    Username:
                </label>
                <input type="username"
                    id="username"
                    name="username"
                    placeholder="Enter your Username"
                    required
                />

                <label htmlFor="password">
                    Password:
                </label>
                <input type="password"
                    id="password"
                    name="password"
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
