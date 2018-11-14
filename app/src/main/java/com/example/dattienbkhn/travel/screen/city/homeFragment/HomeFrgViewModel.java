package com.example.dattienbkhn.travel.screen.city.homeFragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.databinding.FragmentCityHomeBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.weather.currentweather.Weather;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.common.SlideImageAdapter;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class HomeFrgViewModel extends BaseObservable implements HomeFrgContract.ViewModel {
    private DialogManager mDialogManager;
    private HomeFrgContract.View mNavigator;
    private FragmentCityHomeBinding binding;
    private PlaceAdapter mPlayPlaceAdapter;
    private PlaceAdapter mStayPlaceAdapter;
    private PlaceAdapter mEnterPlaceAdapter;
    private PlaceAdapter mEatDrinklaceAdapter;
    private SlideImageAdapter mImageAdapter;
    private City mCurrenCity;
    //weatherobj contain list weather
    private WeatherObj mCurrentWeatherObj;
    //however,in this screen we use one weather main of list to show
    private Weather mCurrentWeather;

    public final ObservableField<String> mPosCurSlideTab = new ObservableField<>();
    public HomeFrgViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {

        loadImageSlideOfCity(mCurrenCity.getCityId());
        loadFamousPlayPlace(mCurrenCity.getCityId());
        loadFamousEatDrinkPlace(mCurrenCity.getCityId());
        loadFamousStayPlace(mCurrenCity.getCityId());
        loadFamousEnterPlace(mCurrenCity.getCityId());
        loadWeatherCurrent(mCurrenCity.getCityName());
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (HomeFrgContract.View) viewNavigator;
    }

    public void setBinding(FragmentCityHomeBinding binding) {
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
                mPosCurSlideTab.set((position+1)+" / "+mImageAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        mPlayPlaceAdapter.setItemPlaceClick(itemPlaceClick);
        mEatDrinklaceAdapter.setItemPlaceClick(itemPlaceClick);
        mEnterPlaceAdapter.setItemPlaceClick(itemPlaceClick);
        mStayPlaceAdapter.setItemPlaceClick(itemPlaceClick);
    }

    @Bindable
    public PlaceAdapter getPlayPlaceAdapter() {
        return mPlayPlaceAdapter;
    }

    public void setPlayPlaceAdapter(PlaceAdapter mPlayPlaceAdapter) {
        this.mPlayPlaceAdapter = mPlayPlaceAdapter;
    }

    @Bindable
    public PlaceAdapter getStayPlaceAdapter() {
        return mStayPlaceAdapter;
    }

    public void setStayPlaceAdapter(PlaceAdapter mStayPlaceAdapter) {
        this.mStayPlaceAdapter = mStayPlaceAdapter;
    }

    @Bindable
    public PlaceAdapter getEnterPlaceAdapter() {
        return mEnterPlaceAdapter;
    }

    public void setEnterPlaceAdapter(PlaceAdapter mEnterPlaceAdapter) {
        this.mEnterPlaceAdapter = mEnterPlaceAdapter;
    }

    @Bindable
    public PlaceAdapter getEatDrinklaceAdapter() {
        return mEatDrinklaceAdapter;
    }

    public void setEatDrinklaceAdapter(PlaceAdapter mEatDrinklaceAdapter) {
        this.mEatDrinklaceAdapter = mEatDrinklaceAdapter;
    }

    @Bindable
    public Weather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(Weather mCurrentWeather) {
        this.mCurrentWeather = mCurrentWeather;
        notifyPropertyChanged(BR.currentWeather);
    }

    @Bindable
    public WeatherObj getCurrentWeatherObj() {
        return mCurrentWeatherObj;
    }

    public void setCurrentWeatherObj(WeatherObj mCurrentWeatherObj) {
        this.mCurrentWeatherObj = mCurrentWeatherObj;
        notifyPropertyChanged(BR.currentWeatherObj);
    }

    @Bindable
    public SlideImageAdapter getImageAdapter() {
        return mImageAdapter;
    }

    public void setImageAdapter(SlideImageAdapter mImageAdapter) {
        this.mImageAdapter = mImageAdapter;
        notifyPropertyChanged(BR.imageAdapter);
    }

    @Override
    public void loadImageSlideOfCity(int idCity) {
        AppRepository.getImageRepo().getImagesOfCity(idCity)
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
                        if (imageResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ImageAlbumOfCity.toString())) {
                            mImageAdapter.setData(imageResponseWrapperResponse.getData());
                            mPosCurSlideTab.set((1)+" / "+mImageAdapter.getCount());

                        }

                        mDialogManager.dismissProgressDialog();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load images of city");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void loadFamousPlayPlace(int idCity) {
        AppRepository.getPlaceRepo().getFamousPlace(idCity, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Place>> placeResponseWrapperResponse) throws Exception {

                        if (placeResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListFamousPlayPlace.toString())) {
                            mPlayPlaceAdapter.setData(placeResponseWrapperResponse.getData());
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error When Load Famous Play Place");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void loadFamousStayPlace(int idCity) {
        AppRepository.getPlaceRepo().getFamousPlace(idCity, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Place>> placeResponseWrapperResponse) throws Exception {
                        if (placeResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListFamousStayPlace.toString())) {
                            mStayPlaceAdapter.setData(placeResponseWrapperResponse.getData());
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error When Load Famous Stay Place");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void loadFamousEatDrinkPlace(int idCity) {
        AppRepository.getPlaceRepo().getFamousPlace(idCity, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Place>> placeResponseWrapperResponse) throws Exception {
                        if (placeResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListFamousEatDrinkPlace.toString())) {
                            mEatDrinklaceAdapter.setData(placeResponseWrapperResponse.getData());
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error When Load Famous Eat Place");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void loadFamousEnterPlace(int idCity) {
        AppRepository.getPlaceRepo().getFamousPlace(idCity, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Place>> placeResponseWrapperResponse) throws Exception {
                        if (placeResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListFamousEnterPlace.toString())) {
                            mEnterPlaceAdapter.setData(placeResponseWrapperResponse.getData());
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mNavigator.onErrorMessage("Error When Load Famous Entertainment Place");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void loadWeatherCurrent(String cityName) {

        AppRepository.getCommonRepo().getWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherObj>() {
                    @Override
                    public void accept(WeatherObj weatherObj) throws Exception {
                        setCurrentWeatherObj(weatherObj);
                        setCurrentWeather(weatherObj.getWeather().get(0));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                       mNavigator.onErrorMessage("Error When Load Current Weather!");
                        Log.e("wea",""+throwable.getMessage());
                    }
                });
    }

    @Override
    public void onSeeMoreClick() {

        mNavigator.goToExploreTab();
    }

    @Override
    public void onWeatherClick() {
        mNavigator.goToWeatherAct(mCurrentWeatherObj);
    }

    @Bindable
    public City getCurrenCity() {
        return mCurrenCity;
    }

    public void setCurrenCity(City mCurrenCity) {
        this.mCurrenCity = mCurrenCity;
    }
}
