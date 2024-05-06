import useFetchImage from './useFetchImage'; // Import the useFetchImage hook
import logo from "./assets/soundclown.png";


const TrackImage = ({ image_path }) => {
    const { imageSrc, isLoading, error, resetError } = useFetchImage(`http://localhost:8080/${image_path}`);

    return (
        <>
            {isLoading ? (
                <div>Loading...</div>
            ) :  (
                // Conditionally render the image
                imageSrc ? (
                    <img src={imageSrc} alt="Track Image" style={{ width: '64px', height: '64px' }} />
                ) : (
                    <img src={logo} alt="Default Track Image" style={{ width: '64px', height: '64px' }} />
                )
            )}
        </>
    );
};

export default TrackImage;
