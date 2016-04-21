package com.td1026.gpsemergencia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.td1026.gpsemergencia.Dados.Hospital;
import com.td1026.gpsemergencia.MetodosAuxiliares.EscritaLeituraFicheiros;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;

import java.util.List;

public class Activity_DescricaoHospital extends AppCompatActivity {

    //-------------------------------------------Variaveis-------------------------------------------
    Activity_DescricaoHospital t = this;
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Metodos da Classe-------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_descricao_hospital);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Logs.fluxo(this.getLocalClassName(), "onCreate");

            //defenir click do butao
            Button btaddhospital = (Button) findViewById(R.id.dh_bt_Submeter);
            btaddhospital.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        double lblat;
                        double lblon;
                        String lbnome;
                        //retirar espaços brancos a mais e ver se nao esta vazio o campo nome
                        if (!((EditText) findViewById(R.id.dh_et_Nome)).getText().toString().trim().replaceAll("(\\s)+", "$1").isEmpty()) {
                            lbnome = ((EditText) findViewById(R.id.dh_et_Nome)).getText().toString();
                            //ver se nao esta vazio o campo Latitude
                            if (!((EditText) findViewById(R.id.dh_et_Latitude)).getText().toString().isEmpty()) {
                                lblat = Double.parseDouble(((EditText) findViewById(R.id.dh_et_Latitude)).getText().toString());
                                //ver se nao esta vazio o campo Longitude
                                if (!((EditText) findViewById(R.id.dh_et_Longitude)).getText().toString().isEmpty()) {
                                    lblon = Double.parseDouble(((EditText) findViewById(R.id.dh_et_Longitude)).getText().toString());
                                    List<Hospital> aux;
                                    //Carregar Dados
                                    aux = EscritaLeituraFicheiros.LerHospitais();
                                    //ver se ja existe com este Nome
                                    for (int i = 0; i < aux.size(); i++) {
                                        if (aux.get(i).getNome().equals(lbnome)) {
                                            Toast.makeText(Activity_DescricaoHospital.this, getString(R.string.lb_Nomejaexiste),
                                                    Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    //Guardar Dados
                                    aux.add(new Hospital(lbnome, lblat, lblon));
                                    EscritaLeituraFicheiros.EscreverHospitais(aux);
                                    Toast.makeText(Activity_DescricaoHospital.this, getString(R.string.lb_AdicionadocomSucesso),
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        Toast.makeText(Activity_DescricaoHospital.this, getString(R.string.lb_FaltaAlgumaInformaçao),
                                Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Logs.erro(t.getLocalClassName(), e.getMessage());
                    }
                }
            });
        }catch (Exception e){
            Logs.erro(this.getLocalClassName(),e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------
    //-------------------------------------------Outros Metodos-----------------------------

    //--------------------------------------------------------------------------------------
}
