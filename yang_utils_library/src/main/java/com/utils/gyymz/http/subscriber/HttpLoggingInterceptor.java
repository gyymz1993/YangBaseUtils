package com.utils.gyymz.http.subscriber;

import com.utils.gyymz.utils.L_;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
* @作者    Yang
* @创建时间 2018/12/7 10:02
*
 * OkHttp的{@link Interceptor}, 通过设置
*/

public class HttpLoggingInterceptor implements Interceptor {
    public static final String TAG = "HttpLogging";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();

        Buffer buffer = new Buffer();
        if (request.body() != null)
            request.body().writeTo(buffer);

        L_.e(TAG, String.format("Sending request %s on %s%n%sRequest Params: %s",
                request.url(), chain.connection(), request.headers(), buffer.clone().readUtf8()));
        buffer.close();

        Response response = chain.proceed(request);
        L_.e(TAG, response.toString());
        long t2 = System.nanoTime();

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        buffer = source.buffer().clone();
        L_.e(TAG, String.format("Received response for %s in %.1fms%n%sResponse Json: %s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(),
                buffer.readUtf8()));
        buffer.close();

        return response;
    }
}
