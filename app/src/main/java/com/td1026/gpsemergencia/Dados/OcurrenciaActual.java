package com.td1026.gpsemergencia.Dados;

import android.location.Location;

import com.td1026.gpsemergencia.Activity_ServicePrincipal;
import com.td1026.gpsemergencia.BaseDados.Operacoes;
import com.td1026.gpsemergencia.MetodosAuxiliares.Distancias;
import com.td1026.gpsemergencia.MetodosAuxiliares.EscritaLeituraFicheiros;
import com.td1026.gpsemergencia.MetodosAuxiliares.Formatos;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Telmo on 30/10/2015.
 */
public class OcurrenciaActual
{

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Variaveis-------------------------------------------
    private ConfigGerais configgerais;
    private List<Hospital> listaHospitais;
    //0 - Disponivel
    //1 - Caminho do local de socorro
    //2 - Local de socorro
    //3 - Caminho do hospital
    //4 - Hospital
    private int estadoocurrencia;
    private List<Posicao> percurso;
    private Dados_Ocurrencia ocurrencia;
    private int indexChegadalocal = -1;
    private int indexSaidalocal = -1;
    private int indexChegadaHospital = -1;
    private Activity_ServicePrincipal t;

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Construtores-------------------------------------------

    public OcurrenciaActual(Activity_ServicePrincipal c) throws IOException {
        configgerais = EscritaLeituraFicheiros.LerConfigGerais();
        listaHospitais = EscritaLeituraFicheiros.LerHospitais();
        estadoocurrencia = 1;
        percurso = new ArrayList<>();
        ocurrencia = new Dados_Ocurrencia();
        ocurrencia.setHoraInicial(new Date());
        t = c;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Gets e Sets-------------------------------------------

    public Dados_Ocurrencia getOcurrencia() {
        return ocurrencia;
    }
    public List<Posicao> getPercurso() {
        return percurso;
    }
    public int getIndexChegadalocal() {
        return indexChegadalocal;
    }
    public int getIndexSaidalocal() {
        return indexSaidalocal;
    }
    public int getIndexChegadaHospital() {
        return indexChegadaHospital;
    }
    public int getEstadoocurrencia() {
        return estadoocurrencia;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Metodos-------------------------------------------

    public boolean addPercurso(Location l, Date d) throws SQLException {
        switch (estadoocurrencia)
        {
            case 1:
                //se percurso vazio
                if (percurso.isEmpty()) {
                    percurso.add(new Posicao(l, d));
                    ocurrencia.setLocalInicial(l);
                }
                else
                {
                    //pegar na ultima posiçao e verificar se ja passaram os minutos
                    GregorianCalendar actual = new GregorianCalendar();
                    actual.setTime(d);
                    GregorianCalendar ultima = new GregorianCalendar();
                    ultima.setTime(percurso.get(percurso.size() - 1).getData());
                    ultima.add(Calendar.MINUTE, configgerais.getTempoLocal());
                    if (actual.before(ultima)) {
                        percurso.add(new Posicao(l, d));
                    } else {
                        ocurrencia.setLocalChegadaSocorro(percurso.get(percurso.size() - 1).getLocal());
                        ocurrencia.setHoraChegadaSocorro(percurso.get(percurso.size() - 1).getData());
                        estadoocurrencia = 2;
                        indexChegadalocal = percurso.size()-1;
                        percurso.add(new Posicao(l, d));
                    }
                }

                break;
            case 2:
                //ver se me mexi mais de
                if (Distancias.DistanciaEntrePontos(
                        ocurrencia.getLocalChegadaSocorro().getLatitude(),
                        ocurrencia.getLocalChegadaSocorro().getLongitude(),
                        l.getLatitude(),
                        l.getLongitude())*1000 >= configgerais.getDistanciaLocal() )
                {
                    ocurrencia.setHoraSaidaSocorro(d);
                    estadoocurrencia = 3;
                    indexSaidalocal=percurso.size()-1;
                }
                percurso.add(new Posicao(l, d));
                break;
            case 3:
                //pegar na ultima posiçao e verificar se ja passaram os minutos
                GregorianCalendar actual = new GregorianCalendar();
                actual.setTime(d);
                GregorianCalendar ultima = new GregorianCalendar();
                ultima.setTime(percurso.get(percurso.size() - 1).getData());
                ultima.add(Calendar.MINUTE, configgerais.getTempoLocal());
                if (actual.before(ultima)) {
                    percurso.add(new Posicao(l, d));
                } else {
                    Hospital h = GetHospitalProximo(l);
                    if(h != null) {
                        ocurrencia.setHospitalDestino(h);
                        ocurrencia.setHoraChegadaHospital(percurso.get(percurso.size() - 1).getData());
                        estadoocurrencia = 4;
                        indexChegadaHospital = percurso.size()-1;
                    }
                    percurso.add(new Posicao(l,d));

                }
                break;
            case 4:
                // ja sai do hospital metros
                if (Distancias.DistanciaEntrePontos(
                        ocurrencia.getHospitalDestino().getLatitude(),
                        ocurrencia.getHospitalDestino().getLongitude(),
                        l.getLatitude(),
                        l.getLongitude())*1000 >= configgerais.getDistanciaHospital())
                {
                    percurso.add(new Posicao(l,d));
                    ocurrencia.setHoraFinal(d);
                    estadoocurrencia = 0;
                    t.showNotification();
                    return false;
                }
                else
                    percurso.add(new Posicao(l, d));
                break;
        }
        t.showNotification();
        return true;
    }
    public boolean proximoEstado() throws SQLException {

        switch(estadoocurrencia)
        {
            case 1:
                ocurrencia.setLocalChegadaSocorro(percurso.get(percurso.size() - 1).getLocal());
                ocurrencia.setHoraChegadaSocorro(new Date());
                estadoocurrencia = 2;
                indexChegadalocal=percurso.size();
                break;
            case 2:
                ocurrencia.setHoraSaidaSocorro(new Date());
                estadoocurrencia = 3;
                indexSaidalocal=percurso.size();
                break;
            case 3:
                ocurrencia.setHoraChegadaHospital(new Date());
                estadoocurrencia = 4;
                indexChegadaHospital=percurso.size();
                break;
            case 4:
                ocurrencia.setHoraFinal(new Date());
                estadoocurrencia = 0;
                t.showNotification();
                return false;
        }
        t.showNotification();
        return true;
    }
    @Override
    public String toString() {
        String str = "Estado Da Ocurrencia = " ;
        str +=getEstadoString();
        str +=" \n\n";
        str +="Tamanho do Percurso = " + percurso.size();
        str +=" \n\n";
        str += ocurrencia.toString();

        return str;
    }
    public String getEstadoString()
    {
        String str = "";
        switch(estadoocurrencia)
        {
            case 0:
                str += "Disponivel";
                break;
            case 1:
                str += "A Caminho do Local de Socorro";
                break;
            case 2:
                str += "No Local de Socorro";
                break;
            case 3:
                str += "A Caminho do Hospital";
                break;
            case 4:
                str += "No Hospital";
                break;
        }
        return str;
    }


    private Hospital GetHospitalProximo(Location l)
    {
        double min = Double.MAX_VALUE;
        Hospital hh = null;
        for (Hospital h : listaHospitais) {
            double Distancia = Distancias.DistanciaEntrePontos(
                    h.getLatitude(),
                    h.getLongitude(),
                    l.getLatitude(),
                    l.getLongitude());
            if (Distancia<min) {
                min = Distancia;
                hh = h;
            }
        }
        return hh;
    }

}