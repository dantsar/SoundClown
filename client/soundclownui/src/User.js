import useFetch from './useFetch';
import React from 'react';
import Cookies from 'js-cookie';


const User =  React.memo(({user_id}) => {
    const { data: user, isPending, error } = useFetch('http://localhost:8080/get/user/' + user_id);
    // console.log("User.js error:");
    // console.log(error);

    return(
        <div className="user">
            { isPending && <div>Loading...</div> }
            { error && <div>Artist Not Found</div> }
            { user && <div>{user._user_name}</div> }
        </div>
    );
});

export default User
