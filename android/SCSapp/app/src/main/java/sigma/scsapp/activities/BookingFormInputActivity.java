package sigma.scsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sigma.scsapp.*;

public class BookingFormInputActivity extends AppCompatActivity
    {


        EditText errand;
        EditText purpose;
        EditText destination;

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener()
            {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                    switch (item.getItemId())
                        {
                        case R.id.navigation_home:
                            errand.setText(R.string.title_home);
                            return true;
                        case R.id.navigation_dashboard:
                            purpose.setText(R.string.title_dashboard);
                            return true;
                        case R.id.navigation_notifications:
                            destination.setText(R.string.title_notifications);
                            return true;
                        }
                    return false;
                    }

            };

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booking_form_input);


            errand = (EditText) findViewById(R.id.et_errand);
            purpose = (EditText) findViewById(R.id.et_purpose);
            destination = (EditText) findViewById(R.id.et_destination);


            String errandResult = (String) errand.getText().toString();
            String purposeResult= (String) purpose.getText().toString();
            String destinationResult = (String) destination.getText().toString();


            Intent goBack = new Intent(this, BookingActivity.class);
            Bundle packageInfo = new Bundle(3);
            packageInfo.putString("errandResultBundle", errandResult);
            packageInfo.putString("purposeResultBundle", purposeResult);
            packageInfo.putString("destinationResultBundle", destinationResult);

            goBack.putExtra("Bundle", packageInfo);

            startActivity(goBack, packageInfo);





           // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
           // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }

    }
