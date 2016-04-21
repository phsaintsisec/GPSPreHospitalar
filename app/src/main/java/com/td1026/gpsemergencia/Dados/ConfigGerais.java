package com.td1026.gpsemergencia.Dados;

/**
 * Created by Telmo on 02/11/2015.
 */
public class ConfigGerais
{
    //--------------------------------------------------------------------------------------
    //-------------------------------------------Variaveis-------------------------------------------

    int DistanciaLocal;
    int DistanciaHospital;
    int TempoLocal;
    int TempoHospital;
    String Cidade;

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Construtores-------------------------------------------

    public ConfigGerais() {
        DistanciaLocal = 100;
        DistanciaHospital = 100;
        TempoLocal = 5;
        TempoHospital = 5;
        Cidade = "Coimbra";
    }
    public ConfigGerais(int distanciaLocal, int distanciaHospital, int tempoLocal, int tempoHospital, String cidade) {
        DistanciaLocal = distanciaLocal;
        DistanciaHospital = distanciaHospital;
        TempoLocal = tempoLocal;
        TempoHospital = tempoHospital;
        Cidade = cidade;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Gets e Sets-------------------------------------------

    public int getDistanciaLocal() {
        return DistanciaLocal;
    }
    public void setDistanciaLocal(int distanciaLocal) {
        DistanciaLocal = distanciaLocal;
    }
    public int getDistanciaHospital() {
        return DistanciaHospital;
    }
    public void setDistanciaHospital(int distanciaHospital) {
        DistanciaHospital = distanciaHospital;
    }
    public int getTempoLocal() {
        return TempoLocal;
    }
    public void setTempoLocal(int tempoLocal) {
        TempoLocal = tempoLocal;
    }
    public int getTempoHospital() {
        return TempoHospital;
    }
    public void setTempoHospital(int tempoHospital) {
        TempoHospital = tempoHospital;
    }
    public String getCidade() {
        return Cidade;
    }
    public void setCidade(String cidade) {
        Cidade = cidade;
    }
}
