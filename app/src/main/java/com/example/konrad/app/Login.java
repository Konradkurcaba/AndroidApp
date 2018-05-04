package com.example.konrad.app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.Socket;

public class Login extends AppCompatActivity implements NetworksCallbacks{

    private Socket socket;

    private static final int SERVERPORT = 2500;
    private static final String SERVERIP = "10.0.2.2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connect();
    }

    private void connect()
    {
        ConnectToServer connectTask = new ConnectToServer(SERVERIP,SERVERPORT,this);
        connectTask.execute();
    }

    public void connectResult(Socket socket)
    {
        if(this.socket != null) {
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

            socket = new Socket(SERVERIP, SERVERPORT);

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