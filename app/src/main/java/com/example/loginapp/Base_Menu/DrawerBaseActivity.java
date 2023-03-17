package com.example.loginapp.Base_Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.loginapp.Datos_Usuario.Perfil;
import com.example.loginapp.Indexs.Index;
import com.example.loginapp.Indexs.IndexCatalogos;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    String correo;

    public void setContentView(View view) {
        drawerLayout=(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container=drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar=drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView=drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle togggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(togggle);
        togggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_user:
                Intent u = new Intent(this, Perfil.class);
                u.putExtra("email",correo);
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
                Intent m = new Intent(this, Index.class);
                startActivity(m);
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
                        finish();
                        Intent main = new Intent(DrawerBaseActivity.this, MainActivity.class);
                        startActivity(main);
                        finish();

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

    public void correo(String email){
        correo=email;
    }

}