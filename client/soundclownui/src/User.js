import useFetch from './useFetch';

const User = ({user_id}) => {
    const { data: user, isPending, error } = useFetch('http://localhost:8080/get/user/' + user_id);

    return(
        <div className="user">
            { isPending && <div>Loading...</div> }
            { error && <div>Artist Not Found</div> }
            { user && <div>{user._user_name}</div> }
        </div>
    );
}

export default User
