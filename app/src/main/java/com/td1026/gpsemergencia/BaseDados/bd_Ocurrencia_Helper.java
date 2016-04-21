package com.td1026.gpsemergencia.BaseDados;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.td1026.gpsemergencia.R;

import java.sql.SQLException;

/**
 * Created by Telmo on 02/11/2015.
 * o
 */
public class bd_Ocurrencia_Helper extends OrmLiteSqliteOpenHelper
{


    private static final String DATABASE_NAME = "bd_Ocurencia";
    private static final int DATABASE_VERSION = 1;

    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<bd_Ocurrencia, Long> todoDao;

    public bd_Ocurrencia_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                /**
                 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                 * /res/raw/ directory of this project
                 * */
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            /**
             * creates the Todo database table
             */
            TableUtils.createTable(connectionSource, bd_Ocurrencia.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
    int oldVersion, int newVersion) {
        try {
            /**
             * Recreates the database when onUpgrade is called by the framework
             */
            TableUtils.dropTable(connectionSource, bd_Ocurrencia.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an instance of the data access object
     * @return
     * @throws SQLException
     */
    public Dao<bd_Ocurrencia, Long> getDao() throws SQLException {
        if(todoDao == null) {
            todoDao = getDao(bd_Ocurrencia.class);
        }
        return todoDao;
    }
}
