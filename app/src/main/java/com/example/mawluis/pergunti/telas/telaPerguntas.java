package com.example.mawluis.pergunti.telas;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.mawluis.pergunti.R;
import com.example.mawluis.pergunti.global.global;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class telaPerguntas extends AppCompatActivity {


    ListView lv;
    private Handler handler = new Handler();


    private static String[][] consulta;


    static ArrayList<String> poolPergs = new ArrayList();

    public ArrayList<String> getPoolPergs() {
        return poolPergs;
    }

    public void setPoolPergs(ArrayList<String> poolPergs) {
        this.poolPergs = poolPergs;
    }


    static ArrayList<String> poolPergsid = new ArrayList();

    public ArrayList<String> getPoolPergsid() {
        return poolPergsid;
    }

    public void setPoolPergsid(ArrayList<String> poolPergsid) {
        this.poolPergsid = poolPergsid;
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
                        enviarPerguntas("banco");
                        break;
                    case R.id.rbRede:
                        enviarPerguntas("rede");
                        break;
                    case R.id.rbProgram:
                        enviarPerguntas("programação");
                        break;
                    case R.id.rbGeral:
                        enviarPerguntas("geral");
                        break;
                    case R.id.rbSistema:
                        enviarPerguntas("sistema");
                        break;
                    default:
                        Toast.makeText(telaPerguntas.this, "Espaço reservado para novo tema a ser categorizado", Toast.LENGTH_SHORT).show();
                }

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (poolPergsid.contains(consulta[0][i])) {
                    Toast.makeText(telaPerguntas.this, "Erro: \nA pergunta: " + consulta[0][i] + " já está na sua lista.", Toast.LENGTH_SHORT).show();

                } else {
                    poolPergsid.add(consulta[0][i]);//aqui eu add o ID ao arraylist de perguntas.
                    poolPergs.add("(ID:"+consulta[0][i]+")  (Perg:"+consulta[1][i]+")");
                    Toast.makeText(telaPerguntas.this, "A pergunta: " + consulta[0][i] + " foi adicionada na sua lista.", Toast.LENGTH_SHORT).show();
                    global.setStatuslist(true);
                }

            }
        });


    }

    public ListView getLv() {
        return lv;
    }

    public void setLv(ListView lv) {
        this.lv = lv;
    }

    public void enviarPerguntas(String tema){ //conexão criada fora da classes de conexão por erros de passagem de vetor entre activities
        final ProgressDialog dialog = new ProgressDialog(telaPerguntas.this); //,"","Realizando consulta",true,true);
        dialog.setTitle("Aguarde...");
        dialog.setMessage("Baixando perguntas...");
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
                                String count = "SELECT count(*) id from pergunta where tema='"+tema+"'";
                                String sql = "select id,pergunta from pergunta where tema='"+tema+"'";
                                PreparedStatement pst1 = con.prepareStatement(count);
                                ResultSet rs1 = pst1.executeQuery();
                                if (rs1.next()){
                                    int n = Integer.parseInt(rs1.getObject(1).toString());
                                    String vetorPerg[][] = new String[2][n]; //criei em vetor por não saber usar arraylist multidimensional
                                    //primeiro termo: id/pergunta
                                    // segundo termo: registro(s)
                                    PreparedStatement pst2 = con.prepareStatement(sql);
                                    ResultSet rs2 = pst2.executeQuery();
                                    int x=0;
                                    while (rs2.next()){
                                        vetorPerg[0][x] = rs2.getObject(1).toString(); //populando vetor de id
                                        vetorPerg[1][x] = rs2.getObject(2).toString(); //populando vetor de perguntas
                                        x++;
                                    }
                                    setConsulta(vetorPerg);
                                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(telaPerguntas.this,android.R.layout.simple_list_item_1,consulta[1]);//pegar vetor
                                    lv.setAdapter(adapter);
                                    rs2.close();
                                    pst2.close();
                                } else {
                                    Toast.makeText(telaPerguntas.this, "Não há perguntas sobre esse tema", Toast.LENGTH_LONG).show();
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
