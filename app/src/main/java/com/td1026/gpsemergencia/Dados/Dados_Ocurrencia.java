package com.td1026.gpsemergencia.Dados;

import android.location.Location;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");
        String str = "Ocurrencia : " +"\n";


            if (HoraInicial == null)
                str += "HoraInicial\t\t\t=" + "--:--:-- \n";
            else
                str += "HoraInicial\t\t\t=" + dt.format(HoraInicial )+ "\n" ;
            if (HoraChegadaSocorro == null)
                str += "HoraChegadaSocorro\t=" + "--:--:-- \n";
            else
                str += "HoraChegadaSocorro\t=" + dt.format(HoraChegadaSocorro) +"\n" ;
            if (HoraSaidaSocorro == null)
                str += "HoraSaidaSocorro\t=" + "--:--:-- \n";
            else
                str += "HoraSaidaSocorro\t=" + dt.format(HoraSaidaSocorro) +"\n" ;
            if (HoraChegadaHospital == null)
                str += "HoraChegadaHospital\t=" + "--:--:-- \n";
            else
                str += "HoraChegadaHospital\t=" + dt.format(HoraChegadaHospital) +"\n" ;
            if (HoraFinal == null)
                str += "HoraFinal\t\t\t=" + "--:--:-- \n";
            else
                str += "HoraFinal\t\t\t=" + dt.format(HoraFinal) +"\n" ;


            str +="\n" ;

            if (LocalInicial == null)
                str += "Local Inicial = " + "------ \n";
            else {
                str += "Local Inicial Latitude  = " + LocalInicial.getLatitude() + "\n";
                str += "Local Inicial Longitude = " + LocalInicial.getLongitude() + "\n";
            }
            if (LocalChegadaSocorro == null)
                str += "Local Socorro =" + "------ \n";
            else {
                str += "Local Socorro Latitude =" + LocalChegadaSocorro.getLatitude() + "\n";
                str +=  "Local Socorro Longitude =" + LocalChegadaSocorro.getLongitude() + "\n";
            }
            if (HospitalDestino == null)
                str += "Hospital =" + "------ \n";
            else {
                str += "Hospital Nome =" + HospitalDestino.getNome() + "\n" ;
                str += "Hospital Latitude =" + HospitalDestino.getLatitude() + "\n" ;
                str += "Hospital Longitude =" + HospitalDestino.getLongitude() + "\n \n";
            }
        return str;
    }
}
