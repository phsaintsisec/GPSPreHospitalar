package com.td1026.gpsemergencia.BaseDados;

import android.content.Context;
import android.os.StrictMode;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.td1026.gpsemergencia.Dados.Dados_Ocurrencia;
import com.td1026.gpsemergencia.Dados.Posicao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Telmo on 31/05/2016.
 */
public class SQLPostgre {


    static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static String QueryCreateBD_Ocorrencia = "CREATE TABLE IF NOT EXISTS bd_Ocurrencia(id integer PRIMARY KEY,\n" +
            "                                                                id_ocorrencia int,\n" +
            "                                                                HoraInicial date,\n" +
            "                                                                LatLocalInicial decimal,\n" +
            "\t\t\t\t\t\t\t\tLonLocalInicial decimal,\n" +
            "\t\t\t\t\t\t\t\tHoraChegadaSocorro date,\n" +
            "\t\t\t\t\t\t\t\tLatLocalChegadaSocorro decimal,\n" +
            "\t\t\t\t\t\t\t\tLonLocalChegadaSocorro decimal,\n" +
            "\t\t\t\t\t\t\t\tHoraSaidaSocorro date,\n" +
            "\t\t\t\t\t\t\t\tHoraChegadaHospital date,\n" +
            "\t\t\t\t\t\t\t\tLatHospitalDestino decimal,\n" +
            "\t\t\t\t\t\t\t\tLonHospitalDestino decimal,\n" +
            "\t\t\t\t\t\t\t\tHoraFinal date,\n" +
            "\t\t\t\t\t\t\t\tDistanciaSocorro decimal,\n" +
            "\t\t\t\t\t\t\t\tDistanciaHospital decimal)";
    static String QueryCreateBD_trajecto = "CREATE TABLE IF NOT EXISTS bd_Trajecto(id integer PRIMARY KEY,\n" +
            "                                                                id_ocorrencia int,\n" +
            "                                                                tempo date,\n" +
            "                                                                latitude decimal,\n" +
            "                                                                longitude decimal,\n" +
            "                                                                velocidade decimal)";
    static String QueryinsertBD_Ocorrencia = "insert into bd_Ocurrencia(\n" +
            "\t\t\tHoraInicial,\n" +
            "\t\t\tLatLocalInicial ,\n" +
            "\t\t\tLonLocalInicial ,\n" +
            "\t\t\tHoraChegadaSocorro ,\n" +
            "\t\t\tLatLocalChegadaSocorro ,\n" +
            "\t\t\tLonLocalChegadaSocorro ,\n" +
            "\t\t\tHoraSaidaSocorro ,\n" +
            "\t\t\tHoraChegadaHospital ,\n" +
            "\t\t\tLatHospitalDestino ,\n" +
            "\t\t\tLonHospitalDestino ,\n" +
            "\t\t\tHoraFinal ,\n" +
            "\t\t\tDistanciaSocorro ,\n" +
            "\t\t\tDistanciaHospital ) \n" +
            "values(\n" +
            "\t\t\t'2012-05-01 11:12:13',\n" +
            "\t\t\t'40.1',\n" +
            "\t\t\t'40.2',\n" +
            "\t\t\t'2012-05-02 11:12:13',\n" +
            "\t\t\t'40.3',\n" +
            "\t\t\t'40.4',\n" +
            "\t\t\t'2012-05-03 11:12:13',\n" +
            "\t\t\t'2012-05-04 11:12:13',\n" +
            "\t\t\t'40.5',\n" +
            "\t\t\t'40.6',\n" +
            "\t\t\t'2012-05-05 11:12:13',\n" +
            "\t\t\t'40.7',\n" +
            "\t\t\t'40.8')";
    static String QueryinsertBD_trajecto = "";

    static String Querymax = "select max(id) from bd_Ocurrencia";


    static String CreateQueryCreateOcorrencia(bd_Ocurrencia d) {
        String str = "";
        str += "insert into bd_Ocurrencia(\n" +
                "\t\t\tHoraInicial,\n" +
                "\t\t\tLatLocalInicial ,\n" +
                "\t\t\tLonLocalInicial ,\n" +
                "\t\t\tHoraChegadaSocorro ,\n" +
                "\t\t\tLatLocalChegadaSocorro ,\n" +
                "\t\t\tLonLocalChegadaSocorro ,\n" +
                "\t\t\tHoraSaidaSocorro ,\n" +
                "\t\t\tHoraChegadaHospital ,\n" +
                "\t\t\tLatHospitalDestino ,\n" +
                "\t\t\tLonHospitalDestino ,\n" +
                "\t\t\tHoraFinal ,\n" +
                "\t\t\tDistanciaSocorro ,\n" +
                "\t\t\tDistanciaHospital ) \n" +
                "values(\n" +
                "\t\t\t'" + dt.format(d.getHoraInicial()) + "',\n" +
                "\t\t\t'" + d.getLatLocalInicial() + "',\n" +
                "\t\t\t'" + d.getLonLocalInicial() + "',\n" +
                "\t\t\t'" + dt.format(d.getHoraChegadaSocorro()) + "',\n" +
                "\t\t\t'" + d.getLatLocalChegadaSocorro() + "',\n" +
                "\t\t\t'" + d.getLonLocalChegadaSocorro() + "',\n" +
                "\t\t\t'" + dt.format(d.getHoraSaidaSocorro()) + "',\n" +
                "\t\t\t'" + dt.format(d.getHoraChegadaHospital()) + "',\n" +
                "\t\t\t'" + d.getLatHospitalDestino() + "',\n" +
                "\t\t\t'" + d.getLonHospitalDestino() + "',\n" +
                "\t\t\t'" + dt.format(d.getHoraFinal()) + "',\n" +
                "\t\t\t'" + d.getDistanciaSocorro() + "',\n" +
                "\t\t\t'" + d.getDistanciaHospital() + "')";
        return str;
    }

    static String CreateQueryCreateposicao(int idocorrencia, bd_Trajecto d) {

        String str = "insert into bd_Trajecto(\n" +
                "\t\t\tid_ocorrencia,\n" +
                "\t\t\ttempo,\n" +
                "\t\t\tlatitude,\n" +
                "\t\t\tlongitude,\n" +
                "\t\t\tvelocidade)\n" +
                "values(\n" +
                "\t\t\t'" + idocorrencia + "',\n" +
                "\t\t\t'" + dt.format(d.getData()) + "',\n" +
                "\t\t\t'" + d.getLatitude() + "',\n" +
                "\t\t\t'" + d.getLongitude() + "',\n" +
                "\t\t\t'" + d.getVelocidade() + "') ";
        return str;
    }


    public static void gravarOcorrencia(bd_Ocurrencia o, List<bd_Trajecto> l) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
            e.printStackTrace();
            return;
        }
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://kenobi.dei.uc.pt:5432/gpsemergencia",
                    "telmo",
                    "telmo12345");
            st = connection.createStatement();

            st.executeUpdate(SQLPostgre.CreateQueryCreateOcorrencia(o));
            int idocorrencia = 0;
            rs = st.executeQuery(SQLPostgre.Querymax);
            if (rs.next()) {
                idocorrencia = rs.getInt(1);
            }
            for (bd_Trajecto p : l) {
                System.out.println(p);
                st.executeUpdate(SQLPostgre.CreateQueryCreateposicao(idocorrencia, p));
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public static void gravarOcorrenciasnaoenviadas(Context c) {
        List<bd_Ocurrencia> lista = null;
        bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(c, bd_Ocurrencia_Helper.class);
        Dao<bd_Ocurrencia, Long> todoDao = null;
        Dao<bd_Trajecto, Long> todoDaoTrajecto = null;
        try{
            todoDao = todoOpenDatabaseHelper.getDao_bd_Ocurrencia();
            todoDaoTrajecto = todoOpenDatabaseHelper.getDao_bd_Trajecto();
            lista = todoDao.queryForAll();
            if (lista == null)
                return;
            for (bd_Ocurrencia o : lista) {
                if (!o.isEnviado()) {
                    List<bd_Trajecto> trajeto = todoDaoTrajecto.queryForEq("id_ocorrencia", o.getId());
                    System.out.println( o +" / "+ trajeto.size());
                    gravarOcorrencia(o, trajeto);
                    o.setEnviado();
                    todoDao.update(o);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }



    public static void teste() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
            e.printStackTrace();
            return;
        }
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://kenobi.dei.uc.pt:5432/gpsemergencia",
                    "telmo",
                    "telmo12345");
            st = connection.createStatement();
            int idocorrencia = 0;
            rs = st.executeQuery(SQLPostgre.Querymax);
            if (rs.next()) {
                idocorrencia = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}
