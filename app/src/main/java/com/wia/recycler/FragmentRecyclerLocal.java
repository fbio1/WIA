package com.wia.recycler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wia.activity.DetalhesActivity;
import com.wia.MyRecyclerView;
import com.wia.R;
import com.wia.RecyclerAdapter;
import com.wia.model.Local;

import java.io.File;
import java.io.IOException;

public class FragmentRecyclerLocal extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_fragment_recycler_local, container, false);

        imageView = (ImageView) v.findViewById(R.id.imagemdocaralho);
//        Picasso.get().
//                load("https://firebasestorage.googleapis.com/v0/b/wia-ufrn.appspot.com/o/reiot.jpg?alt=media&token=63587484-1c35-4140-bc89-d9c12f2f044c").
//                into(imageView);

        //imageView.setImageResource(R.drawable.ufrn);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference().child("local");



        recyclerView = v.findViewById(R.id.recyclerview);

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


                //localBundle.putString("imagem", local.getImage());
                readImage(local.getImage());

                localBundle.putString("setor", local.getSetor());
                localBundle.putDouble("latitude", local.getLatitude());
                localBundle.putDouble("longitude", local.getLongitude());

                intentDetalhes.putExtras(localBundle);
                startActivity(intentDetalhes);
                Log.i("TESTE", "Entrou aqui no click");
            }
        }));

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Local local = dataSnapshot.getValue(Local.class);
                adapter.add(local);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        return v;
    }

    public RecyclerAdapter getAdapter(){
        return this.adapter;
    }


    public void readImage(String imageName){
        final String urlFirebase = "gs://wia-ufrn.appspot.com";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(urlFirebase).child(imageName);

        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);

                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}
    }
}
