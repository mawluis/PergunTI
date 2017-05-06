package com.example.mawluis.pergunti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Toast.makeText(telaLogin.this, "Verificando "+login+" e senha "+senha+" no sistema", Toast.LENGTH_SHORT).show();
                conexaoBD logar = new conexaoBD();
                logar.acessoSistema(login, senha);

                if (global.isLogado() == true) {
                    global.setLogin(login);
                    Toast.makeText(telaLogin.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent abremenu = new Intent(telaLogin.this, telaInicial.class);
                    startActivity(abremenu);
                } else {
                    Toast.makeText(telaLogin.this, "Dados informados inválidos.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    public void mensagemLogado(){
        //Toast.makeText(telaLogin.this, "Sucesso: \n "+login+" logado com sucesso!", Toast.LENGTH_SHORT).show();
        //global.setLogin(login);
        Intent abremenu = new Intent(telaLogin.this, telaCadastro.class);
        startActivity(abremenu);
        //telaLogin.startActivity(intent);
        //startActivity(intent);


    }

    public void mensagemInvalido(){
        Toast.makeText(telaLogin.this, "Dados informados inválidos.", Toast.LENGTH_SHORT).show();
    }
}
