package com.td1026.gpsemergencia.BaseDados;


import com.td1026.gpsemergencia.Dados.Dados_Ocurrencia;
import com.td1026.gpsemergencia.Dados.Posicao;
import com.td1026.gpsemergencia.MetodosAuxiliares.Distancias;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Telmo on 02/11/2015.
 * o
 */
public class Operacoes
{


    public static Dados_Ocurrencia PrepararOcurrencia(List<Posicao> percurso, Dados_Ocurrencia ocurrencia, int indexchegadaLocal, int indexSaidaLocal, int indexHospital) throws SQLException {
        int i;
        for (i=1;i<indexchegadaLocal;i++)
        {
            double total = 0;
            total += Distancias.DistanciaEntrePontos(
                    percurso.get(i - 1).getLocal().getLatitude(),
                    percurso.get(i - 1).getLocal().getLongitude(),
                    percurso.get(i).getLocal().getLatitude(),
                    percurso.get(i).getLocal().getLongitude());
            if(percurso.get(i).getLocal().getLatitude()== ocurrencia.getLocalChegadaSocorro().getLatitude() &&
                    percurso.get(i).getLocal().getLongitude()==ocurrencia.getLocalChegadaSocorro().getLongitude())
            {
                ocurrencia.setDistanciaSocorro(total/1000.0);
            }
        }
        for (i = indexSaidaLocal+1;i<indexHospital;i++)
        {
            double total = 0;
            total += Distancias.DistanciaEntrePontos(
                    percurso.get(i-1).getLocal().getLatitude(),
                    percurso.get(i-1).getLocal().getLongitude(),
                    percurso.get(i).getLocal().getLatitude(),
                    percurso.get(i).getLocal().getLongitude());
            if(percurso.get(i).getLocal().getLatitude()== ocurrencia.getLocalChegadaSocorro().getLatitude() &&
                    percurso.get(i).getLocal().getLongitude()==ocurrencia.getLocalChegadaSocorro().getLongitude())
            {
                ocurrencia.setDistanciaHospital(total / 1000.0);
            }
        }


        return ocurrencia;





    }





}
