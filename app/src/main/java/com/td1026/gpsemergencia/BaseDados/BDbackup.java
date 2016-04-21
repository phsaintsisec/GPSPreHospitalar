package com.td1026.gpsemergencia.BaseDados;


import android.content.Context;
import android.os.Environment;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.td1026.gpsemergencia.Dados.OcurrenciaActual;
import com.td1026.gpsemergencia.Dados.Posicao;
import com.td1026.gpsemergencia.MetodosAuxiliares.Formatos;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Telmo on 31-03-2016.
 */
public class BDbackup {


    public static void gravarLocalmente (Context t, OcurrenciaActual o) {
        List<bd_Trajecto> tar = new ArrayList<>();
        bd_Ocurrencia nova = new bd_Ocurrencia(o.getOcurrencia());
        try {
            bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(t, bd_Ocurrencia_Helper.class);
            Dao<bd_Ocurrencia, Long> todoDao = todoOpenDatabaseHelper.getDao();
            todoDao.create(nova);


            bd_Trajecto_Helper bd_trajecto_helper = OpenHelperManager.getHelper(t, bd_Trajecto_Helper.class);
            Dao<bd_Trajecto, Long> todoDao2 = bd_trajecto_helper.getDao();
            for (Posicao p:o.getPercurso()) {
                bd_Trajecto nova2 = new bd_Trajecto(nova.getId(), p);
                todoDao2.create(nova2);
                tar.add(nova2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        gravarFicheiro(nova,tar);
    }
    public static void gravarPosgres (Context t) {


    }
    public static void gravarFicheiro (bd_Ocurrencia o, List<bd_Trajecto> t) {
        try {
            if (!Logs.isExternalStorageWritable() || !Logs.isExternalStorageReadable()) {
                return;
            }
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/" + Logs.Diretoria);
            dir.mkdirs();
            File file = new File(dir,"oc_" + o.getId() + "_"+ Formatos.getDataHoraMinSegFormat(o.getHoraInicial()));
            file.createNewFile();
            FileWriter writer = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(writer, true);
            String str = "***********************************************************************";
            out.println(str);
            str += "[ ID ]:[" + o.getId() + "]";
            out.println(str);
            str += "[ Data Inicial ]:[" + Formatos.getDataHoraMinSegFormat(o.getHoraInicial());
            out.println(str);
            str += "[ Data Chegada Local ]:[" + Formatos.getDataHoraMinSegFormat(o.getHoraChegadaSocorro());
            out.println(str);
            str += "[ Data Saida Local ]:[" + Formatos.getDataHoraMinSegFormat(o.getHoraSaidaSocorro());
            out.println(str);
            str += "[ Data Chegada Hospital ]:[" + Formatos.getDataHoraMinSegFormat(o.getHoraChegadaHospital());
            out.println(str);
            str += "[ Data Saida Hospital ]:[" + Formatos.getDataHoraMinSegFormat(o.getHoraFinal());
            out.println(str);
            str += "[ Local Inicial ]:[ Lat:" + o.getLatLocalInicial() + " Long:"+ o.getLonLocalInicial()+ "]";
            out.println(str);
            str += "[ Local Local ]:[ Lat:" + o.getLatLocalChegadaSocorro() + " Long:"+ o.getLonLocalChegadaSocorro()+ "]";
            out.println(str);
            str += "[ Local Hospital ]:[Lat:" + o.getLatHospitalDestino() + " Long:"+ o.getLonHospitalDestino()+ "]";
            out.println(str);
            str += "[ Distancia Socorro ]:[" + o.getDistanciaSocorro() + "]";
            out.println(str);
            str += "[ Distancia Hospital ]:[" + o.getDistanciaHospital() + "]";
            out.println(str);
            str = "***********************************************************************";
            out.println(str);
            str = "***********************************************************************";
            out.println(str);
            for (bd_Trajecto p: t) {
                str = "[" + p.getId() + "]:[" + Formatos.getDataHoraMinSegFormat(p.getData()) + "]:[" + p.getLatitude() + "]:[" + p.getLongitude() + "]";
                out.println(str);

            }
            str = "***********************************************************************";
            out.println(str);
            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
