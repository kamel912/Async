package com.mk.async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by seven tg on 29/05/2017.
 */

public class ConnectionManager {
    public static boolean isInternetConectted(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null)
            return true;
        return false;
    }

    public static HttpURLConnection openConnect(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection urlConnection;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("POST");
            // setDoInput and setDoOutput to true as we send and recieve data
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);


            }
        }finally {

        }
        return urlConnection;
    }

    public static String buReader(HttpURLConnection urlConnection) throws IOException {
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line);
        }
        inputStream.close();
        urlConnection.disconnect();
        return buffer.toString();
    }

    public static void buWriter(HttpURLConnection urlConnection, String queryKey, String queryValue) throws IOException {

        Uri.Builder builder = new Uri.Builder().appendQueryParameter(queryKey, queryValue);
       // String query = builder.build().getEncodedQuery();
        OutputStream outputStream = urlConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        bufferedWriter.write(builder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        urlConnection.disconnect();
    }
}
