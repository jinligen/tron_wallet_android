package prochain.com.tronbox.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tron.api.GrpcAPI;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Utils;
import org.tron.keystore.StringUtils;
import org.tron.protos.Protocol;
import org.tron.test.Test;
import org.tron.walletcli.WalletApiWrapper;
import org.tron.walletserver.WalletApi;

import java.io.FileInputStream;
import java.util.Base64;
import java.util.Optional;

import prochain.com.tronbox.R;
import prochain.com.tronbox.utils.fancyDataCenter;

public class TestCenterActivity extends fancyBaseActivity {



    private String localWalletPwd = "iI.tronbox";

    private String localWalletPwd_2 = "kK.tronbox";


    private String keystore = "tron_UTC--2019-01-16T06-53-35.834000000Z--TE219sxVkibLg38uKLiLz5eBiZ8RCLJQR2.json";



    private WalletApiWrapper walletApi;


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
                    login_getAccount();
                }
            });
        }

        {
            Button btn = findViewById(R.id.btn5);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backupWallet();
                }
            });
        }



        {
            Button btn = findViewById(R.id.btn6);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeWalletPwd();
                }
            });
        }

        {
            Button btn = findViewById(R.id.btn7);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tranferTron();
                }
            });
        }


        {
            Button btn = findViewById(R.id.btn8);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callReadContract();

                }
            });
        }


        {
            Button btn = findViewById(R.id.btn9);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callSignContract();

                }
            });
        }

    }






    private void callSignContract()
    {
        if (walletApi==null)
        {
            Log.d("wallet", "please login first");
            return ;
        }

        String contractAddress = "THBE7KgFSP6zWrfJh7yp4gfZd9VCfPcbws";

        String method = "click";
        String params = "1";

        String privateKey = "fa3bad461a2cf1f9bf779ed4e508850ce78acd86d43c3159836fa811612cee19";
        walletApi.ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));
        //ClickEvent
        walletApi.triggerContract(contractAddress,method, params);


    }

    private void callReadContract()
    {
        if (walletApi==null)
        {
            Log.d("wallet", "please login first");
            return ;
        }

        //contract push TK11RC2sFpvxPJUDW45wMCbvNpctSL4KuQ getUser “TWuDgresypbz5bHPBuZe22GDYcpufhs5EZ”

        walletApi.triggerContract("TK11RC2sFpvxPJUDW45wMCbvNpctSL4KuQ","getUser", "\"TWuDgresypbz5bHPBuZe22GDYcpufhs5EZ\"");

    }

    private void tranferTron()
    {
        if (walletApi==null)
        {
            Log.d("wallet", "please login first");
            return ;
        }



        //String toAddress = "TWuDgresypbz5bHPBuZe22GDYcpufhs5EZ";  //account: huang
        String toAddress = "TGzCCWNYxEJSUvjFXSHgLEMLafAwCXWATL"; // account: fancy


        String privateKey = "fa3bad461a2cf1f9bf779ed4e508850ce78acd86d43c3159836fa811612cee19";

        GrpcAPI.EasyTransferResponse response = WalletApi
                .easyTransferByPrivate(ByteArray.fromHexString(privateKey),
                        WalletApi.decodeFromBase58Check(toAddress),  1000000L);

        if (response.getResult().getResult() == true) {
            Protocol.Transaction transaction = response.getTransaction();
            System.out.println("Easy transfer successful!!!");
            System.out.println(
                    "Receive txid = " + ByteArray.toHexString(response.getTxid().toByteArray()));
            System.out.println(Utils.printTransaction(transaction));
        } else {
            System.out.println("Easy transfer failed!!!");
            System.out.println("Code = " + response.getResult().getCode());
            System.out.println("Message = " + response.getResult().getMessage().toStringUtf8());
        }


    }


    private void changeWalletPwd()
    {
        WalletApiWrapper walletApi = new WalletApiWrapper();
        walletApi.context = this;

        try {
            walletApi.changePassword(localWalletPwd.toCharArray(), localWalletPwd_2.toCharArray(), keystore, this);
        }
        catch (Exception e)
        {
            Log.d("wallet", "change pwd error " + e.toString());

        }


    }



    private void getNodelist()
    {

        if (walletApi==null)
        {
            Log.d("wallet", "please login first");
            return ;
        }
        WalletApiWrapper walletApi = new WalletApiWrapper();
        walletApi.context = this;

        Optional<GrpcAPI.NodeList> result = walletApi.listNodes();
        Log.d("wallet", "the node list  return " + result.toString());

        Protocol.Account account =  walletApi.queryAccount();

        Log.d("wallet", "get account return " + account.toString());

    }

    private void login_getAccount()
    {

        String filepath = "tron_UTC--2019-01-16T06-53-35.834000000Z--TE219sxVkibLg38uKLiLz5eBiZ8RCLJQR2.json";

        walletApi = new WalletApiWrapper();
        walletApi.context = this;

        fancyDataCenter.getInstance().setCurrentTronPath(filepath);


        try {
             boolean blogin =   walletApi.loginAnroid(localWalletPwd_2.toCharArray(),filepath, this);
            Log.d("wallet", "the login return " + blogin);

            fancyDataCenter.getInstance().walletApi = walletApi;
            String address =  walletApi.getAddress();
            Log.d("wallet", "get address return " + address);
            fancyDataCenter.getInstance().setTronAddress(address);


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
            String fileurl =  walletApi.registerWallet(localWalletPwd_2.toCharArray());
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



    private void backupWallet()
    {

        if (walletApi==null)
        {
            Log.d("wallet", "please login first");
            return ;
        }



        try{
            byte[] priKey = walletApi.backupWallet(localWalletPwd_2.toCharArray(), keystore, this);
            System.out.println("BackupWallet successful ");
            for (int i = 0; i < priKey.length; i++) {
                StringUtils.printOneByte(priKey[i]);
            }


            Base64.Encoder encoder = Base64.getEncoder();
            byte[] priKey64 = encoder.encode(priKey);
            StringUtils.clear(priKey);
            System.out.println("BackupWallet successful base64");
            for (int i = 0; i < priKey64.length; i++) {
                System.out.print((char) priKey64[i]);
            }


            System.out.println();
            StringUtils.clear(priKey64);

        }
        catch ( Exception e)
        {
            Log.d("wallet", "backup wallet fail " + e.toString());

        }

    }



}
