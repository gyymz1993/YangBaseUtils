package com.utils.gyymz.http.subscriber;
import android.app.Activity;
import com.utils.gyymz.http.callback.CallBackShowLoading;

import java.util.List;

/**
 * @作者 Yang
 * @创建时间 2018/12/7 10:02
 * 使用此类来订阅分页的请求结果。参考{@link ResponseSubscriber}。
 */

public abstract class PaginationSubscriber<T> extends CallBackShowLoading<T>  {


    /**
     */
    public PaginationSubscriber(Activity mActivity) {
        super(mActivity);
    }


    /**
     * 当请求成功，并且数据为空的情况下会回调此函数，
     * 默认会调用BaseView的showEmptyHint方法。
     */
    protected abstract void onDataIsNull();


    /**
     * 当请求成功，没有更多数据
     */
    protected abstract void onNotMoreData();


    /**
     *
     * @param t
     * @return
     */
    protected abstract boolean checkDataNotNull(T t);


    /**
     *
     * @param data
     * @return
     */
    protected boolean checkDataNotNull(List data) {
        return data != null && data.size() != 0;
    }

    /**
     * 提供分页条件，如服务器返回的总页数，或者是服务器返回的分页集合数据。需要根据不同的分页策略返回不同的条件。
     */
    protected Object getCondition(List data){
        return data.size()>0?data:null;
    }

}