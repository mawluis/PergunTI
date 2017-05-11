package com.example.mawluis.pergunti;

import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class telaPerguntas extends AppCompatActivity {
    private static final String URL="jdbc:postgresql://ec2-54-243-253-17.compute-1.amazonaws.com:5432/djdvphd5vpn4l?sslmode=require";
    private static final String user="aqxgmmdlvyecas";
    private static final String pass="bb7241b8c75b44f40e50d3ab71c84cc51d9f9708301f82bd7a508daae0ef285b";
    private static final String classforname="org.postgresql.Driver"; //com.mysql.jdbc.Driver ou org.postgresql.Driver

    ListView lv;
    private static String[][] consulta;
    static ArrayList<String> poolPergs = new ArrayList();

    public ArrayList<String> getPoolPergs() {
        return poolPergs;
    }

    public void setPoolPergs(ArrayList<String> poolPergs) {
        this.poolPergs = poolPergs;
    }



    public static void setConsulta( String con[][] ) {
        consulta = Arrays.copyOf(con, con.length);
    }

    public static String[][] getConsulta() {
        return Arrays.copyOf(consulta, consulta.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perguntas);

        lv = (ListView) findViewById(R.id.lv);


        RadioGroup rg = (RadioGroup) findViewById(R.id.radioPerguntas);


        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rbBanco:
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            Class.forName(classforname);
                            Connection con = DriverManager.getConnection(URL, user, pass);
                            String count = "SELECT count(*) codigo from perguntas where tema='banco'";
                            String sql = "select codigo,pergunta from perguntas where tema='banco'";
                            PreparedStatement pst1 = con.prepareStatement(count);
                            ResultSet rs1 = pst1.executeQuery();

                            if (rs1.next()){
                                int n = Integer.parseInt(rs1.getObject(1).toString());

                                String vetorPerg[][] = new String[2][n];
                                //primeiro termo: codigo/pergunta
                                // segundo termo: registro(s)
                                PreparedStatement pst2 = con.prepareStatement(sql);
                                ResultSet rs2 = pst2.executeQuery();

                                int x=0;


                                while (rs2.next()){
                                    vetorPerg[0][x] = rs2.getObject(1).toString(); //codigo
                                    vetorPerg[1][x] = rs2.getObject(2).toString(); //pergunta
                                    x++;
                                }

                                setConsulta(vetorPerg);

                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(telaPerguntas.this,android.R.layout.simple_list_item_1,consulta[1]);
                                lv.setAdapter(adapter);

                                rs2.close();
                                pst2.close();

                            } else {

                                //todo adicionar o que fazer caso não tenha perguntas deste tema.

                            }
                            rs1.close();
                            pst1.close();
                            con.close();


                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.rbRede:
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            Class.forName(classforname);
                            Connection con = DriverManager.getConnection(URL, user, pass);
                            String count = "SELECT count(*) codigo from perguntas where tema='rede'";
                            String sql = "select codigo,pergunta from perguntas where tema='rede'";
                            PreparedStatement pst1 = con.prepareStatement(count);
                            ResultSet rs1 = pst1.executeQuery();
                            if (rs1.next()){
                                int n = Integer.parseInt(rs1.getObject(1).toString());
                                String vetorPerg[][] = new String[2][n];
                                PreparedStatement pst2 = con.prepareStatement(sql);
                                ResultSet rs2 = pst2.executeQuery();
                                int x=0;
                                while (rs2.next()){
                                    vetorPerg[0][x] = rs2.getObject(1).toString(); //codigo
                                    vetorPerg[1][x] = rs2.getObject(2).toString(); //pergunta
                                    x++;
                                }
                                setConsulta(vetorPerg);
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(telaPerguntas.this,android.R.layout.simple_list_item_1,consulta[1]);
                                lv.setAdapter(adapter);
                                rs2.close();
                                pst2.close();
                            } else {
                                Toast.makeText(telaPerguntas.this, "Não há perguntas sobre esse tema", Toast.LENGTH_SHORT).show(); //nunca será utilizado
                            }
                            rs1.close();
                            pst1.close();
                            con.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.rbProgram:
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            Class.forName(classforname);
                            Connection con = DriverManager.getConnection(URL, user, pass);
                            String count = "SELECT count(*) codigo from perguntas where tema='programação'";
                            String sql = "select codigo,pergunta from perguntas where tema='programação'";
                            PreparedStatement pst1 = con.prepareStatement(count);
                            ResultSet rs1 = pst1.executeQuery();
                            if (rs1.next()){
                                int n = Integer.parseInt(rs1.getObject(1).toString());
                                String vetorPerg[][] = new String[2][n];
                                PreparedStatement pst2 = con.prepareStatement(sql);
                                ResultSet rs2 = pst2.executeQuery();
                                int x=0;
                                while (rs2.next()){
                                    vetorPerg[0][x] = rs2.getObject(1).toString(); //codigo
                                    vetorPerg[1][x] = rs2.getObject(2).toString(); //pergunta
                                    x++;
                                }
                                setConsulta(vetorPerg);
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(telaPerguntas.this,android.R.layout.simple_list_item_1,consulta[1]);
                                lv.setAdapter(adapter);
                                rs2.close();
                                pst2.close();
                            } else {
                                Toast.makeText(telaPerguntas.this, "Não há perguntas sobre esse tema", Toast.LENGTH_SHORT).show(); //nunca será utilizado
                            }
                            rs1.close();
                            pst1.close();
                            con.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.rbGeral:
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            Class.forName(classforname);
                            Connection con = DriverManager.getConnection(URL, user, pass);
                            String count = "SELECT count(*) codigo from perguntas where tema='geral'";
                            String sql = "select codigo,pergunta from perguntas where tema='geral'";
                            PreparedStatement pst1 = con.prepareStatement(count);
                            ResultSet rs1 = pst1.executeQuery();
                            if (rs1.next()){
                                int n = Integer.parseInt(rs1.getObject(1).toString());
                                String vetorPerg[][] = new String[2][n];
                                PreparedStatement pst2 = con.prepareStatement(sql);
                                ResultSet rs2 = pst2.executeQuery();
                                int x=0;
                                while (rs2.next()){
                                    vetorPerg[0][x] = rs2.getObject(1).toString(); //codigo
                                    vetorPerg[1][x] = rs2.getObject(2).toString(); //pergunta
                                    x++;
                                }
                                setConsulta(vetorPerg);
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(telaPerguntas.this,android.R.layout.simple_list_item_1,consulta[1]);
                                lv.setAdapter(adapter);
                                rs2.close();
                                pst2.close();
                            } else {
                                Toast.makeText(telaPerguntas.this, "Não há perguntas sobre esse tema", Toast.LENGTH_SHORT).show(); //nunca será utilizado
                            }
                            rs1.close();
                            pst1.close();
                            con.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        Toast.makeText(telaPerguntas.this, "Switch não configurado", Toast.LENGTH_SHORT).show();
                }

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                boolean repetida=false;
                if (poolPergs.contains(consulta[0][i])) {
                    Toast.makeText(telaPerguntas.this, "Erro: \nA pergunta: " + consulta[0][i] + " já está na sua lista.", Toast.LENGTH_SHORT).show();

                } else {
                    poolPergs.add(consulta[0][i]);
                    Toast.makeText(telaPerguntas.this, "A pergunta: " + consulta[0][i] + " foi adicionada na sua lista.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
