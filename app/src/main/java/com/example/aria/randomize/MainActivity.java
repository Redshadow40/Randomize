package com.example.aria.randomize;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the default fragment
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new IntegerFragment()).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Integer);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager manager = getSupportFragmentManager();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        //} else if(manager.findFragmentById(R.id.fragment_container) instanceof WebFragment){
          //  WebFragment webFrag = (WebFragment)manager.findFragmentById(R.id.fragment_container);
            //if (webFrag.checkHistory()){
            //    webFrag.backHistory();
            //}
            //else
              //  super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag = null;
        if (id == R.id.nav_Integer) {
            frag = new IntegerFragment();
        } else if (id == R.id.nav_Float) {
            frag = new FloatFragment();
        } else if (id == R.id.nav_Colors) {
            frag = new ColorFragment();
        } else if (id == R.id.nav_Dice) {
            frag = new DiceFragment();
        } else if (id == R.id.nav_Cards) {
            frag = new CardsFragment();
        } else if (id == R.id.nav_Web) {
            frag = new WebFragment();
        }

        android.support.v4.app.FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
        fragTran.replace(R.id.fragment_container, frag);
        //fragTran.addToBackStack(null);
        fragTran.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Generate(View view) throws Exception{
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.fragment_container) instanceof IntegerFragment) {
            ((IntegerFragment)manager.findFragmentById(R.id.fragment_container)).Generate(view);
        }
        else if (manager.findFragmentById(R.id.fragment_container) instanceof FloatFragment) {
            ((FloatFragment) manager.findFragmentById(R.id.fragment_container)).Generate(view);
        }
        else if (manager.findFragmentById(R.id.fragment_container) instanceof ColorFragment){
            ((ColorFragment)manager.findFragmentById(R.id.fragment_container)).Generate(view);
        }
        else if (manager.findFragmentById(R.id.fragment_container) instanceof CardsFragment) {
            ((CardsFragment) manager.findFragmentById(R.id.fragment_container)).Generate(view);
        }
        else if (manager.findFragmentById(R.id.fragment_container) instanceof DiceFragment) {
            ((DiceFragment) manager.findFragmentById(R.id.fragment_container)).Generate(view);
        }
    }

 }

