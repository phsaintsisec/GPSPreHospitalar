package com.td1026.gpsemergencia;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.td1026.gpsemergencia.BaseDados.BDbackup;
import com.td1026.gpsemergencia.Dados.OcurrenciaActual;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;

import java.util.Date;

public class Activity_ServicePrincipal extends Service {

    //-------------------------------------------Variaveis-------------------------------------------
    private static final int NOTIFICATION = 1;
    public static Boolean SERVICERUNNING = false;
    public static Boolean GUARDAR = false;
    public static OcurrenciaActual DADOS = null;
    private LocationListener locationlistener;
    public Activity_ServicePrincipal t = this;
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Metodos da Classe-------------------------------------------
    public Activity_ServicePrincipal() {
    }

    @Override
    public void onCreate() {
        Logs.fluxo("Activity_ServicePrincipal", "onCreate");
        try {
            super.onCreate();
            //-----------Iniciar os Dados---------------------
            DADOS = new OcurrenciaActual(this);
            //-----------Receber os dados de Gps--------------
            LocationManager locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationlistener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    try {
                        //Se addpercurso returna falso  = fim do percurso
                        if (!DADOS.addPercurso(location, new Date())) {
                            GUARDAR = true;
                            //Destruir Servico
                            onDestroy();
                        }
                    } catch (Exception e) {
                        Logs.erro(t.getPackageName(), e.getMessage());
                    }
                }
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                public void onProviderEnabled(String provider) {
                }
                public void onProviderDisabled(String provider) {
                }
            };
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, locationlistener);
            }
            SERVICERUNNING = true;
            //Lançar Notificaçao na barra de notificaçoes
            showNotification();
        } catch (Exception e) {
            Logs.erro(this.getPackageName(), e.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs.fluxo(this.getPackageName(), "onDestroy");
        //Deixar de receber dados de gps
        if (locationlistener != null) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                locationManager.removeUpdates(locationlistener);
            }
        }
        try{
            if(GUARDAR) {
                //Guardar dados na Base de Dados
                BDbackup.gravarLocalmente(t, DADOS);
            }
        } catch (Exception e) {
            Logs.erro(this.getPackageName(), e.getMessage());
        }
        SERVICERUNNING = false;
        //Apagar Notificaçao da barra de notificaçoes
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION);
    }
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Outros Metodos-------------------------------------------
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification() {
        //---------------------Variaveis---------------------
        String str = DADOS.getEstadoString();
        //Lançar notificaçao
        Intent intent = new Intent(this, Activity_Main.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification n  = new Notification.Builder(this)
                //Titulo
                .setContentTitle(getString(R.string.app_name))
                //Texto
                .setContentText(str)
                //Icon
                .setSmallIcon(R.drawable.ic_barandtab)
                //Activity que abre ao click
                .setContentIntent(pIntent)
                //Nao Desaparecer depois de Click
                .setAutoCancel(false)
                .build();
        //nao deixar que seja removida
        n.flags |= Notification.FLAG_NO_CLEAR;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION, n);
    }
    //--------------------------------------------------------------------------------------

}
