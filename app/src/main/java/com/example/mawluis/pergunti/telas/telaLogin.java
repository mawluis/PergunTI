package com.example.mawluis.pergunti.telas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.mawluis.pergunti.global.global.hashPassword;

public class telaLogin extends AppCompatActivity {

    EditText txtLogin, txtSenha;
    Button btnLogar, btnNovoUsuario;
    public static String login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        txtLogin = (EditText)findViewById(R.id.txtLogin);
        txtSenha = (EditText)findViewById(R.id.txtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        btnNovoUsuario = (Button)findViewById(R.id.btnNovoUsuario);


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

                if (global.isLogado() == true) {
                    global.setGame("normal");//zerando variável de jogo.
                    Toast.makeText(telaLogin.this, "Logado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent abremenu = new Intent(telaLogin.this, telaInicial.class);
                    startActivity(abremenu);
                } else {
                    Toast.makeText(telaLogin.this, "Dados informados inválidos.", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

}
