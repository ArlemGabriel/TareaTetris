package com.example.tetrisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Informacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
    }
    public void btnAtrasClicked(View view){
        Intent i = new Intent(this,PantallaInicio.class);
        startActivity(i);
        finish();
    }

}
