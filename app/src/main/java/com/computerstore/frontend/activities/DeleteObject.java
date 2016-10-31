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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DeleteObject extends AppCompatActivity {

    private  Button btnSubmit;
    private EditText txtIdToDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        setControls();
    }

    private void setControls() {
        btnSubmit = (Button) findViewById(R.id.btnSubmitDelete);
        txtIdToDelete = (EditText) findViewById(R.id.txtEnterIDDelete);
    }

    public void onBtnSubmitDeleteClick(View view)
    {
            if(txtIdToDelete.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(),"ID field may not be blank",Toast.LENGTH_SHORT).show();
            }
            else
            {
                InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                new DeleteDrink().execute();
            }
    }

    public void onBtnReadDataHomeClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class DeleteDrink extends AsyncTask<Void,Void,Void>
    {
        private String id;
        private int httResponse;
        @Override
        protected void onPreExecute() {
            id = txtIdToDelete.getText().toString();
            httResponse = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://10.0.3.2:8080/memory/" + id;
            HttpURLConnection connection = null;
            URL uri = null;
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection) uri.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(false);
                connection.setRequestMethod("DELETE");
                httResponse = connection.getResponseCode();
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
            if(httResponse == 204)
            {
                Toast.makeText(getApplicationContext(),"Item deleted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"ID not in database",Toast.LENGTH_SHORT).show();
            }
        }
    }
}