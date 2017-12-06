package com.victorsaico.cartive.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victorsaico.cartive.Activities.LoginActivity;
import com.victorsaico.cartive.Activities.MainActivity;
import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Servicies.ApiService;
import com.victorsaico.cartive.Servicies.ApiServiceGenerator;
import com.victorsaico.cartive.Servicies.ResponseMessage;

import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText edtusername,edtapellido,edtnombre,edtcorreo,edtdni,edtpassword,edtpassword2;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnregistrar;
    SweetAlertDialog pDialog = null;

    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edtnombre = view.findViewById(R.id.edtregistername);
        edtapellido = view.findViewById(R.id.edtregisteruserapellido);
        edtusername = view.findViewById(R.id.edtregisterusername);
        edtcorreo = view.findViewById(R.id.edtregistercorreo);
        edtdni = view.findViewById(R.id.edtregisterdni);
        edtpassword = view.findViewById(R.id.edtregisterpass);
        edtpassword2 = view.findViewById(R.id.edtregisterpass2);
        btnregistrar = view.findViewById(R.id.btnregistrar);
        //inicializamos el progress bar para poder usarlo
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog.show();
                validar();
            }
        });

        return view;
    }


    public void validar()
    {
        String nombre = edtnombre.getText().toString();
        String apellido = edtapellido.getText().toString();
        String username = edtusername.getText().toString();
        String correo = edtcorreo.getText().toString();
        String dni = edtdni.getText().toString();
        String password = edtpassword.getText().toString();
        String password2 = edtpassword2.getText().toString();

        if(nombre.isEmpty())
        {
            edtnombre.setError("Ingrese su nombre por favor");

        }
        if(apellido.isEmpty())
        {
            edtapellido.setError("Ingrese su apellido por favor");

        }
        if(username.isEmpty())
        {
            edtusername.setError("Oops se olvido su username");

        }
        if(correo.isEmpty())
        {
            edtcorreo.setError("Oops se olvido su correo");

        }
        if(dni.isEmpty())
        {
            edtdni.setError("Oops se olvido su dni");

        }
        if(password.isEmpty())
        {
            edtpassword.setError("Oops se olvido su password");

        }
        if(password2.isEmpty())
        {
            edtpassword2.setError("Oops se olvido su password");

        }
        //validamos nuestro correo
        //validarEmail(correo);
        if(password != password2){
            edtpassword.setError("Las contraseñas no coinciden");
            edtpassword2.setError("Las contraseñas no coincidem");
        }
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean rpt = pattern.matcher(correo).matches();
        if(rpt == false)
        {
            edtcorreo.setError("Correo no valido");

        }
        initialize(username, correo, nombre, apellido,  dni, password);
    }

    private void initialize(String username, String correo, String nombre, String apellido, String dni, String password){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<ResponseMessage> call = null;
        call = service.register(username, correo, password,nombre,apellido,dni);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try
                {
                    int statusCode = response.code();
                    Log.d(TAG, "Http status code :"+ statusCode);
                    if(response.isSuccessful()){
                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage"+ responseMessage);
                        pDialog.dismiss();
                        goMain();
                    }else {
                        ResponseMessage responseMessage = response.body();
                        Log.e(TAG, "on Error" + response.errorBody().string());
                        pDialog.dismiss();
                        alertError();
                        //Toast.makeText(getActivity(), responseMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Throwable t)
                {
                    try
                    {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }catch (Throwable x)
                    {
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure" + t.toString());
                Toast.makeText(getActivity(), "Error en el servicio", Toast.LENGTH_SHORT).show();
                alertError();
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
    //metodo para poder validar que el correo ingresado sea el correcto
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    //metodo para redigirnos hacia el activity principal
public void goMain()
{
    Intent intent = new Intent(getActivity(), MainActivity.class);
    startActivity(intent);
}
}
