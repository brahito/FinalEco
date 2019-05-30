package com.example.finaleco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Observer{
    private ConexionUDP conexion;
    String prueba;
    private Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prueba = "sdbyuasbf";
        boton = findViewById(R.id.actualizar);
        ConexionUDP.setConexion("192.168.0.20", 5000);
        conexion = ConexionUDP.getInicializar();
        conexion.getObservador(this);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("coso" + prueba, "coso" +prueba);

            }
        });
    }

    @Override
    public void recibirMensaje(String mensaje) {
        prueba = mensaje;
    }
}
