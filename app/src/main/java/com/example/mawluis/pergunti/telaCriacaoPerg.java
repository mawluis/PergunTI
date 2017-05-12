package com.example.mawluis.pergunti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class telaCriacaoPerg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criacao_perg);

        Button btnCriaPerg = (Button)findViewById(R.id.btnCriarPerg);

        EditText edtPerg = (EditText)findViewById(R.id.edtPerg);
        EditText edtOpt1 = (EditText)findViewById(R.id.edtOpt1);
        EditText edtOpt2 = (EditText)findViewById(R.id.edtOpt2);
        EditText edtOpt3 = (EditText)findViewById(R.id.edtOpt3);
        EditText edtOpt4 = (EditText)findViewById(R.id.edtOpt4);
        EditText edtResp = (EditText)findViewById(R.id.edtResp);
        EditText edtTema = (EditText)findViewById(R.id.edtTema);
        EditText edtComplex = (EditText)findViewById(R.id.edtComplex);


        btnCriaPerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String nome = String.valueOf(novoNome.getText());
                global b = new global();


                conexaoBD a = new conexaoBD();
                a.novaPerg(String.valueOf(edtPerg.getText()),String.valueOf(edtOpt1.getText()),
                        String.valueOf(edtOpt2.getText()),
                        String.valueOf(edtOpt3.getText()),
                        String.valueOf(edtOpt4.getText()),
                        Integer.parseInt(String.valueOf(edtResp.getText())),
                        b.getLogin(),
                        Integer.parseInt(String.valueOf(edtComplex.getText())),
                        String.valueOf(edtTema.getText()));

                if (global.isPergCriada()){
                    Toast.makeText(telaCriacaoPerg.this, "Pergunta criada com sucesso", Toast.LENGTH_SHORT).show();
                    global.setPergCriada(false);
                }else{
                    Toast.makeText(telaCriacaoPerg.this, "Erro: \nPergunta não criada", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
