package com.sp.igeeti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sp.igeeti.utils.FileReader;

import java.io.File;
import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {
    private TextView textView;
    private ProgressBar progressBar;
    private final ArrayList<String> songs = new ArrayList<>();
    private final ArrayList<String> songNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        textView = findViewById(R.id.waitText);
        progressBar = findViewById(R.id.launcherLoader);

        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        progressBar.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        File[] dirs = ContextCompat.getExternalFilesDirs(LauncherActivity.this,null);
                        for (File musicFile:
                                dirs) {
                            if( musicFile != null)
                            {
                                File listFile = new File(musicFile.getPath().split("/Android/data")[0]);
                                FileReader.getFileList(listFile, songs,songNames);
                            }
                        }
                        new Thread(() -> {
                            try {
                                Thread.sleep(3000);
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class)
                                        .putExtra("songs",songs)
                                        .putExtra("songNames",songNames);
                                runOnUiThread(() -> {
                                    progressBar.setVisibility(View.GONE);
                                    textView.setVisibility(View.GONE);
                                });
                                startActivity(intent);
                                finish();

                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }).start();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(LauncherActivity.this, "Give Storage Permission to Use this app", Toast.LENGTH_LONG).show();
                        LauncherActivity.this.finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }
}