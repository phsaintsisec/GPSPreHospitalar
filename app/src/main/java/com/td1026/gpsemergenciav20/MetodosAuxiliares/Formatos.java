package com.td1026.gpsemergenciav20.MetodosAuxiliares;

import android.location.Location;


import com.td1026.gpsemergenciav20.BaseDados.bd_Ocurrencia;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Telmo on 30/10/2015.
 */
public class Formatos
{
    //-------------------------------------------Metodos de Escrita de Formatos-------------------------------------------
    public static String getHoraMinFormat(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
        return dt.format(date);
    }
    public static String getHoraMinSegFormat(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");
        return dt.format(date);
    }
    public static String getDataFormat(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        return dt.format(date);
    }
    public static String getDataHoraMinSegFormat(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy   HH:mm:ss");
        return dt.format(date);
    }
    public static String getCoordenadasFormat(Location Coordenadas) {
        return  "Lat: " + Coordenadas.getLatitude() + "Lon: " + Coordenadas.getLongitude();
    }
    public static String getEstadosFormat(Date date, Location Coordenadas ) {
        return getDataHoraMinSegFormat(date) + "   " + getCoordenadasFormat(Coordenadas);
    }
    public static String getEstadosFormat(Date date)
    {
        return getDataHoraMinSegFormat(date)+"";
    }
    public static String getbd_OcurrenciaFormat(bd_Ocurrencia b) {
        return b.getId() + " - "+ getDataHoraMinSegFormat(b.getHoraInicial());
    }

}
