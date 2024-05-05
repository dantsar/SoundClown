import { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

// WHOAMI

const WhoAmI = () => {
    const username = Cookies.get('username');
    const [user_name, setUsername] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsername = async () => {
          try {
            const response = await fetch('http://localhost:8080/whoami', {
              method: 'GET',
              headers: { "Accept": "text/plain" }, // Expecting text
              credentials: 'include', // This is important for cookies to be sent and received
            });

            if (!response.ok) {
              throw new Error('Network response was not ok: ' + response.statusText);
            }
            const text = await response.text(); // Get the text response

            setUsername(text); // Set username with text response
          } catch (error) {
            // setError('There has been a problem with your fetch operation: ' + error.message);
            console.error('There has been a problem with your fetch operation:', error);
          }
        };

        fetchUsername();
      }, []); // Empty dependency array ensures this only runs once

    const handleLoginClick = () => {
        Cookies.remove("username");
        navigate('/login'); // Navigate to the login page
    };

    const handleDeleteClick = async () => {
        try {
            const response = await fetch('http://localhost:8080/clear/user/' +username , {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to delete');
            }
            try {
                const response = await fetch('http://localhost:8080/delete/user/' +username , {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (!response.ok) {
                    throw new Error('Failed to delete');
                }
                Cookies.remove("username");
                navigate('/create-user'); // Navigate to the login page
                window.location.reload();
            } catch (error) {
                console.error('Error:', error);
            }
        } catch (error) {
            console.error('Error:', error);
        }
        // There has to be a better way than reloading
    };

    return (
        <div className="whoAmI">
            {user_name === "null" ? (
                <>
                    <p>Currently Not Logged In!</p>
                    <button onClick={handleLoginClick}>Login</button>
                </>
            ) : (
                <>
                    <p>User: {user_name}</p>
                    <p>Want to login to another account?</p>
                    <button onClick={handleLoginClick}>Logout</button>
                    <p>Would you like to delete your account?</p>
                    <button onClick={handleDeleteClick}>Delete</button>
                </>
            )}
        </div>
    );

}

export default WhoAmI
