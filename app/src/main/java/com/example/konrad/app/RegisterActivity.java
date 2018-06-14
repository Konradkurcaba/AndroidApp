package com.example.konrad.app;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        TextInputEditText loginView = (TextInputEditText) findViewById(R.id.register_input) ;
        TextInputEditText passwordView = (TextInputEditText) findViewById(R.id.password_input);
        TextInputEditText passwordView2 = (TextInputEditText) findViewById(R.id.password2_input);

        registerButton.setOnClickListener((event) ->
        {
            String login = loginView.getText().toString();
            String password = passwordView.getText().toString();
            String password2 = passwordView2.getText().toString();

            if(password.equals(password2))
            {
                RegisterTask registerTask = new RegisterTask(login,password,getApplicationContext());
                loginView.setText("");
                passwordView.setText("");
                passwordView2.setText("");
            }

        });

    }
}
class RegisterTask extends AsyncTask<Void,Void,Void>
{

    private BufferedReader bufferedReader;
    private PrintWriter writer;
    private String login;
    private String password;
    private Context aplicationContext;
    private String loginStatus = null;

    public RegisterTask(String login, String password,Context context)
    {
        this.bufferedReader = User.getSocketReader();
        this.writer = User.getSocketWriter();
        this.login = login;
        this.password = password;
        this.aplicationContext = context;

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            writer.println("Register");
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
        if(loginStatus.equals("OK"))
        {
            Toast.makeText(aplicationContext, "Konto zostało utworzone, mozesz sie zalogowac", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(aplicationContext, "Rejestracja nie powiodła się", Toast.LENGTH_SHORT).show();
        }

    }
}