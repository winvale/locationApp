package com.redylab.redylabsirr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.database.DatabaseManagerUser;

public class userRegistro extends AppCompatActivity {

    private TextView loginLink;
    private ImageView imageView;
    private EditText password;
    private EditText nombre;
    private EditText cedula;
    private Spinner estausu,tipousu;
    private Button registrar;
    private String sEstado,sTipo_usu;
    private DatabaseManagerUser managerUsuario;
    private String sNombre,sPassword,sCedula,sEst,sTipo ;
    private int request_code = 1;
    private int pos_estado,pos_tipo;
    private RoundedBitmapDrawable roundedBitmapDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.registro_user);

        imageView = (ImageView) findViewById(R.id.usuario_imagen_registro);
        //loginLink = (TextView)findViewById(R.id.link_login);
        cedula = (EditText)findViewById(R.id.cedula_registro);
        password = (EditText)findViewById(R.id.password_registro);
        nombre = (EditText)findViewById(R.id.nombre_registro);
        estausu =(Spinner) findViewById( R.id.spEst );
        tipousu =(Spinner)findViewById( R.id.spTusu );
        registrar = (Button)findViewById(R.id.btn_registro_usuario);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
        estausu.setSelection( 1 );
        estausu.setEnabled( false );
        estausu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                pos_estado = pos;//pos
                sEstado = adapterView.getItemAtPosition(pos_estado).toString();
                //Toast.makeText(getBaseContext(),"Estado "+sEstado.substring( 0,1 ),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        tipousu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                pos_tipo = pos;
                sTipo_usu = adapterView.getItemAtPosition(pos_tipo).toString();
                //Toast.makeText(getBaseContext(),"Tipo "+sTipo_usu.substring( 0,1 ),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });

        /*loginLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });*/
    }

    public void registrar(){

        if (!validar()) return;

        sCedula = cedula.getText().toString();
        sPassword = password.getText().toString();
        sNombre = nombre.getText().toString();
        sTipo =sTipo_usu.substring( 0,1 );
        sEst =sEstado.substring( 0,1 );
        ProgressBar progressBar = new ProgressBar(userRegistro.this, null, android.R.attr.progressBarStyleHorizontal);

        final ProgressDialog progressDialog = new ProgressDialog(userRegistro.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.show();

        managerUsuario = new DatabaseManagerUser(this);

        cedula.getText().clear();
        password.getText().clear();
        nombre.getText().clear();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(managerUsuario.comprobarRegistro2(sCedula)){
                            progressDialog.dismiss();
                            password.setText(sPassword);
                            nombre.setText(sNombre);
                            String mesg = "Los Datos que has ingresado  ya se encuentran registrados";
                            Toast.makeText(getApplicationContext(),mesg, Toast.LENGTH_LONG).show();
                        }else {
                            managerUsuario.insertar_parametros(null, sCedula , sNombre , sPassword ,sEst ,sTipo);

                            String mesg = String.format("%s ha sido guardado", sNombre);
                            Toast.makeText(getBaseContext(),mesg, Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(getApplicationContext(),mainActivity.class);
                            intent.putExtra("IDENT",sCedula);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }
                    }
                }, 1000);
    }

    private boolean validar() {
        boolean valid = true;

        String sNombre = nombre.getText().toString();
        String sPassword = password.getText().toString();
        String sCedula = cedula.getText().toString();

        if (sNombre.isEmpty() || sNombre.length() < 3) {
            nombre.setError("Ingrese al menos 3 caracteres");
            valid = false;
        } else {
            nombre.setError(null);
        }

        if (sCedula.isEmpty()) {
            cedula.setError("Ingrese la Cedula");
            valid = false;
        } else {
            cedula.setError(null);
        }

        if (sPassword.isEmpty() || password.length() < 4 || password.length() > 12) {
            password.setError("Ingrese entre 4 a 12 Numeros");
            valid = false;
        } else {
            password.setError(null);
        }
        if(estausu.getSelectedItem().toString().trim().equals("SELECCIONE")){
            Toast.makeText(getBaseContext(),"Error! Seleccione un Estado",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(tipousu.getSelectedItem().toString().trim().equals("SELECCIONE")){
            Toast.makeText(getBaseContext(),"Error! Seleccione el Tipo de Usuario",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == request_code){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


