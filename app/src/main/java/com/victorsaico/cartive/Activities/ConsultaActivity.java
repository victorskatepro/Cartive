package com.victorsaico.cartive.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victorsaico.cartive.Adapter.ViajeAdapter;
import com.victorsaico.cartive.Models.Viaje;
import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Servicies.ApiService;
import com.victorsaico.cartive.Servicies.ApiServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultaActivity extends AppCompatActivity {

    private static final String TAG = ConsultaActivity.class.getSimpleName();
    private String nombreciudad;
    private Date fechaseleccionada;
    private RecyclerView viajeslist;
    private ImageView btnback;
    private String fecha1;
    private TextView txttitulo,txtfechatitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        viajeslist = (RecyclerView) findViewById(R.id.recyclerviajes);
        txttitulo= (TextView) findViewById(R.id.txttitulo);
        txtfechatitulo = (TextView) findViewById(R.id.txtfechatitulo);
        viajeslist.setLayoutManager(new LinearLayoutManager(this));
        viajeslist.setAdapter(new ViajeAdapter(this));
        recepcionarDatos();
        txttitulo.setText("Lima a "+nombreciudad);
        txtfechatitulo.setText(fecha1);
        rellenarDatos();
    }
    public void rellenarDatos(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<List<Viaje>> call = null;

        call = service.buscarViaje(nombreciudad, fecha1);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Viaje> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        ViajeAdapter adapter = (ViajeAdapter) viajeslist.getAdapter();
                        adapter.setViajesAdapter(productos);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                }catch (Throwable t){
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ConsultaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ConsultaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void recepcionarDatos(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        nombreciudad = getIntent().getExtras().getString("nombre");
        fecha1 = getIntent().getExtras().getString("fecha");

        try {
            fechaseleccionada = formatter.parse(fecha1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "fecha" + formateador.format(fechaseleccionada));
        Log.d(TAG, "es" + fecha1);
        Log.d(TAG, "nombre" + nombreciudad);
    }
    public void regresar(View view){
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}
