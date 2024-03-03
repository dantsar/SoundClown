package com.SoundClown.util;

import java.sql.Connection;

public abstract class DataAccessObject <T extends DataTransferObject> {

    protected final Connection connection;
//  protected final static String LAST_VAL = "SELECT last_value FROM ";
//  protected final static String PLAYER_SEQUENCE = "player_seq";

    public DataAccessObject(Connection connection){
        super();
        this.connection = connection;
    }

    public abstract T find_by_user_name(T dto);

    public abstract T create(T dto);

//  public abstract T update(T dto);
}
