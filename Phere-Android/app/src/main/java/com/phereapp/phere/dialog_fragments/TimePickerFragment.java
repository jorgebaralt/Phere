package com.phereapp.phere.dialog_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TimeFromOnTimeSet mCallback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int hour = hourOfDay % 12;
        String selectedTimeGlobal = String.format(Locale.US, "%02d:%02d %s", hour == 0 ? 12 : hour,
                minute, hourOfDay < 12 ? "am" : "pm");
        Toast.makeText(getActivity(), selectedTimeGlobal, Toast.LENGTH_SHORT).show();
        mCallback.timeFromOnTimeSet(selectedTimeGlobal);
    }

    public interface TimeFromOnTimeSet {
        void timeFromOnTimeSet(String time);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
          mCallback = (TimeFromOnTimeSet) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Most implement TimeFromOnTimeSet");
        }
    }
}
