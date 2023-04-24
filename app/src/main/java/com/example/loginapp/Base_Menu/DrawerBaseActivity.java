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
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.loginapp.Datos_Usuario.Perfil;
import com.example.loginapp.Indexs.Index;
import com.example.loginapp.Catalogos.IndexCatalogos;
import com.example.loginapp.Indexs.Movimientos.Index_movi_distribuidor;
import com.example.loginapp.Indexs.Movimientos.Index_movimi_productor;
import com.example.loginapp.Indexs.Movimientos.indexmovimientoMunicipios;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    String emisorRol;
    NavigationView navigationView;
    Intent m;


    public void setContentView(View view) {
        drawerLayout=(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container=drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar=drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //variables sesion
        emisorRol= MainActivity.obtenerrol(DrawerBaseActivity.this,MainActivity.r);
        Toast.makeText(DrawerBaseActivity.this, "rolemitido: "+emisorRol, Toast.LENGTH_SHORT).show();

        switch (emisorRol){
            case "1":
                esconderAlgunosItems();
                break;
            case "2":
                navigationView=drawerLayout.findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);
                break;
            case "3":
                navigationView=drawerLayout.findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);
                break;
            case "4":
                navigationView=drawerLayout.findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);
                break;
            case "5":
                esconderAlgunosItems();
                break;
            case "6":
                esconderAlgunosItems();
                break;
            case "7":
                esconderAlgunosItems();
                break;
            case "8":
                esconderAlgunosItems();
                break;
            case "9":
                esconderAlgunosItems();
                break;
            case "10":
                esconderAlgunosItems();
                break;
            case "11":
                esconderAlgunosItems();
                break;
        }



        ActionBarDrawerToggle togggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(togggle);
        togggle.syncState();

    }


    private void esconderAlgunosItems() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu miMenu= navigationView.getMenu();

        miMenu.findItem(R.id.nav_movimientos).setVisible(false);

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

            case R.id.nav_catalogos://Aqui agregar la referencia a index catalogos
                Intent c = new Intent(this, IndexCatalogos.class);
                startActivity(c);
                overridePendingTransition(0,0);
                break;

            case R.id.nav_movimientos:
                switch (emisorRol){
                    case "2":
                        m = new Intent(this, Index_movimi_productor.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case"3":
                        m = new Intent(this, Index_movi_distribuidor.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                    case"4":
                        m = new Intent(this, indexmovimientoMunicipios.class);
                        startActivity(m);
                        overridePendingTransition(0,0);
                        break;
                }
                break;
            case R.id.nav_reportes:
                Intent r = new Intent(this, Index.class);
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