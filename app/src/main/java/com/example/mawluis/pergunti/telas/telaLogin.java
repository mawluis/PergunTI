package com.example.mawluis.pergunti.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import static com.example.mawluis.pergunti.global.global.hashPassword;


public class telaLogin extends AppCompatActivity {

    EditText txtLogin, txtSenha;
    TextView txtVersao;
    Button btnLogar, btnNovoUsuario,btnSobre;
    public static String login;
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        txtLogin = (EditText)findViewById(R.id.txtLogin);
        txtSenha = (EditText)findViewById(R.id.txtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        btnNovoUsuario = (Button)findViewById(R.id.btnNovoUsuario);
        btnSobre = (Button)findViewById(R.id.btnSobre);
        txtVersao = (TextView)findViewById(R.id.txtVersao);

        txtVersao.setText(global.getVersao());


        btnNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCadastro = new Intent(telaLogin.this, telaCadastro.class);
                startActivity(abreCadastro);
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.setLogado(false);
                final ProgressDialog dialog = new ProgressDialog(telaLogin.this); //,"","Realizando consulta",true,true);
                dialog.setTitle("Aguarde...");
                dialog.setMessage("Verificando seus dados");
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

                                    login = String.valueOf(txtLogin.getText());
                                    String senha= String.valueOf(txtSenha.getText());
                                    try{                                                                               //convertendo senha e hash
                                        senha = (hashPassword(senha));                                                 //convertendo senha e hash
                                    }                                                                                  //convertendo senha e hash
                                    catch(NoSuchAlgorithmException e){                                                 //convertendo senha e hash
                                        Toast.makeText(telaLogin.this,"Exceção:"+ (e), Toast.LENGTH_SHORT).show();     //convertendo senha e hash
                                    }                                                                                  //convertendo senha e hash
                                    conexaoBD logar = new conexaoBD();
                                    logar.acessoSistema(login, senha);

                                }
                            });
                        } catch (Exception e) {
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setMessage("Feito!");
                                dialog.dismiss();

                                if (global.isLogado() == true) {
                                    global.setGame("normal");//zerando variável de jogo.
                                    Intent abremenu = new Intent(telaLogin.this, telaInicial.class);
                                    startActivity(abremenu);
                                } else {
                                    Toast.makeText(telaLogin.this, "Dados informados inválidos.", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                }.start();

            }
        });


        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(telaLogin.this);
                dlg.setMessage("PergunTI.\n" +
                        "Desenvolvido por Mawluis.\n" +
                        "Relatar um erro, sugestão ou melhorias:\n" +
                        "Contato mawluis@hotmail.com\n" +
                        ""+global.getVersao());
                dlg.setNeutralButton("Ok!", null);
                dlg.show();
            }
        });
    }

}
