package prochain.com.tronbox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import prochain.com.tronbox.Fragments.homeFragment;
import prochain.com.tronbox.Fragments.settingFragment;
import prochain.com.tronbox.Fragments.walletFragment;
import prochain.com.tronbox.main.TestCenterActivity;
import prochain.com.tronbox.utils.MessageBus.EventType;
import prochain.com.tronbox.utils.MessageBus.MessageEvent;

public class MainActivity extends AppCompatActivity {


    private List<Fragment> fragmentList = new ArrayList<>();
    private homeFragment home;
    private walletFragment wallet;
    private settingFragment settingF;

    private MyPagerAdapter adapter;
    private ViewPager viewPager;

    private AlphaTabsIndicator mAlphaTabsIndicator;

    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);

            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }

    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt int getStatusBarColor() {
        return Color.WHITE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        setStatusBar(Color.WHITE);

        TextView t = findViewById(R.id.title);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });

        bindID();

        home = new homeFragment();
        wallet = new walletFragment();
        settingF = new settingFragment();
        //填充数据
        fragmentList.add(home);
        fragmentList.add(wallet);
        fragmentList.add(settingF);

        adapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==1)
                {
                    MessageEvent msg = new MessageEvent("dismiss", EventType.EVENT_TYPE_FRESH_TRON_DATA);
                    EventBus.getDefault().post(msg);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAlphaTabsIndicator = findViewById(R.id.alphaIndicator);
        mAlphaTabsIndicator.setViewPager( viewPager);                     //Set ViewPager
        mAlphaTabsIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                viewPager.setCurrentItem(tabNum);
            }
        });
    }


    private void bindID() {
        viewPager = findViewById(R.id.viewpager);
    }

    private void startTest()
    {
        Intent intent = new Intent(this, TestCenterActivity.class);
        startActivity(intent);
    }


    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.type == EventType.EVENT_TYPE_FRESH_TRON_DATA) {
           wallet.freshData();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

}
