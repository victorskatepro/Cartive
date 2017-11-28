package com.victorsaico.cartive.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.victorsaico.cartive.Fragments.HomeFragment;
import com.victorsaico.cartive.Fragments.PerfilFragment;
import com.victorsaico.cartive.Fragments.TicketFragment;
import com.victorsaico.cartive.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Fragment ticketsFragment = new TicketFragment();
        final Fragment homeFragment = new HomeFragment();
        final Fragment perfilFragment = new PerfilFragment();

        //Configuracion del bottomnavigation
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bnve.enableAnimation(false);
        bnve.enableItemShiftingMode(true);

        //configurar tamañado de los iconos
        int iconSize =40;
        bnve.setIconSize(iconSize, iconSize);
        bnve.setItemHeight(BottomNavigationViewEx.dp2px(this, 65));

        //Cambiar los fragments cuando se precionan
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, homeFragment).commit();
        }
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(item.getItemId() == R.id.menu_ticket){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, ticketsFragment).commit();
                }else if (item.getItemId() == R.id.menu_perfil){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, perfilFragment).commit();
                }else if (item.getItemId() == R.id.menu_home){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, homeFragment).commit();
                }
                return true;
            }
        });
    }
}
