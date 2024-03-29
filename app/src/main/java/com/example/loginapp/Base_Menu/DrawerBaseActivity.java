package com.example.loginapp.Base_Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.loginapp.Datos_Usuario.Perfil;
import com.example.loginapp.Index;
import com.example.loginapp.Catalogos.IndexCatalogos;
import com.example.loginapp.Movimientos.Index_movi_ERP;
import com.example.loginapp.Movimientos.Index_movi_distribuidor;
import com.example.loginapp.Movimientos.Index_movimi_productor;
import com.example.loginapp.Movimientos.indexmovientos;
import com.example.loginapp.Movimientos.indexmovimientoMunicipios;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.example.loginapp.Reportes.IndexReportes;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    String emisorRol;
    NavigationView navigationView;
    Intent m,c;


    public void setContentView(View view) {
        drawerLayout=(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container=drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar=drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //variables sesion
        emisorRol= MainActivity.obtenerrol(DrawerBaseActivity.this,MainActivity.r);
        //Toast.makeText(DrawerBaseActivity.this, "rolemitido: "+emisorRol, Toast.LENGTH_SHORT).show();

        switch (emisorRol){
            case "1"://admin
               // navigationView=drawerLayout.findViewById(R.id.nav_view);
                //navigationView.setNavigationItemSelectedListener(this);
                esconderAlgunosItems();
                break;
            case "2"://productor
                esconderItemsProductor();
                break;
            case "3"://distribuidor
               // navigationView=drawerLayout.findViewById(R.id.nav_view);
               // navigationView.setNavigationItemSelectedListener(this);
                esconderItemsDis();
                break;
            case "4"://municipios
                // navigationView=drawerLayout.findViewById(R.id.nav_view);
                // navigationView.setNavigationItemSelectedListener(this);
                esconderAlgunosItems();
                break;
            case "5"://empre recolec priva
                // navigationView=drawerLayout.findViewById(R.id.nav_view);
                // navigationView.setNavigationItemSelectedListener(this);
                esconderAlgunosItems();
                break;
            case "6"://empre destino
                esconderAlgunosItems();
                break;
            case "7"://AMOCALI
                // navigationView=drawerLayout.findViewById(R.id.nav_view);
                // navigationView.setNavigationItemSelectedListener(this);
                esconderAlgunosItems();
                break;
            case "8"://ASICA
                esconderAlgunosItems();
                break;
            case "9"://CESAVEJAL
                esconderAlgunosItems();
                break;
            case "10"://APEAJAL
                esconderAlgunosItems();
                break;
            case "11"://cat
                esconderAlgunosItems();
                break;
        }
        ActionBarDrawerToggle togggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(togggle);
        togggle.syncState();
    }

    //esconder items productor------------------------------------------------------------------------------------
    private void esconderItemsProductor() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu miMenu= navigationView.getMenu();

        miMenu.findItem(R.id.nav_ubisdis).setVisible(false);
        miMenu.findItem(R.id.nav_entredis).setVisible(false);
        miMenu.findItem(R.id.nav_alertasdis).setVisible(false);

        miMenu.findItem(R.id.nav_ubisjorna).setVisible(false);
        miMenu.findItem(R.id.nav_cargjorna).setVisible(false);
        miMenu.findItem(R.id.nav_entrejorna).setVisible(false);

        miMenu.findItem(R.id.nav_reportes).setVisible(false);

        navigationView.setNavigationItemSelectedListener(this);
    }
    //esconder items distri------------------------------------------------------------------------------------
    private void esconderItemsDis() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu miMenu= navigationView.getMenu();

        miMenu.findItem(R.id.nav_ubispro).setVisible(false);
        miMenu.findItem(R.id.nav_entrepro).setVisible(false);

        miMenu.findItem(R.id.nav_ubisjorna).setVisible(false);
        miMenu.findItem(R.id.nav_cargjorna).setVisible(false);
        miMenu.findItem(R.id.nav_entrejorna).setVisible(false);

        miMenu.findItem(R.id.nav_reportes).setVisible(false);

        navigationView.setNavigationItemSelectedListener(this);
    }
    //esconder items jornada------------------------------------------------------------------------------------
  /*  private void esconderItemsJorna() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu miMenu = navigationView.getMenu();

        miMenu.findItem(R.id.nav_ubispro).setVisible(false);
        miMenu.findItem(R.id.nav_entrepro).setVisible(false);

        miMenu.findItem(R.id.nav_ubisdis).setVisible(false);
        miMenu.findItem(R.id.nav_entredis).setVisible(false);
        miMenu.findItem(R.id.nav_alertasdis).setVisible(false);

        miMenu.findItem(R.id.nav_reportes).setVisible(false);

        navigationView.setNavigationItemSelectedListener(this);
    }*/

   private void esconderAlgunosItems() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu miMenu= navigationView.getMenu();

        //miMenu.findItem(R.id.nav_movimientos).setVisible(false);
       miMenu.findItem(R.id.nav_ubispro).setVisible(false);
       miMenu.findItem(R.id.nav_entrepro).setVisible(false);

       miMenu.findItem(R.id.nav_ubisdis).setVisible(false);
       miMenu.findItem(R.id.nav_entredis).setVisible(false);
       miMenu.findItem(R.id.nav_alertasdis).setVisible(false);

       miMenu.findItem(R.id.nav_ubisjorna).setVisible(false);
       miMenu.findItem(R.id.nav_cargjorna).setVisible(false);
       miMenu.findItem(R.id.nav_entrejorna).setVisible(false);

       miMenu.findItem(R.id.nav_reportes).setVisible(false);
       navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_user:
                Intent u = new Intent(this, Perfil.class);
                startActivity(u);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_home:
                Intent h = new Intent(this, Index.class);
                startActivity(h);
                overridePendingTransition(0,0);
                break;

//----------------------ubicaciones --------------------------------------------------------------------------
            case R.id.nav_ubispro://ubicaciones productor
                 c = new Intent(this, IndexCatalogos.class);
                startActivity(c);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_ubisdis://ubicaciones distri
                 c = new Intent(this, IndexCatalogos.class);
                startActivity(c);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_ubisjorna://ubicaciones jornada
                c = new Intent(this, IndexCatalogos.class);
                startActivity(c);
                overridePendingTransition(0,0);
                break;
//--------------------movimientos------------------------------------------------------------
            case R.id.nav_entrepro:
                m = new Intent(this, Index_movimi_productor.class);
                startActivity(m);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_entredis:
                m = new Intent(this, Index_movi_distribuidor.class);
                startActivity(m);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_entrejorna:
                break;
           /* case R.id.nav_movimientos:
                switch (emisorRol){
                    case "1"://admin
                        m = new Intent(this, indexmovientos.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case "2"://productor
                        m = new Intent(this, Index_movimi_productor.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case"3"://distri
                        m = new Intent(this, Index_movi_distribuidor.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case"4"://municipios
                        m = new Intent(this, indexmovimientoMunicipios.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case"5"://erp
                        m = new Intent(this, Index_movi_ERP.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case"7"://amocali
                        m = new Intent(this, indexmovientos.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                }
                break;*/
            //-----------Demas items------------------------------------------------------------------------------------------
            case R.id.nav_reportes:
                Intent r = new Intent(this, IndexReportes.class);
                startActivity(r);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_ayuda:
                Intent a = new Intent(this, Index.class);
                startActivity(a);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_salir:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("¿Deseas salir?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences;
                        preferences=getSharedPreferences("keyusuario",Context.MODE_PRIVATE);
                        preferences.edit().clear().apply();

                        Intent main = new Intent(DrawerBaseActivity.this, MainActivity.class);
                        startActivity(main);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
        return false;
    }

    protected void allowActivityTitle(String title) {
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setTitle(title);
        }
    }


}