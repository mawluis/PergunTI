package com.example.mawluis.pergunti.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import static com.example.mawluis.pergunti.global.global.hashPassword;


public class telaCadastro extends AppCompatActivity {

    EditText novoNome, novoLogin, novaSenha, novaSenha2, novoEmail;
    Button btnCriarNovo;
    String tipo = "";
    String senha = "";
    String senha2 ="";
    //int completed =0;
    private Handler handler = new Handler();
    //private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        novoNome = (EditText)findViewById(R.id.novoNome);
        novoLogin = (EditText)findViewById(R.id.novoLogin);
        novaSenha = (EditText)findViewById(R.id.novaSenha);
        novaSenha2 = (EditText)findViewById(R.id.novaSenha2);
        novoEmail = (EditText)findViewById(R.id.novoEmail);
        btnCriarNovo = (Button)findViewById(R.id.btnCriarNovo);


        btnCriarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog dialog = new ProgressDialog(telaCadastro.this); //,"","Realizando consulta",true,true);
                dialog.setTitle("Aguarde...");
                dialog.setMessage("Enviando seus dados");
                dialog.setIcon(R.drawable.ampulheta);
                dialog.show();
                //dialog.setCancelable(false);



                String nome = String.valueOf(novoNome.getText());
                String login = String.valueOf(novoLogin.getText());
                senha = String.valueOf(novaSenha.getText());
                String senha2 = String.valueOf(novaSenha2.getText());
                String email = String.valueOf(novoEmail.getText());
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupTipo);
                RadioButton selectOpt = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                if(nome.equals("")||login.equals("")||senha.equals("")||senha2.equals("")||email.equals("")){
                    Toast.makeText(telaCadastro.this, "Preencha todos os campos, apressadinho.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }else {
                    int idx = login.indexOf(" "); //teste de login com espaços
                    if (idx!=-1){
                        Toast.makeText(telaCadastro.this, "Login não pode ter espaços", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }else{


                        if (senha.equals(senha2)) {//teste de senha

                            if (rg.getCheckedRadioButtonId() == -1) {//teste de tipo
                                Toast.makeText(telaCadastro.this, "Escolha um tipo", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else {
                                tipo = String.valueOf(selectOpt.getText());
                                tipo = tipo.toLowerCase(); //tudo minúsculo
                                try {                                                                                //convertendo senha e hash
                                    senha = (hashPassword(senha));                                                   //convertendo senha e hash
                                }                                                                                    //convertendo senha e hash
                                catch (NoSuchAlgorithmException e) {                                                 //convertendo senha e hash
                                    Toast.makeText(telaCadastro.this, "Exceção:" + (e), Toast.LENGTH_LONG).show();   //convertendo senha e hash
                                }                                                                                    //convertendo senha e hash


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
                                                    conexaoBD conex = new conexaoBD();
                                                    conex.novoUsuario(login, nome, tipo, email, senha);
                                                 }
                                            });
                                        } catch (Exception e) {
                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.setMessage("Feito!");
                                                dialog.dismiss();
                                                if (global.isUsuarioExistente() == true) {
                                                    global.setUsuarioExistente(false);//zerando variável.
                                                    AlertDialog.Builder dlg = new AlertDialog.Builder(telaCadastro.this);
                                                    dlg.setCancelable(false);
                                                    dlg.setTitle("Falha!");
                                                    dlg.setMessage("Usuário " + login + " já está em uso!");
                                                    dlg.setNeutralButton("Ok!", null);
                                                    dlg.show();
                                                    Toast.makeText(telaCadastro.this, "Login já está em uso!", Toast.LENGTH_LONG).show();
                                                }
                                                if (global.isUsuarioCriado() == true) {
                                                    global.setUsuarioCriado(false); //zerando variável de criação.
                                                    AlertDialog.Builder dlg = new AlertDialog.Builder(telaCadastro.this);
                                                    dlg.setCancelable(false);
                                                    dlg.setTitle("Sucesso!");
                                                    dlg.setMessage("Usuário " + login + " criado com sucesso!");
                                                    dlg.setNeutralButton("Ok!", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            finish();
                                                        }
                                                    });
                                                    AlertDialog alert = dlg.create();
                                                    alert.show();

                                                }
                                            }
                                        });
                                    }
                                }.start();

                            }
                        } else {
                            Toast.makeText(telaCadastro.this, "As senhas digitadas não coincidem.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                    }
               }
        });
 }
}
