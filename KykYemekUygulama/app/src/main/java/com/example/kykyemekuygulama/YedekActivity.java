package com.example.kykyemekuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class YedekActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yedek);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        arrayList = new ArrayList<>();
        arrayList.add("Kullanıcı Sekmesi");
        arrayList.add("Admin Sekmesi");
        prepareMainFragment(viewPager,arrayList);
        



    }

    private void prepareMainFragment(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        MainFragment fragment = new MainFragment();
        for(int i=0;i<arrayList.size();i++){
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            
        }

    }

    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        public void getFragment(Fragment fragment,String title){
            fragmentList.add(fragment);
            arrayList.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}