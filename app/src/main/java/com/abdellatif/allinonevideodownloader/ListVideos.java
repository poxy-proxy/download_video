package com.abdellatif.allinonevideodownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListVideos extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listvideos);
        List<String> videos = GetListVideo();
        // получаем элемент TextView
        //TextView selection = findViewById(R.id.selection);
        // получаем элемент ListView
        ListView videoList = findViewById(R.id.videoList);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,videos); //new String[]{"Рыжик", "Барсик", "Мурзик", "Мурка", "Васька"});//videos);
        // устанавливаем для списка адаптер
        videoList.setAdapter(adapter);
        // добавляем для списка слушатель
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent = new Intent(ListVideos.this, StreamingExampleActivity.class);
                intent.putExtra("NameVideo", videos.get(position));
                startActivity(intent);
            }
        });

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //change app bar title
        getSupportActionBar().setTitle("Список скачанных видео");
    }

    private List<String> GetListVideo(){
        String path = getDownloadLocation().toString();
        List<String> videos=new ArrayList<String>();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            videos.add(files[i].getName());
        }
        return videos;
    }

    private File getDownloadLocation() {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File youtubeDLDir = new File(downloadsDir, "youtubedl-android");
        if (!youtubeDLDir.exists()) youtubeDLDir.mkdir();
        return youtubeDLDir;
    }
}
