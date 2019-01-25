package prochain.com.tronbox.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import prochain.com.tronbox.R;
import prochain.com.tronbox.Views.CommonPopupWindow;
import prochain.com.tronbox.main.fancyBaseActivity;
import prochain.com.tronbox.utils.MessageBus.EventType;
import prochain.com.tronbox.utils.MessageBus.MessageEvent;
import prochain.com.tronbox.utils.ToastUtils;
import prochain.com.tronbox.utils.fancyDataCenter;

public class walletSettingActivity extends fancyBaseActivity {



    private CommonPopupWindow popupWindow;


    private RelativeLayout eos_private_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_setting);

        EventBus.getDefault().register(this);

        super.initView();
        setTitleCenter("钱包设置");


        eos_private_key = findViewById(R.id.eos_private_key);
        eos_private_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPopupWalletPwd(0);
            }
        });


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
                                    ToastUtils.toastShort(walletSettingActivity.this,"请输入钱包密码");
                                    MessageEvent msg = new MessageEvent("dismiss", EventType.EVENT_TYPE_POPUP_DISMISS);

                                    EventBus.getDefault().post(msg);
                                }
                                else {
                                    MessageEvent msg = new MessageEvent("dismiss", EventType.EVENT_TYPE_POPUP_DISMISS);

                                    EventBus.getDefault().post(msg);
                                    {
                                        //startKeystore(walletpwd);
                                        startPrivateKey(walletpwd);
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


    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.type == EventType.EVENT_TYPE_POPUP_DISMISS) {
            if (popupWindow!=null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }




    private void startPrivateKey(String walletkey)
    {


        //todo
        String filepath = fancyDataCenter.getInstance().getCurrentTronPath();
        Intent intent = new Intent(this, exportPrivatekeyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("walletpwd", walletkey);
        bundle.putString("filepath", filepath);


        intent.putExtras(bundle);


        startActivity(intent);

    }


}
