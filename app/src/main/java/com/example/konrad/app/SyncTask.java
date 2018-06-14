package com.example.konrad.app;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SyncTask extends AsyncTask<Void,Void,Void>
{


    BufferedReader bufferedReader;
    PrintWriter writer;
    Context context;
    String syncStatus;

    SyncTask(BufferedReader reader,PrintWriter writer,Context context) throws IOException
    {

        this.context = context;
        this.bufferedReader = reader;
        this.writer = writer;

    }


    @Override
    protected Void doInBackground(Void... voids)  {

        writer.println("Sync");
        String status;
        try {
            status = bufferedReader.readLine();
        }catch (IOException e)
        {
            e.printStackTrace();
            syncStatus = "NOK";
            return null;
        }

        if(status.equals("OK"))
        {
            status = sendMealsIds();
            if(status.equals("OK"))
            {
                try {
                    List receiveMelas = receiveMealsFromServer();
                    DatabaseHelper db = new DatabaseHelper(context);
                    db.addMealsFromServer(receiveMelas);

                }catch (IOException e)
                {
                    syncStatus = "NOK";
                    return null;
                }

            }
            else {
                syncStatus = "NOK";
                return null;
            }
        }else
        {
            syncStatus = "NOK";
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    private String sendMealsIds()
    {
        String status;
        writer.println("Send");
        DatabaseHelper db = new DatabaseHelper(context);
        List<Integer> idsList = db.getAllMealsIDs();
        for(Integer idMeal : idsList)
        {
            writer.println(idMeal);
        }
        writer.println("Endsend");

        try {
            status = bufferedReader.readLine();
        }catch (IOException e)
        {
            e.printStackTrace();
            status = "NOK";
        }
        return status;
    }


    private List receiveMealsFromServer() throws IOException
    {
        List receiveMelas = new ArrayList<Diet>();
        String status = bufferedReader.readLine();
        if(status.equals("Send"))
        {
            do {
                String id = bufferedReader.readLine();
                String title = bufferedReader.readLine();
                String summary = bufferedReader.readLine();
                String description = bufferedReader.readLine();
                String mealDate = bufferedReader.readLine();
                String imagePath = bufferedReader.readLine();
                Diet diet = new Diet(title,summary,description,Long.parseLong(mealDate),imagePath);
                diet.setId(Integer.parseInt(id));
                receiveMelas.add(diet);
                status = bufferedReader.readLine();
            }while(!status.equals("Endsend"));
            writer.write("OK");
        }
        return receiveMelas;
    }

}