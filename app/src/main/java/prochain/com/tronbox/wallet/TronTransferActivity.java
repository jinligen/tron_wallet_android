package prochain.com.tronbox.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.tron.api.GrpcAPI;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Utils;
import org.tron.protos.Protocol;
import org.tron.walletcli.WalletApiWrapper;
import org.tron.walletserver.WalletApi;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import prochain.com.tronbox.R;
import prochain.com.tronbox.Views.CommonPopupWindow;
import prochain.com.tronbox.Views.fancyLoadingView;
import prochain.com.tronbox.main.fancyBaseActivity;
import prochain.com.tronbox.setting.exportPrivatekeyActivity;
import prochain.com.tronbox.setting.walletSettingActivity;
import prochain.com.tronbox.utils.MessageBus.EventType;
import prochain.com.tronbox.utils.MessageBus.MessageEvent;
import prochain.com.tronbox.utils.ToastUtils;
import prochain.com.tronbox.utils.fancyDataCenter;


/**
 * @author hanzy
 */
public class TronTransferActivity extends fancyBaseActivity implements View.OnClickListener {

    private TronToken mTronToken;
    private EditText mReceive_account;
    private EditText mTransfer_amount;
    private EditText mRemarks;
    private TextView mTotal_balance;
    private String mBalance;

    private TextView mTitle;

    public fancyLoadingView loading;

    static private int SHOW_ERROR = 1;

    String filepath;

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
                     }
                 });
            }
            if(msg.what==SHOW_ERROR)
            {
                ToastUtils.toastShort(TronTransferActivity.this, "私钥解析失败，请重试钱包密码");

            }
        }
    };


    CommonPopupWindow popupWindow;

    String privatekey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eos_transfer);



        EventBus.getDefault().register(this);

        loading = new fancyLoadingView(this,R.style.loadingDialog);


        mTitle = (TextView) findViewById(R.id.title);
        ImageView imageBack = findViewById(R.id.img_back);
        Button nextBtn = findViewById(R.id.next);
        mReceive_account = (EditText) findViewById(R.id.receive_account);
        mTransfer_amount = (EditText) findViewById(R.id.transfer_amount);
        mRemarks = (EditText) findViewById(R.id.remarks);
        mTotal_balance = (TextView) findViewById(R.id.balance);
        TextView trade_record = findViewById(R.id.trade_record);

        mTronToken = (TronToken) getIntent().getSerializableExtra("TronToken");

        mBalance = mTronToken.balance;
        Log.i("hanzy", "从填单信息，接收到的balance" + mBalance);
        mTotal_balance.setText(mTronToken.balance);
        mTitle.setText(mTronToken.symbol);


        filepath = fancyDataCenter.getInstance().getCurrentTronPath();


        mReceive_account.setText("TGzCCWNYxEJSUvjFXSHgLEMLafAwCXWATL");


        imageBack.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        trade_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }


        if (view.getId() == R.id.trade_record) {

            //check login



        }

        if (view.getId() == R.id.next) {

            //
            String totalAmount = mTotal_balance.getText().toString().trim();
            String receiveAccount = mReceive_account.getText().toString().trim();
            String transferAmount = mTransfer_amount.getText().toString().trim();
            String remarks = mRemarks.getText().toString().trim();

            if (TextUtils.isEmpty(receiveAccount) || TextUtils.isEmpty(transferAmount)) {
                ToastUtils.toastShort(this, "请补全信息");
                return;
            }

            if (!isNumeric(transferAmount)) {
                ToastUtils.toastShort(this, "请输入正确格式的数字");
                mTransfer_amount.setText("");
                return;
            }


            if (!isMoreZero(transferAmount)) {
                ToastUtils.toastShort(this, "请输入大于0的金额");
                mTransfer_amount.setText("");
                return;
            }





            //获取账号信息
            //getAccountInfo(receiveAccount, transferAmount, remarks);

            startPopupWalletPwd(0);
        }
    }



    private boolean isMoreZero(String amount) {

        BigDecimal b1 = new BigDecimal(amount);
        BigDecimal b2 = new BigDecimal(0);
        int i = b1.compareTo(b2);

        if (i <= 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isNumeric(String str) {
        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
        return isInt || isDouble;
    }

    private String parseString(String str) {

        int index = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z') {
                index = i;
                break;
            }
        }

        return str.substring(0, index).trim();
    }









    private void tranferTron()
    {

        WalletApiWrapper walletApi = fancyDataCenter.getInstance().walletApi;
        if (walletApi==null)
        {
            sendDissMissLoading();
            Log.d("wallet", "please login first");
            return ;
        }


        String receiveAccount = mReceive_account.getText().toString().trim();
        String transferAmount = mTransfer_amount.getText().toString().trim();

        Log.d("wallet", "the receiveAccount is " + receiveAccount + " the transfer amount is " + transferAmount);

        BigDecimal big2 = new BigDecimal(transferAmount);


        String toAddress = receiveAccount;



        big2 = big2.multiply(new BigDecimal(1000000));

        GrpcAPI.EasyTransferResponse response = WalletApi
                .easyTransferByPrivate(ByteArray.fromHexString(privatekey),
                        WalletApi.decodeFromBase58Check(toAddress),  big2.longValue());

        if (response.getResult().getResult() == true) {
            Protocol.Transaction transaction = response.getTransaction();
            System.out.println("Easy transfer successful!!!");
            System.out.println(
                    "Receive txid = " + ByteArray.toHexString(response.getTxid().toByteArray()));
            System.out.println(Utils.printTransaction(transaction));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.toastShort(TronTransferActivity.this, "转账成功");
                    finish();
                }
            });

        } else {
            System.out.println("Easy transfer failed!!!");
            System.out.println("Code = " + response.getResult().getCode());
            System.out.println("Message = " + response.getResult().getMessage().toStringUtf8());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.toastShort(TronTransferActivity.this, "转账失败"+response.getResult().getCode());
                }
            });
        }


        sendDissMissLoading();


    }


    private void startPopupWalletPwd(final int type) {

        popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.popup_verify_user_wallet_pwd)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                //.setAnimationStyle(R.style.AnimDown)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView tv_confirm = (TextView) view.findViewById(R.id.confirm_tv);
                        final EditText popup_wallet_pwd = (EditText)view.findViewById(R.id.popup_wallet_pwd);

                        tv_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String walletpwd = popup_wallet_pwd.getText().toString();
                                if (walletpwd==null || walletpwd.length()==0)
                                {
                                    ToastUtils.toastShort(TronTransferActivity.this,"请输入钱包密码");
                                    MessageEvent msg = new MessageEvent("dismiss", EventType.EVENT_TYPE_POPUP_DISMISS);

                                    EventBus.getDefault().post(msg);
                                }
                                else {
                                    MessageEvent msg = new MessageEvent("dismiss", EventType.EVENT_TYPE_POPUP_DISMISS);

                                    EventBus.getDefault().post(msg);
                                    {
                                        //startKeystore(walletpwd);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                getPrivateKey(walletpwd, filepath);
                                            }
                                        }).start();
                                    }
                                }

                            }
                        });


                        TextView tv_cancel;
                        tv_cancel = (TextView) view.findViewById(R.id.cancel_tv);
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MessageEvent msg = new MessageEvent("dismiss", EventType.EVENT_TYPE_POPUP_DISMISS);

                                EventBus.getDefault().post(msg);
                            }
                        });


                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();


        //弹出PopupWindow
        View cv = this.getWindow().getDecorView();
        popupWindow.showAtLocation(cv, Gravity.CENTER, 0, 0);

    }



    public void getPrivateKey(String walletpwd, String filepath)
    {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.show();
            }
        });


        WalletApiWrapper walletApi = fancyDataCenter.getInstance().walletApi;
        if (walletApi==null) {
            walletApi = new WalletApiWrapper();
            fancyDataCenter.getInstance().walletApi = walletApi;
        }


        walletApi.context = this;


        try{
            boolean blogin =   walletApi.loginAnroid(walletpwd.toCharArray(),filepath, this);
            Log.d("wallet", "the login return " + blogin);
            byte[] priKey = walletApi.backupWallet(walletpwd.toCharArray(), filepath, this);

            privatekey =(bytesToHex(priKey));




            tranferTron();

        }catch (Exception e)
        {

            sendDissMissLoading();
            Log.d("wallet", "the register fail " + e.toString());
            ShowError();

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



    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }



    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.type == EventType.EVENT_TYPE_POPUP_DISMISS) {
            if (popupWindow!=null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
        else if (event.type == EventType.EVENT_TYPE_SHOW_ERROR)
        {
            ToastUtils.toastShort(TronTransferActivity.this, "私钥解析失败，请重试钱包密码");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

}
