package com.victorsaico.cartive.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victorsaico.cartive.Fragments.HomeFragment;
import com.victorsaico.cartive.Models.Ticket;
import com.victorsaico.cartive.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JARVIS on 25/11/2017.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private List<Ticket> tickets;
    Activity activity;
    private static final String TAG = HomeFragment.class.getSimpleName();


    public TicketAdapter(Activity activity)
    {
        this.tickets = new ArrayList<>();
        this.activity = activity;
    }
    public void setTicketsAdapter(List<Ticket> tickets)
    {
        this.tickets = tickets;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtdni;
        public TextView txtdestino;
        public TextView txtnombre;
        public TextView txthora;
        public TextView txtnumeroasiento;
        public TextView txtfecha;

        public ViewHolder(View itemView) {
            super(itemView);

            txtdni = (TextView) itemView.findViewById(R.id.txtdni);
            txtdestino = (TextView) itemView.findViewById(R.id.txtdestino);
            txtnombre = (TextView) itemView.findViewById(R.id.txtnombre);
            txtfecha = (TextView) itemView.findViewById(R.id.txtfecha);
            txthora = (TextView) itemView.findViewById(R.id.txthora);
            txtnumeroasiento = (TextView) itemView.findViewById(R.id.txtasiento);
        }
    }
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TicketAdapter.ViewHolder holder, int position) {
        final Ticket ticket = this.tickets.get(position);
        holder.txtdni.setText(""+ticket.getDni());
        holder.txtdestino.setText(""+ticket.getDestino());
        holder.txtnombre.setText("" + ticket.getNombres()+" "+ticket.getApellidos());
        holder.txtnumeroasiento.setText(""+ticket.getNum_asiento());
        holder.txtfecha.setText(""+ticket.getFechapartida());
        holder.txthora.setText(""+ticket.getHorapartida());
    }

    @Override
    public int getItemCount() {
        return this.tickets.size();
    }
}
