package com.example.dattienbkhn.travel.screen.main.mefragment;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.FragmentMeBinding;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.facebook.fbcover.CoverWrap;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccRequest;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class MeFrgViewModel extends BaseObservable implements MeFrgContract.ViewModel {
    private DialogManager mDialogManager;
    private MeFrgContract.View mNavigator;
    private FragmentMeBinding binding;
    private CallbackManager callbackManager;
    private int mCurrentAccountId;
    private LoginResult mCurLoginResult;
    private FriendAdapter mFriendAdapter;
    private boolean isStream;

    //app can be login with fb,google or app act
    private String loginType;

    public final ObservableField<Boolean> isLogin = new ObservableField<>();
    public final ObservableField<Boolean> isViewMoreFriends = new ObservableField<>();
    public final ObservableField<String> mUserName = new ObservableField<>();
    public final ObservableField<String> userProfileUrl = new ObservableField<>();
    public final ObservableField<String> homeImageUrl = new ObservableField<>();
    public final ObservableField<User> mCurUser = new ObservableField<>();


    public MeFrgViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        isViewMoreFriends.set(Boolean.FALSE);
        initFaceBook();
        if (checkLogin()) {
            //do something when user login
            getUserInfor();

            SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
            mCurrentAccountId = prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1);
            getTopFriends(mCurrentAccountId);
            getStreamState(mCurrentAccountId);
        }

    }

    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (MeFrgContract.View) viewNavigator;
    }

    @Override
    public void onSignUpClick() {
        mNavigator.goToRegisterAct();
    }

    @Override
    public void onSettingClick() {
        mNavigator.goToSettingAct();
    }

    @Override
    public boolean checkLogin() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        isLogin.set(prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false));
        loginType = prefs.getString(Constant.SHARED_LOGIN_TYPE, "");
        return isLogin.get();
    }

    @Override
    public void onFacebookLogin() {
        binding.loginButton.performClick();
    }

    @Override
    public void onGoogleLogin() {
        mNavigator.loginGoogle();
    }

    @Override
    public void initFaceBook() {
        // Callback registration
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                binding.imgCover.setImageResource(R.drawable.no_image);
                binding.imgAvatar.setImageResource(R.drawable.no_image);
                // App code
                isLogin.set(true);
                mCurLoginResult = loginResult;
                //save login state local
                SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(Constant.SHARED_LOGIN_STATE, true);
                editor.putString(Constant.SHARED_FACEBOOK_TOKEN, mCurLoginResult.getAccessToken().getToken());
                editor.putString(Constant.SHARED_LOGIN_TYPE, Constant.SHARED_LOGIN_FACEBOOK);
                editor.putString(Constant.SHARED_FACEBOOK_ID, loginResult.getAccessToken().getUserId());

                editor.commit();

                saveFaceBookAccount(loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

       /* AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {

                    //save login state
                    SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(Constant.SHARED_LOGIN_STATE, false);
                    editor.commit();
                }
            }
        };*/

        /*ProfileTracker mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                // Fetch user details from New Profile
               Log.e("facebook",newProfile.getId());
            }
        };*/

    }

    @Override
    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            binding.imgCover.setImageResource(R.drawable.no_image);
            binding.imgAvatar.setImageResource(R.drawable.no_image);
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.e("login_google", "login success");

            isLogin.set(true);
            //save login state local
            SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(Constant.SHARED_LOGIN_STATE, true);
            editor.putString(Constant.SHARED_LOGIN_TYPE, Constant.SHARED_LOGIN_GOOGLE);
            editor.putString(Constant.SHARED_GOOGLE_ID, account.getId());
            editor.putString(Constant.SHARED_GOOGLE_TOKEN, account.getIdToken());
            editor.commit();

            saveGoogleAccount(account.getId());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("login_google", "signInResult:failed code=" + e.getStatusCode() + " " + e.getMessage());

        }
    }

    @Override
    public void loadCoverFbImage(String userId, String token) {
        AppRepository.getCommonRepo().getCoverFbImage(userId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CoverWrap>() {
                    @Override
                    public void accept(CoverWrap coverWrap) throws Exception {
                        homeImageUrl.set(coverWrap.getCover().getSource());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load user cover image !");
                    }
                });
    }

    @Override
    public void saveFaceBookAccount(final String userName) {
        final Profile profile = Profile.getCurrentProfile();
        AppRepository.getAccountRepo().checkExistAcc(userName, "facebook")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<Integer>>() {
                    @Override
                    public void accept(WrapperResponse<Integer> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.AccCheck.toString())) {

                            if (booleanWrapperResponse.getData() == -1) {

                                AccRequest accRequest = new AccRequest();
                                accRequest.setIsFaceBook(1);
                                accRequest.setIsGoogle(0);
                                accRequest.setPassWd(profile.getId());
                                accRequest.setPhone(null);
                                accRequest.setUsername(profile.getId());

                                AppRepository.getAccountRepo().createAccount(accRequest, profile.getName())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<WrapperResponse<Integer>>() {
                                            @Override
                                            public void accept(WrapperResponse<Integer> accountWrapperResponse) throws Exception {
                                                if (accountWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.AccCreated.toString())) {
                                                    Log.e("fb_login", " save acc ");

                                                    mCurrentAccountId = accountWrapperResponse.getData();

                                                    //save login state local
                                                    SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                                                    SharedPreferences.Editor editor = prefs.edit();
                                                    editor.putInt(Constant.SHARED_LOGIN_ACC_ID, mCurrentAccountId);
                                                    Log.e("fb_login", "" + mCurrentAccountId);
                                                    editor.commit();

                                                    getUserInfor();
                                                    getTopFriends(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1));
                                                    mNavigator.connectSocket();
                                                }

                                                mDialogManager.dismissProgressDialog();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                Log.e("fb_login", "helo : " + throwable.getMessage());
                                                mDialogManager.dismissProgressDialog();
                                            }
                                        });
                            } else {
                                mCurrentAccountId = booleanWrapperResponse.getData();

                                //save login state local
                                SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt(Constant.SHARED_LOGIN_ACC_ID, mCurrentAccountId);
                                Log.e("fb_login", "" + mCurrentAccountId);
                                editor.commit();

                                getUserInfor();
                                getTopFriends(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1));
                                mNavigator.connectSocket();
                                mDialogManager.dismissProgressDialog();
                            }

                        } else {
                            mDialogManager.dismissProgressDialog();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("fb_login", throwable.getMessage());
                        mDialogManager.dismissProgressDialog();

                    }
                });
    }

    @Override
    public void saveGoogleAccount(String userName) {
        final GoogleSignInAccount ggAcc = GoogleSignIn.getLastSignedInAccount(((MeFragment) mNavigator).getActivity());
        AppRepository.getAccountRepo().checkExistAcc(userName, "google")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<Integer>>() {
                    @Override
                    public void accept(WrapperResponse<Integer> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.AccCheck.toString())) {

                            if (booleanWrapperResponse.getData() == -1) {

                                AccRequest accRequest = new AccRequest();
                                accRequest.setIsFaceBook(0);
                                accRequest.setIsGoogle(1);
                                accRequest.setPassWd(ggAcc.getId());
                                accRequest.setPhone(null);
                                accRequest.setUsername(ggAcc.getId());

                                AppRepository.getAccountRepo().createAccount(accRequest, ggAcc.getDisplayName())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<WrapperResponse<Integer>>() {
                                            @Override
                                            public void accept(WrapperResponse<Integer> accountWrapperResponse) throws Exception {
                                                if (accountWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.AccCreated.toString())) {
                                                    Log.e("gg_login", " save acc ");

                                                    mCurrentAccountId = accountWrapperResponse.getData();

                                                    //save login state local
                                                    SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                                                    SharedPreferences.Editor editor = prefs.edit();
                                                    editor.putInt(Constant.SHARED_LOGIN_ACC_ID, mCurrentAccountId);
                                                    editor.commit();

                                                    getUserInfor();
                                                    getTopFriends(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1));
                                                    mNavigator.connectSocket();
                                                }

                                                mDialogManager.dismissProgressDialog();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                mDialogManager.dismissProgressDialog();
                                                Log.e("gg_login", "helo : " + throwable.getMessage());
                                            }
                                        });
                            } else {
                                mCurrentAccountId = booleanWrapperResponse.getData();
                                //save login state local
                                SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt(Constant.SHARED_LOGIN_ACC_ID, mCurrentAccountId);
                                editor.commit();

                                getUserInfor();
                                getTopFriends(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1));
                                mNavigator.connectSocket();
                                mDialogManager.dismissProgressDialog();
                            }

                        } else {
                            mDialogManager.dismissProgressDialog();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("gg_login", throwable.getMessage());
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void getUserInfor() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        AppRepository.getAccountRepo().getUserInfor(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<User>>() {
                    @Override
                    public void accept(WrapperResponse<User> userWrapperResponse) throws Exception {
                        if (userWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.UserInfor.toString())) {
                            mCurUser.set(userWrapperResponse.getData());
                            mUserName.set(mCurUser.get().getName());
                            if (mCurUser.get().getAvatar() != null) {
                                byte[] imrArr = Base64.decode(mCurUser.get().getAvatar(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgAvatar.setImageBitmap(bm);
                            }

                            if (mCurUser.get().getCoverImage() != null) {
                                byte[] imrArr = Base64.decode(mCurUser.get().getCoverImage(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgCover.setImageBitmap(bm);
                            }

                            if (mCurUser.get().getAvatar() == null || mCurUser.get().getCoverImage() == null) {
                                if (loginType.equalsIgnoreCase(Constant.SHARED_LOGIN_FACEBOOK)) {
                                    //login with fb
                                    Profile profile = Profile.getCurrentProfile();
                                    if (mCurUser.get().getAvatar() == null) {
                                        userProfileUrl.set("https://graph.facebook.com/" + profile.getId() + "/picture?type=large");
                                    }

                                    if (mCurUser.get().getCoverImage() == null) {

                                        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                                        loadCoverFbImage(profile.getId(), prefs.getString(Constant.SHARED_FACEBOOK_TOKEN, " "));
                                    }


                                } else if (loginType.equalsIgnoreCase(Constant.SHARED_LOGIN_GOOGLE)) {
                                    //login with gg

                                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(((MeFragment) mNavigator).getActivity());
                                    if (acct != null) {

                                        if (mCurUser.get().getAvatar() == null) {
                                            if (acct.getPhotoUrl() != null)
                                                userProfileUrl.set(acct.getPhotoUrl().toString());
                                            else userProfileUrl.set("");
                                        }

                                    }
                                }
                            }
                        }
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void getTopFriends(int accId) {
        Log.e("friend", "cochay + accId = " + accId);
        mFriendAdapter.setData(new ArrayList<Friend>());
        AppRepository.getAccountRepo().getTopFriends(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<Friend>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Friend>> friendsWrapperResponse) throws Exception {
                        if (friendsWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Friend.toString())) {
                            List<Friend> friendsTmp = friendsWrapperResponse.getData();
                            if (friendsTmp.size() >= 6) {
                                isViewMoreFriends.set(Boolean.TRUE);
                                friendsTmp.remove(friendsTmp.size() - 1);
                            }


                            mFriendAdapter.setData(friendsTmp);


                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("friend", " get friend : " + throwable.getMessage());
                        mNavigator.onErrorMessage("Error when get friends");
                    }
                });
    }

    @Override
    public void onLovedPlaceClick() {
        mNavigator.goToLovedPlaceAct(TravelSharedPreference.getINSTANCE().getInt(Constant.SHARED_LOGIN_ACC_ID, -1));
    }

    @Override
    public void onStreamClick() {
        if (!isStream) {
            SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
            mNavigator.goToLiveStreamAct(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID,-1));
        } else {
            mNavigator.onErrorMessage("This account is streaming...");
        }
    }

    @Override
    public void onFriendSearchClick() {
        mNavigator.goToFriendListAct(mCurrentAccountId);
    }

    @Override
    public void onVodClick() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        mNavigator.goToVodAct(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID,-1),mUserName.get());
    }

    @Override
    public void onViewMoreFriendsClick() {
        mNavigator.goToFriendListAct(mCurrentAccountId);
    }

    @Override
    public void getStreamState(int accId) {
        AppRepository.getAccountRepo().getStreamState(accId)
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
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.StreamState.toString())) {
                            isStream = booleanWrapperResponse.getData();
                        }
                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("stream_state", " : " + throwable.getMessage());
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    public void updateUserInfor() {

    }

    public void setBinding(FragmentMeBinding binding) {
        this.binding = binding;
    }

    public Bitmap getFacebookProfilePicture(String userID) throws IOException {
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    @Bindable
    public FriendAdapter getFriendAdapter() {
        return mFriendAdapter;
    }

    public void setFriendAdapter(FriendAdapter mFriendAdapter) {
        this.mFriendAdapter = mFriendAdapter;
        notifyPropertyChanged(BR.friendAdapter);
    }
}
