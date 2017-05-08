package com.example.mawluis.pergunti;

import android.app.AlertDialog;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;
//import android.telecom.Connection; Connection já está em uso
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



/**
 * Created by Mawluis on 23/04/2017.
 */

public class conexaoBD extends telaCadastro{

    private static final String URL="jdbc:postgresql://ec2-54-243-253-17.compute-1.amazonaws.com:5432/djdvphd5vpn4l?sslmode=require";
    private static final String user="aqxgmmdlvyecas";
    private static final String pass="bb7241b8c75b44f40e50d3ab71c84cc51d9f9708301f82bd7a508daae0ef285b";
    private static final String classforname="org.postgresql.Driver"; //com.mysql.jdbc.Driver



    public void conectarBD(){

        //Connection connection = null;


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(classforname);
            Connection  con = DriverManager.getConnection(URL, user, pass);

            //String result = "Database Connection success\n";
            //Statement st = con.createStatement();
            //ResultSet rs = st.executeQuery("SELECT * from usuario");
            /*ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()) {
                result += rsmd.getColumnName(1) + ":" +rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2) + ":" +rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ":" +rs.getString(3) + "\n";
            }
            */
            String insert = "INSERT INTO perguntas.usuario (login, nome, tipo, email, senha) VALUES ('LOGINandroid', 'nomeAndroid', 'tipoAN', 'emailAndr', 'senhaA');";
            PreparedStatement pst = con.prepareStatement(insert);
            int rs = pst.executeUpdate(); //funcionando.



/*
            AlertDialog.Builder dlg = new AlertDialog.Builder(conexaoBD.this);
            dlg.setMessage(result);
            dlg.setNeutralButton("ok", null);
            dlg.show();


            Context contexto = getApplicationContext();

            int duracao = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexto, result,duracao);
            toast.show();*/

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    } //não utilizado


    public void novoUsuario (String login, String nome, String tipo, String email, String senha){


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(classforname);
            Connection  con = DriverManager.getConnection(URL, user, pass);

            String insert = "INSERT INTO perguntas.usuario (login, nome, tipo, email, senha) VALUES ('"+login+"', '"+nome+"', '"+tipo+"', '"+email+"', '"+senha+"');";
            String sql = "select * from usuario where login='"+login+"'";
            PreparedStatement pst1 = con.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();

            if(rs1.next()){
                   usuarioExistente();


           /* AlertDialog.Builder dlg = new AlertDialog.Builder(conexaoBD.this);
            dlg.setMessage("Login já existente");
            dlg.setNeutralButton("ok", null);
            dlg.show();*/
                /*
            Context contexto = getApplicationContext();

            int duracao = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexto, "Login já existente",duracao);
            toast.show();*/
            } else {
                PreparedStatement pst2 = con.prepareStatement(insert);
                int rs2 = pst2.executeUpdate();
                usuarioCriado();
            }

            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }

    public void acessoSistema (String login, String senha){


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(classforname);
            Connection  con = DriverManager.getConnection(URL, user, pass);
            String sql = "select * from usuario where login='"+login+"' and senha= '"+senha+"'";
            PreparedStatement pst1 = con.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();
            //telaLogin res = new telaLogin();

            if(rs1.next()){

                //res.mensagemLogado();
                global.setLogado(true);


            } else {
                //res.mensagemInvalido();
                global.setLogado(false);
            }

            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }

    public void pergunta (int pergunta){


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(classforname);
            Connection  con = DriverManager.getConnection(URL, user, pass);
            String sql = "select pergunta,opt1,opt2,opt3,opt4,resposta from perguntas where codigo= "+pergunta;
            PreparedStatement pst1 = con.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();


            if(rs1.next()){

                //pergunta existe
                String perguntaSql = rs1.getObject(1).toString();
                String opt1Sql = rs1.getObject(2).toString();
                String opt2Sql = rs1.getObject(3).toString();
                String opt3Sql = rs1.getObject(4).toString();
                String opt4Sql = rs1.getObject(5).toString();
                String respostaSqlString = rs1.getObject(6).toString(); //vem como string
                int respostaSql = Integer.parseInt(respostaSqlString); //converti para inteiro
                //int respostaSql = Integer.parseInt(respostaSqlString.toString());

                telaJogo passarDados = new telaJogo();
                passarDados.setPergunta(perguntaSql);
                passarDados.setOpt1(opt1Sql);
                passarDados.setOpt2(opt2Sql);
                passarDados.setOpt3(opt3Sql);
                passarDados.setOpt4(opt4Sql);
                global.setResposta(respostaSql);


            } else {
                //pergunta não existe

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


    public void randPergs (int pergunta){


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(classforname);
            Connection  con = DriverManager.getConnection(URL, user, pass);

            String sql = "select codigo from perguntas";
            String count = "SELECT count(*) codigo from perguntas";


            PreparedStatement pst1 = con.prepareStatement(count);
            ResultSet rs1 = pst1.executeQuery();

            if (rs1.next()){
                int n = Integer.parseInt(rs1.getObject(1).toString());
                System.out.println("Count = "+n);
                int vetor[] = new int[n];

                PreparedStatement pst2 = con.prepareStatement(sql);
                ResultSet rs2 = pst2.executeQuery();

                int c=0;

                for (int d=0; d<n; d++){
                    System.out.println("O vetor "+d+" é "+vetor[c]);
                }

                while (rs2.next()){
                   vetor[c] = Integer.parseInt(rs2.getObject(1).toString());
                   c++;
                                }

                rs2.close();
                pst2.close();

            } else {

                //todo adicionar o que fazer caso não tenha perguntas para serem sorteadas.

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

}
