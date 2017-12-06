package com.victorsaico.cartive.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victorsaico.cartive.Activities.LoginActivity;
import com.victorsaico.cartive.Activities.MainActivity;
import com.victorsaico.cartive.Models.Usuario;
import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Servicies.ApiService;
import com.victorsaico.cartive.Servicies.ApiServiceGenerator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private EditText edtcorreo,edtcontrasena;
    private Button btningresar;
    private SharedPreferences sharedPreferences;
    private static final String TAG = LoginActivity.class.getSimpleName();
    SweetAlertDialog pDialog = null;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_login, container, false);
        edtcorreo = (EditText) view.findViewById(R.id.correo);
        edtcontrasena = (EditText) view.findViewById(R.id.password);
        btningresar = (Button)view.findViewById(R.id.btningresar);

        //inicializamos el progress bar para poder usarlo
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        //inicializamos el sharedpreferences y si esta logueado lo redigirimos directo al mainactivity
        inicializeshared();

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog.show();
                inicializelogin();
            }
        });

         return view;
    }
    public void inicializelogin()
    {
        String username = edtcorreo.getText().toString();
        String password = edtcontrasena.getText().toString();

        if(username.isEmpty())
        {
            edtcorreo.setError("Se le olvido el email");
        }
        if(password.isEmpty())
        {
            edtcontrasena.setError("Por favor ingrese su contraseña");
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuario> call = null;



        call = service.login(username, password);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        assert response != null;
                        Usuario usuarios = response.body();
                        guardarShared(usuarios);
                        Log.e(TAG, "usuarios"+usuarios);
                    }else {

                        Log.e(TAG, "onError: " + response.errorBody().string());
                        pDialog.dismiss();
                        alertError();
                        throw new Exception("Usuario no autorizado");
                    }
                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    //alerta de error de username o password
    public void alertError()
    {

        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Username y/o password incorrecto")
                .show();
    }
    public void guardarShared(Usuario usuarios)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor
                .putString("username", usuarios.getUsername())
                .putInt("dni", usuarios.getDni())
                .putString("idUsuario", Integer.toString(usuarios.getId()))
                .putString("Nombre", usuarios.getNombres())
                .putString("Apellidos", usuarios.getApellidos())
                .putString("Correo", usuarios.getCorreo())
                .putBoolean("islogged", true)
                .commit();
        Log.d(TAG, "correologin"+usuarios.getCorreo());
        goMain();
    }
    public void goMain()
    {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void inicializeshared()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sharedPreferences.getBoolean("islogged", false)){
            Log.d(TAG,"al main");
            goMain();
        }
    }

}
