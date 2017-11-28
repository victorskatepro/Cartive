package com.victorsaico.cartive.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Servicies.ApiService;
import com.victorsaico.cartive.Servicies.ApiServiceGenerator;
import com.victorsaico.cartive.Servicies.ResponseMessage;

import org.json.JSONException;

import java.math.BigDecimal;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    private int precio, idviaje;
    private String destino, fecha;
    private int numasiento;
    private TextView txtdestino;
    private TextView txtnumasiento;
    private TextView txtprecio;
    private TextView txtfecha;
    private TextView edtDni;
    private TextView edtNombres;
    PayPalPayment thingToBuy;
    private static final String TAG = PayActivity.class.getSimpleName();
    private Button btnpagar;
    private SweetAlertDialog pDialog;
    private ImageView imgperfil;

    private int LOAD_PAYMENT_DATA_REQUEST_CODE;

    //configurando la instancia para pagar
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "Ab1yuWH8CXN5PbqMfGzfGe_YXDq8n0sHMZBOOuV5MBUOmMFOPVPSUkaq0KEzzaoLJeTly331_SFy16NN";

    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)

            // configuracion minima del ente
            .merchantName("Mi tienda")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.mi_tienda.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.mi_tienda.com/legal"));

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        txtdestino = (TextView) findViewById(R.id.txtdestino);
        txtnumasiento = (TextView) findViewById(R.id.txtnumasiento);
        txtprecio = (TextView) findViewById(R.id.txtprecio);
        edtDni = (TextView) findViewById(R.id.edtdni);
        edtNombres = (TextView) findViewById(R.id.txtnombre);
        txtfecha = (TextView) findViewById(R.id.txtfecha);
        imgperfil = (ImageView) findViewById(R.id.imgperfilusuario);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        pDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        startService(intent);
        recepcionarDatos();

    }

    public void recepcionarDatos(){

        //recoger los datos del usuario
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PayActivity.this);
        String nombre = sharedPreferences.getString("Nombre", null);
        String apellido = sharedPreferences.getString("Apellidos", null);
        int dni = sharedPreferences.getInt("dni", -1);

        //recoger los datos del viaje
        precio = getIntent().getExtras().getInt("Precio");
        destino = getIntent().getExtras().getString("Destino");
        numasiento = getIntent().getExtras().getInt("Asiento");
        fecha = getIntent().getExtras().getString("Fecha");

        //List<Asiento> asientos = (ArrayList<Asiento>) getIntent().getSerializableExtra("Asientos");


        int color = ColorGenerator.MATERIAL.getColor(nombre);
        TextDrawable drawable = TextDrawable.builder().buildRect(nombre.substring(0, 1), color);
        imgperfil.setImageDrawable(drawable);

        txtdestino.setText(destino);
        txtfecha.setText(fecha);
        txtnumasiento.setText("Asiento"+numasiento);
        txtprecio.setText(""+precio);
        edtNombres.setText(nombre +" "+apellido);
        edtDni.setText(""+dni);

    }

    public void btnpagar(View view) {

        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(10)), "USD",
                "Pasaje", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PayActivity.this,
                PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data
                    .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {

                    // informacion extra del pedido
                    System.out.println(confirm.toJSONObject().toString(4));
                    System.out.println(confirm.getPayment().toJSONObject()
                            .toString(4));


                    Toast.makeText(getApplicationContext(), "Orden procesada",
                            Toast.LENGTH_LONG).show();
                    register();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("El usuario canceló el pago");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void register(){
        String id = sharedPreferences.getString("idUsuario", null);
        idviaje = getIntent().getExtras().getInt("Idviaje");
        String tipo = getIntent().getExtras().getString("Tipo");
        Log.d(TAG, "parametros"+id+idviaje+tipo+numasiento);

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<ResponseMessage> call = null;
        call = service.createticket(id, idviaje, numasiento);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);
                        Intent intent = new Intent(PayActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {

                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(PayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(PayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
