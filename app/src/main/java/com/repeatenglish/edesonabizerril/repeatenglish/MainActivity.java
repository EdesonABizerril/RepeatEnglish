package com.repeatenglish.edesonabizerril.repeatenglish;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvContador, tvDica, tvStatus;
    private ImageButton imgbInfo;
    private int contVezes, contSessao, contReset, contToast = 0;
    private Button btVContMaisUm, reset;
    private AlertDialog.Builder dialogInfo;

    private final int VEZES = 20;

    private String vetor[] = {"LEITURA","LEITURA + ESCUTA", "ESCUTA", "LEITURA + ESCUTA","Fim da revisão"};
    private Dialog activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContador = (TextView) findViewById(R.id.tvContID);
        tvDica = (TextView) findViewById(R.id.tvDicaID);
        tvStatus = (TextView) findViewById(R.id.tvStatusID);
        imgbInfo = (ImageButton) findViewById(R.id.btInfoID);
        btVContMaisUm = (Button) findViewById(R.id.btContadorID);
        reset = (Button) findViewById(R.id.btresetID);

            imgbInfo.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {   //iniciar a Activity Info

                    //Criando dialogo de informações do cronograma
                    dialogInfo = new AlertDialog.Builder(MainActivity.this);
                    dialogInfo.setTitle("CRONOGRAMA DE REPETIÇÃO")
                              .setMessage("- 01 sessão de Leitura (linha por linha)\n- 20 sessões de Leitura + Escuta\n- 20 sessões de Escuta\n- 20 sessões de Leitura + Imitação\n\nOBS: Revisar o deck do Anki diariamente")
                              .create().show();
                }
            });

        tvContador.setText(String.valueOf(contVezes));

        btVContMaisUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contVezes++;
                    if (contSessao == 0){ //Sessão de leitura
                           tvStatus.setText(vetor[0]);
                           tvContador.setText(String.valueOf(contVezes));
                           contSessao++;   //Para acontecer uma unica vez
                           contVezes = -1;
                     }else{
                           if (contVezes <= VEZES && contSessao >= 1 && contSessao < 4){ //com && contSessao >= 2, acontece da segunda sessão em diante
                                   contReset = 0;   //zero a variavel reset
                                   tvContador.setText(String.valueOf(contVezes));
                                   tvStatus.setText(vetor[contSessao]);
                           }if(contVezes > VEZES && contSessao >= 1 && contSessao < 4){
                                   contReset = 0;   //zero a variavel reset
                                   contVezes = 0;
                                   tvContador.setText(String.valueOf(contVezes));
                                   contSessao++;
                                   tvStatus.setText(vetor[contSessao]);
                           }if(contSessao > 3 && contSessao >= 1){
                                   tvStatus.setText(vetor[4]);
                                   contVezes = 0;
                                   tvContador.setText(String.valueOf(contVezes));
                            if(contToast < 1) {
                                   Toast.makeText(MainActivity.this, "Repetições finalizadas,\nresete para continuar", Toast.LENGTH_SHORT).show();
                                   contToast++;
                               }
                           }
                       }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //botão resete
                contVezes = 0;
                contSessao = contToast = 0;
                tvStatus.setText("Inicie a revisão!");
                tvContador.setText(String.valueOf(contVezes));
                if(contReset < 1) {
                    Toast.makeText(MainActivity.this, "Resete realizado!", Toast.LENGTH_SHORT).show();
                    contReset += 1;
                }
            }
        });

    }

    public Dialog getActivity() {
        return activity;
    }
}
