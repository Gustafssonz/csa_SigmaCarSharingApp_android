package sigma.scsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import sigma.scsapp.R;
import sigma.scsapp.adapters.VehicleAdapter;
import sigma.scsapp.controllers.JSONTaskBooking;
import sigma.scsapp.controllers.JSONTaskVehicle;
import sigma.scsapp.model.Booking;
import sigma.scsapp.model.Vehicle;
import sigma.scsapp.utility.AsyncResponseBooking;
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

import com.google.gson.Gson;

import static sigma.scsapp.utility.URL.URL_TO_HIT;
import static sigma.scsapp.utility.URL.getAllBookings;
import static sigma.scsapp.utility.URL.getAllVehicle;


public class BookingActivity extends Activity implements AsyncResponseVehicle, AsyncResponseBooking {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    /*
         Date Time Data
    */
    TextView tv_resultStartDate;
    TextView tv_resultStartTime;
    TextView tv_resultEndDate;
    TextView tv_resultEndTime;

    private String resultStartDate;
    private String resultStartTime;
    private String resultEndDate;
    private String resultEndTime;

    DateFormat format = new SimpleDateFormat("DD-MM-YYYY", Locale.ENGLISH);
    DateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    Date date;

    /*
         Booking info data
    */
    EditText et_errand;
    EditText et_purpose;
    EditText et_destination;
    Button btn_bookingInfo_completed;

    private String resultErrand;
    private String resultPurpose;
    private String resultDestination;

    TextView tv_errand;
    TextView tv_purpose;
    TextView tv_destination;
    /*
        Vehicle data and picker
    */
    private List<Vehicle> availableVehicles;
    private List<Vehicle> availableVehiclesForSite;
    JSONTaskVehicle mJsonTaskVehicle = new JSONTaskVehicle();
    private Vehicle pickedVehicle;

    JSONTaskBooking mJsonTaskBooking = new JSONTaskBooking();
    private Booking pickedBookings;

    private String currentStartDate;
    private String currentStartTime;
    private String currentEndDate;
    private String currentEndTime;
    private Date myStartDate;
    private Date myEndDate;
    private Date myStartTime;
    private Date myEndTime;
    private Date dateDateStart;
    private Date dateDateEnd;
    private Date dateTimeStart;
    private Date dateTimeEnd;
    private String postString = "Gothenburg";


    /*
        Gson handling
    */
    Gson gson;
    private String currentVehicleId;
    private List<Vehicle> smallList;
    private String wantedSite;
    private Button btn_pickTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activityview);

        expListView = findViewById(R.id.exvListView);
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        if (postString.equals("Gothenburg")) {
                wantedSite = "Gbg";
        }

        /*
        *   Booking info
        */
        et_errand = findViewById(R.id.et_errand);
        et_purpose = findViewById(R.id.et_purpose);
        et_destination = findViewById(R.id.et_destination);

        tv_errand = findViewById(R.id.tv_errand);
        tv_purpose = findViewById(R.id.tv_purpose);
        tv_destination = findViewById(R.id.tv_destination);

        /*
        *  Date & Time
        */
        Intent fetchData = getIntent();
        resultStartDate = fetchData.getStringExtra("ResultStartDate");
        resultStartTime = fetchData.getStringExtra("ResultStartTime");
        resultEndDate = fetchData.getStringExtra("ResultEndDate");
        resultEndTime = fetchData.getStringExtra("ResultEndTime");
        boolean isCompleted = fetchData.getBooleanExtra("ResultIsCompleted", false);

        tv_resultStartDate = findViewById(R.id.tv_resultStartDate);
        tv_resultStartTime = findViewById(R.id.tv_resultStartTime);
        tv_resultEndDate = findViewById(R.id.tv_resultEndDate);
        tv_resultEndTime = findViewById(R.id.tv_resultEndTime);

        tv_resultStartDate.setText(resultStartDate);
        tv_resultStartTime.setText(resultStartTime);
        tv_resultEndDate.setText(resultEndDate);
        tv_resultEndTime.setText(resultEndDate);


        if (isCompleted == true) {
            Log.i("BookingActivity", " Parsing String Data to DATE :" + resultStartTime + " - " + resultStartDate);

            try {
                myStartDate = format.parse(resultStartDate);
                myEndDate = format.parse(resultEndDate);
                myStartTime = formatTime.parse(resultStartTime);
                myEndTime = formatTime.parse(resultEndTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            findAvalibleVehicles();
            findAllBookings();

        }


        btn_bookingInfo_completed = (Button) findViewById(R.id.btn_bookingInfo_accepted);
        btn_bookingInfo_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultErrand = (String) et_errand.getText().toString();
                resultPurpose = (String) et_purpose.getText().toString();
                resultDestination = (String) et_destination.getText().toString();

                tv_errand.setText(resultErrand);
                tv_purpose.setText(resultPurpose);
                tv_destination.setText(resultDestination);


                Log.i("BookingActivity", " info" + resultErrand + " -" + resultPurpose + " - " + resultDestination + "\n"
                        + tv_resultStartDate + tv_resultEndDate);
            }
        });
        /*
        *  Get Vehicle and pick
        */





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
        Log.i("BookingActivity", "result from output" + output.toString());
        if (output != null) {
            availableVehicles = returnAvailableVehiclesforSite(output);
            Log.i("BookingActivity", "AvalibleVehciles list is now: " + availableVehicles.toString());
            this.updateListView();
        } else {
            Toast.makeText(BookingActivity.this, "Not able to fetch pickedVehicle data from server.", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Vehicle> returnAvailableVehiclesforSite(List<Vehicle> list) {
        smallList = new ArrayList<>();
        int x = list.size();
        Log.i("BookingActivity", " CHECK: K (size of Output) is : " + x);

        for (int i = 0; x > i; i++) {
            Log.i("BookingActivity", "CHECK: Current iteration is: " + i);
            Log.i("BookingActivity", "CHECK: Output get method is       : " + list.get(i));
            Log.i("BookingActivity", "CHECK: Test object is now         : " + list.toString());
            Log.i("BookingActivity", "Current Site is: " + list.get(i).getSite());
            String currentSite = list.get(i).getSite();
            Log.i("BookingActivity", "strings are: " + currentSite + " - " + wantedSite);

            if (currentSite.equals(wantedSite)) {
                Log.i("BookingActivity", " Adding this Vehicles into the list since site is " + currentSite);
                smallList.add(list.get(i));
            }
            Log.i("BookingActivity", "Small list is now (inside) : " + smallList);
        }
        Log.i("BookingActivity", "Small list is now: " + smallList);
        return smallList;
    }

    // TODO: 2017-10-23 Change the Bookingout together with choosen Site
    @Override
    public void processFinishBooking(List<Booking> output) {
        if (output != null) {
            int x = output.size();
            Log.i("BookingActivity", " FinishBooking CHECK: K (size of Output) is : " + x);
            List<Vehicle> availableListOfVehicle = availableVehicles;
            Log.i("BookingActivity", "Testing list for AvVehForSite : " + availableListOfVehicle);


            for (int i = 0; x >= i; i++) {
                Booking currentBooking = output.get(i);
                Log.i("BookingActivity", " FinishBookingCHECK: I is: " + i);
                Log.i("BookingActivity", "FinishBookingCHECK: Output Booking get method is: " + output.get(i));
                currentStartDate = currentBooking.getStartingDate();
                currentStartTime = currentBooking.getStartingTime();
                currentEndDate = currentBooking.getEndingDate();
                currentEndTime = currentBooking.getEndingTime();
                currentVehicleId = currentBooking.getVehicleId();

                try {
                    dateDateStart = format.parse(currentStartDate);
                    dateDateEnd = format.parse(currentEndDate);
                    dateTimeStart = formatTime.parse(currentStartTime);
                    dateTimeEnd = formatTime.parse(currentEndTime);
                } catch (ParseException e) {
                }
                String currentId = currentBooking.getVehicleId();

//                for (int z = 0; currentVehicleId.equals(); z++) {
                // TODO: 2017-10-22 Checka av ifall tiden infaller på vald tid. Tag då bort positionens vechileId och remova den från listan med avalibleVehicles.
                if (myStartDate.before(dateDateEnd) || myEndDate.after(dateDateStart)) {
                    // if (myStartTime.before(dateTimeEnd) || myEndTime.after(dateTimeStart)
                    Log.i("BookingActivity", "CurrentId is now : " + currentId);
                    if (availableListOfVehicle.get(i).getVehicleId().equals(currentBooking.getVehicleId()))
                    {
                        Log.i("BookingActivity", "Found conflict, removing vech from the list");
                        availableListOfVehicle.remove(i);
                    } else {
                        Log.i("BookingActivity", "No conflict found...");
                    }
                    //  }
                }
            }
            this.availableVehiclesForSite = availableListOfVehicle;
            this.updateListView();
        } else {
            Toast.makeText(BookingActivity.this, "Not able to fetch pickedVehicle data from server.", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateListView() {
        if (this.availableVehicles != null) {
            showTimeButton();
            VehicleAdapter adapter = new VehicleAdapter(this, this, R.layout.list_row_vehicle_simple, availableVehicles);
            ListView lvVehicle = (ListView) findViewById(R.id.lv_listOfAvalibleVehicles);
            lvVehicle.setAdapter(adapter);
            lvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pickedVehicle = availableVehicles.get(position);
                    Toast.makeText(BookingActivity.this, "You picked a car" + pickedVehicle.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    private void showTimeButton(){
        btn_pickTime = findViewById(R.id.btn_pickTime);
        btn_pickTime.setVisibility(View.VISIBLE);
        btn_pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startBooking = new Intent(BookingActivity.this, BookingFormActivity.class);
                startBooking.putExtra("site", postString);
                startActivity(startBooking);
            }
        });

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
                findAvalibleVehicles();
                postString = String.valueOf(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                TextView selectedRegion = (TextView) findViewById(R.id.tv_select_site);


                selectedRegion.setText(postString);

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

        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findAllBookings() {
        mJsonTaskBooking.delegate = this;
        mJsonTaskBooking.execute(URL_TO_HIT + getAllBookings);
    }

    private void findAvalibleVehicles() {
        mJsonTaskVehicle.delegate = this;
        mJsonTaskVehicle.execute(URL_TO_HIT + getAllVehicle);
    }

    public List<Vehicle> returnAvailableListOfVehicle() {
        return availableVehicles;
    }

    public String gsonVehicle() {
        String pickedVehicleToJson = gson.toJson(pickedVehicle);
        Log.i("BookingActivity", "Json format:" + pickedVehicleToJson);
        return pickedVehicleToJson;
    }

    public Vehicle returnAvalibleVehcile(Vehicle foundVehcile) {

        /*
        * Kontrollera Site -> Vehicle på site.
        *  List <Vehicle> ny = returnAvalibleVehicles.getSite(Göteborg); (list<Vehicles>)
        *
        * Kontrollera Tiden för Vehicles
        * LOOP: POSITION {
        * getVehicleId(ny) getAllBookings() {
        *  LOOP : ALLBOOKINGS
        *  if startDate && Enddate != myStartDate && myEndDate {
        *  }
         * return AvalibleBookings
         * }
        *}
        * Skicka start och slut period?
        * Alternativt att hämta availableVehicles egna bookings
        *
        *
        * GetAllBookings -> return list
            List(position).getVehicleID(#)
            Add to new List <bookings> nylist (1)

            List <bookings> getStartDate getEndDate
            Nylist mystartdate myenddate

            ( Myenddate before getstartdate && mystartdate after getenddate)
            Return true / false

            IsAvalible = true
            #=1
        */

        return foundVehcile;
    }

    public void startReview() {
        Intent reviewIntent = new Intent();
        reviewIntent.putExtra("destination", resultDestination);
        reviewIntent.putExtra("purpose", resultPurpose);
        reviewIntent.putExtra("errand", resultErrand);
        reviewIntent.putExtra("startDate", resultStartDate);
        reviewIntent.putExtra("startTime", resultStartTime);
        reviewIntent.putExtra("endDate", resultEndDate);
        reviewIntent.putExtra("endTime", resultEndTime);
    }
}