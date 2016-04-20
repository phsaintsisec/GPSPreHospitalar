package com.td1026.gpsemergenciav20.MetodosAuxiliares;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by Telmo on 04/11/2015.
 * o
 */
public class Logs
{
    //-------------------------------------------Variaveis-------------------------------------------
    private static String NomeFicheiroLogs = "Logs.txt";
    private static String Diretoria = "Logs";

    //-------------------------------------------Metodos-------------------------------------------

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    private static void EscreverLogs(String context,String msg,String tipo){
        try {
            if (!isExternalStorageWritable() || !isExternalStorageReadable()) {
                return;
            }
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/" + Diretoria);
            dir.mkdirs();
            File file = new File(dir,NomeFicheiroLogs);

                file.createNewFile();

            FileWriter writer = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(writer, true);

            String str = "[" + tipo + "]:[" + Formatos.getDataHoraMinSegFormat(new Date());
            str += "]:[" + context + "] : [" + msg + "]";
            out.println(str);

            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void info(String context , String msg)
    {
        EscreverLogs(context,msg,"info");
    }
    public static void erro(String context , String msg)
    {
        EscreverLogs(context,msg,"erro");
    }
    public static void fluxo(String context , String msg)
    {
        EscreverLogs(context,msg,"fluxo");
    }
    public static void valores(String context , String msg) {
        EscreverLogs(context,msg,"valores");
    }

}

