package com.victorsaico.cartive.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.victorsaico.cartive.Activities.ConsultaActivity;
import com.victorsaico.cartive.Adapter.CiudadAdapter;
import com.victorsaico.cartive.Models.Ciudad;
import com.victorsaico.cartive.R;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment implements DatePickerListener{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private HorizontalPicker picker;
    private String fecha;
    private TextView txthoy;
    private ImageView imginfo;
    private RelativeLayout sliderlayout;
    private ListView lista;
    ArrayList<Ciudad> listaCiudad;
    private CiudadAdapter adapter;
    private String nombreCiudad;
    private SearchView txtlima;
    private Animation uptop;
    private ImageButton imgselecion;
    public HomeFragment() {
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

         View view = inflater.inflate(R.layout.fragment_home, container, false);

        lista = (ListView) view.findViewById(R.id.ContenlistView);
        txtlima = (SearchView) view.findViewById(R.id.buscador);
        txthoy = (TextView)view.findViewById(R.id.txthoy);
        picker = (HorizontalPicker) view.findViewById(R.id.datePicker);
        imgselecion = (ImageButton)view.findViewById(R.id.btnseleccionar);
        sliderlayout =(RelativeLayout) view.findViewById(R.id.animationlist);

        imginfo = (ImageView) view.findViewById(R.id.imginformacion);

        //averiguar como poner en un metodo el onclick !!!!no olvidar
        imgselecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarItem();
            }
        });

        //imgbutton para mostrar informacion
        imginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerinformacion();
            }
        });
        //iniciar y configurar el date picker horizontal
        initPicker();
        //ocultar la lista
        lista.setVisibility(View.GONE);
        //rellenar la lista de las ciudades
        completarlist();
        //adapatar nuestra lista
        adapter = new CiudadAdapter(getActivity(), listaCiudad);
        lista.setAdapter(adapter);
        //mostrar la lista cuando se precione editar texto
        mostrarLista();
        //selecciona la ciudad
        seleccionCiudad();
        //filtrar la ciudad
        filtrarCiudad();
        //ocultamos el boton de seleccionar
        imgselecion.setVisibility(View.GONE);
        return view;
    }

    public void seleccionCiudad()
    {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                nombreCiudad = listaCiudad.get(i).getNombre();
                txtlima.setQueryHint(nombreCiudad);
                animar(false);
                lista.setVisibility(View.GONE);
                imgselecion.setVisibility(View.VISIBLE);
            }
        });
    }
    public void mostrarLista()
    {
        txtlima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animar(true);
                lista.setVisibility(View.VISIBLE);
            }
        });
    }
    public void filtrarCiudad()
    {
        txtlima.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                animar(true);
                lista.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }
    public void completarlist()
    {
        listaCiudad = new ArrayList<>();
        Ciudad ciu1 = new Ciudad("lima");
        Ciudad ciu2 = new Ciudad("Huancayo");
        Ciudad ciu3 = new Ciudad("Arequipa");
        Ciudad ciu4 = new Ciudad("Junin");
        Ciudad ciu5 = new Ciudad("Loreto");
        listaCiudad.add(ciu1);
        listaCiudad.add(ciu2);
        listaCiudad.add(ciu3);
        listaCiudad.add(ciu4);
        listaCiudad.add(ciu5);
    }
    //metodo para
    public void obtenerinformacion()
    {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Proyecto Cartive")
                .setContentText("victor.saico@tecsup.edu.pe")
                .setCustomImage(R.drawable.logo_cartive)
                .show();
    }
    //metodo para iniciar y configurar nuestro datepicket horizontal
    public void initPicker()
    {
        picker
                .setListener(this)
                //.setDays(10)
                .setOffset(10)
                .setDateSelectedColor(getResources().getColor(R.color.primary))
                .showTodayButton(false)
                .init();
        picker.setDate(new DateTime().plusDays(0));
        picker.setDate(new DateTime().plusDays(0));
    }
    public void seleccionarItem()
    {
        Log.d(TAG,"Enviar"+nombreCiudad+fecha);
        Intent intent = new Intent(getContext(), ConsultaActivity.class);
        if(nombreCiudad.isEmpty()){
            Toast.makeText(getContext(), "Seleccione un destino por favor", Toast.LENGTH_SHORT).show();
        }
        intent.putExtra("nombre", nombreCiudad);
        intent.putExtra("fecha", fecha);
        startActivity(intent);
    }
    private void animar(boolean mostrar)
    {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar) {
            //desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.1f);
        } else {    //desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.0f);
        }
        //duración en milisegundos
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);
        sliderlayout.setLayoutAnimation(controller);
        sliderlayout.startAnimation(animation);

    }
    @Override
    public void onDateSelected(DateTime dateSelected)
    {
        Log.d(TAG, "Selected date is " + dateSelected.toString());
        int day = dateSelected.getDayOfWeek();
        int day3 = dateSelected.getDayOfMonth();
        int month = dateSelected.getMonthOfYear();
        int year = dateSelected.getYear();
        java.util.Date dia = new Date();
        int day2 = dia.getDay();
        int mes = dia.getMonth();
        mes = mes +1;
        int ano = dia.getYear();
        //asignamos a fecha lo que se selecciono
        fecha = ""+year+"-"+month+"-"+day3;
        Log.d(TAG, "dia"+dia);
        //Toast.makeText(getContext(), "la fehca"+day+day2+"sis:"+mes+"cal:+"+month+ano, Toast.LENGTH_SHORT).show();
        //imprimir hoy en la seleccion de fecha
        if(mes == month && day == day2){
            txthoy.setText("Hoy");
        }else {
            txthoy.setText("");
        }
    }
}
