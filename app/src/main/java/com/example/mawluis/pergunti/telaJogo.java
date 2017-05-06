package com.example.mawluis.pergunti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class telaJogo extends AppCompatActivity {

    private static String pergunta, opt1, opt2, opt3, opt4;
    private int marcacao, resposta;
    Button btnResponder, btnPergunta;
    EditText codPergunta;
    TextView txtNumPerg, txtPergunta;

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

    public int getResposta() {
        return resposta;
    }

    public void setResposta(int resp) {
        resposta = resp;
    }


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
                    marcacao++; // 1-4

                    Toast.makeText(telaJogo.this, "Você escolheu "+marcacao+" !", Toast.LENGTH_SHORT).show();
                    setResposta(global.getResposta());

                    if(marcacao==resposta) {
                        Toast.makeText(telaJogo.this, "Você acertou!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(telaJogo.this, "Você errou! a resposta correta é: "+resposta, Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        btnPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codPergunta = (EditText)findViewById(R.id.codPergunta);
                conexaoBD perguntar = new conexaoBD();
                perguntar.pergunta(Integer.parseInt(codPergunta.getText().toString())); // mandei o editText convertido para inteiro para o método pergunta

                txtNumPerg = (TextView)findViewById(R.id.txtNumPerg);
                txtPergunta = (TextView)findViewById(R.id.txtPergunta);
                RadioButton rBtnOpt1 = (RadioButton)findViewById(R.id.rBtnOpt1);
                RadioButton rBtnOpt2 = (RadioButton)findViewById(R.id.rBtnOpt2);
                RadioButton rBtnOpt3 = (RadioButton)findViewById(R.id.rBtnOpt3);
                RadioButton rBtnOpt4 = (RadioButton)findViewById(R.id.rBtnOpt4);

                txtNumPerg.setText("Pergunta nº "+codPergunta.getText()+": ");
                txtPergunta.setText(getPergunta());
                rBtnOpt1.setText(getOpt1());
                rBtnOpt2.setText(getOpt2());
                rBtnOpt3.setText(getOpt3());
                rBtnOpt4.setText(getOpt4());








            }
        });




    }
}
