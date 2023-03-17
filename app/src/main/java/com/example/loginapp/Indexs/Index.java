package com.example.loginapp.Indexs;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Datos_Usuario.Perfil;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexBinding;


public class Index extends DrawerBaseActivity {

    //es del menu para enlazarlo
    ActivityIndexBinding activityIndexBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_catalogos);

        Bundle bundle= getIntent().getExtras();
        String c= bundle.getString("mail");//recibe de main
        //Toast.makeText(this,c, Toast.LENGTH_SHORT).show();


        //aqui va lo del menu
        activityIndexBinding=ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(activityIndexBinding.getRoot());
        allowActivityTitle("Inicio");
        correo(c);//se envia a drawer base


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent main = new Intent(Index.this, MainActivity.class);
            startActivity(main);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}