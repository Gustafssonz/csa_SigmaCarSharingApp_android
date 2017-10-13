package sigma.scsapp.fragment;

import android.widget.DatePicker;

public class EndDatePickerFragment
        extends DatePickerFragment
    {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
            ((EndDateListener) getActivity()).onEndDateSet(year, month, day);
            }

        public interface EndDateListener
            {
                void onEndDateSet(int year, int month, int day);
            }
    }