package com.wia.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wia.R;
import com.wia.model.Local;

public class DetalhesActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView image;
    private TextView nome;
    private TextView descricao;
    private TextView contato;
    private TextView responsavel;
    private TextView setor;

    GoogleMap mGoogleMap;
    MapView mMapView;

    Local local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Detalhes da Local");
        ab.setDisplayHomeAsUpEnabled(true);

        image = findViewById(R.id.imageDetalhes);
        nome = findViewById(R.id.nomeDetalhes);
        descricao = findViewById(R.id.descricaoDetalhes);
        contato = findViewById(R.id.contatoDetalhes);
        responsavel = findViewById(R.id.responsavelDetalhes);
        setor = findViewById(R.id.setorDetalhes);

        local = new Local();
        Intent recebe = getIntent();
        Bundle bundlelocal = recebe.getExtras();

        if (bundlelocal!=null){
            local.setNome(bundlelocal.getString("nome"));
            local.setDescricao(bundlelocal.getString("descricao"));
            local.setContato(bundlelocal.getString("contato"));
            local.setSetor(bundlelocal.getString("setor"));
            local.setResponsavel(bundlelocal.getString("responsavel"));
            local.setImage(bundlelocal.getString("imagem"));
            local.setEmail(bundlelocal.getString("email"));
            local.setLatitude(bundlelocal.getDouble("latitude"));
            local.setLongitude(bundlelocal.getDouble("longitude"));

            nome.setText(local.getNome());
            descricao.setText(local.getDescricao());
            contato.setText(local.getContato());
            responsavel.setText(local.getResponsavel());
            setor.setText(local.getSetor());
            Glide.with(image.getContext())
                    .load(local.getImage())
                    .into(image);
        }

        mMapView = (MapView) findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        mGoogleMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition liberty = CameraPosition.builder().target(new LatLng(local.getLatitude(), local.getLongitude())).zoom(16).bearing(0).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

        googleMap.addMarker(new MarkerOptions().position(new LatLng(local.getLatitude(), local.getLongitude())).title(local.getNome()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(local.getLatitude(), local.getLongitude()),16));
    }
}
