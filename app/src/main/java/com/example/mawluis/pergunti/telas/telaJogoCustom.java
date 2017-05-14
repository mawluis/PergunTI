package com.example.mawluis.pergunti.telas;

import android.content.Intent;
import android.os.StrictMode;
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
    ArrayList<String> poolPergs = new ArrayList();
    String pergunta;
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
                if (poolPergs.size()==0){
                    Toast.makeText(telaJogoCustom.this, "Não há perguntas para criar sua sala", Toast.LENGTH_SHORT).show();
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

                        poolPergs.trimToSize(); //aparando o arraylist (tirando os espaço vazios tipo null)
                        Toast.makeText(telaJogoCustom.this, "tamanho do array:" + poolPergs.size(), Toast.LENGTH_SHORT).show();
                        String insert;
                        Statement statement = con.createStatement();
                        for (String pergunta : poolPergs) {
                            insert = "INSERT INTO sala (usuario, pergunta, acerto) VALUES ('" + global.getId() + "', '" + pergunta + "', '0');";
                            Toast.makeText(telaJogoCustom.this, "insert pergunta:" + pergunta + " na tabela. com usuário " + global.getId(), Toast.LENGTH_SHORT).show();
                            statement.executeUpdate(insert, statement.RETURN_GENERATED_KEYS); //tentativa de retornar o autoincremente
                        }
                        ResultSet rs = statement.getGeneratedKeys();
                        if (rs.next()) {
                            String idsala = rs.getObject(1).toString();
                            AlertDialog.Builder dlg = new AlertDialog.Builder(telaJogoCustom.this);
                            dlg.setMessage("Sala nº " + idsala + " foi criado com sucesso");
                            dlg.setNeutralButton("ok", null);
                            dlg.show();
                        }
                        statement.close();
                        rs.next();
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
                    pergunta= poolPergs.get(i);
                    Toast.makeText(telaJogoCustom.this, "A pergunta: "+ pergunta +" foi removida da sua lista.", Toast.LENGTH_SHORT).show();
                    poolPergs.remove(i);
                    onResume();



            }
        });
    }


}
