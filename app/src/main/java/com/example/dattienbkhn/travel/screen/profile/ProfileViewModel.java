package com.example.dattienbkhn.travel.screen.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivityProfileBinding;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.facebook.fbcover.CoverWrap;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 21/03/2018.
 */

public class ProfileViewModel extends BaseObservable implements ProfileContract.ViewModel,
        DatePickerDialog.OnDateSetListener {
    private ProfileContract.View mNavigator;
    private Context ctx;
    private DialogManager mDialogManager;
    private ActivityProfileBinding binding;
    private AlertDialog.Builder builder;
    private int accId;
    private String accType;
    private final User userRequest = new User();
    private boolean isUserChange;
    private User mCurUser;

    public final ObservableField<Boolean> isAppAcc = new ObservableField<>();
    public final ObservableField<String> mAddress = new ObservableField<>();
    public final ObservableField<String> mName = new ObservableField<>();
    public final ObservableField<String> mGender = new ObservableField<>();
    public final ObservableField<String> mBirthday = new ObservableField<>();

    public ProfileViewModel(DialogManager mDialogManager, Context ctx) {
        this.mDialogManager = mDialogManager;
        this.ctx = ctx;
    }

    @Override
    public void onStart() {
        isUserChange = false;
        userRequest.setAccId(accId);
        if (accType.equalsIgnoreCase(Constant.SHARED_LOGIN_APP)) {
            isAppAcc.set(true);
        } else {
            isAppAcc.set(false);
        }
        loadUserInfor(accId, accType);
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (ProfileContract.View) viewNavigator;
    }

    @Override
    public void setProfileImage(byte[] byteImage, boolean isAvatar) {
        Bitmap bm = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        if (isAvatar) {
            binding.imgAvatar.setImageBitmap(bm);
            isUserChange = true;
            String encoded = Base64.encodeToString(byteImage,Base64.DEFAULT);
            userRequest.setAvatar(encoded);
        } else {
            binding.imgCover.setImageBitmap(bm);
            isUserChange = true;
            String encoded = Base64.encodeToString(byteImage,Base64.DEFAULT);
            userRequest.setCoverImage(encoded);
        }

    }

    @Override
    public void onBackClick() {
        mNavigator.goFinish();
    }

    @Override
    public void onSaveSetting() {
        if (isUserChange) {
            AppRepository.getAccountRepo().changeUserInfor(userRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mDialogManager.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                        @Override
                        public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                            if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.UserInforChange.toString())) {
                                if (booleanWrapperResponse.getData()) {
                                    mNavigator.onErrorMessage("Update information success!");
                                    isUserChange = false;
                                } else {
                                    mNavigator.onErrorMessage("Error when update information!");
                                    Log.e("change_user_infor", booleanWrapperResponse.getMessage());
                                }
                            } else {
                                mNavigator.onErrorMessage("Error when update information!");
                                Log.e("change_user_infor", booleanWrapperResponse.getMessage());
                            }
                            mDialogManager.dismissProgressDialog();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mDialogManager.dismissProgressDialog();
                            mNavigator.onErrorMessage("Error when update information!");
                            Log.e("change_user_infor", throwable.getMessage());
                        }
                    });
        } else {
            mNavigator.onErrorMessage("Information is not update!");
        }
    }

    @Override
    public void onPassWdChangeClick() {

    }

    @Override
    public void onNameChangeClick() {
        builder.setTitle("Name change");
        // Set up the input
        final EditText input = new EditText(ctx);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();

                if (name.length() >= 18) {
                    mNavigator.onErrorMessage("Name content is too long!");
                } else if (name.length() == 0) {
                    mNavigator.onErrorMessage("Name content is empty!");
                } else if (name.length() > 0 && name.length() < 5) {
                    mNavigator.onErrorMessage("Name content is too short!");
                } else {
                    userRequest.setName(name);
                    isUserChange = true;
                    mName.set(name);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onGenderChangeClick() {
        builder.setTitle("Gender change");
        // Set up the input
        final RadioButton male = new RadioButton(ctx);
        male.setText("Male");
        final RadioButton female = new RadioButton(ctx);
        female.setText("Female");
        RadioGroup gr = new RadioGroup(ctx);
        gr.addView(male);
        gr.addView(female);
        gr.setPadding(50, 20, 50, 20);
        builder.setView(gr);

        if (mCurUser.getGender() == null || mCurUser.getGender() == 1) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isUserChange = true;
                if (male.isChecked()) {
                    mGender.set("Male");
                    userRequest.setGender(1);
                    Log.e("gender", "male");
                } else {
                    mGender.set("Female");
                    userRequest.setGender(0);
                    Log.e("gender", "female");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onBirthdayChangeClick() {
        DatePickerDialog datePickerDialog = null;
        if (mCurUser.getBirthDay() == null) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(
                    ctx, this, year, month, day);
            datePickerDialog.show();
        } else {
            DateFormat format = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);
            try {
                Date date = format.parse(mCurUser.getBirthDay());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(
                        ctx, this, year, month, day);
                datePickerDialog.show();

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onAddressChangeClick() {
        builder.setTitle("Address change");
        // Set up the input
        final EditText input = new EditText(ctx);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String add = input.getText().toString();

                if (add.length() >= 18) {
                    mNavigator.onErrorMessage("Address content is too long!");
                } else if (add.length() == 0) {
                    mNavigator.onErrorMessage("Address content is empty!");
                } else {
                    userRequest.setAddress(add);
                    isUserChange = true;
                    mAddress.set(add);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onAvatarChangeClick() {
        mNavigator.pickImage(true);
    }

    @Override
    public void onCoverImageChangeClick() {
        mNavigator.pickImage(false);
    }

    @Override
    public void loadUserInfor(final int accId, final String accType) {
        /*if (accType.equalsIgnoreCase(Constant.SHARED_LOGIN_FACEBOOK)) {
            loadFaceBookInfor(accId);
        } else if (accType.equalsIgnoreCase(Constant.SHARED_LOGIN_GOOGLE)) {
            loadGoogleInfor(accId);
        } else {
            //app acc type
        }*/
        AppRepository.getAccountRepo().getUserInfor(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<User>>() {
                    @Override
                    public void accept(WrapperResponse<User> userWrapperResponse) throws Exception {
                        if (userWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.UserInfor.toString())) {
                            User userRes = userWrapperResponse.getData();
                            mAddress.set(userRes.getAddressString());
                            mGender.set(userRes.getGenderString());
                            mBirthday.set(userRes.getBirthDayString());
                            mName.set(userRes.getName());
                            mCurUser = userWrapperResponse.getData();

                            if (userRes.getAvatar() != null) {
                                byte[] imrArr = Base64.decode(userRes.getAvatar(),Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgAvatar.setImageBitmap(bm);
                            }

                            if (userRes.getCoverImage() != null) {
                                byte[] imrArr = Base64.decode(userRes.getCoverImage(),Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgCover.setImageBitmap(bm);
                            }

                            if (userRes.getAvatar() == null || userRes.getCoverImage() == null) {
                                if (accType.equalsIgnoreCase(Constant.SHARED_LOGIN_FACEBOOK)) {
                                    loadFaceBookInfor(accId);
                                } else if (accType.equalsIgnoreCase(Constant.SHARED_LOGIN_GOOGLE)) {
                                    loadGoogleInfor(accId);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void loadFaceBookInfor(int fbId) {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();

        if (mCurUser.getAvatar() == null) {
            String userAvatar = "https://graph.facebook.com/"
                    + prefs.getString(Constant.SHARED_FACEBOOK_ID, "")
                    + "/picture?type=large";
            Picasso.with(binding.imgAvatar.getContext()).load(userAvatar).into(binding.imgAvatar);
        }

        if (mCurUser.getCoverImage() == null) {

            AppRepository.getCommonRepo().getCoverFbImage(prefs.getString(Constant.SHARED_FACEBOOK_ID, ""), prefs.getString(Constant.SHARED_FACEBOOK_TOKEN, " "))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CoverWrap>() {
                        @Override
                        public void accept(CoverWrap coverWrap) throws Exception {
                            Picasso.with(binding.imgCover.getContext()).load(coverWrap.getCover().getSource()).into(binding.imgCover);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mNavigator.onErrorMessage("Error when load user cover image !");
                        }
                    });
        }
    }

    @Override
    public void loadGoogleInfor(int ggId) {
        if (mCurUser.getAvatar() == null) {
            SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
            AppRepository.getAccountRepo().getGoogleInfor(prefs.getString(Constant.SHARED_GOOGLE_ID, ""))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GoogleInfor>() {
                        @Override
                        public void accept(GoogleInfor googleInfor) throws Exception {
                            if (googleInfor.getEntry().getGphotothumbnail().gett() != null) {
                                Picasso.with(binding.imgAvatar.getContext()).
                                        load(googleInfor.getEntry().getGphotothumbnail().gett())
                                        .into(binding.imgAvatar);
                            } else {
                                Picasso.with(binding.imgAvatar.getContext()).
                                        load(R.drawable.google_96)
                                        .into(binding.imgAvatar);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Picasso.with(binding.imgAvatar.getContext()).
                                    load(R.drawable.google_96)
                                    .into(binding.imgAvatar);

                        }
                    });
        }
    }

    public void setBinding(ActivityProfileBinding binding) {
        this.binding = binding;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public void setBuilder(AlertDialog.Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        java.util.Date date = new java.util.Date(year - 1900, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Log.e("picker", sdf.format(date));
        userRequest.setBirthDay(sdf.format(date));
        mBirthday.set(sdf.format(date));
        isUserChange = true;
    }
}
