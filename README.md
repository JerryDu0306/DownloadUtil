# DownloadUtil
这是android 文件下载工具类，实现了多线程断点续传功能

#使用
```java
DownloadUtil mUtil;

/**
 * 初始化下载工具类
 */
 private void init(){
 	mUtil = new DownloadUtile();
 }
 
private void download(){
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
                    public void onStart(long startLocation) {
                        super.onStart(startLocation);
                    }

                    @Override
                    public void onChildResume(long resumeLocation) {
                        super.onChildResume(resumeLocation);
                    }

                    @Override
                    public void onChildComplete(long finishLocation) {
                        super.onChildComplete(finishLocation);
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
```


#示例
![例子图](https://github.com/AriaLyy/DownloadUtil/blob/master/img/11.gif "")

#下载
