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

public class UpdateObject extends AppCompatActivity {

    private Button btnSubmit;
    private EditText txtId,txtName,txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setControls();
    }

    private void setControls()
    {
        btnSubmit = (Button)findViewById(R.id.btnSubmitUpdate);
        txtId = (EditText)findViewById(R.id.txtEnterCurrentID);
        txtName = (EditText)findViewById(R.id.txtNewName);
        txtPrice = (EditText)findViewById(R.id.txtNewPrice);

    }

    public void onBtnSubmitUpdateClick(View view) {
        if((txtId.getText().toString().isEmpty()) || (txtName.getText().toString().isEmpty()) || (txtPrice.getText().toString().isEmpty()))
        {
            Toast.makeText(getApplicationContext(),"You may not submit blank fields",Toast.LENGTH_SHORT).show();
        }
        else {
            InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            new UpdateDrink().execute();
        }
    }

    public void onBtnReadDataHomeClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class UpdateDrink extends AsyncTask<Void,Void,Void>
    {
        private Memory memory = new Memory();
        private String name, price,id;
        private int response;
        @Override
        protected void onPreExecute() {
            id = txtId.getText().toString();
            name = txtName.getText().toString();
            price = txtPrice.getText().toString();
            memory.setName(name);
            memory.setPrice(price);
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection connection = null;
            String url = "http://10.0.3.2:8080/memory/" + id;
            URL uri = null;
            JSONObject jsonObject = new JSONObject();
            //Set JSON values
            try {
                jsonObject.put("name",memory.getName());
                jsonObject.put("price",memory.getPrice());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection)uri.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("PUT");
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
            if (response == 200)
            {
                Toast.makeText(getApplicationContext(),"Updated successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"ID not in database",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
