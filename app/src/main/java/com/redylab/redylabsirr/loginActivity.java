package com.redylab.redylabsirr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.database.DatabaseManagerUser;


public class loginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText usuario,pass_usu;
    private String operario;
    private String password;
    private Cursor comprobar,comprobar2;
    private TextView registrar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login);
        usuario=(EditText) findViewById( R.id.usuario);
        pass_usu = (EditText) findViewById( R.id.password );
        Button bsalir =(Button) findViewById( R.id.buttonsalir);
        bsalir.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View view ) {
                finish();
            }
        } );
        //registrar = (TextView)findViewById(R.id.signup);

        /*registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),userRegistro.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });*/
        Button bingresar = (Button) findViewById( R.id.buttoningresar);
        bingresar.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View view ) {
                iniciar();
            }
        } );


    }

    private void iniciar() {
        if (!validar()) return;

        operario = usuario.getText().toString();
        password = pass_usu.getText().toString();

        //ProgressBar progressBar = new ProgressBar(loginActivity.this, null, android.R.attr.progressBarStyleHorizontal);
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Iniciando...");
        progressDialog.show();

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        usuario.getText().clear();
        pass_usu.getText().clear();

            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (databaseManager.comprobarRegistro2(operario)){
                            comprobar = databaseManager.getDb().rawQuery("SELECT cedula,nombre,estado FROM users" + " WHERE cedula='"+operario+"' AND pass='"+password+"' AND estado='A'",null);
                            if(comprobar.moveToFirst()){
                                Intent intent =new Intent(getApplicationContext(),mainActivity.class);
                                intent.putExtra("IDENT",operario);
                                startActivity(intent);
                                usuario.getText().clear();
                                pass_usu.getText().clear();
                                finish();
                                progressDialog.dismiss();
                            }else{
                                comprobar2 = databaseManager.getDb().rawQuery("SELECT cedula,nombre,estado FROM users" + " WHERE cedula='"+operario+"' AND pass='"+password+"' AND estado='I'",null);
                                if(comprobar2.moveToFirst()){
                                    usuario.setText(operario);
                                    progressDialog.dismiss();
                                    String mesg = "Usuario Inactivo. Hable con el Administrador del Aplicativo Redylab SIRR";
                                    Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();
                                }else{
                                    usuario.setText(operario);
                                    progressDialog.dismiss();
                                    String mesg = "Password incorrecto";
                                    Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();
                                    }
                            }
                        }else{
                            progressDialog.dismiss();
                            String mesg = "El Usuario que has introducido no coinciden con ninguna cuenta";
                            Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();

                        }
                    }
                }, 2000);

    }

    private boolean validar() {
        boolean valid = true;

        String operario = usuario.getText().toString();
        String password = pass_usu.getText().toString();

        if (operario.isEmpty()) {
            usuario.setError("Introduzca un Usuario válido");
            valid = false;
        } else {
            usuario.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            pass_usu.setError("Entre 4 y 12 Numeros");
            valid = false;
        } else {
            pass_usu.setError(null);
        }

        return valid;
    }

}




