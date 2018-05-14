package com.example.konrad.app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Login extends AppCompatActivity implements NetworksCallbacks{

    private Socket socket;
    private Button loginButton;
    private static final int SERVERPORT = 2500;
    private static final String SERVERIP = "10.0.2.2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login();
            }
        });


        connect();
    }

    private void connect()
    {
        ConnectToServer connectTask = new ConnectToServer(SERVERIP,SERVERPORT,this);
        connectTask.execute();
    }

    private void login()
    {
        LogInTask loginTask = new LogInTask(socket);
        loginTask.execute();
    }

    public void connectResult(Socket socket)
    {
        if(socket != null) {
            this.socket = socket;
        }else
        {
            // to do when connect failed
            System.out.print("Connect failed");
        }
    }


}

class ConnectToServer extends AsyncTask<String,Integer,Void>
{

    private Socket socket;
    private final String SERVERIP;
    private final int SERVERPORT;
    private final NetworksCallbacks callback;

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
        callback.connectResult(socket);
    }
}

class LogInTask extends AsyncTask<Void,Void,Void>
{

    Socket socket;

    BufferedReader bufferedReader;
    PrintWriter writer;


    public LogInTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);

            writer.println("LOGIN");



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
}
