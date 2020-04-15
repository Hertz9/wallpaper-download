package com.example.wallpaperpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class FullDataInformation extends AppCompatActivity {

    DatabaseReference reference;
    String key12 = null;
    ImageView imageView;
    String imageGet;
    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_data_information);

        imageView = (ImageView) findViewById(R.id.display1);

        reference = FirebaseDatabase.getInstance().getReference().child("Imageadd");

        key12 = getIntent().getExtras().getString("key");


        reference.child(key12).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageGet = (String) dataSnapshot.child("image").getValue();

                Picasso.get().load(imageGet).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ImageDownload(View view) {
        Toast.makeText(getApplicationContext(), "Downloading image....", Toast.LENGTH_SHORT).show();
        downloadFile(getApplicationContext(), "wallpaer", ".jpeg", DIRECTORY_DOWNLOADS, imageGet);


    }


    public void downloadFile(Context context, String filenamee, String fileExtension, String destibationDirectory, String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destibationDirectory, fileExtension + fileExtension);

        downloadManager.enqueue(request);
    }

    public void setimage(View view) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext());
        InputStream ins = null;
        try {
            ins = new URL(imageGet).openStream();
            wpm.setStream(ins);
            Toast.makeText(this,"wallpaer set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
