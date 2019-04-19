package com.utils.gyymz.base.lazy;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.utils.gyymz.base.BaseAppCompatFragment;
import com.utils.gyymz.base.mvp.MVPBaseActivity;
import com.utils.gyymz.base.mvp.BasePresenter;

import io.reactivex.annotations.Nullable;

 abstract class FragmentActivity<P extends BasePresenter> extends MVPBaseActivity<P> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //写死竖屏
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //添加栈底的第一个fragment
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (getFirstFragment() != null) {
                pushFragment(getFirstFragment());
            } else {
                throw new NullPointerException("getFirstFragment() cannot be null");
            }
        }
    }


    public void pushFragment(BaseAppCompatFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment)
                    .addToBackStack(((Object) fragment).getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //回退键处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = super.getSupportParentActivityIntent();
        if (intent == null) {
            finish();
        }
        return intent;
    }

    protected abstract int getFragmentContainerId();

    protected abstract BaseAppCompatFragment getFirstFragment();


    @Override
    public void requestPermissionSucceed() {
        super.requestPermissionSucceed();
    }
}
