import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { App, About } from './App';
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import LoginPage from './pages/LoginPage';
import CreateUserPage from './pages/CreateUserPage';

import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="/about" element={<About />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/create-user" element={<CreateUserPage />} />
        </Routes>
    </BrowserRouter>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
