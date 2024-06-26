const handleAddTrack = async (playlist_id, track_id, username,callback) => {
    if (username == null) {
        return;
    }
    try {
        const response = await fetch('http://18.222.225.165:8080/addtrack/playlist', {
            method: 'POST',
            headers: {
                'dataType': 'text',
                // Add any additional headers if needed
            },
            body: JSON.stringify({
                playlist_id: String(playlist_id),
                track_id: String(track_id),
            }),
        });

        // Handle the response
        if (response.ok) {
            callback('Song added to playlist!');
        } else {
            callback('Error adding song to playlist.');
            // Handle error response
        }
    } catch (error) {
        // Handle network error or any other errors
        console.error('Error:', error);
        callback('Error adding song to playlist.');
    }
    //window.location.reload();

}

export default handleAddTrack;
