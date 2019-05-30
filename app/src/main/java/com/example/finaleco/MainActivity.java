package com.example.finaleco;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Observer{
    private ConexionUDP conexion;
    String prueba;
    private Button boton;
    public static final String correo = "";
    private Ingresar ingresar;
    private boolean estado;
    private DatabaseReference Puntajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prueba = "";
        boton = findViewById(R.id.actualizar);
        ConexionUDP.setConexion("192.168.1.60", 5000);
        conexion = ConexionUDP.getInicializar();
        conexion.getObservador(this);
        ingresar = new Ingresar();
        estado = false;
        boton.setOnClickListener(this);
        Puntajes = FirebaseDatabase.getInstance().getReference("Puntajes");

    }
    public void registrarPuntaje(){

    }

    @Override
    public void recibirMensaje(String mensaje) {
        prueba = mensaje;
        boton.setTextColor(Color.rgb(255,0,0));
        estado = true;
    }

    @Override
    public void onClick(View v) {
        if(v.equals(boton)){
            String correo = getIntent().getStringExtra("");
            Puntajes subir = new Puntajes(correo,prueba);
            if(estado==true){
                Puntajes.child("Usuarios").child(correo).setValue(subir);
            }
            prueba = "";
            boton.setTextColor(Color.rgb(255,255,255));
            estado=false;
        }
    }
}
