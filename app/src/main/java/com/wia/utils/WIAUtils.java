package com.wia.utils;

import android.app.ActionBar;
import android.app.Application;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wia.model.Local;

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

    public static void handleImage(ImageView imageView, Local local) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference().child(local.getImage());

        Glide.with(imageView.getContext().getApplicationContext())
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(imageView);
    }



    /*FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference().child(local.getImage());

        try {

            final File localFile = File.createTempFile("images", "jpg");

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    //locaisViewHolder.img.setImageBitmap(bitmap);

                    Glide.with(locaisViewHolder.img.getContext().getApplicationContext())
                            .using(new FirebaseImageLoader())
                            .load(storageRef)
                            .into(locaisViewHolder.img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (Exception e) {
            Log.i("DEU RUIM", "HEHE");
        }*/
}

