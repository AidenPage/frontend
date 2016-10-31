package com.computerstore.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.computerstore.frontend.activities.CreateObject;
import com.computerstore.frontend.activities.DeleteObject;
import com.computerstore.frontend.activities.ReadAllObjects;
import com.computerstore.frontend.activities.ReadObjectByID;
import com.computerstore.frontend.activities.UpdateObject;

public class MainActivity extends AppCompatActivity {

    Button btnCreate,btnReadByID,btnReadAll,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();
    }

    private void setButtons()
    {
        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnReadByID = (Button)findViewById(R.id.btnReadByID);
        btnReadAll = (Button)findViewById(R.id.btnReadAll);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
    }

    public void onBtnCreateClick(View view) {
        Intent intent  = new Intent(this,CreateObject.class);
        startActivity(intent);
        finish();
    }

    public void onBtnReadByIDClick(View view) {
        Intent intent = new Intent(this,ReadObjectByID.class);
        startActivity(intent);
        finish();
    }

    public void onBtnReadAllClick(View view) {
        Intent intent = new Intent(this,ReadAllObjects.class);
        startActivity(intent);
        finish();
    }

    public void onBtnUpdateClick(View view) {
        Intent intent = new Intent(this,UpdateObject.class);
        startActivity(intent);
        finish();
    }

    public void onBtnDeleteClick(View view) {
        Intent intent = new Intent(this,DeleteObject.class);
        startActivity(intent);
        finish();
    }
}
