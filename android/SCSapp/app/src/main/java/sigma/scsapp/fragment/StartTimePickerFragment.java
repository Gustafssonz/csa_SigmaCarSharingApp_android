package sigma.scsapp.fragment;

import android.widget.TimePicker;

public class StartTimePickerFragment
        extends TimePickerFragment
    {

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minutes)
            {
            ((StartTimeListener) getActivity()).onStartTimeSet(hour, minutes);
            }

        public interface StartTimeListener
            {
                void onStartTimeSet(int hour, int minutes);
            }
    }