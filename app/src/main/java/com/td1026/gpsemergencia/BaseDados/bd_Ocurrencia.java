package com.td1026.gpsemergencia.BaseDados;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.td1026.gpsemergencia.Dados.Dados_Ocurrencia;
import com.td1026.gpsemergencia.MetodosAuxiliares.Formatos;
import java.util.Date;

/**
 * Created by Telmo on 02/11/2015.
 * o
 */
@DatabaseTable(tableName = "bd_Ocurencia")
public class bd_Ocurrencia
{
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private Date HoraInicial;
    @DatabaseField
    private double LatLocalInicial;
    @DatabaseField
    private double LonLocalInicial;
    @DatabaseField
    private Date HoraChegadaSocorro;
    @DatabaseField
    private double LatLocalChegadaSocorro;
    @DatabaseField
    private double LonLocalChegadaSocorro;
    @DatabaseField
    private Date HoraSaidaSocorro;
    @DatabaseField
    private Date HoraChegadaHospital;
    @DatabaseField
    private double LatHospitalDestino;
    @DatabaseField
    private double LonHospitalDestino;
    @DatabaseField
    private Date HoraFinal;
    @DatabaseField
    private double DistanciaSocorro;
    @DatabaseField
    private  double DistanciaHospital;
    @DatabaseField
    private  boolean Enviado;


    public bd_Ocurrencia() {
    }

    public bd_Ocurrencia(Dados_Ocurrencia Ocurrencia)
    {
        HoraInicial = Ocurrencia.getHoraInicial();
        if(Ocurrencia.getLocalInicial() == null)
        {
            LonLocalInicial = 0;
            LonLocalInicial = 0;
        }
        else
        {
            LatLocalInicial = Ocurrencia.getLocalInicial().getLatitude();
            LonLocalInicial = Ocurrencia.getLocalInicial().getLongitude();
        }
        HoraChegadaSocorro = Ocurrencia.getHoraChegadaSocorro();
        if(Ocurrencia.getLocalChegadaSocorro() == null)
        {
            LatLocalChegadaSocorro = 0;
            LonLocalChegadaSocorro = 0;
        }
        else
        {
            LatLocalChegadaSocorro = Ocurrencia.getLocalChegadaSocorro().getLatitude();
            LonLocalChegadaSocorro = Ocurrencia.getLocalChegadaSocorro().getLongitude();
        }

        if(Ocurrencia.getHospitalDestino() == null)
        {
            LatHospitalDestino = 0;
            LonHospitalDestino = 0;
        }
        else
        {
            LatHospitalDestino = Ocurrencia.getHospitalDestino().getLatitude();
            LonHospitalDestino = Ocurrencia.getHospitalDestino().getLongitude();
        }
        HoraSaidaSocorro = Ocurrencia.getHoraSaidaSocorro();
        HoraChegadaHospital = Ocurrencia.getHoraChegadaHospital();
        HoraFinal = Ocurrencia.getHoraFinal();
        DistanciaSocorro = Ocurrencia.getDistanciaSocorro();
        DistanciaHospital = Ocurrencia.getDistanciaHospital();
        Enviado = false;
    }

    public Long getId() {
        return id;
    }
    public Date getHoraInicial() {
        return HoraInicial;
    }
    public double getLatLocalInicial() {
        return LatLocalInicial;
    }
    public double getLonLocalInicial() {
        return LonLocalInicial;
    }
    public Date getHoraChegadaSocorro() {
        return HoraChegadaSocorro;
    }
    public double getLatLocalChegadaSocorro() {
        return LatLocalChegadaSocorro;
    }
    public double getLonLocalChegadaSocorro() {
        return LonLocalChegadaSocorro;
    }
    public Date getHoraSaidaSocorro() {
        return HoraSaidaSocorro;
    }
    public Date getHoraChegadaHospital() {
        return HoraChegadaHospital;
    }
    public double getLatHospitalDestino() {
        return LatHospitalDestino;
    }
    public double getLonHospitalDestino() {
        return LonHospitalDestino;
    }
    public Date getHoraFinal() {
        return HoraFinal;
    }
    public double getDistanciaSocorro() {
        return DistanciaSocorro;
    }
    public double getDistanciaHospital() {
        return DistanciaHospital;
    }
    public boolean isEnviado() {
        return Enviado;
    }
    public void setEnviado() {
        Enviado = true;
    }

    @Override
    public String toString() {
        String aux =
                "id=" + id +"\n" +"\n" +
                "Hora de Inicial  =  " + Formatos.getDataHoraMinSegFormat(HoraInicial) +"\n" +"\n" +
                "Hora de Chegada ao Local do Socorro  =  " + Formatos.getDataHoraMinSegFormat(HoraChegadaSocorro) +"\n" +"\n" +
                "Hora de Saida do Localdo Socorro  =  " + Formatos.getDataHoraMinSegFormat(HoraSaidaSocorro) +"\n" +"\n" +
                "Hora de Chegada ao Hospital  =  " + Formatos.getDataHoraMinSegFormat(HoraChegadaHospital) +"\n" +"\n" +
                "Hora de Saida do Hospital  =  " + Formatos.getDataHoraMinSegFormat(HoraFinal) +"\n" +"\n" +
                "Latitude do Local Inicial  =  " + LatLocalInicial +"\n" +"\n" +
                "Longitude do Local Inicial  =  " + LonLocalInicial +"\n" +"\n" +
                "Latitude do Local de Socorro  =  " + LatLocalChegadaSocorro +"\n" +"\n" +
                "Longitude do Local de Socorro  =  " + LonLocalChegadaSocorro +"\n" +"\n" +
                "Latitude do Hospital de Destino  =  " + LatHospitalDestino +"\n" +"\n" +
                "Longitude do Hospital de Destino  =  " + LonHospitalDestino +"\n" +"\n" +
                "Distancia Percorrida ate ao Local de Socorro  =  " + DistanciaSocorro +" Km" +"\n" +"\n" +
                "Distancia Percorrida ate ao Hospital  =  " + DistanciaHospital +" Km"+"\n" +"\n" +
                "Enviado para Servidor  =  " ;

        if(Enviado)
            aux += "Sim";
        else
            aux += "Não";

        return aux +"\n";

    }
}
