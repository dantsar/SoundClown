import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

const Upload = () => {
    const username= Cookies.get("username");
    // these have to correspond to db entries
    const [track_name, setTrackName] = useState('');
    // const [artist_id, setArtistId] = useState('');
    const [isPending, setIsPending] = useState(false);
    const [error, setError] = useState(null);
    const [description, setDescription] = useState('');
    const [selectedFile, setSelectedFile] = useState('');
    const [selectedImageFile, setSelectedImageFile] = useState('');
    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState("");


    useEffect(() => {
        if (username == null) {
            setErrorMsg("Not signed in!");
        } else {
            setErrorMsg(null);
        }
    }, []);
    const handleSubmit = (e) => {
        e.preventDefault();
        const track_path = "download/" + track_name;
        const track = {
            track_name,
            track_path,
            // artist_id,
            description,
            username,
        };

        setIsPending(true);

        if (username == null) {
            if (username == null) {
                setIsPending(false);
                setError("Not signed in!");
                return; // Prevent further execution of the function
            }
        }
        fetch('http://localhost:8080/create/track', {
            method: 'POST',
            headers: { "dataType": "text" },
            credentials: 'include', // Include credentials for cookie support
            body: JSON.stringify(track)
        })
        .then(res => {
            if(!res.ok) {
                throw Error("could not fetch the data for that resource");
            }
            return res.json();
        })
        .then(track_id => {
            console.log(track_id);
            setIsPending(false);
            setError(null);

            const file = new FormData();
            file.append('file',selectedFile);
            setIsPending(true);
            fetch('http://localhost:8080/upload/' + track_id, {
                method: 'POST',
                body: file
            })
            .then(res => {
                if(!res.ok) {
                    throw Error("could not fetch the data for that resource");
                }
            })
            .catch(err => {
                // make sure to delete the table entry if the uploading data has failed
                if (err.name === 'AbortError') {
                    //fetch aborted
                } else {
                    setIsPending(false);
                    setError(err.message);

                    track_id && fetch('http://localhost:8080/delete/track/' + track_id, {
                        method: 'POST'
                    })
                    return;
                }
            })

            const image_file = new FormData();
            image_file.append('image_file', selectedImageFile);
            console.log(image_file)
            setIsPending(true);
            fetch('http://localhost:8080/upload-image/' + track_id, {
                method: 'POST',
                body: image_file
            })
            .then(res => {
                if(!res.ok) {
                    throw Error("could not fetch the data for that resource");
                }
            })
            .catch(err => {
                // make sure to delete the table entry if the uploading data has failed
                if (err.name === 'AbortError') {
                    //fetch aborted
                } else {
                    setIsPending(false);
                    setError(err.message);

                    track_id && fetch('http://localhost:8080/delete/track/' + track_id, {
                        method: 'POST'
                    })
                    return;
                }
            })


            if (track_id < 0) {
                if (track_id === -1) {
                    setError("Track already exists");
                } else {
                    setError("Not signed in!")
                }
                console.log(error);
            } else {
                navigate("/");
            }
        })
    }

    return (
        <div className='upload'>
            {errorMsg ? (
                    <>
                        <p className="error">{errorMsg}</p>
                        <button onClick={() => navigate('/login')}
                                style={{
                                    backgroundColor: '#ff5500',
                                    border: 'none',
                                    color: 'white',
                                    padding: '5px 10px',
                                    textAlign: 'center',
                                    textDecoration: 'none',
                                    display: 'inline-block',
                                    fontSize: '16px',
                                    borderRadius: '4px',
                                    marginTop: '10px'
                                }}>Login</button>
                    </>

            ) : (
                <>
                <h1>Upload a Track</h1>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="trackname">
                        Track Name:
                    </label>
                    <input type="text"
                           id="trackname"
                           name="trackname"
                           placeholder="Enter the track name"
                           required
                           value={track_name}
                           onChange={(e) => setTrackName(e.target.value)}
                    />

                    <label htmlFor="description">
                        Description:
                    </label>
                    <textarea
                        id="description"
                        name="description"
                        placeholder="Enter a description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    >
                    </textarea>

                    <input type="file"
                           id="file"
                           name="file"
                           required
                            accept=".mp3"
                           onChange={(e) => setSelectedFile(e.target.files[0])}
                    />
 
                    <input type="file" 
                            id="image_file"
                            name="image_file"
                            accept=".png"
                            onChange={(e) => setSelectedImageFile(e.target.files[0])}
                    />

                    <button>Submit</button>
                </form>
                {error && <p className="error">{error}</p>}
                </>
            )}
        </div>
    );
}

export default Upload;
