package prochain.com.tronbox.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import prochain.com.tronbox.R;


public class EOSWalletCreateSelectActivity extends Activity {



    Integer eos_account_status = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eoswallet_create_select);


        ImageView close = findViewById(R.id.close_icon);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        RelativeLayout eos_create_key = findViewById(R.id.eos_create_key);
        RelativeLayout eos_import_key = findViewById(R.id.eos_import_key);

        eos_create_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateKey();
            }
        });
        eos_import_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImportKey();
            }
        });
    }


    public void startImportKey()
    {
        Intent intent = new Intent(this, TronWalletImportActivity.class);
        startActivity(intent);
    }



    public void startCreateKey()
    {
        Intent intent = new Intent(this, TronWalletCreateActivity.class);
        startActivity(intent);
    }







    public void CheckEOSAccountStatus()
    {

    }




    public void checkRemoteAccountStatus(String account, String pubkey)
    {

    }



}
