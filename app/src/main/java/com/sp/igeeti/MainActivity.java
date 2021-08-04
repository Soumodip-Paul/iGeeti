package com.sp.igeeti;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.igeeti.adapter.ViewAdapter;
import com.sp.igeeti.listener.ClickItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickItem {


    private final ArrayList<String> songNames = new ArrayList<>();
    private final ArrayList<String> songData = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mList);

        songNames.addAll(getIntent().getStringArrayListExtra("songNames"));
        songData.addAll(getIntent().getStringArrayListExtra("songs"));

        ViewAdapter adapter = new ViewAdapter(songNames,songData,MainActivity.this);
        setUpRecyclerView(adapter);
    }

    private void setUpRecyclerView(ViewAdapter adapter) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnClick(int position, ArrayList<String> files) {
        Intent intent = new Intent(this,PlaySongActivity.class)
                .putExtra("songs",files)
                .putExtra("songNames",songNames)
                .putExtra("currentPosition",position);
        startActivity(intent);
    }
}