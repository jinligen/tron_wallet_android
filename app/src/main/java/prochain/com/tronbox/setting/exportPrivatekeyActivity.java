package prochain.com.tronbox.setting;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tron.keystore.StringUtils;
import org.tron.walletcli.WalletApiWrapper;

import prochain.com.tronbox.R;
import prochain.com.tronbox.Views.fancyLoadingView;
import prochain.com.tronbox.main.fancyBaseActivity;
import prochain.com.tronbox.utils.ToastUtils;

public class exportPrivatekeyActivity extends fancyBaseActivity {



    TextView eos_public_key;
    TextView eos_private_key;



    Button private_key_copy_btn;

    ClipboardManager myClipboard;

    public fancyLoadingView loading;

    public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == COMPLETED)
            {
                loading.dismiss();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_privatekey);
        super.initView();
        setTitleCenter("私钥备份");


        loading = new fancyLoadingView(this,R.style.loadingDialog);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String walletpwd = bundle.getString("walletpwd");
        String filepath = bundle.getString("filepath");

        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        eos_public_key = findViewById(R.id.eos_public_key);
        eos_private_key = findViewById(R.id.eos_private_key);
        private_key_copy_btn = findViewById(R.id.private_key_copy_btn);
        private_key_copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eos_private_key.getText().toString().length()==0)
                {

                }
                else {
                    ToastUtils.toastShort(exportPrivatekeyActivity.this, "私钥复制成功");
                    ClipData myClip;
                    myClip = ClipData.newPlainText("text", eos_private_key.getText().toString());//text是内容
                    myClipboard.setPrimaryClip(myClip);
                }
            }
        });





        loading.show();
        new Thread(new Runnable(){
            @Override
            public void run() {
                getPrivateKey(walletpwd,filepath);

            }
        }).start();


    }


    public void getPrivateKey(String walletpwd, String filepath)
    {


        WalletApiWrapper walletApi = new WalletApiWrapper();
        walletApi.context = this;

        try{
            boolean blogin =   walletApi.loginAnroid(walletpwd.toCharArray(),filepath, this);
            Log.d("wallet", "the login return " + blogin);

            String address =  walletApi.getAddress();
            Log.d("wallet", "get address return " + address);

            eos_public_key.setText(address);

            byte[] priKey = walletApi.backupWallet(walletpwd.toCharArray(), filepath, this);
            System.out.println("BackupWallet successful ");

            eos_private_key.setText(bytesToHex(priKey));


            sendDissMissLoading();


        }catch (Exception e)
        {

            sendDissMissLoading();
            Log.d("wallet", "the register fail " + e.toString());
            ToastUtils.toastShort(exportPrivatekeyActivity.this, "私钥解析失败，请重试钱包密码");

        }

    }


    public void sendDissMissLoading()
    {
        Message msg = new Message();
        msg.what = COMPLETED;
        handler.sendMessage(msg);
    }


    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
