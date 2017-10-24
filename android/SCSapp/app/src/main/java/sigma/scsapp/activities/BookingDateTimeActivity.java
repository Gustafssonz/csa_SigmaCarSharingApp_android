package sigma.scsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sigma.scsapp.R;
import sigma.scsapp.fragment.DatePickerFragment;
import sigma.scsapp.fragment.EndDatePickerFragment;
import sigma.scsapp.fragment.EndTimePickerFragment;
import sigma.scsapp.fragment.StartDatePickerFragment;
import sigma.scsapp.fragment.StartTimePickerFragment;
import sigma.scsapp.fragment.TimePickerFragment;
import sigma.scsapp.utility.BottomNavigationViewHelper;

public class BookingDateTimeActivity
        extends AppCompatActivity
        implements StartTimePickerFragment.StartTimeListener, EndTimePickerFragment.EndTimeListener, StartDatePickerFragment.StartDateListener, EndDatePickerFragment.EndDateListener, NavigationView.OnNavigationItemSelectedListener  {

    TimePickerFragment timePickerStart;
    TimePickerFragment timePickerEnd;

    DatePickerFragment datePickerStart;
    DatePickerFragment datePickerEnd;

    Button btn_done_resultDateTime;

    private int startHour;
    private int startMinute;

    private int endHour;
    private int endMinute;

    private int startYear;
    private int startMonth;
    private int startDay;

    private int endYear;
    private int endMonth;
    private int endDay;


    private String format = "";
    private TextView time;

//   Could change this activity into Fragment instead
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingdatetime_content);

        btn_done_resultDateTime = (Button) findViewById(R.id.btn_ResultDateTime);
        btn_done_resultDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToBooking = new Intent(BookingDateTimeActivity.this, BookingActivity.class);
                goBackToBooking.putExtra("ResultStartDate", startDay + "-" + startMonth + "-" + startYear);
                Log.i("BookingFormActivit", "Start date" + startDay + "-" + startMonth + "-" + startYear);
                goBackToBooking.putExtra("ResultStartTime",+ startHour + ":" + startMinute);
                Log.i("BookingFormActivit", "Start Time" + startHour + ":" + startMinute);
                goBackToBooking.putExtra("ResultEndDate",endDay + "-" + endMonth + "-" + endYear);
                goBackToBooking.putExtra("ResultEndTime", endHour + ":" + endMinute);
                goBackToBooking.putExtra("ResultIsCompleted", true);
                startActivity(goBackToBooking);
            }
        });
    }

    // TIME START AND END
    public void showTimePickerDialogStart(View v) {
        Log.i("Tag Time", "Clicked on Start Time");
        timePickerStart = new StartTimePickerFragment();
        timePickerStart.show(getFragmentManager(), "timePickerStart");
    }

    public void showTimePickerDialogEnd(View v) {
        Log.i("Tag Time", "Clicked on End Time");
        timePickerEnd = new EndTimePickerFragment();
        timePickerEnd.show(getFragmentManager(), "timePickerEnd");
    }


    // DATE START AND END
    public void showDatePickerDialogStart(View v) {
        Log.i("Tag Date", "Clicked on Start Date");
        datePickerStart = new StartDatePickerFragment();
        datePickerStart.show(getFragmentManager(), "datePickerStart");
    }

    public void showDatePickerDialogEnd(View v) {
        Log.i("Tag Date", "Clicked on End Date");
        datePickerEnd = new EndDatePickerFragment();
        datePickerEnd.show(getFragmentManager(), "datePickerEnd");
    }


    @Override
    public void onEndDateSet(int year, int month, int day) {
        Log.i("Booking", "Setting end date: " + year + ":" + month + ":" + day);
        endYear = year;
        endMonth = month;
        endDay = day;
        ((TextView) findViewById(R.id.tv_bookingform_set_date_end)).setText(endDay + "-" + endMonth + "-" + endYear);
    }

    @Override
    public void onStartDateSet(int year, int month, int day) {
        Log.i("Booking", "Setting start date: " + year + ":" + month + ":" + day);
        startYear = year;
        startMonth = month;
        startDay = day;

        ((TextView) findViewById(R.id.tv_bookingform_set_date_start)).setText(startDay + "-" + startMonth + "-" + startYear);
    }


    @Override
    public void onEndTimeSet(int hour, int minutes) {
        Log.i("Booking", "Setting end time: " + hour + ":" + minutes);
        endHour = hour;
        endMinute = minutes;
        ((TextView) findViewById(R.id.tv_bookingform_set_time_end)).setText(endHour + " : " + endMinute);
    }

    @Override
    public void onStartTimeSet(int hour, int minutes) {
        Log.i("Booking", "Setting start time: " + hour + ":" + minutes);
        startHour = hour;
        startMinute = minutes;
        ((TextView) findViewById(R.id.tv_bookingform_set_time_start)).setText(startHour + " : " + startMinute);
    }

    public void addBottomBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_books:
                        Intent intent2 = new Intent(BookingDateTimeActivity.this, BookingActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(BookingDateTimeActivity.this, MapsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:


                        break;
                }


                return false;
            }
        });
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
    public void addToolBarWithDrawerFunction(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        Log.i("Tag", "You pressed the naviagion drawer --------------");
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    public void addNavigationbar () {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_manage) {
            // go back to profile-view
            startActivity(new Intent(BookingDateTimeActivity.this, AdminActivity.class));


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.toolbar) {
            startActivity(new Intent(BookingDateTimeActivity.this, UserProfileActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



