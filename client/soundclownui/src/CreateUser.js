import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const CreateUser = () => {
    const [user_name, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isPending, setIsPending] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = { user_name, password };

        setIsPending(true);

        fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: { "dataType": "text" },
            body: JSON.stringify(user)
        }).then(() => {
            setIsPending(false);
            navigate("/login");
        });
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
        </div>
    );
}

export default CreateUser
