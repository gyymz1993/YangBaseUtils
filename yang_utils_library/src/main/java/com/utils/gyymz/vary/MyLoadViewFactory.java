/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.utils.gyymz.vary;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.lsjr.net.R;


public class MyLoadViewFactory implements ILoadViewFactory {

    private LoadViewHelper loadViewHelper;

    public MyLoadViewFactory(View rootView, ViewRefreshListener onClickRefreshListener) {
        loadViewHelper = new LoadViewHelper(rootView, onClickRefreshListener);
    }

    public MyLoadViewFactory(View rootView) {
        loadViewHelper = new LoadViewHelper(rootView);
    }

    @Override
    public ILoadView madeLoadView() {
        return loadViewHelper;
    }


    private static class LoadViewHelper implements ILoadView {
        private VaryViewHelper helper;
        private ViewRefreshListener onClickRefreshListener;
        private Context context;

        public LoadViewHelper(View switchView) {
            this.context = switchView.getContext();
            helper = new VaryViewHelper(switchView);
        }

        public LoadViewHelper(View switchView, ViewRefreshListener onClickRefreshListener) {
            this.context = switchView.getContext();
            this.onClickRefreshListener = onClickRefreshListener;
            helper = new VaryViewHelper(switchView);
        }


        @Override
        public void init(View switchView, ViewRefreshListener onClickRefreshListener) {
            this.context = switchView.getContext();
            this.onClickRefreshListener = onClickRefreshListener;
            helper = new VaryViewHelper(switchView);
        }


        @Override
        public void restore() {
            helper.restoreView();
        }

        @Override
        public void showLoading() {
            View mLoadingView = helper.inflate(R.layout.layout_view_loading);
            if (null != mLoadingView) {
                //GifView gif_loading = mLoadingView.findViewById(R.id.gif_loading);
               // gif_loading.setMovieResource(R.raw.loading_one_touch);
            }
            helper.showLayout(mLoadingView);
        }

        @Override
        public void tipFail(String exception) {
            Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void showFail(String exception) {
            View layout = helper.inflate(R.layout.layout_status_layout_manager_error);
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRefreshListener != null) {
                        onClickRefreshListener.onRefreshContent();
                    }
                }
            });
            helper.showLayout(layout);
        }


        @Override
        public void showEmpty() {
            View layout = helper.inflate(R.layout.layout_status_layout_manager_empty);
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRefreshListener != null) {
                        onClickRefreshListener.onRefreshContent();
                    }
                }
            });
            helper.showLayout(layout);
        }

        @Override
        public void showEmptSrc(int imageResourceId) {
            View layout = helper.inflate(R.layout.layout_status_layout_manager_empty);
//            ImageView imageView = layout.findViewById(R.id.iv_status_empty_img);
//            imageView.setImageResource(imageResourceId);
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRefreshListener != null) {
                        onClickRefreshListener.onRefreshContent();
                    }
                }
            });
            helper.showLayout(layout);
        }

        @Override
        public void showNetError() {
            View layout = helper.inflate(R.layout.layout_status_layout_net_error);
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRefreshListener != null) {
                        onClickRefreshListener.onRefreshContent();
                    }
                }
            });
            helper.showLayout(layout);
        }

        @Override
        public void onRefreshListenerView(ViewRefreshListener onClickRefreshListener) {
            this.onClickRefreshListener = onClickRefreshListener;
        }

    }

}
