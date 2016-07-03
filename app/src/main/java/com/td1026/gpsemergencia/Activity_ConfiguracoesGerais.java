package com.td1026.gpsemergencia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.td1026.gpsemergencia.Dados.ConfigGerais;
import com.td1026.gpsemergencia.MetodosAuxiliares.EscritaLeituraFicheiros;
import com.td1026.gpsemergencia.MetodosAuxiliares.Logs;

public class Activity_ConfiguracoesGerais extends AppCompatActivity {

    //-------------------------------------------Variaveis-------------------------------------------
    Activity_ConfiguracoesGerais t = this;
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Metodos da Classe-------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_activity__configuracoes_gerais);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //Definir Click de botao
            final Button btaddhospital = (Button) findViewById(R.id.cg_bt_Submeter);
            btaddhospital.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        TextView tv_DistanciaLocal = (TextView) findViewById(R.id.cg_et_RaioLocal);
                        TextView tv_DistanciaHospital = (TextView) findViewById(R.id.cg_et_RaioHospital);
                        TextView tv_TempoLocal = (TextView) findViewById(R.id.cg_et_TempoLocal);
                        TextView tv_TempoHospital = (TextView) findViewById(R.id.cg_et_TempoHospital);
                        TextView tv_Local = (TextView) findViewById(R.id.cg_et_Local);
                        //retirar espaços brancos a mais e ver se nao esta vazio o campo local
                        if (!(tv_Local.getText().toString().trim().replaceAll("(\\s)+", "$1").isEmpty())) {
                            //ver se nao estam vazio os restantes campos
                            if (!tv_DistanciaLocal.getText().toString().isEmpty() &&
                                    !tv_DistanciaHospital.getText().toString().isEmpty() &&
                                    !tv_TempoLocal.getText().toString().isEmpty() &&
                                    !tv_TempoHospital.getText().toString().isEmpty()) {
                                //Guardar dados
                                ConfigGerais config = new ConfigGerais(
                                        Integer.parseInt(tv_DistanciaLocal.getText().toString()),
                                        Integer.parseInt(tv_DistanciaHospital.getText().toString()),
                                        Integer.parseInt(tv_TempoLocal.getText().toString()),
                                        Integer.parseInt(tv_TempoHospital.getText().toString()),
                                        tv_Local.getText().toString());
                                EscritaLeituraFicheiros.EscreverConfigGerais(config);
                                Toast.makeText(Activity_ConfiguracoesGerais.this, getString(R.string.lb_ConfiguracoesGuardadascomSucesso),
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        Toast.makeText(Activity_ConfiguracoesGerais.this, getString(R.string.lb_FaltaAlgumaInformaçao),
                                Toast.LENGTH_SHORT).show();
                    }catch (Exception e ) {
                        Logs.erro(t.getLocalClassName(),e.getMessage());
                    }
                }

            });
        }catch (Exception e) {
            Logs.erro(this.getLocalClassName(),e.getMessage());
        }
    }

    @Override
    protected void onResume()
    {
        try {
            super.onResume();
            TextView tv_DistanciaLocal = (TextView) findViewById(R.id.cg_et_RaioLocal);
            TextView tv_DistanciaHospital = (TextView) findViewById(R.id.cg_et_RaioHospital);
            TextView tv_TempoLocal = (TextView) findViewById(R.id.cg_et_TempoLocal);
            TextView tv_TempoHospital = (TextView) findViewById(R.id.cg_et_TempoHospital);
            TextView tv_Local = (TextView) findViewById(R.id.cg_et_Local);
            //Ler dados e desenhar no ecra
            ConfigGerais config = EscritaLeituraFicheiros.LerConfigGerais();
            tv_DistanciaLocal.setText(((String.valueOf(config.getDistanciaLocal()).subSequence(0, ((String.valueOf(config.getDistanciaLocal()).length()))))));
            tv_DistanciaHospital.setText(((String.valueOf(config.getDistanciaHospital()).subSequence(0, ((String.valueOf(config.getDistanciaHospital()).length()))))));
            tv_TempoLocal.setText(((String.valueOf(config.getTempoLocal()).subSequence(0,((String.valueOf(config.getTempoLocal()).length()))))));
            tv_TempoHospital.setText(((String.valueOf(config.getTempoHospital()).subSequence(0,((String.valueOf(config.getTempoHospital()).length()))))));
            tv_Local.setText(((String.valueOf(config.getCidade()).subSequence(0,((String.valueOf(config.getCidade()).length()))))));
        } catch (Exception e) {
            Logs.erro(this.getLocalClassName(), e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------


    //-------------------------------------------Outros Metodos-------------------------------------------

    //--------------------------------------------------------------------------------------
}
