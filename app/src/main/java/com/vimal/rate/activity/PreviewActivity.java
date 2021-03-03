
//Video Preview
package com.vimal.rate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.vimal.helpers.helpers.ShareHelper;
import com.vimal.rate.R;
import com.vimal.rate.adapter.ViewPagerVideoAdapter;

import java.io.File;
import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity {

    ArrayList<String> imageUrls = new ArrayList<String>();
    private int current_position = 0;
    private ImageView imgBack, ivShare, ivSaveDelete, ivRepost, img_background;
    ViewPager2 videoViewpager;
    ViewPagerVideoAdapter adaptervideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);
        Intent i = getIntent();
        current_position = i.getIntExtra("position", 0);
        imageUrls = i.getStringArrayListExtra("arraylist");

        imgBack = findViewById(R.id.iv_back);
        ivRepost = findViewById(R.id.iv_repost);
        ivShare = findViewById(R.id.iv_share);
        ivSaveDelete = findViewById(R.id.iv_save_delete);
        img_background = findViewById(R.id.img_background);
        videoViewpager = findViewById(R.id.videosViewpager);
        adaptervideo = new ViewPagerVideoAdapter(imageUrls);
        videoViewpager.setAdapter(adaptervideo);
        videoViewpager.setCurrentItem(current_position, true);
        loadbg();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivSaveDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage(current_position);
            }
        });

        ivRepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareHelper.shareVideo(PreviewActivity.this, imageUrls.get(videoViewpager.getCurrentItem()), ShareHelper.WHATSAPP);
            }
        });


        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/mp4");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUrls.get(videoViewpager.getCurrentItem())));
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_video)));

            }
        });

        videoViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                current_position = position;
                loadbg();
            }
        });


    }

    public void loadbg( ) {
        Log.e("vml", "loadbg");
        Glide.with(PreviewActivity.this)
                .load(imageUrls.get(current_position))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(img_background);
    }

    private void deleteImage(int position) {

        delete(imageUrls.get(videoViewpager.getCurrentItem()));
        Toast.makeText(PreviewActivity.this, getString(R.string.del_img), Toast.LENGTH_SHORT).show();

        adaptervideo.removeView(position);
        if (adaptervideo.getItemCount() < 1) {
            finish();
        }

    }

    public static boolean delete(String path) {
        return new File(path).delete();
    }


    @Override
    protected void onResume( ) {
        super.onResume();
    }
}
