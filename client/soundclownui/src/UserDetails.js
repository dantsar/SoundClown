import { useNavigate, useParams } from "react-router-dom";
import useFetch from './useFetch';

const UserDetails = () => {
    const { user_id } = useParams();
    const { data: user, error, isPending } = useFetch("http://localhost:8080/get/user/" + user_id);
    const navigate = useNavigate();

    const handleClick = () => {
        fetch('http://localhost:8080/delete/user/' + user_id, {
            method: 'POST'
        }).then(() => {
            navigate("/");
        })
    }

    return (
        <div className="user-details">
            { isPending && <div>Loading...</div> }
            { error && <div>{ error }</div> }
            { user && (
                <article>
                    <h2>{user._user_name}</h2>
                    <button onClick={handleClick}>delete</button>
                </article>
            )}
        </div>
    );
}

export default UserDetails;
