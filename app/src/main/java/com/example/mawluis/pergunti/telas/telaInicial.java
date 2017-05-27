package com.example.mawluis.pergunti.telas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.global.global;

public class telaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        Button btnJogar = (Button)findViewById(R.id.btnJogar);
        Button btnRanking= (Button)findViewById(R.id.btnRanking);
        Button btnJogoPersonalizado= (Button)findViewById(R.id.btnJogoPersonalizado);

        Toast.makeText(telaInicial.this, "Bem vindo, "+ global.getNome().substring(0,1).toUpperCase() + global.getNome().substring(1).toLowerCase() +" !!!", Toast.LENGTH_SHORT).show();
        //primeiro caractere maiúsculo + restante minúsculo
        if (global.getTipo().equals("professor")) {

        }else{
            btnJogoPersonalizado.setVisibility(View.INVISIBLE);
        }


        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaInicial.this, telaSelJogo.class);
                startActivity(intent);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaInicial.this, telaRanking.class);
                startActivity(intent);

            }
        });

        btnJogoPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaInicial.this, telaJogoCustom.class);
                startActivity(intent);
            }
        });



    }
}
