package com.example.konrad.app;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    static private boolean loginStatus = false;
    static private Socket userSocket = null;
    static private BufferedReader socketReader = null;
    static private PrintWriter socketWriter = null;

    public static boolean getLoginStatus() {
        return loginStatus;
    }

    public static Socket getUserSocket() {
        return userSocket;
    }

    public static BufferedReader getSocketReader() {
        return socketReader;
    }

    public static PrintWriter getSocketWriter() {
        return socketWriter;
    }
    protected static void setParameters(Socket socket,BufferedReader reader,PrintWriter writer)
    {
        userSocket = socket;
        socketReader = reader;
        socketWriter = writer;

    }
    protected static void setLoginStatus(boolean status)
    {
        loginStatus = true;
    }
}
