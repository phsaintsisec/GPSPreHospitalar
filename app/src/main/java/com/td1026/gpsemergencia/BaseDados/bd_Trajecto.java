package com.td1026.gpsemergencia.BaseDados;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.td1026.gpsemergencia.Dados.Posicao;
import com.td1026.gpsemergencia.MetodosAuxiliares.Formatos;

import java.util.Date;

/**
 * Created by Telmo on 18-04-2016.
 */

@DatabaseTable(tableName = "bd_Trajecto")
public class bd_Trajecto {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private Long id_ocorrencia;
    @DatabaseField
    private Date Data;
    @DatabaseField
    private Double Latitude;
    @DatabaseField
    private Double Longitude;
    @DatabaseField
    private float Velocidade;

    public bd_Trajecto() {
    }
    public bd_Trajecto(Long id , Posicao p) {
        this.id_ocorrencia = id;
        Data = p.getData();
        Latitude = p.getLocal().getLatitude();
        Longitude = p.getLocal().getLongitude();
        Velocidade = p.getLocal().getSpeed();
    }
    public bd_Trajecto(Long id_ocorrencia, Date data, Double latitude, Double longitude,float velocidade) {
        this.id_ocorrencia = id_ocorrencia;
        Data = data;
        Latitude = latitude;
        Longitude = longitude;
        Velocidade = velocidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_ocorrencia() {
        return id_ocorrencia;
    }

    public void setId_ocorrencia(Long id_ocorrencia) {
        this.id_ocorrencia = id_ocorrencia;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public float getVelocidade() {
        return Velocidade;
    }

    public void setVelocidade(float velocidade) {
        Velocidade = velocidade;
    }

    @Override
    public String toString() {
        return "[ Data=" + Formatos.getHoraMinSegFormat(Data) +
                ", Lat=" + Latitude +
                ", Lon=" + Longitude +
                ", Vel=" + Velocidade +
                '}';
    }
}
