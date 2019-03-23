package com.example.tetrisapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
    }
    public void btnIniciarJuego(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
    public void btnInformacion(View view){
        Intent i = new Intent(this,Informacion.class);
        startActivity(i);
        finish();
    }
}
