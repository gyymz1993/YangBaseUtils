package com.utils.gyymz.exception;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lsjr.net.R;
import com.utils.gyymz.utils.T_;


public class ExceptionDetailActivity extends Activity {

    private TextView tv_detail, tv_copy, tv_close;
    private String outputInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_exception_detail);
       // statusBarColorCompat.setstatusBarColorColor(this, UIUtils.getColor(R.color.app_status_bar_color));
        outputInfo = getIntent().getStringExtra(ExceptionDialogActivity.OUTPUT_INFO);

        tv_detail = findViewById(R.id.tv_detail);
        tv_copy = findViewById(R.id.tv_copy_info);
        tv_close = findViewById(R.id.tv_close);

        tv_detail.setText(outputInfo);
        tv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText(ExceptionDialogActivity.OUTPUT_INFO, outputInfo);
                manager.setPrimaryClip(data);
                T_.showCustomToast("错误详情已复制到剪贴板");
            }
        });
    }

}
