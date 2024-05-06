import { useState, useEffect } from 'react';

const useFetchImage = (url) => {
    const [imageSrc, setImageSrc] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const resetError = () => {
        setError(null)
    };
    useEffect(() => {
        let storedImageUrl = sessionStorage.getItem('imageUrl')
        if (!url)
        {
            if (storedImageUrl === "null")
            {
                return;
            }
        }
        else 
        {
            storedImageUrl = url;
        }

        sessionStorage.setItem('imageUrl', storedImageUrl);

        const fetchImage = async () => {
            setIsLoading(true);
            try {
                const response = await fetch(storedImageUrl);

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const blob = await response.blob();
                const imageUrl = URL.createObjectURL(blob);
                setImageSrc(imageUrl);
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchImage();
    }, [url]);

    return { imageSrc, isLoading, error, resetError };
};

export default useFetchImage;
