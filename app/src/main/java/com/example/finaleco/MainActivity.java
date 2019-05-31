package com.example.finaleco;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Observer{
    private ConexionUDP conexion;
    String prueba;
    private Button boton;
    private ListView listaPuntos;
    public static final String correo = "";
    private Ingresar ingresar;
    private boolean estado;
    private DatabaseReference Puntajes,consultar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prueba = "";
        boton = findViewById(R.id.actualizar);
        listaPuntos = findViewById(R.id.listaPuntos);
        ConexionUDP.setConexion("172.30.174.151", 5000);
        conexion = ConexionUDP.getInicializar();
        conexion.getObservador(this);
        ingresar = new Ingresar();

        estado = false;
        boton.setOnClickListener(this);
        Puntajes = FirebaseDatabase.getInstance().getReference("Puntajes");
       // consultar = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_Puntajes));

        Puntajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayAdapter <String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();
                for (DataSnapshot datasnapshot:dataSnapshot.getChildren()){
                    Puntajes puntajes = datasnapshot.getValue(Puntajes.class);
                    String datos =puntajes.getUsuario() +" tiene un puntaje de: "+ puntajes.getPuntos();
                    listado.add(datos);
                }
                adaptador = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,listado);
                listaPuntos.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
            String clave = Puntajes.push().getKey();
            if(estado==true){
                Puntajes.child(clave).setValue(subir);
            }
            prueba = "";
            boton.setTextColor(Color.rgb(255,255,255));
            estado=false;
        }
    }
}
