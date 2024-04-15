import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const WhoAmI = () => {

    const [user_name, setUsername] = useState(null);

    fetch('http://localhost:8080/whoami', {
        method: 'GET',
        headers: { "Accept": "text/plain" },
    }).then((response) => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        console.log("response");
        console.log(response);
        return response.json();
    }).then((data) => {
        console.log("data");
        console.log(data);
        setUsername(data.user_name);
    }).catch((error) => {
        console.error('There has been a problem with your fetch operation:', error);
    });

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
