package sigma.scsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.adapters.VehicleAdapter;
import sigma.scsapp.controllers.JSONTaskVehicle;
import sigma.scsapp.model.Vehicle;
import sigma.scsapp.utility.AsyncResponseVehicle;
import sigma.scsapp.utility.ExpandableListAdapter;
import sigma.scsapp.utility.BottomNavigationViewHelper;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static sigma.scsapp.utility.URL.URL_TO_HIT;
import static sigma.scsapp.utility.URL.getActiveVehicles;


public class BookingActivity extends Activity implements AsyncResponseVehicle {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String site;

    // Date Time Data
    TextView tv_resultStartDateTime;
    TextView tv_resultEndDateTime;


    // Booking info data
    EditText et_errand;
    EditText et_purpose;
    EditText et_destination;
    Button btn_bookingInfo_completed;

    private String errandResult;
    private String purposeResult;
    private String destinationResult;
    private String resultStartDataTime;
    private String resultEndDateTime;

    private List<Vehicle> vehicles;
    JSONTaskVehicle mJsonTaskVehicle = new JSONTaskVehicle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activityview);

        expListView = findViewById(R.id.exvListView);
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);


        /*
        *   Booking info
        */
        et_errand = findViewById(R.id.et_errand);
        et_purpose = findViewById(R.id.et_purpose);
        et_destination = findViewById(R.id.et_destination);

        /*
        *  Date & Time
        */
        Intent fetchData = getIntent();
        resultStartDataTime = fetchData.getStringExtra("ResultStart");
        resultEndDateTime = fetchData.getStringExtra("ResultEnd");
        tv_resultStartDateTime = findViewById(R.id.tv_resultStartDateTime);
        tv_resultEndDateTime = findViewById(R.id.tv_resultEndDateTime);

        if (tv_resultStartDateTime.is && tv_resultEndDateTime ){

        }
        /*
        *  Get Vehicle and pick
        */
        // TODO: 2017-10-19 Send request for avaliable vehicles

        // TODO: 2017-10-19 Show JSON in a list
        // TODO: 2017-10-19 Pick car

        btn_bookingInfo_completed = (Button) findViewById(R.id.btn_bookingInfo_accepted);
        btn_bookingInfo_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errandResult = (String) et_errand.getText().toString();
                purposeResult = (String) et_purpose.getText().toString();
                destinationResult = (String) et_destination.getText().toString();

                tv_resultStartDateTime.setText(resultStartDataTime);
                tv_resultEndDateTime.setText(resultEndDateTime);
                Log.i("BookingActivity", " info" + errandResult + " -" + purposeResult + " - " + destinationResult + "\n"
                        + tv_resultStartDateTime + tv_resultEndDateTime);
            }
        });


            /*
             * NAV MENU
             */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_books:
                        // Current
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(BookingActivity.this, MapsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(BookingActivity.this, LogActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }

    @Override
    public void processFinishVehicle(List<Vehicle> output) {
        if (output != null) {
            this.vehicles = output;
            this.updateListView();
        } else {
            Toast.makeText(BookingActivity.this, "Not able to fetch vehicle data from server.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateListView() {
        if (this.vehicles != null) {
            // the Adapter takes the Row-Layout, inserting the result into it.
            VehicleAdapter adapter = new VehicleAdapter(this, this, R.layout.list_row_vehicle, vehicles);
            // the ListView (lvBooking) takes the adapter, in this case the Row (with the result) and add it into the ListView.
            ListView lvVehicle = (ListView) findViewById(R.id.lv_listOfAvalibleVehicles);
            lvVehicle.setAdapter(adapter);
            lvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Booking booking = bookings.get(position); // getting the model
                    //intent.putExtra("bookingkey", new Gson().toJson(booking)); // converting model json into string type and sending it via intent
                    // startActivity(intent);
//                    Toast.makeText(UserProfileActivity.this, "You clicked on your active booking", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Site");
        List<String> locations = new ArrayList<String>();
        locations.add("Gothenburg");
        locations.add("Stockholm");
        locations.add("Malmö");
        locations.add("Jonkoping");

        listDataChild.put(listDataHeader.get(0), locations);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                int position = (childPosition + 1);
                startDateSelection();
                String postString = String.valueOf(position);
                TextView selectedRegion = (TextView) findViewById(R.id.tv_select_site);


                selectedRegion.setText(postString);
                site = selectedRegion.toString();
                Log.i("BookingInfo", "Selected site is:" + site);

                Intent startBooking = new Intent(BookingActivity.this, BookingFormActivity.class);
                startBooking.putExtra("site", postString);
                startActivity(startBooking);

                expListView.collapseGroup(groupPosition);
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)

                        .show();
                return false;
            }

            private void startDateSelection() {

            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });


      /*  et_errand.setText(errandResult);
        et_purpose.setText(purposeResult);
        et_destination.setText(destinationResult); */
    }

    private void findAvalibleVehicles() {
        mJsonTaskVehicle.delegate = this;
        mJsonTaskVehicle.execute(URL_TO_HIT + getActiveVehicles);
    }
}