package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Dashboard extends AppCompatActivity
{
    TabLayout tabLayout;
    TabItem tabItem1,tabItem2;
    ViewPager viewPager;
    PageAdapter pageAdapter;

    NavigationView navView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    TextView user_name ,user_email,user_phone;

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        // Navigation Drawer
        navView = (NavigationView)findViewById(R.id.navbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this ,drawerLayout , toolbar , R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        View headerView = navView.getHeaderView(0); // For accessing navdrawer elements  get headerview
        user_name = (TextView)headerView.findViewById(R.id.user_name);
        user_name.setText(Constant.USER_NAME);
        user_email = (TextView)headerView.findViewById(R.id.user_email);
        user_email.setText(Constant.USER_EMAIL);
        user_phone = (TextView)headerView.findViewById(R.id.user_phone);
        user_phone.setText(Constant.USER_PHONE);


        // Listener Navigation Drawer
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.menu_logout:
                        Toast.makeText(getBaseContext(), "HOME",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START );
                        break;
                }
                return true;
            }
        });


        tabLayout=(TabLayout)findViewById(R.id.tablayout1);
        tabItem1=(TabItem)findViewById(R.id.tab1);
        tabItem2=(TabItem)findViewById(R.id.tab2);
        viewPager=(ViewPager)findViewById(R.id.vpager);

        logout = (Button)findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        
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
            finishAffinity();  // clear the back stack of activity queue and then finish current activity CLOSE THE APP
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void ToDashboard(View view){
             //RELOAD DASHBOARD
    }

    public void Logout() {
        Toast.makeText(getBaseContext(), "Logout",Toast.LENGTH_LONG).show();
        drawerLayout.closeDrawer(GravityCompat.START );
        Intent intent = new Intent(Dashboard.this,Login_activity.class);
        startActivity(intent);
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
