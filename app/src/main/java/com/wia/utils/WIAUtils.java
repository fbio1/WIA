package com.wia.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wia.R;
import com.wia.adapter.RecyclerAdapter;
import com.wia.connection.ConnectivityReceiver;
import com.wia.model.Local;

import java.io.File;
import java.io.IOException;

public class WIAUtils extends Application {

    public static final String URL_FIREBASE = "gs://wia-ufrn.appspot.com/";

    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    public static void handleImage(final RecyclerAdapter.LocalViewHolder locaisViewHolder, Local local) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(URL_FIREBASE).child(local.getImage());

        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    //locaisViewHolder.img.setImageBitmap(bitmap);

                    Glide.with(locaisViewHolder.img.getContext().getApplicationContext())
                            .load(bitmap)
                            .into(locaisViewHolder.img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e) {
        }
    }
}

