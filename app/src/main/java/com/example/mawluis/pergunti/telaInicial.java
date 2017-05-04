package com.example.mawluis.pergunti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class telaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        Button btnJogar = (Button)findViewById(R.id.btnJogar);
        Button btnRanking= (Button)findViewById(R.id.btnRanking);
        Button btnJogoPersonalizado= (Button)findViewById(R.id.btnJogoPersonalizado);

        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaInicial.this, telaJogo.class);
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
                Toast.makeText(telaInicial.this, "TODO: fazer botão e tela de jogo personalizado", Toast.LENGTH_SHORT).show();
                //TODO botão e tela de jogo personalizado
            }
        });



    }
}
