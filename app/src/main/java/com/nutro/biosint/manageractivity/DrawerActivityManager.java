package com.nutro.biosint.manageractivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.nutro.biosint.R;
import com.nutro.biosint.activity.LoginActivity;
import com.nutro.biosint.fragmentmanager.HomeFragmentManager;
import com.nutro.biosint.fragmentmanager.AddClientsFragment;
import com.nutro.biosint.fragmentmanager.AssignClientsFragment;
import com.nutro.biosint.fragmentmanager.AddUserFragment;
import com.nutro.biosint.fragmentmanager.SettingsFragment;
import com.nutro.biosint.fragmentmanager.ViewEmployeeCheckInReportFragment;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.modelresponse.MyClientResponse;
import com.nutro.biosint.utils.GetMyClientDetails;
import com.nutro.biosint.utils.GetMyEmpDetails;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;


public class DrawerActivityManager extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GetMyEmpDetails.GetMyAllEmployeeDetailsListener, GetMyClientDetails.GetMyClientDetailsListener {

    GetMyEmpDetails getMyEmpDetails;
    GetMyClientDetails getMyClientDetails;

    List<ManageEmployeeResponse> employeeNameDTOList;
    List<MyClientResponse> myClientResponseArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        employeeNameDTOList = new ArrayList<>();
        getMyEmpDetails = new GetMyEmpDetails(DrawerActivityManager.this, DrawerActivityManager.this);
        getMyEmpDetails.getEmployeeDetails();

        myClientResponseArrayList = new ArrayList<>();
        getMyClientDetails = new GetMyClientDetails(DrawerActivityManager.this, DrawerActivityManager.this);
        getMyClientDetails.getMyClientList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment quoteFragment = new HomeFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screenArea, quoteFragment);
        fragmentTransaction.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.manager_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.managerhome) {

            fragment = new HomeFragmentManager();

        } else if (id == R.id.addUsers) {

            fragment = new AddUserFragment(employeeNameDTOList);

        } else if (id == R.id.addClients) {

            fragment = new AddClientsFragment();

        } else if (id == R.id.assignClients) {

            fragment = new AssignClientsFragment(myClientResponseArrayList);

        } else if (id == R.id.manageCheckins) {
            //fragment = new ManageCheckInsFragment();
            fragment = new ViewEmployeeCheckInReportFragment(employeeNameDTOList);

        } else if (id == R.id.settings) {

            fragment = new SettingsFragment();

        } else if (id == R.id.logOut) {

            callLoginActivity();

        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screenArea, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callLoginActivity() {

        PreferenceUtil.clear(DrawerActivityManager.this);

        Intent intent = new Intent(DrawerActivityManager.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void getMyEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse) {
        /*for (int i = 0; i < manageEmployeeResponse.size(); i++) {
            employeeNameDTOList.add(manageEmployeeResponse.get(i).getName());

        }*/

        employeeNameDTOList = manageEmployeeResponse;

    }

    @Override
    public void getMyClient(List<MyClientResponse> myClientResponseArrayList) {

        this.myClientResponseArrayList = myClientResponseArrayList;

    }
}
