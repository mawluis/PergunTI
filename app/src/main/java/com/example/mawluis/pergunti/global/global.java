package com.example.mawluis.pergunti.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mawluis on 01/05/2017.
 */

public class global {
    private static String login;
    private static boolean logado = false;
    private static int resposta;
    private static boolean usuarioExistente = false;
    private static boolean usuarioCriado = false;
    private static int vetor[]; //verificar se eu uso bagaça
    private static String[] consulta; //verificar se eu uso essa bagaça
    private static boolean pergCriada= false;
    private static int id;
    private static String nome;
    private static String tipo;
    private static List<Integer> poolPergs = new ArrayList<Integer>();

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







    public void vetores (String[] vetor){

    }

    public static void setConsulta( String con[] ) {
        consulta = Arrays.copyOf(con, con.length);
    }

    public static String[] getConsulta() {
        return Arrays.copyOf(consulta, consulta.length);
    }


    /* método automático getter and setter do Android Studio
    public static String[] getConsulta() {
        return consulta;
    }

    public static void setConsulta(String[] consulta) {
        global.consulta = consulta;
    }*/



    public static int[] getVetor() {
        return vetor;
    }

    public static void setVetor(int[] vetor) {
        global.vetor = vetor;
    }



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