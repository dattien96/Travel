package com.example.dattienbkhn.travel.screen.detailsplace;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivityPlaceBinding;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.model.app.Love;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.model.app.Rate;
import com.example.dattienbkhn.travel.model.realm.PlaceSave;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.common.SlideImageAdapter;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * Created by dattienbkhn on 12/02/2018.
 */

public class PlaceViewModel extends BaseObservable implements PlaceContract.ViewModel {
    private PlaceContract.View mNavigator;
    private DialogManager mDialogManager;
    private ActivityPlaceBinding binding;
    private SlideImageAdapter mImageAdapter;
    private Place mCurrentPlace;
    private PlaceInfor mCurrenPlaceInfor;
    private CommentAdapter mCommentAdapter;
    private int cityId;
    private int accId;

    //this property save state of love place when act run
    //when act onstop, if love state not same values of this property
    //we must call api save new state
    private boolean recentlyLoveState;
    private boolean recentlyLoveSave;

    //this property save rate of rate place when act run
    //when act onstop, if rate not same values of this property
    //we must call api save new state
    private float recentlyUserRate;

    public final ObservableField<Boolean> isLogin = new ObservableField<>();
    public final ObservableField<Boolean> isPlaceLoves = new ObservableField<>();
    public final ObservableField<Boolean> isPlaceSave = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforName = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforDes = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforPhone = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforOpenHour = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforAttraction = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforCuisine = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforHotelClass = new ObservableField<>();
    public final ObservableField<Boolean> isHasInforHotelPrize = new ObservableField<>();
    public final ObservableField<Boolean> isViewMoreComment = new ObservableField<>();
    public final ObservableField<String> mPosCurSlideTab = new ObservableField<>();
    public final ObservableField<Float> mCurRateOfUser = new ObservableField<>();

    public PlaceViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {

        mCurrenPlaceInfor = new PlaceInfor();
        isPlaceLoves.set(Boolean.FALSE);
        isPlaceSave.set(Boolean.FALSE);
        isHasInforName.set(Boolean.FALSE);
        isHasInforDes.set(Boolean.FALSE);
        isHasInforPhone.set(Boolean.FALSE);
        isHasInforOpenHour.set(Boolean.FALSE);
        isHasInforAttraction.set(Boolean.FALSE);
        isHasInforCuisine.set(Boolean.FALSE);
        isHasInforHotelClass.set(Boolean.FALSE);
        isHasInforHotelPrize.set(Boolean.FALSE);
        isViewMoreComment.set(Boolean.FALSE);
        mCurRateOfUser.set(0f);


        if (checkLogin()) {
            getPlaceLoveOfUser(accId, mCurrentPlace.getPlaceId());
            getUserRateOfplace(accId, mCurrentPlace.getPlaceId());
        }

        getCityId(mCurrentPlace.getPlaceId());
        loadImageSlideOfPlace(mCurrentPlace.getPlaceId());
        checkSavePlace();
        loadCommentOfPlace(mCurrentPlace.getPlaceId());
        loadDetailsInforOfPlace(mCurrentPlace.getAddressId());
        increasePlaceView(mCurrentPlace.getPlaceId());


    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (PlaceContract.View) viewNavigator;
    }

    public void setBinding(ActivityPlaceBinding binding) {
        this.binding = binding;
        viewPagerListener();
    }

    private void viewPagerListener() {
        binding.bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosCurSlideTab.set((position + 1) + " / " + mImageAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Bindable
    public SlideImageAdapter getImageAdapter() {
        return mImageAdapter;
    }

    public void setImageAdapter(SlideImageAdapter mImageAdapter) {
        this.mImageAdapter = mImageAdapter;
    }

    @Bindable
    public Place getCurrentPlace() {
        return mCurrentPlace;
    }

    public void setCurrentPlace(Place mCurrentPlace) {
        this.mCurrentPlace = mCurrentPlace;
        notifyPropertyChanged(BR.currentPlace);
    }

    @Bindable
    public PlaceInfor getCurrenPlaceInfor() {
        return mCurrenPlaceInfor;
    }

    public void setCurrenPlaceInfor(PlaceInfor mCurrenPlaceInfor) {
        this.mCurrenPlaceInfor = mCurrenPlaceInfor;
        notifyPropertyChanged(BR.currenPlaceInfor);
    }

    @Bindable
    public CommentAdapter getCommentAdapter() {
        return mCommentAdapter;
    }

    public void setCommentAdapter(CommentAdapter mCommentAdapter) {
        this.mCommentAdapter = mCommentAdapter;
    }

    @Override
    public void onStop() {
        if (isLogin.get() && isPlaceLoves.get() != recentlyLoveState) {
            Love love = new Love();
            love.setAccId(accId);
            love.setPlaceId(mCurrentPlace.getPlaceId());

            saveLoveState(love, isPlaceLoves.get());
        }

        if (isLogin.get() && binding.rateBar.getRating() != recentlyUserRate) {
            Rate rate = new Rate();
            rate.setAccId(accId);
            rate.setPlaceId(mCurrentPlace.getPlaceId());
            rate.setStar((double) binding.rateBar.getRating());

            saveRate(rate);
        }

        if (isPlaceSave.get() != recentlyLoveSave) {
            savePlace(mCurrentPlace);
        }


    }

    @Override
    public void onResume() {
        loadCommentOfPlace(mCurrentPlace.getPlaceId());
    }

    @Override
    public void reloadPlaceData(int placeId) {
        AppRepository.getPlaceRepo().getPlaceById(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Place>>() {
                    @Override
                    public void accept(WrapperResponse<Place> placeWrapperResponse) throws Exception {
                        if (placeWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Place.toString())) {
                            setCurrentPlace(placeWrapperResponse.getData());
                            onStart();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load place information!");
                    }
                });
    }

    @Override
    public void increasePlaceView(int placeId) {
        AppRepository.getPlaceRepo().increasePlaceViews(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Integer>>() {
                    @Override
                    public void accept(WrapperResponse<Integer> integerWrapperResponse) throws Exception {
                        Log.e("place_view", " : " + integerWrapperResponse.getData());
                    }
                });
    }

    @Override
    public void loadImageSlideOfPlace(int idPlace) {
        AppRepository.getImageRepo().getImagesOfPlace(idPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<Image>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Image>> imageResponseWrapperResponse) throws Exception {

                        if (imageResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ImageAlbumOfPlace.toString())) {
                            mImageAdapter.setData(imageResponseWrapperResponse.getData());
                            mPosCurSlideTab.set((1) + " / " + mImageAdapter.getCount());
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load images of place");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void loadCommentOfPlace(int idPlace) {
        AppRepository.getPlaceRepo().getTopComments(idPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<Comment>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Comment>> listWrapperResponse) throws Exception {
                        if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Comments.toString())) {
                            List<Comment> comments = listWrapperResponse.getData();
                            if (comments.size() >= 6) {
                                isViewMoreComment.set(Boolean.TRUE);
                                comments.remove(comments.size() - 1);
                            }

                            mCommentAdapter.setData(comments);

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("get_cmt", " error : " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void loadDetailsInforOfPlace(int idAddress) {
        AppRepository.getPlaceRepo().getPlaceDetailsInfor(idAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<PlaceInfor>>() {
                    @Override
                    public void accept(WrapperResponse<PlaceInfor> addressWrapperResponse) throws Exception {
                        if (addressWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.DetailsInforOfPlace.toString())) {

                            setCurrenPlaceInfor(addressWrapperResponse.getData());

                            if (mCurrenPlaceInfor.getName() != null && !mCurrenPlaceInfor.getName().isEmpty())
                                isHasInforName.set(true);
                            if (mCurrenPlaceInfor.getDescription() != null && !mCurrenPlaceInfor.getDescription().isEmpty())
                                isHasInforDes.set(true);
                            if (mCurrenPlaceInfor.getPhone() != null && !mCurrenPlaceInfor.getPhone().isEmpty())
                                isHasInforPhone.set(true);
                            if (mCurrenPlaceInfor.getOpenHour() != null && !mCurrenPlaceInfor.getOpenHour().isEmpty())
                                isHasInforOpenHour.set(true);
                            if (mCurrenPlaceInfor.getAttractionType() != null && !mCurrenPlaceInfor.getAttractionType().isEmpty())
                                isHasInforAttraction.set(true);
                            if (mCurrenPlaceInfor.getCuisine() != null && !mCurrenPlaceInfor.getCuisine().isEmpty())
                                isHasInforCuisine.set(true);
                            if (mCurrenPlaceInfor.getHotelClass() != null && !mCurrenPlaceInfor.getHotelClass().isEmpty())
                                isHasInforHotelClass.set(true);
                            if (mCurrenPlaceInfor.getHotelCost() != null && !mCurrenPlaceInfor.getHotelCost().isEmpty())
                                isHasInforHotelPrize.set(true);
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        mNavigator.onErrorMessage("Error when load information of place!");
                    }
                });
    }

    @Override
    public void getCityId(int placeId) {
        AppRepository.getCityRepo().getCityId(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Integer>>() {
                    @Override
                    public void accept(WrapperResponse<Integer> integerWrapperResponse) throws Exception {
                        if (integerWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.CityId.toString())) {
                            cityId = integerWrapperResponse.getData();
                            Log.e("cityId", cityId + "");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void getUserRateOfplace(int accId, int placeId) {
        AppRepository.getAccountRepo().getUserRateOfplace(accId, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Rate>>() {
                    @Override
                    public void accept(WrapperResponse<Rate> rateWrapperResponse) throws Exception {
                        if (rateWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Rate.toString())) {
                            mCurRateOfUser.set((float) rateWrapperResponse.getData().getStar());
                            recentlyUserRate = (float) rateWrapperResponse.getData().getStar();
                        }
                    }
                });
    }

    @Override
    public void onBackClick() {
        mNavigator.goBack();
    }

    @Override
    public void onLovesClick() {

        if (isLogin.get()) {
            if (isPlaceLoves.get()) {
                isPlaceLoves.set(Boolean.FALSE);
            } else isPlaceLoves.set(Boolean.TRUE);
        } else {
            mNavigator.onErrorMessage("Login to continue");
        }
    }

    @Override
    public void onSaveClick() {
        if (isPlaceSave.get()) {
            isPlaceSave.set(Boolean.FALSE);
        } else isPlaceSave.set(Boolean.TRUE);
    }

    @Override
    public void onShareClick() {

    }

    @Override
    public void onReviewsClick() {
        if (isLogin.get()) {
            mNavigator.goToCommentAct(accId, mCurrentPlace);
        } else {
            mNavigator.onErrorMessage("Login to continue");
        }
    }

    @Override
    public void onMapClick() {
        mNavigator.onLocationPermissionCheck(mCurrentPlace, mCurrenPlaceInfor);
    }

    /**
     * This method actualy not for rate event
     * Because rate click is default event of RatingBar
     * However we has this method in case user not login, we use a view to preven use click
     * in rating bar -> and show noti user must login
     * When login success,we hide cover view,then use can actualy click in ratingbar
     */
    @Override
    public void onRateClick() {
        mNavigator.onErrorMessage("Login to continue");
    }

    @Override
    public void onViewMoreCmtClick() {
        if (isLogin.get()) {
            mNavigator.goToCommentAct(accId, mCurrentPlace);
        } else {
            mNavigator.goToCommentAct(-1, mCurrentPlace);
        }
    }

    @Override
    public boolean checkLogin() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        isLogin.set(prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false));
        accId = prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1);
        return isLogin.get();
    }

    @Override
    public void checkSavePlace() {
        Realm realm = Realm.getDefaultInstance();
        final PlaceSave placeSaves = realm.where(PlaceSave.class).equalTo("id", mCurrentPlace.getPlaceId()).findFirst();
        if (placeSaves == null) {
            recentlyLoveSave = false;
            isPlaceSave.set(Boolean.FALSE);
        } else {
            recentlyLoveSave = true;
            isPlaceSave.set(Boolean.TRUE);
        }
    }

    @Override
    public void getPlaceLoveOfUser(int accId, int placeId) {
        AppRepository.getAccountRepo().checkLove(accId, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Love.toString())) {
                            isPlaceLoves.set(booleanWrapperResponse.getData());
                            recentlyLoveState = booleanWrapperResponse.getData();
                        }
                    }
                });
    }

    @Override
    public void saveLoveState(Love love, boolean isLove) {
        AppRepository.getAccountRepo().saveLove(love, isLove)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Love.toString())) {
                            Log.e("place_love", " : save love state success");
                        }
                    }
                });
    }

    @Override
    public void saveRate(Rate rate) {
        AppRepository.getAccountRepo().saveRate(rate)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Rate.toString())) {
                            Log.e("place_rate", " : save rate success");
                        }
                    }
                });
    }

    @Override
    public void savePlace(Place place) {
        Realm realm = Realm.getDefaultInstance();
        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (recentlyLoveSave) {
                    if (isPlaceSave.get()) {
                        //do nothing
                    } else {

                        //delete
                        PlaceSave placeSave = bgRealm.where(PlaceSave.class).equalTo("id", mCurrentPlace.getPlaceId()).findFirst();
                        placeSave.deleteFromRealm();
                    }
                } else {
                    if (isPlaceSave.get()) {
                        //save
                        PlaceSave placeSave = bgRealm.createObject(PlaceSave.class); // Create managed objects directly
                        placeSave.setId(mCurrentPlace.getPlaceId());
                        placeSave.setImage(mCurrentPlace.getPlaceImageUrl());
                        placeSave.setName(mCurrentPlace.getPlaceName());
                        placeSave.setRate(mCurrentPlace.getPlaceRate());
                        placeSave.setType(mCurrentPlace.getTypeDetails());
                        placeSave.setView(mCurrentPlace.getPlaceReview());
                    } else {
                        //donothing
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.

            }
        });
    }

}
