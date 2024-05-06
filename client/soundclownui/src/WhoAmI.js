import { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

// WHOAMI

const WhoAmI = () => {
    const username = Cookies.get('username');
    const navigate = useNavigate();

    const handleLoginClick = async () => {
        Cookies.remove("username");
        navigate('/login'); // Navigate to the login page
        window.location.reload();
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
            {username == null ? (
                <>
                    <p>Currently Not Logged In!</p>
                    <button onClick={handleLoginClick}>Login</button>
                </>
            ) : (
                <>
                    <p>User: {username}</p>
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
