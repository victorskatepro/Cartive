package com.victorsaico.cartive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.victorsaico.cartive.R;
import com.victorsaico.cartive.Models.Ciudad;

import java.util.ArrayList;

/**
 * Created by JARVIS on 20/10/2017.
 */

public class CiudadAdapter extends BaseAdapter implements Filterable{
    Context context;
    ArrayList<Ciudad> Ciudad;
    LayoutInflater inflater;
    CustomFilter filtro;
    ArrayList<Ciudad> filtroList;

    public CiudadAdapter(Context context, ArrayList<Ciudad> Ciudad){
        this.context = context;
        this.Ciudad = Ciudad;
        this.filtroList = Ciudad;
    }
    @Override
    public int getCount() {
        return Ciudad.size();
    }

    @Override
    public Object getItem(int i) {
        return Ciudad.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Ciudad.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView nombre;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null){
            view = inflater.inflate(R.layout.item_city, viewGroup, false);
        }
        nombre = (TextView) view.findViewById(R.id.txtnombreciudad);
        nombre.setText(Ciudad.get(i).getNombre());
        return view;
    }

    @Override
    public Filter getFilter() {
        if(filtro == null){
            filtro = new CustomFilter();
        }
        return filtro;
    }
    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults resulst = new FilterResults();
            if(charSequence != null && charSequence.length()>0){
                //pasamos a mayusculas
                charSequence= charSequence.toString().toUpperCase();

                ArrayList<Ciudad> filtro = new ArrayList<Ciudad>();

                for(Integer i=0;i<filtroList.size();i++){
                    if(filtroList.get(i).getNombre().toUpperCase().contains(charSequence)){
                        Ciudad ciudad = new Ciudad(filtroList.get(i).getNombre());
                        filtro.add(ciudad);
                    }
                }
                resulst.count= filtro.size();
                resulst.values = filtro;
            }else{
                resulst.count= filtroList.size();
                resulst.values = filtroList;
            }
            return resulst;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Ciudad = (ArrayList<Ciudad>) results.values;
            notifyDataSetChanged();

        }
    }
}
