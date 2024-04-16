import { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';

// WHOAMI

const WhoAmI = () => {

    const [user_name, setUsername] = useState(null);

    // fetch('http://localhost:8080/whoami', {
    //     method: 'GET',
    //     headers: { "Accept": "text/plain" },
    // }).then((response) => {
    //     if (!response.ok) {
    //         throw new Error('Network response was not ok ' + response.statusText);
    //     }
    //     console.log("response");
    //     console.log(response);
    //     return response.text();
    // }).then((data) => {
    //     console.log("data");
    //     console.log(data);
    //     setUsername(data.user_name);
    // }).catch((error) => {
    //     console.error('There has been a problem with your fetch operation:', error);
    // });

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

    if (user_name == "null") {
        return (
            <div className="whoAmI">
                <p>Currently Not Logged In!</p>
            </div>
        )
    } else {
        return (
            <div className="whoAmI">
                <p>User: {user_name}</p>
            </div>
        );
    }
}

export default WhoAmI
