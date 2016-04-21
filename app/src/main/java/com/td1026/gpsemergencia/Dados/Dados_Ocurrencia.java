package com.td1026.gpsemergencia.Dados;

import android.location.Location;

import java.util.Date;

/**
 * Created by Telmo on 30/10/2015.
 */
public class Dados_Ocurrencia
{

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Variaveis-------------------------------------------

    private Date HoraInicial;
    private Location LocalInicial;
    private Date HoraChegadaSocorro;
    private Location LocalChegadaSocorro;
    private Date HoraSaidaSocorro;
    private Date HoraChegadaHospital;
    private Hospital HospitalDestino;
    private Date HoraFinal;
    private double DistanciaSocorro;
    private  double DistanciaHospital;

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Construtores-------------------------------------------
    public Dados_Ocurrencia() {
        HoraInicial = null;
        LocalInicial = null;
        HoraChegadaSocorro = null;
        LocalChegadaSocorro = null;
        HoraSaidaSocorro = null;
        HoraChegadaHospital = null;
        HospitalDestino = null;
        HoraFinal = null;
        DistanciaSocorro = -1;
        DistanciaHospital = -1;
    }
    public Dados_Ocurrencia(int i) {
            HoraInicial = new Date();
            LocalInicial = null;
            HoraChegadaSocorro = new Date();
            LocalChegadaSocorro = null;
            HoraSaidaSocorro = new Date();
            HoraChegadaHospital = new Date();
            HospitalDestino = new Hospital("nome Hosp",45.2668, -45.6977);
            HoraFinal = new Date();
            DistanciaSocorro = i*3;
            DistanciaHospital = i*2;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Gets e Sets-------------------------------------------
    public Date getHoraInicial() {
        return HoraInicial;
    }
    public void setHoraInicial(Date horaInicial) {
        HoraInicial = horaInicial;
    }
    public Location getLocalInicial() {
        return LocalInicial;
    }
    public void setLocalInicial(Location localInicial) {
        LocalInicial = localInicial;
    }
    public Date getHoraChegadaSocorro() {
        return HoraChegadaSocorro;
    }
    public void setHoraChegadaSocorro(Date horaChegadaSocorro) {
        HoraChegadaSocorro = horaChegadaSocorro;
    }
    public Location getLocalChegadaSocorro() {
        return LocalChegadaSocorro;
    }
    public void setLocalChegadaSocorro(Location localChegadaSocorro) {
        LocalChegadaSocorro = localChegadaSocorro;
    }
    public Date getHoraSaidaSocorro() {
        return HoraSaidaSocorro;
    }
    public void setHoraSaidaSocorro(Date horaSaidaSocorro) {
        HoraSaidaSocorro = horaSaidaSocorro;
    }
    public Date getHoraChegadaHospital() {
        return HoraChegadaHospital;
    }
    public void setHoraChegadaHospital(Date horaChegadaHospital) {
        HoraChegadaHospital = horaChegadaHospital;
    }
    public Hospital getHospitalDestino() {
        return HospitalDestino;
    }
    public void setHospitalDestino(Hospital hospitalDestino) {
        HospitalDestino = hospitalDestino;
    }
    public Date getHoraFinal() {
        return HoraFinal;
    }
    public void setHoraFinal(Date horaFinal) {
        HoraFinal = horaFinal;
    }
    public double getDistanciaSocorro() {
        return DistanciaSocorro;
    }
    public void setDistanciaSocorro(double distanciasocorro) {
        DistanciaSocorro = distanciasocorro;
    }
    public double getDistanciaHospital() {
        return DistanciaHospital;
    }
    public void setDistanciaHospital(double distanciahospital) {
        DistanciaHospital = distanciahospital;
    }

    @Override
    public String toString() {
        return "Dados_Ocurrencia{" +"\n" +
                "HoraInicial=" + HoraInicial + "\n" +
                ", LocalInicial=" + LocalInicial +"\n" +
                ", HoraChegadaSocorro=" + HoraChegadaSocorro +"\n" +
                ", LocalChegadaSocorro=" + LocalChegadaSocorro +"\n" +
                ", HoraSaidaSocorro=" + HoraSaidaSocorro +"\n" +
                ", HoraChegadaHospital=" + HoraChegadaHospital +"\n" +
                ", HospitalDestino=" + HospitalDestino +"\n" +
                ", HoraFinal=" + HoraFinal +"\n" +
                ", DistanciaSocorro=" + DistanciaSocorro +"\n" +
                ", DistanciaHospital=" + DistanciaHospital +"\n" +
                '}';
    }
}
