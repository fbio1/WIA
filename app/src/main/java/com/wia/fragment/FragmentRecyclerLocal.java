package com.wia.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wia.activity.DetalhesActivity;
import com.wia.R;
import com.wia.adapter.RecyclerAdapter;
import com.wia.connection.ConnectivityReceiver;
import com.wia.model.Local;
import com.wia.recycler.MyRecyclerView;

public class FragmentRecyclerLocal extends Fragment{
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private RecyclerAdapter adapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private View v;

    private ImageView imageView;
    private FloatingActionButton buttonRefreshConnection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_fragment_recycler_local, container, false);

        //checkConnection();

        imageView = v.findViewById(R.id.foto_local);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference().child("local");

        recyclerView = v.findViewById(R.id.recyclerviewLocais);
        linearLayout = v.findViewById(R.id.infoConnection);
        buttonRefreshConnection = v.findViewById(R.id.btn_refresh_connection);

//        buttonRefreshConnection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Manually checking internet connection
//                checkConnection();
//            }
//        });

        adapter = new RecyclerAdapter(null, getContext());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        recyclerView.addOnItemTouchListener(new MyRecyclerView(getActivity(), recyclerView, new MyRecyclerView.ItemTouch() {

            @Override
            public void clickSimples(View view, int position) {
                Intent intentDetalhes = new Intent(getActivity(), DetalhesActivity.class);
                Bundle localBundle = new Bundle();


                Local local = adapter.getLocal(position);

                localBundle.putString("nome", local.getNome());
                localBundle.putString("descricao", local.getDescricao());
                localBundle.putString("contato", local.getContato());
                localBundle.putString("responsavel", local.getResponsavel());
                localBundle.putString("imagem", local.getImage());
                localBundle.putString("email", local.getEmail());
                localBundle.putString("setor", local.getSetor());
                localBundle.putDouble("latitude", local.getLatitude());
                localBundle.putDouble("longitude", local.getLongitude());

                intentDetalhes.putExtras(localBundle);
                startActivity(intentDetalhes);
                //Log.i("TESTE", "Entrou aqui no click");
            }
        }));

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Local local = dataSnapshot.getValue(Local.class);
                adapter.add(local);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        return v;
    }

    public RecyclerAdapter getAdapter() {
        return this.adapter;
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message = null;
        if (!isConnected) {
            recyclerView = v.findViewById(R.id.recyclerviewLocais);
            recyclerView.setVisibility(View.GONE);
            linearLayout = v.findViewById(R.id.infoConnection);
            linearLayout.setVisibility(View.VISIBLE);
            message = "Desculpe! Você não possui conexão com a Internet";
            Toast toast = Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        } else {
            recyclerView = v.findViewById(R.id.recyclerviewLocais);
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout = v.findViewById(R.id.infoConnection);
            linearLayout.setVisibility(View.GONE);
        }

    }

   /* @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
        ListenerConnection.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }*/
}
