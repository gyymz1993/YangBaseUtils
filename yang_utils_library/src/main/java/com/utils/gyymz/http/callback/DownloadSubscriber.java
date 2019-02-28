/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utils.gyymz.http.callback;

import android.app.Activity;
import android.content.Context;

import com.utils.gyymz.utils.FileUtils;
import com.utils.gyymz.utils.L_;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;


/**
 * @作者 Yang
 * @创建时间 2018/12/28 10:40
 */
public abstract class DownloadSubscriber<ResponseBody extends okhttp3.ResponseBody> implements Observer<ResponseBody>, Disposable {
    private Context context;
    private String path;
    private String name;
    private long lastRefreshUiTime;

    public DownloadSubscriber(Activity context, String path, String name) {
        this.context = context;
        this.path = path;
        this.name = name;
        this.lastRefreshUiTime = System.currentTimeMillis();
    }


    @Override
    public void onError(Throwable e) {
        //e.printStackTrace();
        onXError(e.getMessage());
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        writeResponseBodyToDisk(path, name, context, responseBody);
    }

    private void writeResponseBodyToDisk(String downFilepath, String fileName, Context context, ResponseBody body) {
        name = System.currentTimeMillis() + "." + FileUtils.getFileExtension(fileName);
        if (downFilepath == null) {
            path = context.getExternalFilesDir(null) + File.separator + name;
        } else {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + File.separator + name;
            path = path.replaceAll("//", "/");
        }

        L_.e("path:-->" + path);
        try {
            File futureStudioIconFile = new File(path);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                L_.e("file length: " + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    L_.e("file download: " + fileSizeDownloaded + " of " + fileSize);
                    //下载进度
                    float progress = fileSizeDownloaded * 1.0f / fileSize;
                    long curTime = System.currentTimeMillis();
                    //每200毫秒刷新一次数据,防止频繁更新进度
                    if (curTime - lastRefreshUiTime >= 200 || progress == 1.0f) {
                        final long finalFileSizeDownloaded = fileSizeDownloaded;
                        Observable.just(finalFileSizeDownloaded).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) {
                                        update(finalFileSizeDownloaded, fileSize, finalFileSizeDownloaded == fileSize);
                                    }
                                });
                        lastRefreshUiTime = System.currentTimeMillis();
                    }
                }
                outputStream.flush();
                L_.e("file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                final String finalPath = path;
                Observable.just(finalPath).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) {
                                onComplete(finalPath);
                            }
                        });
                L_.e("file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                L_.e("file downloaded: is sucess");

            } catch (IOException e) {
                finalonError(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            finalonError(e);
        }
    }

    private void finalonError(final Exception e) {
        onError(e);
    }


    public abstract void onComplete(String path);

    public abstract void update(long bytesRead, long contentLength, boolean done);

    protected abstract void onXError(String exception);


    final AtomicReference<Disposable> s = new AtomicReference<Disposable>();

    @Override
    public final void onSubscribe(@NonNull Disposable s) {
        if (EndConsumerHelper.setOnce(this.s, s, getClass())) {
            onStart();
        } else {
            onError(new ProtocolViolationException("网络异常"));
        }
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected void onStart() {
    }

    @Override
    public final boolean isDisposed() {
        return s.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        DisposableHelper.dispose(s);
    }

    @Override
    public void onComplete() {

    }

}
