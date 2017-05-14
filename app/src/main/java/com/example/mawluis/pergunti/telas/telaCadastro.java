package com.example.mawluis.pergunti.telas;

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

public class telaCadastro extends AppCompatActivity {

    EditText novoNome, novoLogin, novaSenha, novaSenha2, novoEmail;
    Button btnCriarNovo;
    String tipo = "";
    String senha = "";
    String senha2 ="";




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

                String nome = String.valueOf(novoNome.getText());
                String login = String.valueOf(novoLogin.getText());
                String senha = String.valueOf(novaSenha.getText());
                String senha2 = String.valueOf(novaSenha2.getText());
                String email = String.valueOf(novoEmail.getText());
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupTipo);
                RadioButton selectOpt = (RadioButton) findViewById(rg.getCheckedRadioButtonId());




                if (senha.equals(senha2)) {//teste de senha


                    if (rg.getCheckedRadioButtonId() == -1) {//teste de tipo
                        Toast.makeText(telaCadastro.this, "Escolha um tipo", Toast.LENGTH_SHORT).show();
                    }else{
                        tipo = String.valueOf(selectOpt.getText());
                        tipo = tipo.toLowerCase(); //tudo minúsculo
                        conexaoBD conex = new conexaoBD();
                        conex.novoUsuario(login, nome, tipo, email, senha);
                        Toast.makeText(telaCadastro.this, "Verificando dados no sistema", Toast.LENGTH_SHORT).show();
                        if (global.isUsuarioExistente()==true){
                            Toast.makeText(telaCadastro.this, "Login já está em uso!", Toast.LENGTH_SHORT).show();
                        }
                        if (global.isUsuarioCriado()==true) {
                            global.setUsuarioCriado(false); //zerando variável de criação.
                            Toast.makeText(telaCadastro.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();

                        }
                        }

                } else{
                    Toast.makeText(telaCadastro.this, "As senhas digitadas não coincidem.", Toast.LENGTH_SHORT).show();
                }

            }


        });

 }



}
