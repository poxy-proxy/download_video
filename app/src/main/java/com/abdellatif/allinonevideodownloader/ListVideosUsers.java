package com.abdellatif.allinonevideodownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ListVideosUsers extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy gfgPolicy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listvideos);
        List<String> videos= new ArrayList<String>();
        videos.add("Список пуст");

            videos = GetListVideo();

        ListView videoList = findViewById(R.id.videoList);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,videos);
        // устанавливаем для списка адаптер
        videoList.setAdapter(adapter);
        // добавляем для списка слушатель
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                List<String> urls= GetListUrl();
                Intent intent = new Intent(ListVideosUsers.this, DownloadingExampleActivity.class);
                intent.putExtra("UrlVideo", urls.get(position));
                startActivity(intent);
            }
        });


        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //change app bar title
        getSupportActionBar().setTitle("Список скачанных пользователями видео");
    }

    private List<String> GetListVideo() {
        List<String> videos=new ArrayList<String>();

        final List<VideoItem>[] gsonList1 = new List[]{null};
        try {


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://api-downloader-video.somee.com/users/videos")
                .build();
        Response responses = client.newCall(request).execute();

        if (responses.code() == 200) {
            ResponseBody resp = responses.body();
            String jsonData = responses.body().string();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<VideoItem>>() {}.getType();
            List<VideoItem> gsonList = gson.fromJson(jsonData, listType);
            for(int i=0;i<gsonList.size();i++){
                videos.add(gsonList.get(i).Login+" "+gsonList.get(i).Name);
            }
        } }
        catch (IOException e) {
                String a = e.toString();
            }




        return videos;
    }

    private List<String> GetListUrl() {
        List<String> videos=new ArrayList<String>();

        final List<VideoItem>[] gsonList1 = new List[]{null};
        try {


            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://api-downloader-video.somee.com/users/videos")
                    .build();
            Response responses = client.newCall(request).execute();

            if (responses.code() == 200) {
                ResponseBody resp = responses.body();
                String jsonData = responses.body().string();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<VideoItem>>() {}.getType();
                List<VideoItem> gsonList = gson.fromJson(jsonData, listType);
                for(int i=0;i<gsonList.size();i++){
                    videos.add(gsonList.get(i).Url);
                }
            } }
        catch (IOException e) {
            String a = e.toString();
        }

        return videos;
    }



    private File getDownloadLocation() {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File youtubeDLDir = new File(downloadsDir, "youtubedl-android");
        if (!youtubeDLDir.exists()) youtubeDLDir.mkdir();
        return youtubeDLDir;
    }

    public class VideoItem {

        private String Name;
        private String Url;
        private String Login;

        public VideoItem(String Name, String Url,String Login) {
            this.Name = Name;
            this.Url = Url;
            this.Login = Login;
        }
        public String getName(){
            return this.Name;
        }
        public void setName(String Name) {
            this.Name = Name;

        }
        public String getUrl(){
            return this.Url;
        }
        public void setUrl(String Url) {
            this.Url = Url;

        }
        public String getLogin(){
            return this.Login;
        }
        public void setLogin(String Login) {
            this.Name = Login;

        }
    }
}
