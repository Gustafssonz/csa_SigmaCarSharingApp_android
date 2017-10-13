package sigma.scsapp.fragment;

import android.widget.DatePicker;

public class StartDatePickerFragment
        extends DatePickerFragment
    {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
            ((StartDateListener) getActivity()).onStartDateSet(year, month, day);
            }

        public interface StartDateListener
            {
                void onStartDateSet(int year, int month, int day);
            }
    }