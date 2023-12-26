package com.abdellatif.allinonevideodownloader;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLRequest;

import java.io.File;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StreamingExampleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartStream;
    private Button btnClearInput;
    private EditText etUrl;
    private VideoView videoView;
    private ProgressBar pbLoading;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String TAG = "StreamingExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming_example);

        //home button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //change app bar title
        getSupportActionBar().setTitle("Воспроизведение видео");

        initViews();
        initListeners();

//        btnClearInput.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearInput();
//            }
//        });
    }

    private void initViews() {
        btnStartStream = findViewById(R.id.btn_start_streaming);
       // btnClearInput = findViewById(R.id.btn_clear_input);
        etUrl = findViewById(R.id.et_url);
        videoView = findViewById(R.id.video_view);
        pbLoading = findViewById(R.id.pb_status);
    }

    private void initListeners() {
        btnStartStream.setOnClickListener(this);
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                videoView.start();
            }
        });
    }

    //get back on the home screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_streaming: {
                startStream();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @NonNull
    private File getDownloadLocation() {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File youtubeDLDir = new File(downloadsDir, "youtubedl-android");
        if (!youtubeDLDir.exists()) youtubeDLDir.mkdir();
        return youtubeDLDir;
    }

    private void startStream() {
       // String url = getLatestFilefromDir(getDownloadLocation().toString()).toString();
        Bundle arguments = getIntent().getExtras();
        String name = arguments.get("NameVideo").toString();
//String url = getLatestFilefromDir(getDownloadLocation().toString()).toString();

        String url = getDownloadLocation()+"/" +name;

        setupVideoView(url);
//        if (TextUtils.isEmpty(url)) {
//            etUrl.setError(getString(R.string.url_error));
//            return;
//        }
//
//        pbLoading.setVisibility(View.VISIBLE);
//        Disposable disposable = Observable.fromCallable(() -> {
//            YoutubeDLRequest request = new YoutubeDLRequest(url);
//            // best stream containing video+audio
//            request.addOption("-f", "best");
//            return YoutubeDL.getInstance().getInfo(request);
//        })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(streamInfo -> {
//                    pbLoading.setVisibility(View.GONE);
//                    String videoUrl = streamInfo.getUrl();
//                    if (TextUtils.isEmpty(videoUrl)) {
//                        //Toast.makeText(StreamingExampleActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
//                        Toasty.error(StreamingExampleActivity.this, "Failed to get stream url").show();
//                    } else {
//                        setupVideoView(videoUrl);
//                    }
//                }, e -> {
//                    if (BuildConfig.DEBUG) Log.e(TAG, "failed to get stream info", e);
//                    pbLoading.setVisibility(View.GONE);
//                    //Toast.makeText(StreamingExampleActivity.this, "Streaming failed. failed to get stream info", Toast.LENGTH_LONG).show();
//                    Toasty.error(StreamingExampleActivity.this, "Streaming failed. failed to get stream info").show();
//                });
       // compositeDisposable.add(disposable);
    }

    private File getLatestFilefromDir(String dirPath){
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    private void setupVideoView(String videoUrl) {
        videoView.setVideoURI(Uri.parse(videoUrl));
    }

    private void clearInput() {
        if (etUrl.getText().toString().isEmpty()) {
            Toasty.info(StreamingExampleActivity.this, "The input is already empty!", Toast.LENGTH_SHORT, true).show();
        } else {
            etUrl.setText("");
            Toasty.success(getApplicationContext(), "Input cleared successfully!", Toast.LENGTH_SHORT, true).show();
        }
    }
}
