package com.td1026.gpsemergencia.MetodosAuxiliares;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Telmo on 05/11/2015.
 * o
 */
public class Permissoes
{

    public static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    public static final String[] CAMERA_PERMS={
            Manifest.permission.CAMERA
    };
    public static final String[] CONTACTS_PERMS={
            Manifest.permission.READ_CONTACTS
    };
    public static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static final int INITIAL_REQUEST=1337;

    public static final int CAMERA_REQUEST=INITIAL_REQUEST+1;
    public static final int CONTACTS_REQUEST=INITIAL_REQUEST+2;
    public static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    public static boolean canAccessLocation(Context c) {
        return(hasPermission(c,Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public static boolean canAccessCamera(Context c) {
        return(hasPermission(c,Manifest.permission.CAMERA));
    }

    public static boolean canAccessContacts(Context c) {
        return(hasPermission(c,Manifest.permission.READ_CONTACTS));
    }

    public static boolean hasPermission(Context c , String perm) {
        return(PackageManager.PERMISSION_GRANTED == checkSelfPermission(c, perm));
    }
}
