package com.example.dattienbkhn.travel.utils.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;


import com.example.dattienbkhn.travel.R;

import java.util.Calendar;


/**
 * Created by lion on 06/10/2017.
 */

public class DialogManager implements DialogInterfaceImp {

    private Context mContext;
    private AlertDialog mDialog;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private Calendar mCalendar;
    private ProgressDialog mProgressDialog;

    public DialogManager(Context context) {
        mContext = context;
        mCalendar = Calendar.getInstance();
    }

    public DialogManager dialogWithNoTitleTwoButton(int message,
                                                    DialogInterface.OnClickListener positiveButtonListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setPositiveButton(R.string.action_yes, positiveButtonListener)
                .setNegativeButton(R.string.action_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        mDialog = builder.create();
        return this;
    }

    public DialogManager dialogDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener) {
        mDatePickerDialog =
                new DatePickerDialog(mContext, onDateSetListener, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        return this;
    }

    public DialogManager dialogTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        mTimePickerDialog = new TimePickerDialog(mContext, onTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        return this;
    }

    public void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            return;
        }
        mTimePickerDialog.show();
    }

    public void showDatePickerDialog() {
        if (mDatePickerDialog == null) {
            return;
        }
        mDatePickerDialog.show();
    }

    public DialogManager dialogWarning(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        mDialog = builder.create();
        return this;
    }

    public void show() {
        if (mDialog == null) {
            return;
        }
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog == null) {
            return;
        }
        mDialog.dismiss();
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getString(R.string.please_wait));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
