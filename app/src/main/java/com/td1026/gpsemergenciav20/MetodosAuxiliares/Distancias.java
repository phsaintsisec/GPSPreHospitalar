package com.td1026.gpsemergenciav20.MetodosAuxiliares;

/**
 * Created by Telmo on 30/10/2015.
 */
public class Distancias
{

    //-------------------------------------------Diferença entre dois pontos em metros-------------------------------------------

    public static double DistanciaEntrePontos(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
    //--------------------------------------------------------------------------------------

}
