import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

const CreateUser = () => {
    const [user_name, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isPending, setIsPending] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        console.log("Handle submit?")
        event.preventDefault();
        const user = { user_name, password };
        const response = await fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: { "dataType": "text" },
            credentials: 'include', // Include credentials for cookie support
            body: JSON.stringify(user)
        });

        if (!response.ok) {
            console.log("ERROR?")
            setError("true");
        } else {
            // Assuming successful registration, set the cookie
            Cookies.set('username', user_name);

            // Proceed with login
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
    }

    return (
        <div className="createuser">
            <h1>Create a New SoundClown User</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username:
                </label>
                <input type="text"
                       id="username"
                       name="username"
                       placeholder="Enter your Username"
                       required
                       value={user_name}
                       onChange={(e) => setUsername(e.target.value)}
                />

                <label htmlFor="password">
                    Password:
                </label>
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter your Password"
                       required
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
                />

                <button> Submit </button>
            </form>
            {error && <p>User already exists!</p>}
        </div>
    );

    /*
    return (
        <div className="createuser">
            <h1>Create a New SoundClown User</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username:
                </label>
                <input type="text"
                       id="username"
                       name="username"
                       placeholder="Enter your Username"
                       required
                       value={user_name}
                       onChange={(e) => setUsername(e.target.value)}
                />

                <label htmlFor="password">
                    Password:
                </label>
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter your Password"
                       required
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
                />

                <button> Submit </button>
            </form>
            {error && <p style={{ color: 'red' }}>User already exists!</p>}
        </div>
    );

     */
}

export default CreateUser;
