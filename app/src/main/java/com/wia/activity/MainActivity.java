package com.wia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wia.R;
import com.wia.model.Local;
import com.wia.fragment.FragmentRecyclerLocal;
import com.wia.utils.WIAUtils;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseDatabase database;
    private DatabaseReference localReference;

    private FragmentRecyclerLocal fragmentRecyclerlocal;

    private TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String language = Locale.getDefault().getCountry();
        ActionBar ab = getSupportActionBar();

        setTitleActionBar(language, ab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        localReference = WIAUtils.getDatabase().getReference().child("local");

        //preenchelocal();

        fragmentRecyclerlocal = new FragmentRecyclerLocal();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, fragmentRecyclerlocal);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragmentRecyclerlocal);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void preenchelocal() {
        Local c1 = new Local("Auditório da Reitoria", "", "", "reitoria.jpg", "Próximo ao centro de Convivência", "",
                "", -5.835574, -35.202114);
        Local c2 = new Local("Setor de Aulas IV", "", "", "setor4.jpg", "Bloco A", "",
                "", -5.837592, -35.199491);

        Local c3 = new Local("Setor de Aulas III", "", "", "setor3.jpg", "Bloco G", "",
                "", -5.835574, -35.202114);

        Local c4 = new Local("Anfiteatros do CCET",
                "Centro de Ciências Exatas e da Terra",
                "Prof. Djalma Ribeiro da Silva", "CCET.jpg", "Próximo ao setor 3", "", "(84) 3215-3781", -5.837592, -35.199491);

        Local c5 = new Local("Anfiteatros do CB – Centro de Biociências",
                "O Centro de Biociência é composto por cinco Cursos de Graduação: Engenharia de Aquicultura, Biomedicina, Ciências Biológicas, Ciências Biológicas à Distância e Ecologia. ",
                "Prof. Graco Aurélio Câmara de Melo Viana", "CB.jpg", "Próximo ao setor 4", "contato@cb.ufrn.br", "", -5.835574, -35.202114);

        Local c6 = new Local("Auditório da ECT – Escola de Ciência e Tecnologia",
                "A Escola de Ciências e Tecnologia foi fundada com o objetivo de oferecer todo o suporte operacional ao Bacharelado em Ciências e Tecnologia",
                "Prof. Douglas do Nascimento Silva", "ect.jpg", "Próximo ao setor 4", "", "(84) 3342.2301", -5.837592, -35.199491);

        localReference.push().setValue(c1);
        localReference.push().setValue(c2);
        localReference.push().setValue(c3);
        localReference.push().setValue(c4);
        localReference.push().setValue(c5);
        localReference.push().setValue(c6);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contato) {
            Intent i = new Intent(MainActivity.this, ContatoActivity.class);
            startActivity(i);
        } else if (id == R.id.sobre) {
            Intent i = new Intent(MainActivity.this, SobreActivity.class);
            startActivity(i);
        } else if (id == R.id.ajuda) {
            Intent i = new Intent(MainActivity.this, AjudaActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitleActionBar(String language, ActionBar actionBar){

        if(language.equals("BR"))
            actionBar.setTitle("Locais");
        else if (language.equals("GB"))
            actionBar.setTitle("Places");
        else if (language.equals("FR"))
            actionBar.setTitle("Lieux");
        else if (language.equals("ES"))
            actionBar.setTitle("Lugares");


    }

}
