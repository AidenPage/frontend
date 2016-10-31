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
import android.widget.Toast;

import com.computerstore.frontend.MainActivity;
import com.computerstore.frontend.R;
import com.computerstore.frontend.domain.components.Memory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CreateObject extends AppCompatActivity {

    private Button btnSubmit;
    private EditText txtName,txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_object);
        setControls();
    }

    private void setControls()
    {
        btnSubmit = (Button)findViewById(R.id.btnSubmitCreate);
        txtName = (EditText)findViewById(R.id.txtName);
        txtPrice = (EditText)findViewById(R.id.txtPrice);
    }

    public void onBtnSubmitCreateClick(View view) {
        if ((txtName.getText().toString().isEmpty()) || (txtPrice.getText().toString().isEmpty()))
        {
            Toast.makeText(getApplicationContext(),"You may not have blank fields",Toast.LENGTH_LONG).show();
        }
        else {
            InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            new SendColdDrink().execute();
        }
    }

    public void onBtnReadDataHomeClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    private class SendColdDrink extends AsyncTask<Void,Void,Void>
    {
        private Memory memory = new Memory();
        private String name, price;
        private int response;
        @Override
        protected void onPreExecute() {
            name = txtName.getText().toString();
            price = txtPrice.getText().toString();
            memory.setName(name);
            memory.setPrice(price);
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection connection = null;
            String url = "http://10.0.3.2:8080/memory/";
            URL uri = null;
            JSONObject jsonObject = new JSONObject();
            //Set JSON values
            try {
                jsonObject.put("name",memory.getName());
                jsonObject.put("price",memory.getPrice());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Open connection
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection)uri.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                //Write Data
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
                response = connection.getResponseCode();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(response == 201)
            {
                Toast.makeText(getApplicationContext(),"Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}