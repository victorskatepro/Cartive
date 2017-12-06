package com.victorsaico.cartive.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.victorsaico.cartive.Models.Asiento;
import com.victorsaico.cartive.Models.AsientosAdapter;
import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Servicies.ApiService;
import com.victorsaico.cartive.Servicies.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleActivity extends Activity {
    private static final String TAG = DetalleActivity.class.getSimpleName();

    private Integer id,numasientos;
    GridView gridView;
    AsientosAdapter customGridAdapter;
    //ArrayList<Asiento> gridArray = new ArrayList<Asiento>();
    //List<Asiento> gridArray;
    List<Asiento> gridArray = new ArrayList<>();
    List<Asiento> asientosRS;
    AsientosAdapter asientosAdapter;
    public Bitmap seatIcon;
    public Bitmap seatSelect;
    public Bitmap seatDisabled;
    private int position, idviaje;
    private String destino, tipo,fecha;
    private int precio;
    private ArrayList<Integer> asientosselect = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        recepcionDatos();
        seatIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_avl);
        seatSelect = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_std);
        seatDisabled = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_reserved);
        gridView = (GridView) findViewById(R.id.gridView1);
        recepcionDatos();
        initialize();
    }
    private void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<List<Asiento>> call = service.showAsiento(id);
        call.enqueue(new Callback<List<Asiento>>() {
            @Override
            public void onResponse(Call<List<Asiento>> call, Response<List<Asiento>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        asientosRS = response.body();
                        rellenando();
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }
                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetalleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }
            @Override
            public void onFailure(Call<List<Asiento>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetalleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    public void rellenando(){
        boolean found = false;
        for (int i = 1; i <= numasientos; i++) {
            found = false;
            for (Asiento asiento : asientosRS) {
                asiento.setImage(seatDisabled);
                if (asiento.getNum_asiento() == i) {
                    gridArray.add(asiento);
                    found = true;
                }
            }
            if (!found) {
                Asiento asiento = new Asiento("Seat" + i, seatIcon);
                asiento.setNum_asiento(i);
                gridArray.add(asiento);
            }
        }
        customGridAdapter = new AsientosAdapter(this, R.layout.seaterow_item, gridArray);
        Log.d(TAG, "adaptando");
        gridView.setAdapter(customGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Asiento asiento = gridArray.get(position);

                // si es que el asiento está libre ...
                if(asiento.getImage() != seatDisabled){
                    Bitmap seatcompare = asiento.getImage();
                    if (seatcompare == seatIcon)
                    {
                        seatSelected(position);
                    }
                    else
                    {
                        seatDeselcted(position);

                    }

                }
            }
        });
    }

    public void seatSelected(int pos)
    {
        gridArray.remove(pos);
        gridArray.add(pos, new Asiento("select", seatSelect));
        position = pos + 1;
        asientosselect.add(position);
        Log.d(TAG, "position:"+pos);
        customGridAdapter.notifyDataSetChanged();
    }
    public void seatDeselcted(int pos)
    {
        gridArray.remove(pos);
        int i = pos + 1;
        gridArray.add(pos, new Asiento("seat" + i,seatIcon));
        customGridAdapter.notifyDataSetChanged();
    }

    public void comprar(View view){


        Intent intent = new Intent(this,PayActivity.class);
        intent.putExtra("Idviaje", idviaje);
        intent.putExtra("Destino", destino);
        intent.putExtra("Precio", precio);
        intent.putExtra("Asiento", position);
        intent.putExtra("Tipo", tipo);
        intent.putExtra("Fecha", fecha);
        intent.putExtra("ListaAsientos", asientosselect);
        Log.d(TAG,"listaAsientos"+asientosselect);
        startActivity(intent);
    }
    public void retry(View view){
        Intent intent = new Intent(this, ConsultaActivity.class);
        startActivity(intent);
    }
    public void recepcionDatos(){
        id = getIntent().getExtras().getInt("ID");
        Log.d(TAG,"busid"+id);
        numasientos = getIntent().getExtras().getInt("NumAsientos");
        destino = getIntent().getExtras().getString("Destino");
        precio = getIntent().getExtras().getInt("Precio");
        idviaje = getIntent().getExtras().getInt("Idviaje");
        tipo = getIntent().getExtras().getString("Tipo");
        fecha = getIntent().getExtras().getString("Fecha");
        Log.d(TAG,"fecha"+fecha);
    }
}
