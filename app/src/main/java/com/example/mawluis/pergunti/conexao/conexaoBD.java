package com.example.mawluis.pergunti.conexao;

import android.os.StrictMode;
//import android.telecom.Connection; Connection já está em uso
import com.example.mawluis.pergunti.global.global;
import com.example.mawluis.pergunti.telas.telaCadastro;
import com.example.mawluis.pergunti.telas.telaJogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mawluis on 23/04/2017.
 */

public class conexaoBD extends telaCadastro {




    public void enviarSala(String sala, int id){
        global.setRepetido(false);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            Class.forName(global.getClassforname());
            Connection con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            PreparedStatement pst1 = con.prepareStatement("select pergunta from sala where id='"+sala+"' and usuario='"+id+"'");
            PreparedStatement pst2 = con.prepareStatement("select pergunta from sala where usuario in(select id from usuario where tipo='professor') and id='"+sala+"'");
            //pst1: "ver se o usuario já respondeu."
            //pst2: "resultado das perguntas ref. desta sala com select só no tipo professor."
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()){
                global.setRepetido(true);
                rs1.close();
                pst1.close();
            }
            ResultSet rs2 = pst2.executeQuery();
            List<Integer> poolPergs = new ArrayList<Integer>();
            while (rs2.next()){
                poolPergs.add(Integer.parseInt(rs2.getObject(1).toString()));
            }
            global.setPoolPergs(poolPergs);
            rs2.close();
            pst2.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executaUpdate(String query){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(global.getClassforname());
            Connection  con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            PreparedStatement pst1 = con.prepareStatement(query);
            int rs1 = pst1.executeUpdate();
            pst1.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void novaPerg (String pergunta, String opt1, String opt2, String opt3, String opt4, int resposta, String criador, int complexidade, String tema, int tempo){

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(global.getClassforname());
            Connection  con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            String insert = "INSERT INTO pergunta (pergunta, opt1, opt2, opt3, opt4, resposta, criador, complexidade, tema, tempo) VALUES ('"+pergunta+"', '"+opt1+"', '"+opt2+"', '"+opt3+"', '"+opt4+"', '"+resposta+"', '"+criador+"', '"+complexidade+"', '"+tema+"','"+tempo+"');";
            PreparedStatement pst1 = con.prepareStatement(insert);
            int rs1 = pst1.executeUpdate();
            global.setPergCriada(true);
            pst1.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void novoUsuario (String login, String nome, String tipo, String email, String senha){

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(global.getClassforname());
            Connection  con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            global.setUsuarioExistente(false);
            String insert = "INSERT INTO usuario (login, nome, tipo, email, senha) VALUES ('"+login+"', '"+nome+"', '"+tipo+"', '"+email+"', '"+senha+"');";
            String sql = "select * from usuario where login='"+login+"'";
            PreparedStatement pst1 = con.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();

            if(rs1.next()){
                   global.setUsuarioExistente(true);
            } else {
                PreparedStatement pst2 = con.prepareStatement(insert);
                int rs2 = pst2.executeUpdate();
                global.setUsuarioCriado(true);
                pst2.close();
            }
            pst1.close();
            rs1.close();
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

            Class.forName(global.getClassforname());
            Connection  con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            String sql = "select * from usuario where login='"+login+"' and senha= '"+senha+"'";
            PreparedStatement pst1 = con.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();


            if(rs1.next()){


                global.setId(Integer.parseInt(rs1.getObject(1).toString()));
                global.setLogin(login);
                global.setNome(rs1.getObject(3).toString());
                global.setTipo(rs1.getObject(4).toString());
                global.setLogado(true);


            } else {

                global.setLogado(false);
            }

            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fazerJogo(String insert){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(global.getClassforname());
            Connection con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            PreparedStatement pst1 = con.prepareStatement(insert);
            ResultSet rs1 = pst1.executeQuery();
            List<Integer> poolPergs = new ArrayList<Integer>();
            while (rs1.next()){
                poolPergs.add(Integer.parseInt(rs1.getObject(1).toString()));
                }
                if (poolPergs.size()==0){
                    global.setVazio(true);
                }
            global.setPoolPergs(poolPergs);
                rs1.close();
                pst1.close();
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

            Class.forName(global.getClassforname());
            Connection  con = DriverManager.getConnection(global.getURL(), global.getUser(), global.getPass());
            String sql = "select pergunta,opt1,opt2,opt3,opt4,resposta,tempo from pergunta where id= "+pergunta;
            PreparedStatement pst1 = con.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();


            if(rs1.next()){

                //pergunta existe
                String perguntaSql = rs1.getObject(1).toString();
                String opt1Sql = rs1.getObject(2).toString();
                String opt2Sql = rs1.getObject(3).toString();
                String opt3Sql = rs1.getObject(4).toString();
                String opt4Sql = rs1.getObject(5).toString();
                int respostaSql = Integer.parseInt(rs1.getObject(6).toString());
                int tempo = Integer.parseInt(rs1.getObject(7).toString());

                telaJogo passarDados = new telaJogo();
                passarDados.setPergunta(rs1.getObject(1).toString());
                passarDados.setOpt1(rs1.getObject(2).toString());
                passarDados.setOpt2(rs1.getObject(3).toString());
                passarDados.setOpt3(rs1.getObject(4).toString());
                passarDados.setOpt4(rs1.getObject(5).toString());
                global.setResposta(Integer.parseInt(rs1.getObject(6).toString()));
                global.setTempo(Integer.parseInt(rs1.getObject(7).toString()));



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
