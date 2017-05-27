package com.example.mawluis.pergunti.telas;

import android.content.DialogInterface;
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

import java.security.NoSuchAlgorithmException;

import static com.example.mawluis.pergunti.global.global.hashPassword;

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
                        Toast.makeText(telaCadastro.this, "Escolha um tipo", Toast.LENGTH_LONG).show();
                    }else{
                        tipo = String.valueOf(selectOpt.getText());
                        tipo = tipo.toLowerCase(); //tudo minúsculo
                        try{                                                                               //convertendo senha e hash
                            senha = (hashPassword(senha));                                                 //convertendo senha e hash
                        }                                                                                  //convertendo senha e hash
                        catch(NoSuchAlgorithmException e){                                                 //convertendo senha e hash
                            Toast.makeText(telaCadastro.this,"Exceção:"+ (e), Toast.LENGTH_LONG).show();   //convertendo senha e hash
                        }                                                                                  //convertendo senha e hash

                        conexaoBD conex = new conexaoBD();
                        conex.novoUsuario(login, nome, tipo, email, senha);
                        Toast.makeText(telaCadastro.this, "Verificando dados no sistema", Toast.LENGTH_SHORT).show();
                        if (global.isUsuarioExistente()==true){
                            Toast.makeText(telaCadastro.this, "Login já está em uso!", Toast.LENGTH_LONG).show();
                        }
                        if (global.isUsuarioCriado()==true) {
                            global.setUsuarioCriado(false); //zerando variável de criação.
                            AlertDialog.Builder dlg = new AlertDialog.Builder(telaCadastro.this);
                            dlg.setCancelable(false);
                            dlg.setTitle("Sucesso!");
                            dlg.setMessage("Usuário "+login+" criado com sucesso!");
                            dlg.setNeutralButton("Ok!", new DialogInterface.OnClickListener()     {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
                            AlertDialog alert = dlg.create();
                            alert.show();
                        }
                        }

                } else{
                    Toast.makeText(telaCadastro.this, "As senhas digitadas não coincidem.", Toast.LENGTH_LONG).show();
                }

            }


        });

 }



}
