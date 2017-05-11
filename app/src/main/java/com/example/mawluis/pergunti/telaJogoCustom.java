package com.example.mawluis.pergunti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class telaJogoCustom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo_custom);


        Button btnEscolherPerg = (Button)findViewById(R.id.btnEscolherPerg);


        btnEscolherPerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaJogoCustom.this, telaPerguntas.class);
                startActivity(intent);
            }
        });

    }
}
