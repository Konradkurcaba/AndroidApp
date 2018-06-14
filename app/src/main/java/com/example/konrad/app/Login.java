package com.example.konrad.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(User.getLoginStatus())
        {
            Intent i = new Intent(this,SynchronizeActivity.class);
            this.startActivity(i);
        }

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login("Antoni", "Dzik");
            }});


        connect();

    }




    private void connect()
    {
        ConnectToServer connectTask = new ConnectToServer(SERVERIP,SERVERPORT,this);
        connectTask.execute();
    }

    private void login(String login,String password)
    {
        LogInTask loginTask = new LogInTask(login,password,this);
        loginTask.execute();
    }


    @Override
    public void connectResult(BufferedReader reader,PrintWriter writer,Socket socket)
    {
        if(reader!= null && writer!=null) {
            User.setParameters(socket,reader,writer);
        }else
        {
            // to do when connect failed
            System.out.print("Connect failed");
        }
    }

    @Override
    public void loginResult(boolean result) {

        if(result) {

            User.setLoginStatus(true);
            Intent i = new Intent(this,SynchronizeActivity.class);
            this.startActivity(i);

        }
        else
        {
            User.setLoginStatus(false);
            Toast.makeText(getApplicationContext(),
                    "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(User.getLoginStatus())
        {
            this.finish();
        }
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
        callback.connectResult(bufferedReader,writer,socket);
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

    public LogInTask(String login, String password,NetworksCallbacks callback)
    {
        this.bufferedReader = User.getSocketReader();
        this.writer = User.getSocketWriter();
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

