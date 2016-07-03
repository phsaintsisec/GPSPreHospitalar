package com.td1026.gpsemergencia.MetodosAuxiliares;

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
    private static String NomeFicheiroLogsErros = "Erros.txt";
    private static String NomeFicheiroLogsInfo = "Info.txt";
    private static String NomeFicheiroLogsFluxo = "Fluxo.txt";
    public static String Diretoria = "GPSEmergencia";

    //-------------------------------------------Metodos-------------------------------------------

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    private static void EscreverLogs(String context,String msg,String tipo,String NomeFicheiroLogs ){
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
        EscreverLogs(context,msg,"info",NomeFicheiroLogsInfo);
    }
    public static void erro(String context , String msg)
    {
        EscreverLogs(context,msg,"erro", NomeFicheiroLogsErros);
    }
    public static void fluxo(String context , String msg)
    {
        EscreverLogs(context,msg,"fluxo", NomeFicheiroLogsFluxo);
    }







}

