package com.td1026.gpsemergencia;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.td1026.gpsemergencia.Dados.Hospital;
import com.td1026.gpsemergencia.MetodosAuxiliares.EscritaLeituraFicheiros;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Hospitais extends AppCompatActivity {

    //-------------------------------------------Variaveis-------------------------------------------
    String item;
    Activity_Hospitais t = this;
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Metodos da Classe-------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //----Defenir clicl de botao
        Button btaddhospital = (Button) findViewById(R.id.ch_bt_AdicionarHospital);
        btaddhospital.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ser = new Intent(t, Activity_DescricaoHospital.class);
                startActivity(ser);
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            item = null;
            //----Carregar Dados
            List<Hospital> listhospital = null;
            listhospital = EscritaLeituraFicheiros.LerHospitais();
            //----Desenhar Dados na Lista
            if(listhospital == null)
                return;
            ListView listview = (ListView) findViewById(R.id.ch_lv_ListaPrincipal);
            ArrayList<String> list = new ArrayList<>();
            for (Hospital h: listhospital) {
                list.add(h.getNome());
            }
            //----Defenir click na lista
            StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);
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
                                    .withEndAction(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        //Carregar dados
                                                        final List<Hospital> aux;
                                                        int index = -1;
                                                        aux = EscritaLeituraFicheiros.LerHospitais();
                                                        for (int i = 0; i < aux.size(); i++) {
                                                            if (aux.get(i).getNome().equals(item)) {
                                                                index = i;
                                                                break;
                                                            }
                                                        }
                                                        //Lançar popup
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(t);
                                                        final int finalIndex = index;
                                                        builder
                                                                .setTitle(getString(R.string.lb_TemacertezaquepretendeApagar))
                                                                .setMessage(getString(R.string.lb_Nome) + ": " + aux.get(index).getNome() + "  " + getString(R.string.lb_Lat) + ": " + aux.get(index).getLatitude() + "  " + getString(R.string.lb_Lon) + ": " + aux.get(index).getLongitude())
                                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                                //Se a resposta for sim
                                                                .setPositiveButton(getString(R.string.lb_Sim), new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        try {
                                                                            //remover
                                                                            aux.remove(finalIndex);
                                                                            EscritaLeituraFicheiros.EscreverHospitais(aux);
                                                                            Toast.makeText(Activity_Hospitais.this, item + getString(R.string.lb_ApagadocomSucesso),
                                                                                    Toast.LENGTH_SHORT).show();
                                                                            t.onResume();

                                                                        } catch (Exception e) {
                                                                            Logs.erro(t.getLocalClassName(), e.getMessage());
                                                                        }
                                                                    }
                                                                })
                                                                //se a resposta for nao
                                                                .setNegativeButton(getString(R.string.lb_Nao), null)
                                                                .show();
                                                    }catch (Exception e) {
                                                        Logs.erro(t.getLocalClassName(), e.getMessage());
                                                    }
                                                }
                                            }
                                    );
                        }
                    }
            );
        } catch (IOException e) {
            Logs.erro(this.getLocalClassName(), e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Outros Metodos-------------------------------------------
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
