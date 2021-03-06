/**
 * Copyright (c) 2016-present, RxJava Contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.utils.gyymz.http.callback;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

/**
 * An abstract {@link Observer} that allows asynchronous cancellation by implementing Disposable.
 *
 * <p>All pre-implemented final methods are thread-safe.
 *
 * <p>Use the public {@link #dispose()} method to dispose the sequence from within an
 * {@code onNext} implementation.
 *
 * <p>Like all other consumers, {@code DataCallBackObserver} can be subscribed only once.
 * Any subsequent attempt to subscribe it to a new source will yield an
 * {@link IllegalStateException} with message {@code "It is not allowed to subscribe with a(n) <class name> multiple times."}.
 *
 * <p>Implementation of {@link #onStart()}, {@link #onNext(Object)}, {@link #onError(Throwable)}
 * and {@link #onComplete()} are not allowed to throw any unchecked exceptions.
 * If for some reason this can't be avoided, use {@link io.reactivex.Observable#safeSubscribe(io.reactivex.Observer)}
 * instead of the standard {@code subscribe()} method.
 *
 * <p>Example<pre><code>
 * Disposable d =
 *     Observable.range(1, 5)
 *     .subscribeWith(new DataCallBackObserver&lt;Integer&gt;() {
 *         &#64;Override public void onStart() {
 *             System.out.println("Start!");
 *         }
 *         &#64;Override public void onNext(Integer t) {
 *             if (t == 3) {
 *                 dispose();
 *             }
 *             System.out.println(t);
 *         }
 *         &#64;Override public void onError(Throwable t) {
 *             t.printStackTrace();
 *         }
 *         &#64;Override public void onComplete() {
 *             System.out.println("Done!");
 *         }
 *     });
 * // ...
 * d.dispose();
 * </code></pre>
 *
 * @param <T> the received value type
 */
public abstract class DataCallBackObserver<T> implements Observer<T>, Disposable {


    final AtomicReference<Disposable> s = new AtomicReference<Disposable>();

    @Override
    public final void onSubscribe(@NonNull Disposable s) {
        if (EndConsumerHelper.setOnce(this.s, s, getClass())) {
            onStart();
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


    @Override
    public void onNext(T t) {
        requestSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        requestError(e);
    }

    /**
     * 请求成功同时业务成功的情况下会调用此函数
     */
    protected abstract void requestSuccess(T t);


    /**
     * 请求成功同时业务成功的情况下会调用此函数
     */
    protected abstract void requestError(Throwable e);

}
