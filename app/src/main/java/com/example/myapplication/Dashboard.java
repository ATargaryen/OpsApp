package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Dashboard extends AppCompatActivity
{
    TabLayout tabLayout;
    TabItem tabItem1,tabItem2;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //custome action bar
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        tabLayout=(TabLayout)findViewById(R.id.tablayout1);
        tabItem1=(TabItem)findViewById(R.id.tab1);
        tabItem2=(TabItem)findViewById(R.id.tab2);
        viewPager=(ViewPager)findViewById(R.id.vpager);

        pageAdapter=new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());                  // intialize page adapter
          viewPager.setAdapter(pageAdapter);                                   // viewpager set on basis of pageadapter result

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0 || tab.getPosition()==1 )
                    pageAdapter.notifyDataSetChanged(); // call page adapter
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));           // listen horizontal scroll to change page

    }

    @Override
    public void onBackPressed() {
        boolean Alow = false;
        if (Alow) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void ToDashboard(View view){
             //RELOAD DASHBOARD
    }
}

/* How to make tabbed view using fragment
step 1 : Got a widget tablayout inside it attach tabitems .                          main_activity.xml
step 2 : Add a viewpage widget to layout                                             main_activity.xml
step 3 : set a listner =>  tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
         on tab select call Pageadapter class which used to SET Fragments to viewpager
step 4 : create a class Page Adapter and Make fragments : fragments are the page which viewed on tab press
step 5 : set listner =>         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
         on scroll horizontally page changed
 */
