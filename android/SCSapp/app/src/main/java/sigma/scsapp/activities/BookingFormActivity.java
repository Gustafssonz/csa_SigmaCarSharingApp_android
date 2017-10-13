package sigma.scsapp.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.DateFormat;

import sigma.scsapp.R;
import sigma.scsapp.fragment.*;


public class BookingFormActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.bookingform_activity);
            }


            // TIME START AND END

        public void showTimePickerDialogStart(View v)
            {
            Log.i("Tag Time", "Clicked on Start Time");
            DialogFragment newFragment = new StartTimePickerFragment();
            newFragment.show(getFragmentManager(), "timePickerStart");
            }

        public void showTimePickerDialogEnd(View v)
            {
            Log.i("Tag Time", "Clicked on End Time");
            DialogFragment newFragment = new EndTimePickerFragment();
            newFragment.show(getFragmentManager(), "timePickerEnd");
            }

        // DATE START AND END
        public void showDatePickerDialogStart(View v)
            {
            Log.i("Tag Date", "Clicked on Start Date");
            DialogFragment newFragment = new StartDatePickerFragment();
            newFragment.show(getFragmentManager(), "datePickerStart");
            }

        public void showDatePickerDialogEnd(View v)
            {
            Log.i("Tag Date", "Clicked on End Date");
            DialogFragment newFragment = new EndDatePickerFragment();
            newFragment.show(getFragmentManager(), "datePickerEnd");
            }


        @Override
        public void onDateSet(DatePicker view, int y, int m, int d)
            {
            // Kan skicka in y,m,d i Cal direkt.
            Log.i("Tag onDateSet ", "OnDateSet activated in the BookingFormActivity");
            int year = y;
            int month = m;
            int day = d;
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
            }

        private void setDate(final Calendar calendar)
            {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            ((TextView) findViewById(R.id.tv_bookingform_set_date_start)).setText(dateFormat.format(calendar.getTime()));
            }

        @Override
        public void onTimeSet(TimePicker view, int i, int i1)
            {
            // TODO: 2017-09-25 Fixa en if-sats checkpoint för knapparna (starttime och endtime)
            Log.i("Tag on BookingTest", "onTimeSet activated in the BookingFormActivity");
            int hour = view.getHour();
            int min = view.getMinute();
            setTime(hour, min);

            }

        public void setTime(int hour, int min)
            {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            int hour2 = hour;
            int min2 = min;
            ((TextView) findViewById(R.id.tv_bookingform_set_time_start)).setText(hour2 + " : " + min2);
            }
    }






