package com.example.mawluis.pergunti;

/**
 * Created by Mawluis on 01/05/2017.
 */

public class global {
    private static String login;
    private static boolean logado = false;
    private static int resposta;

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
