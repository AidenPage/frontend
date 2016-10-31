package com.computerstore.frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.computerstore.frontend.MainActivity;
import com.computerstore.frontend.R;
import com.computerstore.frontend.domain.components.Memory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReadAllObjects extends AppCompatActivity {

    private  Button btnHome;
    private  TextView txtRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all);
        setControls();
        new ReadAllColdDrinks().execute();

    }

    private void setControls() {
        btnHome = (Button) findViewById(R.id.btnReadDataHome);
        txtRes = (TextView)findViewById(R.id.txtShowAll);
        txtRes.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onBtnReadDataHomeClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    class ReadAllColdDrinks extends AsyncTask<Void,Void,Void>
    {
        private String result;
        private int httpResult;
        @Override
        protected void onPreExecute() {
            result ="";
            httpResult = 0;
        }
        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://10.0.3.2:8080/memories/";
            HttpURLConnection connection = null;
            URL uri = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection) uri.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestMethod("GET");
                //Reader
                httpResult = connection.getResponseCode();
                if(httpResult == 200)
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }
                    reader.close();
                    result = stringBuilder.toString();
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (httpResult == 200)
            {
                Gson gson = new Gson();
                String temp[];
                try {
                    JSONArray p = (JSONArray) new JSONTokener(result).nextValue();
                    temp = new String[p.length()];
                    for(int i = 0; i < p.length(); i++)
                    {
                        temp[i] = p.getJSONObject(i).toString();
                    }
                    for(int i =0 ;i < temp.length;i++)
                    {
                        Memory b = gson.fromJson(temp[i], Memory.class);
                        txtRes.append(String.format("ID:%d\nName: %s\nPrice: %s\n\n", b.get_id(), b.getName(), b.getPrice()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
