package prochain.com.tronbox.wallet;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.tron.common.utils.ByteArray;
import org.tron.walletcli.WalletApiWrapper;

import prochain.com.tronbox.MainActivity;
import prochain.com.tronbox.R;
import prochain.com.tronbox.Views.fancyLoadingView;
import prochain.com.tronbox.main.fancyBaseActivity;
import prochain.com.tronbox.utils.ToastUtils;
import prochain.com.tronbox.utils.fancyDataCenter;

public class TronWalletImportActivity extends fancyBaseActivity {


    Button import_btn;
    private EditText import_content;
    fancyLoadingView loading;
    private EditText user_wallet_pwd;
    private EditText user_wallet_pwd_repeat;




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
                        ToastUtils.toastShort(TronWalletImportActivity.this, "创建成功。。。。");

                        Intent intent = new Intent(TronWalletImportActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
            }
            if(msg.what==SHOW_ERROR)
            {
                loading.dismiss();
                ToastUtils.toastShort(TronWalletImportActivity.this, "创建失败。。。。");

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tron_wallet_import);

        super.initView();

        setTitleCenter("导入");


        user_wallet_pwd = (EditText)findViewById(R.id.user_wallet_pwd);
        user_wallet_pwd_repeat = (EditText)findViewById(R.id.user_wallet_pwd_repeat);
        import_content = (EditText)findViewById(R.id.import_content);
        loading = new fancyLoadingView(this,R.style.loadingDialog);

        import_btn = (Button)findViewById(R.id.import_btn);
        import_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImport();
            }
        });

    }


    void startImport()
    {

        if (import_content.getText().toString()==null || import_content.getText().toString().length()==0)
        {
            ToastUtils.toastShort(this,"私钥不能为空");
            return;
        }


        if (user_wallet_pwd.getText().toString().length()<8) {
            ToastUtils.toastShort(this, "钱包密码不小于8位");
            return;
        }


        if (!user_wallet_pwd.getText().toString().equals(user_wallet_pwd_repeat.getText().toString()))
        {
            ToastUtils.toastShort(this,"两次输入的钱包密码不同");
            return;
        }
        String walletPwd = user_wallet_pwd.getText().toString();
        String privatekey = import_content.getText().toString();




        loading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                importTask(walletPwd, privatekey);
            }
        }).start();
    }


    void importTask(String walletPwd , String privateKey)
    {
        WalletApiWrapper walletApi = new WalletApiWrapper();
        fancyDataCenter.getInstance().walletApi = walletApi;
        walletApi.context = this;

        try{
            String fileurl =  walletApi.importWallet(walletPwd.toCharArray(), ByteArray.fromHexString(privateKey));

            String address = walletApi.getAddress();
            fancyDataCenter.getInstance().setCurrentTronPath(fileurl);
            fancyDataCenter.getInstance().setTronAddress(address);

            sendDissMissLoading();

            Log.d("wallet", "the import  return " + fileurl + " address " + address);

        }catch (Exception e)
        {
            ShowError();
            Log.d("wallet", "the import fail " + e.toString());

        }
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
