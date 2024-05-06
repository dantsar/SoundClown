import { Link } from 'react-router-dom';
import User from './User';

const PlaylistList = ({playlists, title}) => {
    return (
        <div className='playlist-wrapper'>
            <h2> {title} </h2>
            <div className="playlist-list">
                {playlists.map((playlist) => (
                    <div className="playlist-preview" key={playlist._playlist_id}>
                        <Link to ={`/playlist/${playlist._playlist_id}`}>
                            <h2>{ playlist._playlist_name }</h2>
                            <div>
                                Created by {playlist._user.username}
                            </div>
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default PlaylistList;
