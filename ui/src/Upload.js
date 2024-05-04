import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Upload = () => {
    // these have to correspond to db entries
    const [track_name, setTrackName] = useState('');
    // const [artist_id, setArtistId] = useState('');
    const [isPending, setIsPending] = useState(false);
    const [error, setError] = useState(null);
    const [description, setDescription] = useState('');
    const [selectedFile, setSelectedFile] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const track_path = "download/" + track_name;
        const track = {
            track_name,
            track_path,
            // artist_id,
            description,
        };

        setIsPending(true);

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
                       onChange={(e) => setSelectedFile(e.target.files[0])}
                />

                <button>Submit</button>
            </form>
            {error && <p className="error">{error}</p>}
        </div>
    );
}

export default Upload;
