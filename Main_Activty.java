package com.example.proyecto015;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


// Esta clase que implementará las altas, bajas, modificaciones y consultas:

public class MainActivity extends Activity {

	private EditText et1, et2, et3, et4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// estos son los escuchadores
		et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	  public void alta(View v) // este es el metodo para registar por el boton alta
	  {
	        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
	        SQLiteDatabase bd = admin.getWritableDatabase(); // este es el metodo que utiliza los campos del layout en este caso todos los etitext
	        String dni = et1.getText().toString();
	        String nombre = et2.getText().toString();
	        String colegio = et3.getText().toString();
	        String nromesa = et4.getText().toString();
	        
	        ContentValues registro = new ContentValues();  // Este es el metodo que hace que se inserten los registros en la tabla
	        registro.put("dni", dni);  // aca es donde coge el dni que isertamos
	        registro.put("nombre", nombre);  // aca es donde coge el nombre que insertamos
	        registro.put("colegio", colegio); // aca es donde coge el colegio que insertamos
	        registro.put("nromesa", nromesa);  // aca es donde coger el numero de mesa que insertamos
	        
	        bd.insert("votantes", null, registro);  // este es el metodo que inserta los datos en la tabla votantes, para ello utiliza el metodo registro que esta arriba
	        bd.close();
	        et1.setText("");  // una ves que lo inserta deja el etites en blanco este es el del dni
	        et2.setText("");  // una ves que lo inserta deja el etites en blanco este es el del nombre
	        et3.setText(""); // una ves que lo inserta deja el etites en blanco este es el del colegio
	        et4.setText("");  // una ves que lo inserta deja el etites en blanco este es el del numero de mesa
	        Toast.makeText(this, "Se cargaron los datos de la persona",  Toast.LENGTH_SHORT).show(); // este es mensaje que imprime  cuando termina de ejecutarse la insercion
	    }

	    public void consulta(View v) // este es el metodo para consultar
	   {
	        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);  // llama a la db administracion
	        SQLiteDatabase bd = admin.getWritableDatabase(); // este es el metodo que utiliza los campos del layout en este caso el dni
	        String dni = et1.getText().toString(); // aca toma el dato que ponermos del dni
	        
	        Cursor fila = bd.rawQuery("select nombre,colegio,nromesa  from votantes where dni=" + dni, null);  // este metodo toma el valos del dni y con esto lo procesa en la siguiente en ese select
	        if (fila.moveToFirst()) {  // aca lo que dise es que si el dni es correcto muestre nombre, colegio y numero de mesa
	            et2.setText(fila.getString(0));
	            et3.setText(fila.getString(1));
	            et4.setText(fila.getString(2));
	        } else
	            Toast.makeText(this, "No existe una persona con dicho dni", Toast.LENGTH_SHORT).show();  
	        // aca lo que desie es que si no existe el dni en la tabla imprema atraves del toast  No existe una persona con dicho dni"
	             bd.close();

	    }
	    
	    // este metodo borra los datos 

	    public void baja(View v) {
	        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);  // abren la base de datos
	        SQLiteDatabase bd = admin.getWritableDatabase(); // abren el metodo ver y editar
	        
	        String dni = et1.getText().toString(); // llama al del registro dni que queremos borrar
	        int cant = bd.delete("votantes", "dni=" + dni, null); // ejejcuta el metodo de borrar por dni
	        bd.close();  // cierra la bd ne por que la cierra antes de borrar los datos
	        et1.setText(""); // borra dni
	        et2.setText("");  // borra nombre
	        et3.setText("");  // borra colegio
	        et4.setText("");  // borra numero de mesa
	        if (cant == 1)  // es este if dise que si la cantidad de registros fue 1 imprima el prmero sino imprima el segundo
	            Toast.makeText(this, "Se borró la persona con dicho documento",
	                    Toast.LENGTH_SHORT).show();
	        else
	            Toast.makeText(this, "No existe una persona con dicho documento",
	                    Toast.LENGTH_SHORT).show();
	    }

	    
	    // este es el metodo para modificar los datos
	    
	    public void modificacion(View v) {
	        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1); // abre la db
	        SQLiteDatabase bd = admin.getWritableDatabase(); // abre el metodo ver y editar        
	        String dni = et1.getText().toString();  // estos son los registros que llama
	        String nombre = et2.getText().toString();
	        String colegio = et3.getText().toString();
	        String nromesa = et4.getText().toString();
	        
	        ContentValues registro = new ContentValues();  // este es el metodo que modifica los parametros
	        registro.put("nombre", nombre);  // se tomas los 4 datos y llenan un array temporal de la calse contenvalues
	        registro.put("colegio", colegio);
	        registro.put("nromesa", nromesa);
	        
	        int cant = bd.update("votantes", registro, "dni=" + dni, null);  // el ubdate de la clase SQLiteDatabase data base osea db.update es como SQLiteDatabase.database es el cargado de cambiar los valores por los nuevos
	        bd.close();                                              
	        if (cant == 1) // si el registro modificado es 1 imprima lo primo sino imprima lo segundo
	            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
	                    .show();
	        else
	            Toast.makeText(this, "no existe una persona con dicho documento",
	                    Toast.LENGTH_SHORT).show();
	    }

	}
