import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

const CreatePlaylist = () => {
    const handleSubmit = () => {
    };

    return  (
        <div className='create-playlist'>
            <h1>Create a Playlist</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="playlistname">
                    Playlist Name:
                </label>
            </form>
        </div>
    );
};
export default CreatePlaylist;
