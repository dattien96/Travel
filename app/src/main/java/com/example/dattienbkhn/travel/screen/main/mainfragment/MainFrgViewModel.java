package com.example.dattienbkhn.travel.screen.main.mainfragment;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.FragmentMainBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.ip.IpRes;
import com.example.dattienbkhn.travel.model.location.CurrentLocation;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class MainFrgViewModel extends BaseObservable implements MainFrgContract.ViewModel {

    private MainFrgContract.View mMainFrgNavigator;
    private DialogManager mDialogManager;
    private PlaceofCountryAdapter mPlaceofCountryAdapter;
    private CityAdapter mCityAdapter;
    private FragmentMainBinding mFrgMainBinding;


    //Observable update UI
    public final ObservableField<String> mCollapseUrl = new ObservableField<>();
    public final ObservableField<Boolean> isVisiblePlaceAdapter = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleCityAdapter = new ObservableField<>();
    public final ObservableField<Boolean> isHasCitySupport = new ObservableField<>();


    public MainFrgViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
        isVisibleCityAdapter.set(false);
        isVisiblePlaceAdapter.set(false);
        isHasCitySupport.set(false);
    }

    @Override
    public void loadCollapseImage() {
        mCollapseUrl.set("https://www.freevector.com/uploads/vector/preview/12939/FreeVector-Travel-Background.jpg");
       /* AppRepository.getCommonRepo().getCollapseImageUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<String>>() {
                    @Override
                    public void accept(WrapperResponse<String> stringWrapperResponse) throws Exception {
                        mCollapseUrl.set(stringWrapperResponse.getData()+"");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mActNavigator.onErrorMessage("Connect Failed");
                    }
                });*/
    }

    @Override
    public void onSearchClick() {
        mMainFrgNavigator.goToSearchAct();
    }

    @Override
    public void loadFamousPlaceOfCity() {
        AppRepository.getCommonRepo().getCurrentIpAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<IpRes>() {
                    @Override
                    public void accept(IpRes s) throws Exception {
                        AppRepository.getCommonRepo().getCurrentLocation(s.getIp())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<CurrentLocation>() {
                                    @Override
                                    public void accept(CurrentLocation currentLocation) throws Exception {
                                        //save user location
                                        saveUserLocation(currentLocation.getRegionCode());

                                        //load famous place
                                        AppRepository.getPlaceRepo().getFamousPlaceByCountry(currentLocation.getRegionCode())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                                                    @Override
                                                    public void accept(WrapperResponse<List<Place>> placeResponseWrapperResponse) throws Exception {
                                                        //update UI
                                                        if (placeResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListPlaceFamousByCountry.toString())) {
                                                            List<Place> lis = placeResponseWrapperResponse.getData();
                                                            mPlaceofCountryAdapter.setData(lis);
                                                        }

                                                        //show
                                                        isVisiblePlaceAdapter.set(true);
                                                        isHasCitySupport.set(true);

                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) throws Exception {

                                                        mMainFrgNavigator.onErrorMessage("Error When Get Top Place In Your Country!");
                                                    }
                                                });
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        mMainFrgNavigator.onErrorMessage("Error when get current location !");

                                    }
                                });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMainFrgNavigator.onErrorMessage("Error when get current ip !");

                    }
                });


    }



    @Override
    public void loadTopCities() {
        AppRepository.getCityRepo().getTopCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<City>>>() {
                    @Override
                    public void accept(WrapperResponse<List<City>> cityResponseWrapperResponse) throws Exception {
                        //update UI
                        if (cityResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListTopCities.toString())) {
                            List<City> lis = cityResponseWrapperResponse.getData();
                            mCityAdapter.setData(lis);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMainFrgNavigator.onErrorMessage("Load Top Cities Error");

                    }
                });
    }

    @Override
    public void saveUserLocation(String cityName) {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.SHARED_LAST_LOCATION,cityName);
        editor.apply();
        editor.commit();
    }

    @Override
    public void onStart() {
        loadCollapseImage();
        loadFamousPlaceOfCity();
        loadTopCities();
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mMainFrgNavigator = (MainFrgContract.View) viewNavigator;
    }


    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        mPlaceofCountryAdapter.setItemPlaceClick(itemPlaceClick);
    }

    public void setItemCityClick(ItemTopCityViewModel.IItemCityClick itemCityClick) {
        mCityAdapter.setItemCityClick(itemCityClick);
    }

    @Bindable
    public PlaceofCountryAdapter getPlaceAdapter() {
        return mPlaceofCountryAdapter;
    }

    public void setPlaceAdapter(PlaceofCountryAdapter mPlaceofCountryAdapter) {
        this.mPlaceofCountryAdapter = mPlaceofCountryAdapter;
    }

    public void setFrgMainBinding(FragmentMainBinding mFrgMainBinding) {
        this.mFrgMainBinding = mFrgMainBinding;
    }

    @Bindable
    public CityAdapter getCityAdapter() {
        return mCityAdapter;
    }

    public void setCityAdapter(CityAdapter mCityAdapter) {
        this.mCityAdapter = mCityAdapter;
    }
}
