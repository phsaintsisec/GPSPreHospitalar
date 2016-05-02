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

/**
 * Created by Telmo on 31-03-2016.
 */
public class BDbackup {


    public static void gravarLocalmente (Context t, OcurrenciaActual o) {
        gravarFicheiro(o);
        gravarOcurrencia(t,o);
    }
    public static void gravarPosgres (Context t) {
    }
    public static void gravarFicheiro (OcurrenciaActual o) {
        try {
            if (!Logs.isExternalStorageWritable() || !Logs.isExternalStorageReadable()) {
                return;
            }
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/" + Logs.Diretoria);
            dir.mkdirs();
            File file = new File(dir,"oc_"+ Formatos.getDataHoraMinSegFormat(o.getOcurrencia().getHoraInicial()));
            file.createNewFile();
            FileWriter writer = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(writer, true);
            String str = "***********************************************************************";
            str +='\n';
            str += "[ Data Inicial ]:[" + Formatos.getDataHoraMinSegFormat(o.getOcurrencia().getHoraInicial());
            str +='\n';
            str += "[ Data Chegada Local ]:[" + Formatos.getDataHoraMinSegFormat(o.getOcurrencia().getHoraChegadaSocorro());
            str +='\n';
            str += "[ Data Saida Local ]:[" + Formatos.getDataHoraMinSegFormat(o.getOcurrencia().getHoraSaidaSocorro());
            str +='\n';
            str += "[ Data Chegada Hospital ]:[" + Formatos.getDataHoraMinSegFormat(o.getOcurrencia().getHoraChegadaHospital());
            str +='\n';
            str += "[ Data Saida Hospital ]:[" + Formatos.getDataHoraMinSegFormat(o.getOcurrencia().getHoraFinal());
            str +='\n';
            if(o.getOcurrencia().getLocalInicial() != null)
                str += "[ Local Inicial ]:[ Lat:" + o.getOcurrencia().getLocalInicial().getLatitude() + " Long:"+ o.getOcurrencia().getLocalInicial().getLongitude()+ "]";
            str +='\n';
            if(o.getOcurrencia().getLocalChegadaSocorro() != null)
                str += "[ Local Local ]:[ Lat:" + o.getOcurrencia().getLocalChegadaSocorro().getLatitude() + " Long:"+ o.getOcurrencia().getLocalChegadaSocorro().getLongitude()+ "]";
            str +='\n';
            if(o.getOcurrencia().getHospitalDestino() != null)
                str += "[ Local Hospital ]:[Lat:" + o.getOcurrencia().getHospitalDestino().getLatitude() + " Long:"+ o.getOcurrencia().getHospitalDestino().getLongitude()+ "]";
            str +='\n';
            str += "[ Distancia Socorro ]:[" + o.getOcurrencia().getDistanciaSocorro() + "]";
            str +='\n';
            str += "[ Distancia Hospital ]:[" + o.getOcurrencia().getDistanciaHospital() + "]";
            str +='\n';
            str += "***********************************************************************";
            str +='\n';
            str += "***********************************************************************";
            str +='\n';
            for (int i = 0 ; i<o.getPercurso().size();i++) {
                Posicao p = o.getPercurso().get(i);
                str += "[" + i + "]:[" + Formatos.getDataHoraMinSegFormat(p.getData()) + "]:[" + p.getLocal().getLatitude() + "]:[" + p.getLocal().getLongitude()+ "]";
                str +='\n';
            }
            str += "***********************************************************************";
            str +='\n';
            out.println(str);
            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void gravarOcurrencia (Context t, OcurrenciaActual o) {
        bd_Ocurrencia nova = new bd_Ocurrencia(o.getOcurrencia());
        try {
            bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(t, bd_Ocurrencia_Helper.class);
            Dao<bd_Ocurrencia, Long> todoDao = todoOpenDatabaseHelper.getDao_bd_Ocurrencia();
            todoDao.create(nova);
            Dao<bd_Trajecto, Long> todoDao2 = todoOpenDatabaseHelper.getDao_bd_Trajecto();
            for (Posicao p : o.getPercurso()) {
                bd_Trajecto nova2 = new bd_Trajecto(nova.getId(), p);
                todoDao2.create(nova2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
