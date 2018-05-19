package com.wia.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.wia.FragmentRecyclerLocal;
import com.wia.R;
import com.wia.model.Local;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference localReference;

    private FragmentRecyclerLocal fragmentRecyclerlocal;

    private FirebaseStorage storage;
    private StorageReference storageRef;

    ImageView imageView;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);//Persistencia em disco local
        localReference = database.getReference().child("local");

        preencheBanco();

        fragmentRecyclerlocal = new FragmentRecyclerLocal();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, fragmentRecyclerlocal);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragmentRecyclerlocal);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Lista de Locais");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void preencheBanco() {
        preenchelocal();
    }

    public void preenchelocal() {
        Local c1 = new Local("Auditório da Reitoria", "", "", "", "gs://wia-ufrn.appspot.com/reiot.jpg", "Próximo ao centro de Convivência", -5.835574, -35.202114);
//        Local c2 = new Local("Setor de Aulas IV", "", "", "", "", "Bloco A", -5.837592, -35.199491);
//        Local c3 = new Local("Setor de Aulas III, Bloco G", "", "", "", "", "Setor 3", -5.835574, -35.202114);
//        Local c4 = new Local("Anfiteatros do CCET – Centro de Ciências Exatas e da Terra", "", "", "", "", "Próximo ao setor 3", -5.837592, -35.199491);
//        Local c5 = new Local("Anfiteatros do CB – Centro de Biociências", "", "", "", "", "Próximo ao setor 4", -5.835574, -35.202114);
//        Local c6 = new Local("Auditório da ECT – Escola de Ciência e Tecnologia", "", "", "", "", "Próximo ao setor 4", -5.837592, -35.199491);
        localReference.push().setValue(c1);
//        localReference.push().setValue(c2);
//        localReference.push().setValue(c3);
//        localReference.push().setValue(c4);
//        localReference.push().setValue(c5);
//        localReference.push().setValue(c6);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
}
