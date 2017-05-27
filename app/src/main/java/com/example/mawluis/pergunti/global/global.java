package com.example.mawluis.pergunti.global;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mawluis on 01/05/2017.
 */

public class global {



    private static final String URL="jdbc:postgresql://ec2-54-243-253-17.compute-1.amazonaws.com:5432/djdvphd5vpn4l?sslmode=require";
    private static final String user="aqxgmmdlvyecas";
    private static final String pass="bb7241b8c75b44f40e50d3ab71c84cc51d9f9708301f82bd7a508daae0ef285b";
    private static final String classforname="org.postgresql.Driver"; //com.mysql.jdbc.Driver ou org.postgresql.Driver

    private static String login;
    private static boolean logado = false;
    private static int resposta;
    private static boolean usuarioExistente = false;
    private static boolean usuarioCriado = false;
    private static boolean pergCriada= false;
    private static int id;
    private static String nome;
    private static String tipo;
    private static List<Integer> poolPergs = new ArrayList<Integer>();
    private static String game = "normal";
    private static boolean repetido = false;
    private static boolean vazio=false;
    private static int tempo;

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte b1 : b){
            sb.append(Integer.toHexString(b1 & 0xff));
        }
        return sb.toString();
    }

    public static int getTempo() {
        return tempo;
    }

    public static void setTempo(int tempo) {
        global.tempo = tempo;
    }






    public static String getURL() {
        return URL;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }

    public static String getClassforname() {
        return classforname;
    }






    public static boolean isVazio() {
        return vazio;
    }

    public static void setVazio(boolean vazio) {
        global.vazio = vazio;
    }



    public static boolean isRepetido() {
        return repetido;
    }

    public static void setRepetido(boolean repetido) {
        global.repetido = repetido;
    }




    public static String getGame() {
        return game;
    }

    public static void setGame(String game) {
        global.game = game;
    }



    public static List<Integer> getPoolPergs() {
        return poolPergs;
    }

    public static void setPoolPergs(List<Integer> poolPergs) {
        global.poolPergs = poolPergs;
    }



    public static String getNome() {
        return nome;
    }

    public static void setNome(String nome) {
        global.nome = nome;
    }

    public static String getTipo() {
        return tipo;
    }

    public static void setTipo(String tipo) {
        global.tipo = tipo;
    }



    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        global.id = id;
    }



    public static boolean isPergCriada() {
        return pergCriada;
    }

    public static void setPergCriada(boolean pergCriada) {
        global.pergCriada = pergCriada;
    }






/*
    public void vetores (String[] vetor){

    }

    public static void setConsulta( String con[] ) {
        consulta = Arrays.copyOf(con, con.length);
    }

    public static String[] getConsulta() {
        return Arrays.copyOf(consulta, consulta.length);
    }
*/

    /* método automático getter and setter do Android Studio
    public static String[] getConsulta() {
        return consulta;
    }

    public static void setConsulta(String[] consulta) {
        global.consulta = consulta;
    }*/

/*

    public static int[] getVetor() {
        return vetor;
    }

    public static void setVetor(int[] vetor) {
        global.vetor = vetor;
    }

*/

    public static boolean isUsuarioCriado() {
        return usuarioCriado;
    }

    public static void setUsuarioCriado(boolean usuarioCriado) {
        global.usuarioCriado = usuarioCriado;
    }



    public static boolean isUsuarioExistente() {
        return usuarioExistente;
    }

    public static void setUsuarioExistente(boolean usuarioExistente) {
        global.usuarioExistente = usuarioExistente;
    }



    public static int getResposta() {
        return resposta;
    }

    public static void setResposta(int resposta) {
        global.resposta = resposta;
    }



    public static boolean isLogado() {
        return logado;
    }

    public static void setLogado(boolean logado) {
        global.logado = logado;
    }




    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        global.login = login;
    }





}
