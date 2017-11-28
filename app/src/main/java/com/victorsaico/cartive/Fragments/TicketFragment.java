package com.victorsaico.cartive.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.victorsaico.cartive.Adapter.TicketAdapter;
import com.victorsaico.cartive.Models.Ticket;
import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Servicies.ApiService;
import com.victorsaico.cartive.Servicies.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cunoraz.continuouscrollable.ContinuousScrollableImageView.TAG;

public class TicketFragment extends Fragment {

    private RecyclerView ticketlist;
    private String id;
    private SharedPreferences sharedPreferences;
    private TextView txtnombre,txtdestino,txtdni,txtfecha,txtasiento;
    public TicketFragment() {
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
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        ticketlist = (RecyclerView) view.findViewById(R.id.recyclertickets);
        ticketlist.setLayoutManager(new LinearLayoutManager(getContext()));
        ticketlist.setAdapter(new TicketAdapter(getActivity()));
        obtenerId();
        implimirDatos();
        return view;
    }

    public void implimirDatos()
    {
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<List<Ticket>> call = service.obtenerticket(id);
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Ticket> tickets = response.body();
                        Log.d(TAG, "productos: " + tickets);

                        TicketAdapter adapter = (TicketAdapter) ticketlist.getAdapter();
                        adapter.setTicketsAdapter(tickets);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }
            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
    public void obtenerId ()
    {
        id = sharedPreferences.getString("idUsuario", null);
        Log.d(TAG,"Idusuario"+id);
    }


}
