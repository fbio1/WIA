package com.wia.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.wia.utils.WIAUtils;

public class DetalhesActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView image;
    private TextView nome;
    private TextView descricao;
    private TextView contato;
    private TextView responsavel;
    private TextView setor;
    private TextView email;

    private LinearLayout contatoLayout;
    private LinearLayout emailLayout;
    private LinearLayout resposavelLayout;
    private LinearLayout descricaoLayout;

    GoogleMap mGoogleMap;
    MapView mMapView;

    Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ActionBar ab = getSupportActionBar();
        //ab.setTitle("Detalhes da Local");
        ab.setDisplayHomeAsUpEnabled(true);

        image = findViewById(R.id.imageDetalhes);
        nome = findViewById(R.id.nomeDetalhes);
        descricao = findViewById(R.id.descricaoDetalhes);
        contato = findViewById(R.id.contatoDetalhes);
        responsavel = findViewById(R.id.responsavelDetalhes);
        setor = findViewById(R.id.setorDetalhes);
        email = findViewById(R.id.emailDetalhes);

        emailLayout = (LinearLayout) findViewById(R.id.emailLayout);
        contatoLayout = (LinearLayout) findViewById(R.id.contatoLayout);
        resposavelLayout = (LinearLayout) findViewById(R.id.responsavelLayout);
        descricaoLayout = (LinearLayout) findViewById(R.id.descricaoLayout);


        local = new Local();
        Intent recebe = getIntent();
        Bundle bundlelocal = recebe.getExtras();

        if (bundlelocal != null) {
            local.setNome(bundlelocal.getString("nome"));

            if (!bundlelocal.getString("descricao").isEmpty())
                local.setDescricao(bundlelocal.getString("descricao"));
            else
                descricaoLayout.setVisibility(View.GONE);

            if (!bundlelocal.getString("contato").isEmpty())
                local.setContato(bundlelocal.getString("contato"));
            else
                contatoLayout.setVisibility(View.GONE);

            local.setSetor(bundlelocal.getString("setor"));

            if (!bundlelocal.getString("responsavel").isEmpty())
                local.setResponsavel(bundlelocal.getString("responsavel"));
            else
                resposavelLayout.setVisibility(View.GONE);

            local.setImage(bundlelocal.getString("imagem"));

            if (!bundlelocal.getString("email").isEmpty())
                local.setEmail(bundlelocal.getString("email"));
            else
                emailLayout.setVisibility(View.GONE);

            local.setLatitude(bundlelocal.getDouble("latitude"));
            local.setLongitude(bundlelocal.getDouble("longitude"));

            nome.setText(local.getNome());
            descricao.setText(local.getDescricao());
            contato.setText(local.getContato());
            responsavel.setText(local.getResponsavel());
            setor.setText(local.getSetor());
            email.setText(local.getEmail());

            WIAUtils.handleImage(image, local);
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

        CameraPosition liberty = CameraPosition.builder().target(new LatLng(local.getLatitude(), local.getLongitude())).zoom(18).bearing(0).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

        googleMap.addMarker(new MarkerOptions().position(new LatLng(local.getLatitude(), local.getLongitude())).title(local.getNome()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(local.getLatitude(), local.getLongitude()), 17));
    }

    /*FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(WIAUtils.URL_FIREBASE).child(local.getImage());

            try {
                final File localFile = File.createTempFile("images", "jpg");
                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        //locaisViewHolder.img.setImageBitmap(bitmap);
                        Glide.with(image.getContext().getApplicationContext())
                                .load(bitmap)
                                .into(image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            } catch (IOException e ) {}

        }*/
}
