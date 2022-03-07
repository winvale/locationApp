package com.redylab.redylabsirr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.redylab.redylabsirr.database.DatabaseManagerUser;

public class usuarios_update extends AppCompatActivity {

    private ImageView imageView;
    private EditText password;
    private EditText nombre;
    private EditText cedula;
    private Spinner estausu,tipousu;
    private Button registrar;
    private String sEstado,sTipo_usu,cod_idu,cod_ceu,cod_nou,cod_pau,cod_esu,cod_tiu,cod_ideu,cod_esuf,cod_tiuf;
    private DatabaseManagerUser managerUsuario;
    private String sNombre,sPassword,sCedula,sEst,sTipo ;
    private int request_code = 1;
    private int pos_estado,pos_tipo;
    private RoundedBitmapDrawable roundedBitmapDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.usuarios_updates );

        imageView = (ImageView) findViewById(R.id.usuario_imagen_registro);
        cedula = (EditText)findViewById(R.id.cedula_registro2);
        password = (EditText)findViewById(R.id.password_registro2);
        nombre = (EditText)findViewById(R.id.nombre_registro2);
        estausu =(Spinner) findViewById( R.id.spEst2 );
        tipousu =(Spinner)findViewById( R.id.spTusu2 );
        registrar = (Button)findViewById(R.id.btn_actualizar_usuario);

        Bundle miBundle2 = this.getIntent().getExtras();
        if (miBundle2 != null) {

            cod_idu = miBundle2.getString( "u_id");
            cod_ceu = miBundle2.getString( "u_ce");
            cod_nou = miBundle2.getString( "u_no");
            cod_pau = miBundle2.getString( "u_pa");
            cod_esu = miBundle2.getString( "u_es");
            cod_tiu = miBundle2.getString( "u_ti");
            cod_ideu = miBundle2.getString( "IDENT");

            cedula.setText( cod_ceu );
            password.setText( cod_pau );
            nombre.setText( cod_nou );
        }

        if (cod_esu.equals( "A" )){
            cod_esuf="ACTIVO";
        }else if(cod_esu.equals( "I" )){
            cod_esuf="INACTIVO";
        }
        if (cod_tiu.equals( "A" )){
            cod_tiuf="ADMINISTRADOR";
        }else if(cod_tiu.equals( "O" )){
            cod_tiuf="OPERADOR";
        }
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar();
            }
        });
        estausu.setSelection( obtenerPosicionItem( estausu,cod_esuf) );
        estausu.setEnabled( true );
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
        tipousu.setSelection( obtenerPosicionItem( tipousu,cod_tiuf) );
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
    }// termina el oncreate

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return false;
    }
    //
    //
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //codigo adicional
        this.finish();
    }
    //
    //=========================================================================================//
    //=================================MÉTODO QUE REALIZA LA TAREA=============================//
    //=========================================================================================//

    //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String datobuscado) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String observacion`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(datobuscado)) {
                //spinner.setSelection( i );
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
    //=========================================================================================//

    public void Actualizar(){

        if (!validar()) return;

        sCedula = cedula.getText().toString();
        sPassword = password.getText().toString();
        sNombre = nombre.getText().toString();
        sTipo =sTipo_usu.substring( 0,1 );
        sEst =sEstado.substring( 0,1 );
        ProgressBar progressBar = new ProgressBar(usuarios_update.this, null, android.R.attr.progressBarStyleHorizontal);

        final ProgressDialog progressDialog = new ProgressDialog(usuarios_update.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Actualizando Usuario...");
        progressDialog.show();

        managerUsuario = new DatabaseManagerUser(this);

        cedula.getText().clear();
        password.getText().clear();
        nombre.getText().clear();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                            managerUsuario.actualizar_parametros(cod_idu, sCedula , sNombre , sPassword ,sEst ,sTipo );
                            String mesg = String.format("%s ha sido Actualizado", sNombre);
                            Toast.makeText(getBaseContext(),mesg, Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(getApplicationContext(),ListaUsuariosRecycler.class);
                            intent.putExtra("IDENT",cod_ideu);
                            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();

                    }
                }, 1000);
    }

    private boolean validar() {
        boolean valid = true;
        managerUsuario = new DatabaseManagerUser(this);
        String sNombre = nombre.getText().toString();
        String sPassword = password.getText().toString();
        sCedula = cedula.getText().toString();

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
            if(managerUsuario.comprobarRegistro(sCedula,cod_idu)){
               //if(managerUsuario.comprobarRegistro(cod_idu)){
                   Toast.makeText(getBaseContext(),"Existe un Usuario con esta Cedula",Toast.LENGTH_SHORT).show();
                valid = false;
                cedula.setError(null);
               //}
            }
            //Log.i( "tag","idd "+cod_idu+"cedula "+sCedula );
            //managerUsuario.comprobarRegistro(sCedula,cod_idu);
            //Log.i( "tag","idd "+managerUsuario.comprobarRegistro(cod_idu) );
            //if(){
            //
           // }
            //cedula.setError(null);
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


