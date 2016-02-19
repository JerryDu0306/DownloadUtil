package com.example.arial.downloaddemo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.downloadutil.DownLoadUtil;
import com.arialyy.downloadutil.DownloadListener;
import com.arialyy.downloadutil.Util;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final int DOWNLOAD_PRE = 0x01;
    private static final int DOWNLOAD_STOP = 0x02;
    private static final int DOWNLOAD_FAILE = 0x03;
    private static final int DOWNLOAD_CANCEL = 0x04;
    private static final int DOWNLOAD_RESUME = 0x05;
    private static final int DOWNLOAD_COMPLETE = 0x06;
    private ProgressBar mPb;
    private String mDownloadUrl = "http://static.gaoshouyou.com/d/22/94/822260b849944492caadd2983f9bb624.apk";
    private DownLoadUtil mUtil;
    private Button mStart, mStop, mCancel;
    private TextView mSize;

    private Handler mUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_PRE:
                    mSize.setText(Util.formatFileSize((Long) msg.obj));
                    mStart.setEnabled(false);
                    break;
                case DOWNLOAD_FAILE:
                    Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOAD_STOP:
                    Toast.makeText(MainActivity.this, "暂停下载", Toast.LENGTH_SHORT).show();
                    mStart.setText("恢复");
                    mStart.setEnabled(true);
                    break;
                case DOWNLOAD_CANCEL:
                    mPb.setProgress(0);
                    Toast.makeText(MainActivity.this, "取消下载", Toast.LENGTH_SHORT).show();
                    mStart.setEnabled(true);
                    mStart.setText("开始");
                    break;
                case DOWNLOAD_RESUME:
                    Toast.makeText(MainActivity.this, "恢复下载，恢复位置 ==> " + Util.formatFileSize((Long) msg.obj), Toast.LENGTH_SHORT).show();
                    mStart.setEnabled(false);
                    break;
                case DOWNLOAD_COMPLETE:
                    Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    mStart.setEnabled(true);
                    mCancel.setEnabled(false);
                    mStop.setEnabled(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        mPb = (ProgressBar) findViewById(R.id.progressBar);
        mStart = (Button) findViewById(R.id.start);
        mStop = (Button) findViewById(R.id.stop);
        mCancel = (Button) findViewById(R.id.cancel);
        mSize = (TextView) findViewById(R.id.size);
        mUtil = new DownLoadUtil();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.stop:
                stop();
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

    private void start() {
        mUtil.download(this, mDownloadUrl, Environment.getExternalStorageDirectory().getPath() + "/test.apk"
                , new DownloadListener() {
                    long fileSize = 1;

                    @Override
                    public void onPreDownload(HttpURLConnection connection) {
                        super.onPreDownload(connection);
                        mPb.setMax(100);
                        fileSize = connection.getContentLength();
                        mUpdateHandler.obtainMessage(DOWNLOAD_PRE, fileSize).sendToTarget();
                    }

                    @Override
                    public void onProgress(long currentLocation) {
                        super.onProgress(currentLocation);
                        mPb.setProgress((int) (currentLocation * 100 / fileSize));
                    }

                    @Override
                    public void onStop(long stopLocation) {
                        super.onStop(stopLocation);
                        mUpdateHandler.obtainMessage(DOWNLOAD_STOP).sendToTarget();
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                        mUpdateHandler.obtainMessage(DOWNLOAD_CANCEL).sendToTarget();
                    }

                    @Override
                    public void onResume(long resumeLocation) {
                        super.onResume(resumeLocation);
                        mUpdateHandler.obtainMessage(DOWNLOAD_RESUME, resumeLocation).sendToTarget();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mUpdateHandler.obtainMessage(DOWNLOAD_FAILE).sendToTarget();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mUpdateHandler.obtainMessage(DOWNLOAD_COMPLETE).sendToTarget();
                    }
                });
    }

    private void stop() {
        mUtil.stopDownload();
    }

    private void cancel() {
        mUtil.cancelDownload();
    }

}
