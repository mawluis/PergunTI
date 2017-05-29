package com.example.mawluis.pergunti.telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.global.global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class telaJogoCustom extends AppCompatActivity {

    ListView lv;
    ArrayList<String> poolPergslocal = new ArrayList();
    ArrayList<String> poolPergslocalid = new ArrayList();
    String pergunta, insert, idsala;
    boolean pegIncrement=false;
    private long mLastClickTime = 0; //macete para evitar criação de vários jogos com duplo clique.
  //  private static String[][] consulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo_custom);
    }

    @Override
    public void onResume(){
        super.onResume();

        lv = (ListView)findViewById(R.id.lv);


        telaPerguntas a = new telaPerguntas();
        poolPergslocal = a.getPoolPergs();
        poolPergslocalid = a.getPoolPergsid();
      //  consulta=a.getConsulta();


        if(global.isStatuslist()){
            Toast.makeText(telaJogoCustom.this, "list on", Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,poolPergslocal);
            lv.setAdapter(adapter);
        }else {
            Toast.makeText(telaJogoCustom.this, "list false", Toast.LENGTH_SHORT).show();
        }

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
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000){  //macete para evitar criação de vários jogos com duplo clique.
                    return;                                                  //macete para evitar criação de vários jogos com duplo clique.
                }                                                            //macete para evitar criação de vários jogos com duplo clique.
                mLastClickTime = SystemClock.elapsedRealtime();              //macete para evitar criação de vários jogos com duplo clique.

                pegIncrement=false;
                if (a.getPoolPergsid().size()==0){
                    Toast.makeText(telaJogoCustom.this, "Não há perguntas para criar sua sala", Toast.LENGTH_LONG).show();
                }else {

                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        String URL = "jdbc:postgresql://ec2-54-243-253-17.compute-1.amazonaws.com:5432/djdvphd5vpn4l?sslmode=require";
                        String user = "aqxgmmdlvyecas";
                        String pass = "bb7241b8c75b44f40e50d3ab71c84cc51d9f9708301f82bd7a508daae0ef285b";
                        String classforname = "org.postgresql.Driver"; //com.mysql.jdbc.Driver ou org.postgresql.Driver

                        Class.forName(classforname);
                        Connection con = DriverManager.getConnection(URL, user, pass);

                        a.getPoolPergsid().trimToSize(); //aparando o arraylist (tirando os espaço vazios tipo null)

                        Statement statement = con.createStatement();
                        for (String pergunta : a.getPoolPergsid()) {
                            if (pegIncrement==false){
                            insert = "INSERT INTO sala (usuario, pergunta, acerto) VALUES ('" + global.getId() + "', '" + pergunta + "', '0');";
                            }else{
                                ResultSet rs = statement.getGeneratedKeys();
                                if (rs.next()) {
                                    idsala = rs.getObject(1).toString();
                                    insert = "INSERT INTO sala (id, usuario, pergunta, acerto) VALUES ('"+idsala+"','" + global.getId() + "', '" + pergunta + "', '0');";
                                    rs.close();
                                }
                            }
                            statement.executeUpdate(insert, statement.RETURN_GENERATED_KEYS); //tentativa de retornar o autoincrement
                            pegIncrement=true;
                        }

                        AlertDialog.Builder dlg = new AlertDialog.Builder(telaJogoCustom.this);
                        dlg.setCancelable(false);
                        dlg.setTitle("Sucesso!");
                        dlg.setMessage("Sala nº " + idsala + " foi criado com sucesso");
                        dlg.setNeutralButton("Ok!", new DialogInterface.OnClickListener()     {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        AlertDialog alert = dlg.create();
                        alert.show();

                        statement.close();
                        con.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }


            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(telaJogoCustom.this, "A pergunta: "+ poolPergslocalid.get(i) +" foi removida da sua lista.", Toast.LENGTH_SHORT).show();
                    poolPergslocal.remove(i);
                    poolPergslocalid.remove(i);
                    a.setPoolPergs(poolPergslocal);
                    a.setPoolPergsid(poolPergslocalid);
                    onResume();



            }
        });
    }


}
