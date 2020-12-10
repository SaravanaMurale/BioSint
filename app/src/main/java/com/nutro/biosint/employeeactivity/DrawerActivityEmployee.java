package com.nutro.biosint.employeeactivity;

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
import com.nutro.biosint.commonactivity.LoginActivity;
import com.nutro.biosint.fragmentemployee.AddCheckInFragment;
import com.nutro.biosint.fragmentemployee.AddExpenseFragment;
import com.nutro.biosint.fragmentemployee.EmpAttendanceFragment;
import com.nutro.biosint.fragmentemployee.EmployeeHomeFragment;
import com.nutro.biosint.fragmentemployee.EmployeeLocationFragment;
import com.nutro.biosint.fragmentemployee.EmployeeProfileFragment;
import com.nutro.biosint.fragmentemployee.ViewAttendanceFragment;
import com.nutro.biosint.fragmentemployee.ViewCheckInFragment;
import com.nutro.biosint.fragmentemployee.ViewExpenseFragment;
import com.nutro.biosint.fragmentmanager.HomeFragmentManager;
import com.nutro.biosint.utils.PreferenceUtil;


public class DrawerActivityEmployee extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        NavigationView navigationView = (NavigationView) findViewById(R.id.employee_view);

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

        if (id == R.id.emp_home) {

            fragment = new EmployeeHomeFragment();

            /*Intent intent=new Intent(DrawerActivity.this,LoginActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.empCheckIn) {

            fragment = new AddCheckInFragment();

        } else if (id == R.id.empMyCheckIn) {

            fragment = new ViewCheckInFragment();

        } else if (id == R.id.empAddExpense) {

            fragment = new AddExpenseFragment();

        } else if (id == R.id.empViewExpense) {

            fragment = new ViewExpenseFragment();

        } else if (id == R.id.empAttendance) {

            fragment = new EmpAttendanceFragment();

        } else if (id == R.id.empViewAttendance) {
            fragment = new ViewAttendanceFragment();

        } else if (id == R.id.empLocation) {

            fragment = new EmployeeLocationFragment();

        } else if (id == R.id.empProfile) {

            fragment = new EmployeeProfileFragment();

        } else if (id == R.id.empShare) {

            fragment = new ViewCheckInFragment();

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

        PreferenceUtil.clear(DrawerActivityEmployee.this);

        Intent intent = new Intent(DrawerActivityEmployee.this, LoginActivity.class);
        startActivity(intent);
        //finish();

    }
}
