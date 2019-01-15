package prochain.com.tronbox.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tron.test.Test;

import prochain.com.tronbox.R;

public class TestCenterActivity extends fancyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_center);

        super.initView();
        setTitleCenter("测试中心");


        Button btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test1();
            }
        });

    }


    private void test1()
    {
        Test.testGenKey();
    }
}
