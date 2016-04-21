package com.td1026.gpsemergencia.Dados;

/**
 * Created by Telmo on 30/10/2015.
 */
public class Hospital
{
    //--------------------------------------------------------------------------------------
    //-------------------------------------------Variaveis-------------------------------------------

    String Nome;
    Double Latitude;
    Double Longitude;

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Construtores-------------------------------------------

    public Hospital(String nome, double latitude, double longitude) {
        Nome = nome;
        Latitude = latitude;
        Longitude = longitude;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Gets e Sets-------------------------------------------

    public String getNome() {
        return Nome;
    }
    public Double getLatitude() {
        return Latitude;
    }
    public Double getLongitude() {
        return Longitude;
    }
}
