package com.example.finaleco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Ingresar extends AppCompatActivity implements View.OnClickListener{

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private Button btnIniciar;
    private ProgressDialog progressDialog;
    private String correo;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = findViewById(R.id.TxtEmail);
        TextPassword = findViewById(R.id.TxtPassword);

        btnRegistrar = findViewById(R.id.botonRegistrar);
        btnIniciar = findViewById(R.id.botonIniciar);
        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
        btnIniciar.setOnClickListener(this);



    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(Ingresar.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(Ingresar.this,"Este usuario ya existe ",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(Ingresar.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }


    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            Toast.makeText(Ingresar.this, "Bienvenido: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), MainActivity.class);
                            intencion.putExtra(MainActivity.correo, user);
                            startActivity(intencion);


                        } else {

                                Toast.makeText(Ingresar.this, "No se pudo ingresar el usuario ", Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();
                    }
                });


    }



    public void onClick(View view) {
        //Invocamos al método:
        if(view.equals(btnRegistrar)){
            registrarUsuario();
        }
        if(view.equals(btnIniciar)){
            loguearUsuario();
        }

    }
}
