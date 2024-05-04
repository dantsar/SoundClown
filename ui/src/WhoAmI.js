import { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';

// WHOAMI

const WhoAmI = () => {

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
        navigate('/login'); // Navigate to the login page
    };

    /*
    if (user_name == "null") {
        return (
            <div className="whoAmI">
                <p>Currently Not Logged In!</p>
                <button onClick={handleLoginClick}>Login</button>
            </div>
        )
    } else {
        return (
            <div className="whoAmI">
                <p>User: {user_name}</p>
                <p>Want to login to another account?</p>
                <button onClick={handleLoginClick}>Login</button>
            </div>
        );
    }

     */
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
                    <button onClick={handleLoginClick}>Login</button>
                </>
            )}
        </div>
    );

}

export default WhoAmI
