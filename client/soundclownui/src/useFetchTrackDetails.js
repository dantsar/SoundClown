import { useState, useEffect } from 'react';

const useFetchTrackDetails = (url) => {
  const [data, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const abortCont = new AbortController();
    const fetchData = async () => {
      try {
        const res = await fetch(url, { signal: abortCont.signal });
        if (!res.ok) {
          throw Error('Could not fetch the data for that resource');
        }
        const contentType = res.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
          throw new TypeError('Oops, we haven\'t got JSON!');
        }
        const jsonData = await res.json();
        setData(jsonData);
        setError(null);
      } catch (err) {
        if (err.name === 'AbortError') {
          console.log('Fetch aborted');
        } else {
          setError(err.message);
        }
      } finally {
        setIsPending(false);
      }
    };

    fetchData();

    return () => abortCont.abort();
  }, [url]);

  return { data, isPending, error };
};

export default useFetchTrackDetails;
