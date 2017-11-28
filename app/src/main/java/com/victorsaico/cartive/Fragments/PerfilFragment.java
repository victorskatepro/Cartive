package com.victorsaico.cartive.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.victorsaico.cartive.Activities.LoginActivity;
import com.victorsaico.cartive.R;

import static com.cunoraz.continuouscrollable.ContinuousScrollableImageView.TAG;

public class PerfilFragment extends Fragment {

    private TextView txtnombre;
    private TextView txtcorreo;
    private TextView dni;
    private ImageView imgperfil;
    private Button btnout;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_perfil, container, false);

         txtnombre =view.findViewById(R.id.texto_nombre);
         txtcorreo = view.findViewById(R.id.texto_email);
         imgperfil = view.findViewById(R.id.imgperfil);
         dni = view.findViewById(R.id.txtdni);
         btnout = view.findViewById(R.id.btnout);
         btnout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 logout();
             }
         });
         imprimirDatos();

         return view;
    }

    public void imprimirDatos(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String nombre = sharedPreferences.getString("username", null);
        String correo = sharedPreferences.getString("Correo", null);
        int dniperfil = sharedPreferences.getInt("dni", -1);

        // https://github.com/amulyakhare/TextDrawable
        int color = ColorGenerator.MATERIAL.getColor(nombre);
        TextDrawable drawable = TextDrawable.builder().buildRect(nombre.substring(0, 1), color);
        imgperfil.setImageDrawable(drawable);

        txtnombre.setText(nombre);
        txtcorreo.setText(correo);
        dni.setText(""+dniperfil);

    }
    public void logout(){

            Log.d(TAG,"saliendo");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean success = editor.putBoolean("islogged", false).commit();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
    }
}
