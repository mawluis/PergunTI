package com.example.mawluis.pergunti.telas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

public class telaSelJogo extends AppCompatActivity {

    Button btnGo, btnEasy, btnNormal, btnHard;
    CheckBox chkBanco, chkGeral, chkProgram, chkOpt1, chkOpt2, chkRede;
    EditText edtSala;
    String insert;
    String insert_banco = "";
    String insert_geral = "";
    String insert_program = "";
    String insert_opt1 = "";
    String insert_opt2 = "";
    String insert_rede = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_sel_jogo);

        btnGo = (Button)findViewById(R.id.btnGo);
        btnEasy = (Button)findViewById(R.id.btnEasy);
        btnNormal = (Button)findViewById(R.id.btnNormal);
        btnHard = (Button)findViewById(R.id.btnHard);
        chkBanco = (CheckBox)findViewById(R.id.chkBanco);
        chkGeral = (CheckBox)findViewById(R.id.chkGeral);
        chkProgram = (CheckBox)findViewById(R.id.chkProgram);
        chkOpt1 = (CheckBox)findViewById(R.id.chkOpt1);
        chkOpt2 = (CheckBox)findViewById(R.id.chkOpt2);
        chkRede = (CheckBox)findViewById(R.id.chkRede);
        edtSala = (EditText)findViewById(R.id.edtSala);


        conexaoBD a = new conexaoBD();


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               global.setGame(String.valueOf(edtSala.getText()));
                Toast.makeText(telaSelJogo.this, "Entrando na sala "+String.valueOf(edtSala.getText()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(telaSelJogo.this, telaJogo.class);
                startActivity(intent);

            }
        });

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select(); //passando checkboxes para a String insert
                //insert = "select id from pergunta where complexidade<'4'and (tema='"+insert_banco+"' or tema='"+insert_geral+"' or tema='"+insert_program+"' or tema='"+insert_opt1+"' or tema='"+insert_opt2+"' or tema='"+insert_rede+"')";
                insert = "select id from pergunta where id not in(select pergunta from respondida where jogador = "+global.getId()+") and complexidade<'4' and (tema = '"+insert_banco+"' and tema = '"+insert_geral+"' and tema = '"+insert_program+"' and tema = '"+insert_opt1+"' and tema = '"+insert_opt2+"' and tema = '"+insert_rede+"')";
                a.fazerJogo(insert);
                global.setGame("easy");
                Intent intent = new Intent(telaSelJogo.this, telaJogo.class);
                startActivity(intent);
                Toast.makeText(telaSelJogo.this, insert, Toast.LENGTH_SHORT).show();
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select(); //passando checkboxes para a String insert
                insert = "select id from pergunta where id not in(select pergunta from respondida where jogador = "+global.getId()+") and complexidade<'4' and complexidade>'3' and (tema = '"+insert_banco+"' and tema = '"+insert_geral+"' and tema = '"+insert_program+"' and tema = '"+insert_opt1+"' and tema = '"+insert_opt2+"' and tema = '"+insert_rede+"')";
               /*select * from pergunta
where id not in(select pergunta from respondida where jogador = 1)
and (tema = 'geral' or tema='rede')*/
                a.fazerJogo(insert);
                global.setGame("normal");
                Intent intent = new Intent(telaSelJogo.this, telaJogo.class);
                startActivity(intent);
                Toast.makeText(telaSelJogo.this, insert, Toast.LENGTH_SHORT).show();
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select(); //passando checkboxes para a String insert
                insert = "select id from pergunta where id not in(select pergunta from respondida where jogador = "+global.getId()+") and complexidade>'6' and (tema = '"+insert_banco+"' and tema = '"+insert_geral+"' and tema = '"+insert_program+"' and tema = '"+insert_opt1+"' and tema = '"+insert_opt2+"' and tema = '"+insert_rede+"')";
                a.fazerJogo(insert);
                global.setGame("hard");
                Intent intent = new Intent(telaSelJogo.this, telaJogo.class);
                startActivity(intent);
                Toast.makeText(telaSelJogo.this, insert, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void select (){
        if (chkBanco.isChecked()){
            insert_banco = "banco";
        }else{
            insert_banco = "";//zerar caso não esteja marcado.
        }
        if (chkGeral.isChecked()){
            insert_geral = "geral";
        }else{
            insert_geral = "";
        }
        if (chkProgram.isChecked()){
            insert_program = "programação";
        }else{
            insert_program = "";
        }
        if (chkOpt1.isChecked()){
            insert_opt1 = "opt1";
        }else{
            insert_opt1 = "";
        }
        if (chkOpt2.isChecked()){
            insert_opt2 = "opt2";
        }else{
            insert_opt2 = "";
        }
        if (chkRede.isChecked()){
            insert_rede = "rede";
        }else{
            insert_rede = "";
        }

    }

}
