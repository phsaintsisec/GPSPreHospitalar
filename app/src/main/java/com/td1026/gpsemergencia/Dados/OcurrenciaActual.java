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
import java.util.List;

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
    private int estadoocorrencia;
    private List<Posicao> percurso;
    private Dados_Ocurrencia ocorrencia;
    private int indexChegadalocal = -1;
    private int indexSaidalocal = -1;
    private int indexChegadaHospital = -1;
    private Activity_ServicePrincipal t;

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Construtores-------------------------------------------

    public OcurrenciaActual(Activity_ServicePrincipal c) throws IOException {
        configgerais = EscritaLeituraFicheiros.LerConfigGerais();
        listaHospitais = EscritaLeituraFicheiros.LerHospitais();
        estadoocorrencia = 1;
        percurso = new ArrayList<>();
        ocorrencia = new Dados_Ocurrencia();
        ocorrencia.setHoraInicial(new Date());
        t = c;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Gets e Sets-------------------------------------------

    public Dados_Ocurrencia getOcorrencia() {
        return ocorrencia;
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
    public int getEstadoocorrencia() {
        return estadoocorrencia;
    }

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Metodos-------------------------------------------

    public boolean addPercurso(Location l, Date d) throws SQLException {
        switch (estadoocorrencia)
        {
            case 1:
                //se percurso vazio
                if (percurso.isEmpty()) {
                    percurso.add(new Posicao(l, d));
                    ocorrencia.setLocalInicial(l);
                }
                else
                {
                    //pegar na ultima posiçao e verificar se ja passaram os minutos
                    GregorianCalendar actual = new GregorianCalendar();
                    actual.setTime(d);
                    GregorianCalendar ultima = new GregorianCalendar();
                    ultima.setTime(percurso.get(percurso.size() - 1).getData());
                    ultima.add(Calendar.MINUTE, configgerais.getTempoLocal());
                    Logs.fluxo("case 1 if ", actual.before(ultima) + "");
                    Logs.fluxo("case 1 if ", "actual" + actual.before(ultima) + Formatos.getDataHoraMinSegFormat(actual.getTime()));
                    Logs.fluxo("case 1 if ", "última" + actual.before(ultima) + Formatos.getDataHoraMinSegFormat(percurso.get(percurso.size() - 1).getData()));
                    Logs.fluxo("case 1 if ", "última + 5" + actual.before(ultima) + Formatos.getDataHoraMinSegFormat(ultima.getTime()));
                    if (actual.before(ultima)) {
                        percurso.add(new Posicao(l, d));
                    } else {
                        ocorrencia.setLocalChegadaSocorro(percurso.get(percurso.size() - 1).getLocal());
                        ocorrencia.setHoraChegadaSocorro(percurso.get(percurso.size() - 1).getData());
                        estadoocorrencia = 2;
                        indexChegadalocal = percurso.size()-1;
                    }
                }
                break;
            case 2:
                //ver se me mexi mais de metros
                if (Distancias.DistanciaEntrePontos(
                        ocorrencia.getLocalChegadaSocorro().getLatitude(),
                        ocorrencia.getLocalChegadaSocorro().getLongitude(),
                        l.getLatitude(),
                        l.getLongitude()) >= configgerais.getDistanciaLocal() )
                {
                    ocorrencia.setHoraSaidaSocorro(d);
                    estadoocorrencia = 3;
                    indexSaidalocal=percurso.size()-1;
                }
                else
                    percurso.add(new Posicao(l, d));
                break;
            case 3:
                for (Hospital h : listaHospitais)
                {
                    //se estou a menos de metros do hospital
                    if (Distancias.DistanciaEntrePontos(
                            h.getLatitude(),
                            h.getLongitude(),
                            l.getLatitude(),
                            l.getLongitude()) <= configgerais.getDistanciaHospital())
                    {
                        //pegar na ultima posiçao e verificar se ja passaram os minutos
                        GregorianCalendar actual = new GregorianCalendar();
                        actual.setTime(d);
                        GregorianCalendar ultima = new GregorianCalendar();
                        ultima.setTime(percurso.get(percurso.size()-1).getData());
                        ultima.add(Calendar.MINUTE,configgerais.getTempoHospital());
                        if(actual.after(ultima)) {
                            ocorrencia.setHoraChegadaHospital(percurso.get(percurso.size()-1).getData());
                            ocorrencia.setHospitalDestino(h);
                            estadoocorrencia = 4;
                            indexChegadaHospital=percurso.size()-1;
                        }
                        else
                            percurso.add(new Posicao(l, d));
                    }
                    else
                        percurso.add(new Posicao(l, d));
                }
                break;
            case 5:
                // ja sai do hospital metros
                if (Distancias.DistanciaEntrePontos(
                        ocorrencia.getHospitalDestino().getLatitude(),
                        ocorrencia.getHospitalDestino().getLongitude(),
                        l.getLatitude(),
                        l.getLongitude()) >= configgerais.getDistanciaHospital())
                {
                    ocorrencia.setHoraFinal(d);
                    estadoocorrencia = 0;
                    ocorrencia = Operacoes.PrepararOcurrencia(percurso, ocorrencia, indexChegadalocal, indexSaidalocal, indexChegadaHospital);
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

        switch(estadoocorrencia)
        {
            case 1:
                ocorrencia.setHoraChegadaSocorro(new Date());
                estadoocorrencia = 2;
                indexChegadalocal=percurso.size();
                break;
            case 2:
                ocorrencia.setHoraSaidaSocorro(new Date());
                estadoocorrencia = 3;
                indexSaidalocal=percurso.size();
                break;
            case 3:
                ocorrencia.setHoraChegadaHospital(new Date());
                estadoocorrencia = 4;
                indexChegadaHospital=percurso.size();
                break;
            case 4:
                ocorrencia.setHoraFinal(new Date());
                estadoocorrencia = 0;
                t.showNotification();
                return false;
        }
        t.showNotification();
        return true;
    }
    @Override
    public String toString() {
        String str = "Estado Da Ocorrência = " ;
        str +=getEstadoString();
        str +=" \n\n";
        str +="Tamanho do Percurso = " + percurso.size();
        str +=" \n\n";
        str += ocorrencia.toString();

        return str;
    }
    public String getEstadoString()
    {
        String str = "";
        switch(estadoocorrencia)
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


}