import "./App.css";
import { Link, useNavigate } from "react-router-dom";

import { useState, useEffect } from "react";
import axios from "axios";

function links(text) {
    return (
      <div>
        <nav>
            <Link to="/">Home</Link>
            <Link to="/about">About</Link>
        </nav>
        <h1>{text}</h1>
      </div>
    );
}

function Home() {
   const navigate = useNavigate();
    return (
      <div>
        <nav>
            <Link to="/">Home</Link>
            <Link to="/about">About</Link>
            <button onClick={() => {
                navigate('/create-user')
            }}>Create User</button>
        </nav>
        <h1>Welcome to SoundClown</h1>
      </div>
    );
}

export function About() {
    return links("About SoundClown");
}

export function App() {
    return <Home />;
}
