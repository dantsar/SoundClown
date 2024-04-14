import useFetch from './useFetch';

const User = ({user_id}) => {
    const { data: user, isPending, error } = useFetch('http://localhost:8080/get/user/' + user_id);

    return(
        <div className="user">
            { isPending && <div>Loading...</div> }
            { error && <div>{ error }</div> }
            { user && <p>{user._user_name}</p>}
        </div>
    );
}

export default User
