package com.example.konrad.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity implements NetworksCallbacks{


    private Button loginButton;
    private Button syncButton;
    private static final int SERVERPORT = 2500;
    private static final String SERVERIP = "10.0.2.2";
    private boolean loginStatus = false;
    private static  BufferedReader bufferedReader;
    private static PrintWriter writer;


    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public static PrintWriter getWriter() {
        return writer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login("Antoni", "Dzik");
            }});


        connect();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(loginStatus)
        {
            Intent i = new Intent(this,SynchronizeActivity.class);
            this.startActivity(i);
        }
    }

    private void connect()
    {
        ConnectToServer connectTask = new ConnectToServer(SERVERIP,SERVERPORT,this);
        connectTask.execute();
    }

    private void login(String login,String password)
    {
        LogInTask loginTask = new LogInTask(bufferedReader,writer,login,password,this);
        loginTask.execute();
    }


    @Override
    public void connectResult(BufferedReader reader,PrintWriter writer)
    {
        if(reader!= null && writer!=null) {
            this.bufferedReader = reader;
            this.writer = writer;
            loginStatus = true;
        }else
        {
            // to do when connect failed
            System.out.print("Connect failed");
        }
    }

    @Override
    public void loginResult(boolean result) {

        if(result) {

            loginStatus = true;
            Intent i = new Intent(this,SynchronizeActivity.class);
            this.startActivity(i);

        }
        else loginStatus = false;

    }

}

class ConnectToServer extends AsyncTask<String,Integer,Void>
{

    private Socket socket;
    private final String SERVERIP;
    private final int SERVERPORT;
    private final NetworksCallbacks callback;
    private BufferedReader bufferedReader;
    private PrintWriter writer;

    ConnectToServer(String serverIp,int serverPort,NetworksCallbacks callback)
    {
        this.SERVERIP = serverIp;
        this.SERVERPORT = serverPort;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(String... strings) {
        try {

            socket = new Socket(SERVERIP, 2500);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);

        }catch(IOException e)
        {
            socket = null;
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.connectResult(bufferedReader,writer);
    }
}

class LogInTask extends AsyncTask<Void,Void,Void>
{



    private BufferedReader bufferedReader;
    private PrintWriter writer;
    private String login;
    private String password;
    private NetworksCallbacks callback;

    private String loginStatus = null;

    public LogInTask(BufferedReader reader,PrintWriter writer,String login, String password,NetworksCallbacks callback)
    {
        this.bufferedReader = reader;
        this.writer = writer;
        this.login = login;
        this.password = password;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            writer.println("LOGIN");
            writer.println(login);
            writer.println(password);

            loginStatus = bufferedReader.readLine();



        }catch (IOException e)
        {
            System.out.print(e.toString());

        }
        catch(Exception e)
        {
            System.out.print(e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(loginStatus.equals("OK")) callback.loginResult(true);
        else callback.loginResult(false);

    }
}

