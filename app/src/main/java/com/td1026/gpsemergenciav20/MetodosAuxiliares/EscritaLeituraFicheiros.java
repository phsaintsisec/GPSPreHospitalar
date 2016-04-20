package com.td1026.gpsemergenciav20.MetodosAuxiliares;

import android.os.Environment;


import com.td1026.gpsemergenciav20.Dados.ConfigGerais;
import com.td1026.gpsemergenciav20.Dados.Hospital;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Telmo on 01/11/2015.
 */
public class EscritaLeituraFicheiros
{
    //-------------------------------------------Variaveis-------------------------------------------

    public static String NomeFicheiroHospitais = ConfiguracoesApp.FicheiroHospitais;
    public static String NomeFicheiroConfigGerais = ConfiguracoesApp.FicheiroConfigGerais;
    public static String Diretoria = ConfiguracoesApp.DiretoriaFicheiros;

    //-------------------------------------------Teste de escrita-------------------------------------------
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    //-------------------------------------------Teste de leitura-------------------------------------------
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    //-------------------------------------------Escrever Ficheiros com dados dos Hospitais-------------------------------------------
    public static boolean EscreverHospitais(List<Hospital> listahospitais) throws IOException
    {

        if (!isExternalStorageWritable() || !isExternalStorageReadable()) {
            return false;
        }
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/" + Diretoria);
        dir.mkdirs();
        File file = new File(dir, NomeFicheiroHospitais);
        //Apagar File se existir, se nao tiver este if ele acrescenta
        if (file.exists())
            file.delete();
        file.createNewFile();

        FileWriter writer = new FileWriter(file, true);
        PrintWriter out = new PrintWriter(writer, true);

        out.println(listahospitais.size());
        for (int i = 0; i < listahospitais.size(); i++) {
            out.println(listahospitais.get(i).getNome());
            out.println(listahospitais.get(i).getLatitude());
            out.println(listahospitais.get(i).getLongitude());
        }
        out.close();
        writer.close();
        return  true;
    }
    //-------------------------------------------Ler Ficheiros com dados dos Hospitais-------------------------------------------
    public static List<Hospital> LerHospitais() throws IOException
    {
        if (!isExternalStorageWritable() || !isExternalStorageReadable()) {
            return new ArrayList<>();
        }
        File sdCard = Environment.getExternalStorageDirectory();
        File Directoria = new File(sdCard.getAbsolutePath() + "/" + Diretoria);
        File file = new File(Directoria, NomeFicheiroHospitais);

        if(!file.exists())
            return new ArrayList<>();

        FileReader Redader = new FileReader(file);
        BufferedReader in = new BufferedReader(Redader);

        List<Hospital> listahospitais = new ArrayList<>();
        int si = Integer.valueOf(in.readLine());
        for (int i = 0; i < si; i++)
        {
            String nome = in.readLine();
            double lat = Double.valueOf(in.readLine());
            double lon = Double.valueOf(in.readLine());
            listahospitais.add(new Hospital(nome,lat,lon));
        }
        in.close();
        Redader.close();
        return listahospitais;
    }
    //-------------------------------------------Escrever Ficheiros com dados das Configuraçoes gerais-------------------------------------------
    public static boolean EscreverConfigGerais (ConfigGerais config) throws IOException
    {

        if (!isExternalStorageWritable() || !isExternalStorageReadable()) {
            return false;
        }
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/" + Diretoria);
        dir.mkdirs();
        File file = new File(dir, NomeFicheiroConfigGerais);
        //Apagar File se existir, se nao tiver este if ele acrescenta
        if (file.exists())
            file.delete();
        file.createNewFile();

        FileWriter writer = new FileWriter(file, true);
        PrintWriter out = new PrintWriter(writer, true);


        out.println(config.getDistanciaLocal());
        out.println(config.getDistanciaHospital());
        out.println(config.getTempoLocal());
        out.println(config.getTempoHospital());
        out.println(config.getCidade());


        out.close();
        writer.close();
        return  true;
    }
    //-------------------------------------------Ler Ficheiros com dados dos Hospitais-------------------------------------------
    public static ConfigGerais LerConfigGerais() throws IOException
    {
        if (!isExternalStorageWritable() || !isExternalStorageReadable()) {
            return new ConfigGerais();
        }
        File sdCard = Environment.getExternalStorageDirectory();
        File Directoria = new File(sdCard.getAbsolutePath() + "/" + Diretoria);
        File file = new File(Directoria, NomeFicheiroConfigGerais);

        if(!file.exists())
            return new ConfigGerais();

        FileReader Redader = new FileReader(file);
        BufferedReader in = new BufferedReader(Redader);


        int DistanciaLocal = Integer.valueOf(in.readLine());
        int DistanciaHospital = Integer.valueOf(in.readLine());
        int TempoLocal = Integer.valueOf(in.readLine());
        int TempoHospital = Integer.valueOf(in.readLine());
        String Cidade = in.readLine();

        ConfigGerais configGerais = new ConfigGerais(DistanciaLocal,DistanciaHospital,TempoLocal, TempoHospital,Cidade);

        in.close();
        Redader.close();
        return configGerais;
    }
}