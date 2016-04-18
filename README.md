# DownloadUtil
这是android 文件下载工具类，实现了多线程断点续传功能</br>
如果你觉得我的代码对你有帮助，请麻烦你在右上角给我一个star.^_^

#下载
[![Download](https://api.bintray.com/packages/arialyy/maven/MTDownloadUtil/images/download.svg)](https://bintray.com/arialyy/maven/MTDownloadUtil/_latestVersion)<br/>
compile 'com.arialyy.downloadutil:DownloadUtil:1.0.0'

#使用
```java
DownloadUtil mUtil;

/**
 * 初始化下载工具类
 */
 private void init(){
 	mUtil = new DownloadUtile();
 }
 
 /**
  * 开始下载和恢复下载都是这个..
  */
  private void download(){
	mUtil.download(this, mDownloadUrl, Environment.getExternalStorageDirectory().getPath() + "/test.apk"
                , new DownloadListener() {
                    long fileSize = 1;

                    @Override
                    public void onPreDownload(HttpURLConnection connection) {
                        super.onPreDownload(connection);
                        //在这里编写下载预处理操作
                        fileSize = connection.getContentLength();
                    }

                    @Override
                    public void onStart(long startLocation) {
                        super.onStart(startLocation);
                        //在这里编写开始后的相应操作
                    }

                    @Override
                    public void onChildResume(long resumeLocation) {
                        super.onChildResume(resumeLocation);
                        //子线程恢复下载的位置回调
                    }

                    @Override
                    public void onChildComplete(long finishLocation) {
                        super.onChildComplete(finishLocation);
                        //子线程完成下载的回调
                    }

                    @Override
                    public void onProgress(long currentLocation) {
                        super.onProgress(currentLocation);
                        //下载总进度回调
                    }

                    @Override
                    public void onStop(long stopLocation) {
                        super.onStop(stopLocation);
                        //停止下载的回调
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                       //取消下载回调
                    }

                    @Override
                    public void onResume(long resumeLocation) {
                        super.onResume(resumeLocation);
                       	//恢复下载回调
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        //下载失败回调
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        //下载完成回调
                    }
                });
       
 }
 
 /**
  * 停止下载
  */
  private void stopDownload(){
  	if(mUtil != null){
  		mUtil.stopDownload();
  	}
  }
  
   /**
  * 取消下载
  */
  private void cancelDownload(){
  	if(mUtil != null){
  		mUtil.cancelDownload();
  	}
  }
  
```


#示例
![例子图](https://github.com/AriaLyy/DownloadUtil/blob/master/img/11.gif "")


License
-------

    Copyright 2016 AriaLyy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
