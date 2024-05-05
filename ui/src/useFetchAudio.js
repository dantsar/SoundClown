import { useState, useEffect } from 'react';

const useFetchAudio = (url) => {
    const [audioSrc, setAudioSrc] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const resetError = () => {
        setError(null)
    };

    useEffect(() => {
        if (!url)
        {
            return;
        }
        const fetchAudio = async () => {
            setIsLoading(true);
            try {
                const response = await fetch(url);

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const blob = await response.blob();
                const audioUrl = URL.createObjectURL(blob);
                setAudioSrc(audioUrl);
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchAudio();
    }, [url]);

    return { audioSrc, isLoading, error, resetError };
};

export default useFetchAudio;
