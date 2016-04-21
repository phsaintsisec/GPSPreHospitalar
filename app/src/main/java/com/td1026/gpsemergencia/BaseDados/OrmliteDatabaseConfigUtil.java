package com.td1026.gpsemergencia.BaseDados;



import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Telmo on 02/11/2015.
 * o
 */
public class OrmliteDatabaseConfigUtil extends OrmLiteConfigUtil {

    /**
     * classes represents the models to use for generating the ormlite_config.txt file
     */
    private static final Class<?>[] classes = new Class[] {bd_Ocurrencia.class,bd_Trajecto.class};

    /**
     * Given that this is a separate program from the android app, we have to use
     * a static main java method to create the configuration file.
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {

        String currDirectory = "user.dir";

        String configPath = "/app/src/main/res/raw/ormlite_config.txt";

        /**
         * Gets the project root directory
         */
        String projectRoot = System.getProperty(currDirectory);

        /**
         * Full configuration path includes the project root path, and the location
         * of the ormlite_config.txt file appended to it
         */
        String fullConfigPath = projectRoot + configPath;

        File configFile = new File(fullConfigPath);

        /**
         * In the a scenario where we run this program serveral times, it will recreate the
         * configuration file each time with the updated configurations.
         */
        if(configFile.exists()) {
            configFile.delete();
            configFile = new File(fullConfigPath);
        }

        /**
         * writeConfigFile is a util method used to write the necessary configurations
         * to the ormlite_config.txt file.
         */
        writeConfigFile(configFile, classes);







    }

    //-------------------------teste base dados------------------------
/*
    try {
        bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(t, bd_Ocurrencia_Helper.class);
        Dao<bd_Ocurrencia, Long> todoDao = todoOpenDatabaseHelper.getDao();

        Dados_Ocurrencia dados = new Dados_Ocurrencia(1);
        bd_Ocurrencia nova = new bd_Ocurrencia(dados);
        todoDao.create(nova);

        dados = new Dados_Ocurrencia(2);
        nova = new bd_Ocurrencia(dados);
        todoDao.create(nova);

        dados = new Dados_Ocurrencia(2);
        nova = new bd_Ocurrencia(dados);
        todoDao.create(nova);

        List<bd_Ocurrencia> todos = todoDao.queryForAll();


        int p= 5;
        if(p == 6)
            p = 9;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    //---------------------------------------------------------------------------------------*/
}