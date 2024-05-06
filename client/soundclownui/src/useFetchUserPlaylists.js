// this is exactly the same as useFetchUserTracks but incase anything bad happens im going to separate it

import { useState, useEffect } from 'react';

const useFetch = (url) => {
    const [data, setData] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const abortCont = new AbortController();
        fetch(url, {
            method: 'GET',
            'Content-Type': 'application/json',
            credentials: 'include', // Include credentials for cookie support
            signal: abortCont.signal,
        })
            .then(res => {
                if(!res.ok) {
                    throw Error("could not fetch the data for that resource");
                }
                return res.json();
            })
            .then(data => {
                setData(data);
                setIsPending(false);
                setError(null);
            })
            .catch(err => {
                if (err.name === 'AbortError') {
                    //fetch aborted
                } else {
                    setIsPending(false);
                    setError(err.message);
                }
            });

        return  () => abortCont.abort();
    }, [url]);

    return { data, isPending, error }
}

export default useFetch
