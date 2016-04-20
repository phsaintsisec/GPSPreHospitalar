package com.td1026.gpsemergenciav20.Dados;

import android.location.Location;

import java.util.Date;

/**
 * Created by Telmo on 30/10/2015.
 */
public class Posicao
{

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Variaveis-------------------------------------------

    private Location Local;
    private Date Data;

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Construtores-------------------------------------------

    public Posicao(Location local, Date data) {
        Local = local;
        Data = data;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Gets e Sets-------------------------------------------

    public Location getLocal() {
        return Local;
    }
    public Date getData() {
        return Data;
    }
}
