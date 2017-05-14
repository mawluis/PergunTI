package com.example.mawluis.pergunti.telas;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

import java.util.ArrayList;
import java.util.List;

public class telaJogo extends AppCompatActivity {

    private static String pergunta, opt1, opt2, opt3, opt4;
    private int marcacao, resposta, i=0;
    Button btnResponder, btnPergunta;
    RadioButton rBtnOpt1,rBtnOpt2,rBtnOpt3,rBtnOpt4;
    EditText codPergunta;
    TextView txtNumPerg, txtPergunta, txtJogo;
    List<Integer> poolPergs = new ArrayList<Integer>();
    conexaoBD perguntar = new conexaoBD();

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


        btnPergunta = (Button)findViewById(R.id.btnPergunta) ;
        btnResponder = (Button)findViewById(R.id.btnResponder);
        codPergunta = (EditText) findViewById(R.id.codPergunta);
        txtNumPerg = (TextView)findViewById(R.id.txtNumPerg);
        txtPergunta = (TextView)findViewById(R.id.txtPergunta);
        rBtnOpt1 = (RadioButton)findViewById(R.id.rBtnOpt1);
        rBtnOpt2 = (RadioButton)findViewById(R.id.rBtnOpt2);
        rBtnOpt3 = (RadioButton)findViewById(R.id.rBtnOpt3);
        rBtnOpt4 = (RadioButton)findViewById(R.id.rBtnOpt4);
        txtJogo = (TextView)findViewById(R.id.txtJogo);


       // Toast.makeText(telaJogo.this, "Tamanho do array:"+ global.getPoolPergs().size(), Toast.LENGTH_SHORT).show();

        if (global.getTipo().equals("professor")) {

        }else{
            btnPergunta.setVisibility(View.INVISIBLE);
            codPergunta.setVisibility(View.INVISIBLE);
        }

        if ((global.getGame().equals("easy"))||(global.getGame().equals("normal"))||(global.getGame().equals("hard"))){
            //todo criar jogo single
        }else{
            perguntar.enviarSala(global.getGame(),global.getId());
            if (global.isRepetido()){
                AlertDialog.Builder dlg = new AlertDialog.Builder(telaJogo.this);
                dlg.setMessage("Este usuário já começou este jogo nesta sala.\n " +
                               "Portanto não será possível modificar os\n " +
                                "resultados desta sala.");
                dlg.setNeutralButton("Ok, entendi!", null);
                dlg.show();
            }
            poolPergs=global.getPoolPergs();
            txtJogo.setText("Sala "+global.getGame()+" pergunta "+"1/"+poolPergs.size()+""); //todo colocar o número da pergunta (índice do vetor) exemplo pergunta 2/10
            vemPergunta();

        }



        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupOpts);
                RadioButton selectOpt = (RadioButton) findViewById(rg.getCheckedRadioButtonId()); //todo verificar se pode tirar isso.

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
                        if (!global.isRepetido()){
                           String query = "INSERT INTO sala (id, usuario, pergunta, acerto) VALUES ('"+global.getGame()+"','"+global.getId()+"','"+poolPergs.get(i-1)+"','1')" ;
                            perguntar.executaUpdate(query);
                        }
                        vemPergunta();
                    } else {
                        if (global.getTipo().equals("professor")){
                            Toast.makeText(telaJogo.this, "Você errou! a resposta correta é: "+resposta, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(telaJogo.this, "Você errou!", Toast.LENGTH_SHORT).show();
                        }
                        if (!global.isRepetido()){
                            String query = "INSERT INTO sala (id, usuario, pergunta, acerto) VALUES ('"+global.getGame()+"','"+global.getId()+"','"+poolPergs.get(i-1)+"','0')" ;
                            perguntar.executaUpdate(query);
                        }
                        vemPergunta();
                    }

                }

            }
        });


        btnPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perguntar.pergunta(Integer.parseInt(codPergunta.getText().toString())); // mandei o editText convertido para inteiro para o método pergunta
                txtNumPerg.setText("Pergunta nº "+codPergunta.getText()+": ");
                txtPergunta.setText(getPergunta());
                rBtnOpt1.setText(getOpt1());
                rBtnOpt2.setText(getOpt2());
                rBtnOpt3.setText(getOpt3());
                rBtnOpt4.setText(getOpt4());

            }
        });

    }
    public void vemPergunta (){

        if (poolPergs.size()>i) {
            perguntar.pergunta(poolPergs.get(i));
            Toast.makeText(telaJogo.this, "poolPergs.get(i) é:"+poolPergs.get(i), Toast.LENGTH_SHORT).show();
            txtNumPerg.setText("Pergunta nº " + poolPergs.get(i) + ": ");
            txtPergunta.setText(getPergunta());
            rBtnOpt1.setText(getOpt1());
            rBtnOpt2.setText(getOpt2());
            rBtnOpt3.setText(getOpt3());
            rBtnOpt4.setText(getOpt4());
            i++;
            txtJogo.setText("Sala " + global.getGame() + " pergunta " + i + "/" + poolPergs.size() + "");

        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(telaJogo.this);
            dlg.setMessage("Jogo finalizado");
            dlg.setNeutralButton("Ok!", null);
            dlg.show();
        }
    }
}
