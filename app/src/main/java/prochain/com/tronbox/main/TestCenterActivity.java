package prochain.com.tronbox.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tron.api.GrpcAPI;
import org.tron.protos.Protocol;
import org.tron.test.Test;
import org.tron.walletcli.WalletApiWrapper;

import java.io.FileInputStream;
import java.util.Optional;

import prochain.com.tronbox.R;

public class TestCenterActivity extends fancyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_center);

        super.initView();
        setTitleCenter("测试中心");


        {
            Button btn = findViewById(R.id.btn1);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    test1();
                }
            });
        }
        {
            Button btn = findViewById(R.id.btn2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    test2();
                }
            });
        }

        {
            Button btn = findViewById(R.id.btn3);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readStoreFile();
                }
            });
        }

        {
            Button btn = findViewById(R.id.btn4);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //readStoreFile();
                    getAccount();
                }
            });
        }


    }









    private void getNodelist()
    {

        WalletApiWrapper walletApi = new WalletApiWrapper();
        walletApi.context = this;

        Optional<GrpcAPI.NodeList> result = walletApi.listNodes();
        Log.d("wallet", "the node list  return " + result.toString());

        Protocol.Account account =  walletApi.queryAccount();

        Log.d("wallet", "get account return " + account.toString());

    }

    private void getAccount()
    {

        String filepath = "tron_UTC--2019-01-16T06-53-35.834000000Z--TE219sxVkibLg38uKLiLz5eBiZ8RCLJQR2.json";

        WalletApiWrapper walletApi = new WalletApiWrapper();
        walletApi.context = this;
        try {
          boolean blogin =   walletApi.loginAnroid("iI.tronbox".toCharArray(),filepath, this);
            Log.d("wallet", "the login return " + blogin);

            String address =  walletApi.getAddress();
            Log.d("wallet", "get address return " + address);



            Protocol.Account account =  walletApi.queryAccount();

            Log.d("wallet", "get account return " + account.toString());


        }catch (Exception e)
        {
            Log.d("wallet", "the login error " + e.toString());

        }

    }

    //key generate
    private void test1()
    {
        Test.testGenKey();
    }

    //register wallet
    private void test2()
    {

        WalletApiWrapper walletApi = new WalletApiWrapper();
        walletApi.context = this;

        try{
            String fileurl =  walletApi.registerWallet("iI.tronbox".toCharArray());
            Log.d("wallet", "the register return " + fileurl);

        }catch (Exception e)
        {
            Log.d("wallet", "the register fail " + e.toString());

        }



    }


    //read store file
    private void readStoreFile()
    {
        //tron_UTC--2019-01-16T06-53-35.834000000Z--TE219sxVkibLg38uKLiLz5eBiZ8RCLJQR2.json
        //
        // this.openFileOutput()
        try {
            String filepath = "tron_UTC--2019-01-16T06-53-35.834000000Z--TE219sxVkibLg38uKLiLz5eBiZ8RCLJQR2.json";
            FileInputStream fis = this.openFileInput(filepath);

            //获取文件长度
            int lenght = fis.available();

            byte[] buffer = new byte[lenght];

            fis.read(buffer);

            //将byte数组转换成指定格式的字符串
            String result = new String(buffer, "UTF-8");
            Log.d("wallet", "the keystore return " + result);

        }
        catch (Exception e)
        {

            Log.d("wallet", "the read keystore fail " + e.toString());

        }

    }

}
