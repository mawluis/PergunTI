package com.example.mawluis.pergunti.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

import java.net.HttpURLConnection;
import java.net.URL;

public class telaCriacaoPerg extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private long mLastClickTime = 0; //macete para evitar criação de vários jogos com duplo clique.
    String[] tema={"-----","rede","programação","banco","geral","sistema"};
    EditText edtTema;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criacao_perg);

        Button btnCriaPerg = (Button)findViewById(R.id.btnCriarPerg);

        EditText edtPerg = (EditText)findViewById(R.id.edtPerg);
        EditText edtOpt1 = (EditText)findViewById(R.id.edtOpt1);
        EditText edtOpt2 = (EditText)findViewById(R.id.edtOpt2);
        EditText edtOpt3 = (EditText)findViewById(R.id.edtOpt3);
        EditText edtOpt4 = (EditText)findViewById(R.id.edtOpt4);
        EditText edtResp = (EditText)findViewById(R.id.edtResp);
        edtTema = (EditText)findViewById(R.id.edtTema);
        EditText edtComplex = (EditText)findViewById(R.id.edtComplex);
        EditText edtTempo = (EditText)findViewById(R.id.edtTempo);
        Spinner spinner = (Spinner)findViewById(R.id.spinTema);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tema);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        btnCriaPerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {  //macete para evitar criação de vários jogos com duplo clique.
                    return;                                                  //macete para evitar criação de vários jogos com duplo clique.
                }                                                            //macete para evitar criação de vários jogos com duplo clique.
                mLastClickTime = SystemClock.elapsedRealtime();              //macete para evitar criação de vários jogos com duplo clique.


                if (    String.valueOf(edtPerg.getText()).equals("") || String.valueOf(edtOpt1.getText()).equals("") || //verificação de
                        String.valueOf(edtOpt2.getText()).equals("") || String.valueOf(edtOpt3.getText()).equals("") || //todos os
                        String.valueOf(edtOpt4.getText()).equals("") || String.valueOf(edtComplex.getText()).equals("") || //campos preenchidos
                        String.valueOf(edtTempo.getText()).equals("") || String.valueOf(edtTema.getText()).equals("")) {
                    Toast.makeText(telaCriacaoPerg.this, "Preencha todos os campos, apressadinho.", Toast.LENGTH_LONG).show();
                }else{ //fazer se tiver tudo preenchido


                if (Integer.parseInt(String.valueOf(edtResp.getText())) == 1 || Integer.parseInt(String.valueOf(edtResp.getText())) == 2 //verificando se está
                        || Integer.parseInt(String.valueOf(edtResp.getText())) == 3 || Integer.parseInt(String.valueOf(edtResp.getText())) == 4) {// 1 a 4


                    final ProgressDialog dialog = new ProgressDialog(telaCriacaoPerg.this); //,"","Realizando consulta",true,true);
                    dialog.setTitle("Aguarde...");
                    dialog.setMessage("Verificando sua pergunta");
                    dialog.setIcon(R.drawable.ampulheta);
                    dialog.show();


                    new Thread() {
                        public void run() {
                            try {
                                URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/f/f4/HelpPage_IconPack-03.png");
                                HttpURLConnection connection;
                                connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);
                                connection.connect();  //    treta que funcionou para fazer loading
                                //InputStream input = connection.getInputStream();
                                //final Bitmap imagem = BitmapFactory.decodeStream(input);
                                handler.post(new Runnable() {
                                    public void run() {

                                        global b = new global();
                                        conexaoBD a = new conexaoBD();
                                        a.novaPerg(String.valueOf(edtPerg.getText()),
                                                String.valueOf(edtOpt1.getText()),
                                                String.valueOf(edtOpt2.getText()),
                                                String.valueOf(edtOpt3.getText()),
                                                String.valueOf(edtOpt4.getText()),
                                                Integer.parseInt(String.valueOf(edtResp.getText())),
                                                b.getLogin(),
                                                Integer.parseInt(String.valueOf(edtComplex.getText())),
                                                String.valueOf(edtTema.getText()).toLowerCase(),//tema para minúsculo.
                                                Integer.parseInt(String.valueOf(edtTempo.getText())));

                                    }
                                });
                            } catch (Exception e) {
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.setMessage("Feito!");
                                    dialog.dismiss();

                                    if (global.isPergCriada()) {
                                        AlertDialog.Builder dlg = new AlertDialog.Builder(telaCriacaoPerg.this);
                                        dlg.setCancelable(false);
                                        dlg.setTitle("Sucesso");
                                        dlg.setMessage("Pergunta nº" + global.getNumPergunta() + " criada");
                                        dlg.setNeutralButton("Ok!", null);
                                        AlertDialog alert = dlg.create();
                                        alert.show();
                                        global.setPergCriada(false);
                                    } else {
                                        Toast.makeText(telaCriacaoPerg.this, "Erro: \nPergunta não criada", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }.start();

                } else {
                    Toast.makeText(telaCriacaoPerg.this, "Erro: \nResposta só aceita valores de 1 a 4.", Toast.LENGTH_LONG).show();
                }
            }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (tema[position].equals("-----")){
            edtTema.setText("");
            edtTema.setHint("Tema não está listado?");
             }else{
            edtTema.setText(tema[position]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
