package com.computerstore.frontend.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.computerstore.frontend.MainActivity;
import com.computerstore.frontend.R;
import com.computerstore.frontend.domain.components.Memory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReadObjectByID extends AppCompatActivity {


    private Button btnSubmit,btnHome;
    private TextView txtShow;
    private EditText txtGetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_by_id);
        setControls();
    }

    private void setControls()
    {
        btnSubmit = (Button)findViewById(R.id.btnSubmitID);
        btnHome = (Button)findViewById(R.id.btnReadByIDHome);
        txtShow = (TextView)findViewById(R.id.txtShowByID);
        txtGetID =(EditText)findViewById(R.id.txtEnterID);
    }

    public void onBtnSubmitIDClick(View view) {
        if((txtGetID.getText().toString().isEmpty()))
        {
            Toast.makeText(getApplicationContext(),"Please enter a numeric ID first",Toast.LENGTH_LONG).show();
        }
        else
        {
            InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            new GetColdDrink().execute();
        }
    }

    public void onBtnReadByIDHomeClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class GetColdDrink extends AsyncTask<Void,Void,Void>
    {
        private String id,result;
        private int httpResult;
        @Override
        protected void onPreExecute() {
            id = txtGetID.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://10.0.3.2:8080/memory/" + id;
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
            }
            finally {
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
                Memory beverages = gson.fromJson(result,Memory.class);
                txtShow.setText(String.format("ID:%d\nName: %s\nPrice: %s", beverages.get_id(), beverages.getName(), beverages.getPrice()));
            }
            else {
                Toast.makeText(getApplicationContext(),"ID not in database",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
