package xyz.viccce.smartbutler;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.viccce.smartbutler.fragment.ButlerFragment;
import xyz.viccce.smartbutler.fragment.GirlFragment;
import xyz.viccce.smartbutler.fragment.UserFragment;
import xyz.viccce.smartbutler.fragment.WeChatFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitle;
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }

    private void initView() {
//        mTabLayout = (TabLayout)findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager)findViewById(R.id.mViewPager);

        mViewPager.setOffscreenPageLimit(mFragment.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

//        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.smart_butler));
        mTitle.add("微信精选");
        mTitle.add("美女图片");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WeChatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }
}
