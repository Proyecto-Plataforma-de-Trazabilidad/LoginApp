package com.example.loginapp.Indexs.Movimientos.Productores.Ordenes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Indexs.Index;
import com.example.loginapp.Indexs.Movimientos.Index_movimi_productor;
import com.example.loginapp.R;

public class ConsulGeneralDelProductor extends AppCompatActivity {
    TableLayout tbtCOP;
    String emisorname;
    TextView nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_general_del_productor);

        tbtCOP = findViewById(R.id.tablaO);
        tbtCOP.removeAllViews();//remueve columnas

        //variables sesion
        String emisorname = Index.obtenerrol(ConsulGeneralDelProductor.this, Index.no);
        Toast.makeText(ConsulGeneralDelProductor.this, emisorname, Toast.LENGTH_SHORT).show();

        nom=findViewById(R.id.productor);
        nom.setText(Html.fromHtml("<b>Productor: </b>"+emisorname));

        cargaOrdenes();
    }

    private void cargaOrdenes() {
         for (int i = 0; i < 5;i++ ) {

            View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_ordenes, null, false);

            TextView id = registro.findViewById(R.id.col1);
            TextView distri = registro.findViewById(R.id.col2);
            TextView fact = registro.findViewById(R.id.col3);
            TextView rece = registro.findViewById(R.id.col4);

            //agregar bton
            ImageButton boton=registro.findViewById(R.id.btndetalle);

            id.setText("id");
            distri.setText("naylea");
            fact.setText("factura");
            rece.setText("receta");

            //un valor id valido a boton segun cada fila que se genere
            boton.setId(View.generateViewId());
            boton.setTag(i);//asigna su identificador
             boton.setImageDrawable(getDrawable(R.drawable.detalle));

            boton.setOnClickListener(new View.OnClickListener() {
                @Override //que pasa si se da click en el boton, aqui debe mandar  a llamar a otro metodo que carge el detalle
                public void onClick(View v) {
                    Toast.makeText(ConsulGeneralDelProductor.this, "soy el boton:"+v.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
             tbtCOP.addView(registro);
        }
    }
}