package prochain.com.tronbox.wallet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.tron.walletcli.WalletApiWrapper;

import java.util.List;

import prochain.com.tronbox.MainActivity;
import prochain.com.tronbox.R;
import prochain.com.tronbox.Views.fancyLoadingView;
import prochain.com.tronbox.main.fancyBaseActivity;
import prochain.com.tronbox.utils.ToastUtils;
import prochain.com.tronbox.utils.fancyDataCenter;

public class TronWalletCreateActivity extends fancyBaseActivity {

    private EditText user_wallet_pwd;
    private EditText user_wallet_pwd_repeat;
    private EditText user_wallet_pwd_hint;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private final int EDITTEXT_AMOUNT = 2;
    /**
     * 编辑框监听器
     */
    private TextWatcher mTextWatcher;


    public fancyLoadingView loading;

    Button create_btn;

    private String keywords;


    public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == COMPLETED)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        ToastUtils.toastShort(TronWalletCreateActivity.this, "创建成功。。。。");
                        Intent intent = new Intent(TronWalletCreateActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
            if(msg.what==SHOW_ERROR)
            {
                loading.dismiss();
                ToastUtils.toastShort(TronWalletCreateActivity.this, "创建失败。。。。");

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_create_wallet);
        super.initView();
        setTitleCenter("创建");

        loading = new fancyLoadingView(this,R.style.loadingDialog);



        create_btn = (Button)findViewById(R.id.create_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBackUpDoc();
            }
        });



        user_wallet_pwd = (EditText)findViewById(R.id.user_wallet_pwd);
        user_wallet_pwd_repeat = (EditText)findViewById(R.id.user_wallet_pwd_repeat);


        addTextWatcher();

    }



    public void addTextWatcher()
    {
        mTextWatcher = new TextWatcher() {

            /** 改变前*/
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText最初内容为空 改变EditText内容时 个数加一*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount++;
                    /** 判断个数是否到达要求*/
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        create_btn.setBackground(getResources().getDrawable( R.mipmap.blue_btn_bg));
                        create_btn.setEnabled(true);
                    }

                }
            }

            /** 内容改变*/
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText内容改变之后 内容为空时 个数减一 按钮改为不可以的背景*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount--;

                    create_btn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        /** 需要监听的EditText add*/
        user_wallet_pwd.addTextChangedListener(mTextWatcher);
        user_wallet_pwd_repeat.addTextChangedListener(mTextWatcher);
    }







    public void startBackUpDoc()
    {

        String str1 = user_wallet_pwd.getText().toString();
        String str2 = user_wallet_pwd_repeat.getText().toString();

        if (str1.length()<8 || str2.length()<8 )
        {
            ToastUtils.toastShort(this,"钱包密码必须8位以上");
            return;
        }

        if (!str1.equals(str2))
        {
            ToastUtils.toastShort(this, "两次输入不同");
            return;
        }






        loading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                registerWallet(str1);
            }
        }).start();


    }





    public void registerWallet(String keyWords)
    {

        WalletApiWrapper walletApi = new WalletApiWrapper();
        fancyDataCenter.getInstance().walletApi = walletApi;
        walletApi.context = this;

        try{
             String fileurl =  walletApi.registerWallet(keyWords.toCharArray());

             String address = walletApi.getAddress();
             fancyDataCenter.getInstance().setCurrentTronPath(fileurl);
             fancyDataCenter.getInstance().setTronAddress(address);

            sendDissMissLoading();

            finish();
            Log.d("wallet", "the register return " + fileurl);

        }catch (Exception e)
        {
            ShowError();
            Log.d("wallet", "the register fail " + e.toString());

        }


    }

    public void startImportWallet()
    {


    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //点击空白处隐藏软键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                if (view != null)
                    HideSoftInput(view);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public void sendDissMissLoading()
    {
        Message msg = new Message();
        msg.what = COMPLETED;
        handler.sendMessage(msg);
    }

    public void ShowError()
    {
        Message msg = new Message();
        msg.what = SHOW_ERROR;
        handler.sendMessage(msg);
    }

}
