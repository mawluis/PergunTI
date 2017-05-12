package com.example.mawluis.pergunti;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class telaJogoCustom extends AppCompatActivity {

    ListView lv;
    ArrayList<String> poolPergs = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo_custom);
    }

    @Override
    public void onResume(){
        super.onResume();

        lv = (ListView)findViewById(R.id.lv);

        Toast.makeText(this, "Método OnResume com sucesso", Toast.LENGTH_SHORT).show();
        telaPerguntas a = new telaPerguntas();
        poolPergs = a.getPoolPergs();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a.getPoolPergs());
        lv.setAdapter(adapter);

        Button btnEscolherPerg = (Button)findViewById(R.id.btnEscolherPerg);
        Button btnCriarPerg = (Button)findViewById(R.id.btnCriarPerg);
        Button btnCriarSala = (Button)findViewById(R.id.btnCriarSala);


        btnEscolherPerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaJogoCustom.this, telaPerguntas.class);
                startActivity(intent);
            }
        });

        btnCriarPerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaJogoCustom.this, telaCriacaoPerg.class);
                startActivity(intent);
            }
        });

        btnCriarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(telaJogoCustom.this);
                dlg.setMessage("Sala nº "+idsala+" foi criado com sucesso");
                dlg.setNeutralButton("ok", null);
                dlg.show();

            }
        });
    }


}
