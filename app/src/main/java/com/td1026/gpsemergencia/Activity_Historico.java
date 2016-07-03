package com.td1026.gpsemergencia;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.td1026.gpsemergencia.BaseDados.bd_Ocurrencia;
import com.td1026.gpsemergencia.BaseDados.bd_Ocurrencia_Helper;
import com.td1026.gpsemergencia.BaseDados.bd_Trajecto;
import com.td1026.gpsemergencia.MetodosAuxiliares.Formatos;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Historico extends AppCompatActivity {

    //--------------------------------------------------------------------------------------
    //-------------------------------------------Variaveis----------------------------------
    //--------------------------------------------------------------------------------------
    String item;
    Activity_Historico t = this;
    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    //-------------------------------------------Metodos da Classe--------------------------
    //--------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_historico);
            item = null;

            //----Ler dados da base de dados
            List<bd_Ocurrencia> lista;
            bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(t, bd_Ocurrencia_Helper.class);
            Dao<bd_Ocurrencia, Long> todoDao = todoOpenDatabaseHelper.getDao_bd_Ocurrencia();
            lista = todoDao.queryForAll();
            if(lista == null)
                return;

            //----Desenhar Dados na Lista
            ListView listview = (ListView) findViewById(R.id.h_lv_ListaOcurrencias);
            ArrayList<String> list = new ArrayList<>();
            for (bd_Ocurrencia h: lista) {
                list.add(Formatos.getbd_OcurrenciaFormat(h));
            }
            StableArrayAdapter adapter = new StableArrayAdapter(this , android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);

            //----Definir Click na Lista
            listview.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                            item = (String) parent.getItemAtPosition(position);
                            view
                                    .animate()
                                    .setDuration(500)
                                    .alpha(5)
                                    .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                final List<bd_Ocurrencia> aux;
                                                int index = -1;

                                                //Descubrir Item Selecionado
                                                bd_Ocurrencia_Helper todoOpenDatabaseHelper = OpenHelperManager.getHelper(t, bd_Ocurrencia_Helper.class);
                                                Dao<bd_Ocurrencia, Long> todoDao = todoOpenDatabaseHelper.getDao_bd_Ocurrencia();
                                                aux = todoDao.queryForAll();
                                                for (int i = 0; i < aux.size(); i++) {
                                                    if (Formatos.getbd_OcurrenciaFormat(aux.get(i)).equals(item)) {
                                                        index = i;
                                                        break;
                                                    }
                                                }
                                                //Abrir Janela popup
                                                AlertDialog.Builder builder = new AlertDialog.Builder(t);
                                                final int finalIndex = index;
                                                List<bd_Trajecto> lista;
                                                Dao<bd_Trajecto, Long> todoDaoTrajecto = todoOpenDatabaseHelper.getDao_bd_Trajecto();
                                                lista = todoDaoTrajecto.queryForEq("id_ocorrencia",aux.get(finalIndex).getId());
                                                String str = "\n\nPercurso:    (Total)"+ lista.size()+"\n\n";
                                                builder
                                                        .setTitle(getString(R.string.lb_DescricaodaOcurrencia))
                                                        .setMessage(aux.get(finalIndex).toString() + str)
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .setNeutralButton(getString(R.string.lb_OK), null)
                                                        .show();
                                            } catch (Exception e) {
                                                Logs.erro(t.getLocalClassName(), e.getMessage());
                                            }
                                        }
                                    });
                        }
                    }
            );
        } catch (Exception e) {
            Logs.erro(this.getLocalClassName(), e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    //-------------------------------------------Outros Metodos-----------------------------
    //--------------------------------------------------------------------------------------
    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<>();
        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
    //--------------------------------------------------------------------------------------
}
