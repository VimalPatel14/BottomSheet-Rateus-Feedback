package com.vimal.rate.creation;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.vimal.rate.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyCreationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Status> videoList = new ArrayList<>();
    private Handler handler = new Handler();
    private com.vimal.rate.creation.VideoAdapter videoAdapter;
    private ArrayList<String> photoList = new ArrayList<>();
    LinearLayout id_ll_notfound;
    public static final File Folder_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
            File.separator + "WhatsApp/Media/.Statuses");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Your toolbar is now an action bar and you can use it like you always do, for example:
        ActionBar ab = getSupportActionBar();
        ab.setTitle("My Creation");
        ab.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewVideo);
        id_ll_notfound = findViewById(R.id.id_ll_notfound);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    protected void onResume( ) {
        super.onResume();
        getStatus();
    }


    private void getStatus( ) {
        photoList.clear();
        findVideos(Folder_DIRECTORY, photoList);
        videoAdapter = new com.vimal.rate.creation.VideoAdapter(photoList);
        recyclerView.setAdapter(videoAdapter);

    }

    void findVideos(File dir, ArrayList<String> list) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) findVideos(file, list);
            else if (file.getAbsolutePath().contains(".mp4")) list.add(file.getAbsolutePath());
        }
    }


    @Override
    public void onBackPressed( ) {
        super.onBackPressed();

    }
}