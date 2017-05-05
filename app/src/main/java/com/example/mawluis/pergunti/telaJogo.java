package com.example.mawluis.pergunti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class telaJogo extends AppCompatActivity {

    public static String pergunta, opt1, opt2, opt3, opt4, resposta;
    private int marcacao;
    Button btnResponder, btnPergunta;

        //getters e setters
    public static String getPergunta() {
        return pergunta;
    }

    public static void setPergunta(String pergunta) {
        telaJogo.pergunta = pergunta;
    }

    public static String getOpt1() {
        return opt1;
    }

    public static void setOpt1(String opt1) {
        telaJogo.opt1 = opt1;
    }

    public static String getOpt2() {
        return opt2;
    }

    public static void setOpt2(String opt2) {
        telaJogo.opt2 = opt2;
    }

    public static String getOpt3() {
        return opt3;
    }

    public static void setOpt3(String opt3) {
        telaJogo.opt3 = opt3;
    }

    public static String getOpt4() {
        return opt4;
    }

    public static void setOpt4(String opt4) {
        telaJogo.opt4 = opt4;
    }

    public static String getResposta() {
        return resposta;
    }

    public static void setResposta(String resposta) {
        telaJogo.resposta = resposta;
    }

    public int getMarcacao() {
        return marcacao;
    }

    public void setMarcacao(int marcacao) {
        this.marcacao = marcacao;
    }
        //Fim dos getters e setters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        btnResponder = (Button)findViewById(R.id.btnResponder);
        btnPergunta = (Button)findViewById(R.id.btnPergunta) ;

        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupOpts);
                RadioButton selectOpt = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                if (rg.getCheckedRadioButtonId() == -1) {//teste de tipo
                    Toast.makeText(telaJogo.this, "Escolha uma opção", Toast.LENGTH_SHORT).show();}
                else {
                    marcacao = rg.getCheckedRadioButtonId ();


                    int marcacao = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));

                    conexaoBD conex = new conexaoBD();
                    Toast.makeText(telaJogo.this, "Você escolheu "+marcacao+" !", Toast.LENGTH_SHORT).show();

                }

            }
        });






    }
}
