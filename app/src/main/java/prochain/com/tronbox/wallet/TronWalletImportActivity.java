package prochain.com.tronbox.wallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import prochain.com.tronbox.R;
import prochain.com.tronbox.main.fancyBaseActivity;

public class TronWalletImportActivity extends fancyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tron_wallet_import);

        super.initView();

        setTitleCenter("导入");
    }
}
