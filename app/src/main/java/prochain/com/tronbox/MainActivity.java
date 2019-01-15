package prochain.com.tronbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import prochain.com.tronbox.main.TestCenterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView t = findViewById(R.id.title);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });
    }


    private void startTest()
    {
        Intent intent = new Intent(this, TestCenterActivity.class);
        startActivity(intent);
    }
}
