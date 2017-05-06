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

    private static final String URL = "jdbc:mysql://192.168.0.10/perguntas";
    private static final String user = "root";
    private static final String pass = "";



    public void conectarBD(){

        //Connection connection = null;


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
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

            Class.forName("com.mysql.jdbc.Driver");
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

            Class.forName("com.mysql.jdbc.Driver");
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

            Class.forName("com.mysql.jdbc.Driver");
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
                passarDados.setResposta(2);


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

}
