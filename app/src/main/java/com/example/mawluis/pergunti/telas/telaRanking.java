package com.example.mawluis.pergunti.telas;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.conexao.conexaoBD;
import com.example.mawluis.pergunti.global.global;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class telaRanking extends AppCompatActivity {
    ListView lvt;
    String query, dificuldade="";
    ArrayList<String> ranking = new ArrayList();
    Button btnRankigSala;
    EditText edtRankingSala;
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_ranking);

        lvt = (ListView)findViewById(R.id.lvt);
        RadioGroup radioDificuldade = (RadioGroup) findViewById(R.id.radioDificuldade);
        btnRankigSala = (Button)findViewById(R.id.btnRankingSala);
        edtRankingSala = (EditText)findViewById(R.id.edtRankingSala);


        query = "select dificuldade,(select nome from usuario where ranking.id=usuario.id),data_facanha from ranking where dificuldade=''";

        radioDificuldade.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rbEasy:
                        query=("select acerto, (select nome from usuario where ranking.jogador=usuario.id) from ranking where dificuldade='easy' and acerto>'0' order by  acerto desc");
                        break;
                    case R.id.rbNomal:
                        query=("select acerto, (select nome from usuario where ranking.jogador=usuario.id) from ranking where dificuldade='normal' and acerto>'0' order by  acerto desc");
                        break;
                    case R.id.rbHard:
                        query=("select acerto, (select nome from usuario where ranking.jogador=usuario.id) from ranking where dificuldade='hard' and acerto>'0' order by  acerto desc");
                        break;
                    default:
                        Toast.makeText(telaRanking.this, "Espaço reservado para novo tema a ser categorizado", Toast.LENGTH_SHORT).show();
                }
                ranking(query);
               ArrayAdapter<String> adapter=new ArrayAdapter<String>(telaRanking.this,android.R.layout.simple_list_item_1,ranking);
             lvt.setAdapter(adapter);
            }
        });


        btnRankigSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDificuldade.clearCheck();
                if (String.valueOf(edtRankingSala.getText()).equals("")){
                    Toast.makeText(telaRanking.this, "Digite uma sala", Toast.LENGTH_LONG).show();
                }else {
                    int numSala = Integer.parseInt(String.valueOf(edtRankingSala.getText())); //conversão faz proteção contra sql injection
                    ranking("select SUM(CAST(acerto AS INT)), (select nome from usuario where sala.usuario=usuario.id)" +
                            "from sala where usuario not in(select id from usuario where tipo='professor')" +
                            "and id='" + numSala + "' group by usuario ORDER BY SUM DESC");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(telaRanking.this, android.R.layout.simple_list_item_1, ranking);
                    lvt.setAdapter(adapter);
                }
            }
        });



    }

    public void ranking(String query){
        final ProgressDialog dialog = new ProgressDialog(telaRanking.this); //,"","Realizando consulta",true,true);
        dialog.setTitle("Aguarde...");
        dialog.setMessage("Trazendo ranking...");
        dialog.setIcon(R.drawable.ampulheta);
        dialog.show();

        new Thread() {
            public void run() {
                try {
                    URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/f/f4/HelpPage_IconPack-03.png");
                    HttpURLConnection connection;
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();  //    treta que funcionou para fazer loading
                    //InputStream input = connection.getInputStream();
                    //final Bitmap imagem = BitmapFactory.decodeStream(input);
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                Class.forName(global.getClassforname());
                                Connection con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
                                PreparedStatement pst1 = con.prepareStatement(query);
                                ResultSet rs1 = pst1.executeQuery();
                                ranking.clear();
                                int i=0;
                                while(rs1.next()){
                                    if (i<10){ //exibir os 5 primeiros
                                        ranking.add("| "+rs1.getObject(1).toString()+" |                                           "+rs1.getObject(2).toString());
                                        i++;
                                    }
                                }
                                rs1.close();
                                pst1.close();
                                con.close();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("Feito!");
                        dialog.dismiss();

                    }
                });
            }
        }.start();


    }
}
