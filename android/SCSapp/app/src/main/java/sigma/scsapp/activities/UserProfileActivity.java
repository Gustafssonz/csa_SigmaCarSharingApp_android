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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.adapters.BookingAdapter;
import sigma.scsapp.controllers.JSONTaskBooking;
import sigma.scsapp.controllers.JSONTaskVehicle;
import sigma.scsapp.model.Booking;
import sigma.scsapp.model.User;
import sigma.scsapp.model.Vehicle;
import sigma.scsapp.utility.AsyncResponseBooking;
import sigma.scsapp.utility.AsyncResponseVehicle;
import sigma.scsapp.utility.BottomNavigationViewHelper;

import static sigma.scsapp.utility.URL.URL_TO_HIT;
import static sigma.scsapp.utility.URL.getActiveBookings;
import static sigma.scsapp.utility.URL.getActiveVehicles;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponseVehicle, AsyncResponseBooking {
    User user = new User();
    JSONTaskVehicle mJsonTaskVehicle = new JSONTaskVehicle();
    JSONTaskBooking mJsonTaskBooking = new JSONTaskBooking();
    private boolean accepted = true;

    private List<Booking> bookings;
    private List<Vehicle> vehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mJsonTaskVehicle.delegate = this;
        mJsonTaskVehicle.execute(URL_TO_HIT + getActiveVehicles);

        mJsonTaskBooking.delegate = this;
        mJsonTaskBooking.execute(URL_TO_HIT + getActiveBookings);

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
                        Intent bookingIntent = new Intent(UserProfileActivity.this, BookingActivity.class);
                        startActivity(bookingIntent);
                        break;

                    case R.id.ic_center_focus:
                        // Intent intent3 = new Intent(UserProfileActivity.this, LogActivity.class);
                        // startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent userProfileIntent = new Intent(UserProfileActivity.this, LogActivity.class);
                        startActivity(userProfileIntent);
                        break;
                }
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        Log.i("Tag", "You pressed the naviagion drawer --------------");
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // SET UP PROFILE
        String newString;

        // TODO Generate content based on Database
        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {

                Log.i("test", "Setting up profile");
                TextView profile_userId = (TextView) findViewById(R.id.text_profile_name);
                profile_userId.setText(getIntent().getStringExtra("userName"));

                // TextView profile_userName = (TextView)findViewById(R.id.text_profile_name);
                //profile_userName.setText(getIntent().getStringExtra("profileUserName"));

                // TextView profile_userPhone = (TextView)findViewById(R.id.text_profile_phone);

                // Check database for profile-picture

                // query for databasecheck for picture
                String imageURL = "http://www.seosmarty.com/wp-content/uploads/2011/08/profile-picture.jpg";


                // BUTTONS

            }
        } else {
            newString = (String) savedInstanceState.getSerializable("extra_email");
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

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

    private void updateListView() {
        if (this.vehicles != null && this.bookings != null) {
            // the Adapter takes the Row-Layout, inserting the result into it.
            BookingAdapter adapter = new BookingAdapter(this, UserProfileActivity.this, R.layout.list_row_booking, bookings, vehicles);
            // the ListView (lvBooking) takes the adapter, in this case the Row (with the result) and add it into the ListView.
            ListView lvVehicle = (ListView) findViewById(R.id.lv_listOfActiveBookings);
            lvVehicle.setAdapter(adapter);
            lvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Booking booking = bookings.get(position); // getting the model
                    //intent.putExtra("bookingkey", new Gson().toJson(booking)); // converting model json into string type and sending it via intent
                    // startActivity(intent);
                    Toast.makeText(UserProfileActivity.this, "You clicked on your active booking", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void processFinishVehicle(final List<Vehicle> output) {
        if (output != null) {
            this.vehicles = output;
            this.updateListView();
        } else {
            Toast.makeText(UserProfileActivity.this, "Not able to fetch vehicle data from server.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinishBooking(final List<Booking> output) {
        if (output != null) {
            this.bookings = output;
            this.updateListView();
        } else {
            Toast.makeText(UserProfileActivity.this, "Not able to fetch booking data from server.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // TODO Add camera posiblities, read: http://www.codepool.biz/take-a-photo-from-android-camera-and-upload-it-to-a-remote-php-server.html for example

            // Handle the camera action
        } else if (id == R.id.nav_manage) {
            // go back to profile-view
            startActivity(new Intent(UserProfileActivity.this, AdminActivity.class));


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.toolbar) {
            startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
