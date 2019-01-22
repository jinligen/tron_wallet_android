package prochain.com.tronbox.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import prochain.com.tronbox.R;


/**
 * Created by alex on 2017/7/13.
 */


public class fancyLoadingView extends ProgressDialog {
    private TextView textView;

    public fancyLoadingView(Context context) {
        super(context);
    }

    public fancyLoadingView(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        textView = (TextView) findViewById(R.id.tv_load_dialog);
    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }

    public void setText(String text) {
        textView.setText(text);
    }
}