package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import android.view.View;

import com.example.whatsapp.databinding.ActivityMainBinding;
import com.example.whatsapp.menu.CallsFragment;
import com.example.whatsapp.menu.CameraFragment;
import com.example.whatsapp.menu.ChatsFragment;
import com.example.whatsapp.menu.StatusFragment;

import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DisplayMetrics displayMetrics=new DisplayMetrics();
    private ActivityMainBinding binding;
    private int[] tabIcons={
            R.drawable.ic_baseline_camera_alt_24
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        setUpWithViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        setupTabIcons();
        setSupportActionBar(binding.toolbar);
        binding.tabLayout.getTabAt(1).select();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

       final int width=displayMetrics.widthPixels;

       final int widthOtherThanFirst=(int) ((float)width*(9.40f/10f));
       final int allOther=widthOtherThanFirst/4;

       new Handler().postDelayed(new Runnable(){
           @Override
           public void run(){
               LinearLayout layout1=((LinearLayout) ((LinearLayout)binding.tabLayout.getChildAt(0)).getChildAt(0));
               LinearLayout.LayoutParams layoutParams1=(LinearLayout.LayoutParams) layout1.getLayoutParams();
               layoutParams1.width=width-widthOtherThanFirst;
               layout1.setPadding(0,0,0,0);
               layout1.setLayoutParams(layoutParams1);

                LinearLayout layout2=((LinearLayout) ((LinearLayout)binding.tabLayout.getChildAt(0)).getChildAt(1));
                LinearLayout.LayoutParams layoutParams2=(LinearLayout.LayoutParams) layout2.getLayoutParams();
                layoutParams2.width=allOther;
                layout2.setLayoutParams(layoutParams2);

                LinearLayout layout3=((LinearLayout) ((LinearLayout)binding.tabLayout.getChildAt(0)).getChildAt(2));
                LinearLayout.LayoutParams layoutParams3=(LinearLayout.LayoutParams) layout3.getLayoutParams();
                layoutParams3.width=allOther;
                layout3.setLayoutParams(layoutParams3);

                LinearLayout layout4=((LinearLayout) ((LinearLayout)binding.tabLayout.getChildAt(0)).getChildAt(3));
                LinearLayout.LayoutParams layoutParams4=(LinearLayout.LayoutParams) layout4.getLayoutParams();
                layoutParams4.width=allOther;
                layout4.setLayoutParams(layoutParams4);

                binding.tabLayout.invalidate();
            }
        },400);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    getSupportActionBar().hide();
                    binding.fabAction.setVisibility(View.INVISIBLE);
                }else {
                    getSupportActionBar().show();
                    binding.tabLayout.setVisibility(View.VISIBLE);
                    changeFabIcon(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void setUpWithViewPager(ViewPager viewPager){
        MainActivity.SectionsPagerAdapter adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CameraFragment(),"");
        adapter.addFragment(new ChatsFragment(),"Chats");
        adapter.addFragment(new StatusFragment(),"Status");
        adapter.addFragment(new CallsFragment(),"Calls");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons(){
        binding.tabLayout.getTabAt(0).setIcon(tabIcons[0]);
    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList=new ArrayList<>();
        private final List<String> mFragmentTitleList =new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id){
            case R.id.menu_search:Toast.makeText(MainActivity.this,"Action search",Toast.LENGTH_LONG).show(); break;
            case R.id.action_new_group:Toast.makeText(MainActivity.this,"Action new group",Toast.LENGTH_LONG).show(); break;
            case R.id.action_new_broadcast:Toast.makeText(MainActivity.this,"Action broadcast",Toast.LENGTH_LONG).show(); break;
            case R.id.action_whatsapp_web:Toast.makeText(MainActivity.this,"Action web",Toast.LENGTH_LONG).show(); break;
            case R.id.action_starred_message:Toast.makeText(MainActivity.this,"Action starred message",Toast.LENGTH_LONG).show(); break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFabIcon(final int index){
        binding.fabAction.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (index){
                    case 1:binding.fabAction.setImageDrawable(getDrawable(R.drawable.contacts));
                           binding.fabAction.setOnClickListener(new View.OnClickListener(){
                               @Override
                               public void onClick(View v){
                                   startActivity(new Intent(MainActivity.this,ContactsActivity.class));
                               }
                           });
                           break;
                    case 2:binding.fabAction.setImageDrawable(getDrawable(R.drawable.camera)); break;
                    case 3:binding.fabAction.setImageDrawable(getDrawable(R.drawable.add_call)); break;
                }
                binding.fabAction.show();
            }
        },100);
    }
}