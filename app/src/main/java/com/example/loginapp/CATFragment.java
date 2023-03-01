package com.example.loginapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CATFragment extends Fragment {
    View vista;
    String dato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_c_a_t, container, false);

        //para traer el valor dela combo
        Bundle bundle= getActivity().getIntent().getExtras();//se trae lo que se mando en el extra en el evento al consultar
        dato= bundle.getString("opcion");//dato rescata el string del identificador(osea el valor del estado seleccionado)
        Toast.makeText(getContext(),dato, Toast.LENGTH_SHORT).show();//imprime nomas para asegurarce
        return vista;

    }
}