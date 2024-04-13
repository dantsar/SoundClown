import { NavLink } from "react-router-dom";

const Login = () => {
    return (
        <div className="login">
            <h1>SoundClown</h1>
            <h3>Enter your login credentials</h3>
            <form>
                <label for="username">
                    Username:
                </label>
                <input type="username"
                    id="username"
                    name="username"
                    placeholder="Enter your Username"
                    required
                />

                <label for="password">
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
