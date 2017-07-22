package com.mk.async;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import static com.mk.async.ConnectionManager.buReader;
import static com.mk.async.ConnectionManager.buWriter;
import static com.mk.async.ConnectionManager.openConnect;

/**
 * Created by MK on 11/07/2017.
 */

public class Async extends AsyncTask<String,String,String> {
Context context;
DataListener dataListener;

    public Async(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... Strings) {
        String type = Strings[0];
        String link = "http://192.168.1.123/ayokhedma/" + type + ".php";
        HttpURLConnection connection = null;
        String queryType = Strings[1];
        String query = Strings[2];
        try {
            connection = openConnect(link);
            buWriter(connection, queryType, query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = null;
        try {
            result = buReader(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dataListener.onDataListener(s);

    }


    public void dataListener(DataListener dataListener) {
        this.dataListener=dataListener;
    }
}
