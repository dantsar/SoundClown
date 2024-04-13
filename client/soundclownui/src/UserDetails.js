import { useParams } from "react-router-dom";
import useFetch from './useFetch';

const UserDetails = () => {
    const { user_id } = useParams();

    return (
        <div className="user-details">
            <h2>User Details - { user_id }</h2>
        </div>
    );
}

export default UserDetails;
