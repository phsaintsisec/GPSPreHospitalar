package com.td1026.gpsemergenciav20.BaseDados;

import android.content.Context;

import com.td1026.gpsemergenciav20.Dados.OcurrenciaActual;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.td1026.gpsemergenciav20.Dados.Posicao;

import java.sql.SQLException;

/**
 * Created by Telmo on 31-03-2016.
 */
public class BDbackup {


    public static void gravarLocalmente (Context t, OcurrenciaActual o) {


        try {
            bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(t, bd_Ocurrencia_Helper.class);
            Dao<bd_Ocurrencia, Long> todoDao = todoOpenDatabaseHelper.getDao();
            bd_Ocurrencia nova = new bd_Ocurrencia(o.getOcurrencia());
            todoDao.create(nova);


            bd_Trajecto_Helper bd_trajecto_helper = OpenHelperManager.getHelper(t, bd_Trajecto_Helper.class);
            Dao<bd_Trajecto, Long> todoDao2 = bd_trajecto_helper.getDao();
            for (Posicao p:o.getPercurso()) {
                bd_Trajecto nova2 = new bd_Trajecto(nova.getId(), p);
                todoDao2.create(nova2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
