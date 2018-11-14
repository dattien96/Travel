package com.example.dattienbkhn.travel.screen.city.exploreFragment;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.FragmentCityExploreBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.common.IMapExploreEvent;
import com.example.dattienbkhn.travel.screen.common.PlaceAdapterForAllTabMap;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class ExploreFrgViewModel extends BaseObservable implements ExploreFrgContract.ViewModel, IMapExploreEvent, PlaceAdapterForAllTabMap.IAdapterCallBack {
    private City mCurrentCity;
    private ExploreFrgContract.View mNavigator;
    private DialogManager mDialogManager;
    private PlaceAdapterForAllTabMap mPlaceAdapter;
    private FragmentCityExploreBinding binding;
    private boolean isDrawItemBorder;
    private String mStringTypeForRequest;

    public final ObservableField<Boolean> mMapVisible = new ObservableField<>();
    public final ObservableField<Integer> mCurrentRecycleItem = new ObservableField<>();
    public final ObservableField<List<Place>> places = new ObservableField<>();


    public ExploreFrgViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        mStringTypeForRequest = "";
        mCurrentRecycleItem.set(-1);
        isDrawItemBorder = false;
        mMapVisible.set(false);
        mPlaceAdapter.setData(new ArrayList<Place>());
        loadUserFilterSetting();
        loadPlaceByType(mCurrentCity.getCityId());

    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (ExploreFrgContract.View) viewNavigator;
    }

    @Override
    public void loadPlaceByType(int idCity) {
        Log.e("map", "" + mStringTypeForRequest);
        AppRepository.getPlaceRepo().getAllPlace(idCity, mStringTypeForRequest)
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

                        if (placeResponseWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListPlace.toString())) {
                            places.set(placeResponseWrapperResponse.getData());
                            mPlaceAdapter.setData(placeResponseWrapperResponse.getData());

                        }
                        mMapVisible.set(true);
                        mDialogManager.dismissProgressDialog();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load play place");
                        mDialogManager.dismissProgressDialog();
                        mMapVisible.set(true);
                    }
                });


    }

    @Override
    public void loadUserFilterSetting() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();

        boolean isSeeCheck = prefs.getBoolean(Constant.SHARED_PLACE_SEE_TYPE, false);
        boolean isFoodiesCheck = prefs.getBoolean(Constant.SHARED_PLACE_FOODIES_TYPE, false);
        boolean isDrinkCheck = prefs.getBoolean(Constant.SHARED_PLACE_DRINK_TYPE, false);
        boolean isStayCheck = prefs.getBoolean(Constant.SHARED_PLACE_STAY_TYPE, false);

        if (!isSeeCheck && !isFoodiesCheck && !isDrinkCheck && !isStayCheck) {
            //do nothing, default load see place
            mStringTypeForRequest += Constant.SHARED_PLACE_SEE_TYPE;

        } else {
            if (isSeeCheck) {
                mStringTypeForRequest += Constant.SHARED_PLACE_SEE_TYPE;
                mStringTypeForRequest += " ";
            }
            if (isFoodiesCheck) {
                mStringTypeForRequest += Constant.SHARED_PLACE_FOODIES_TYPE;
                mStringTypeForRequest += " ";
            }
            if (isDrinkCheck) {
                mStringTypeForRequest += Constant.SHARED_PLACE_DRINK_TYPE;
                mStringTypeForRequest += " ";
            }
            if (isStayCheck) {
                mStringTypeForRequest += Constant.SHARED_PLACE_STAY_TYPE;
                mStringTypeForRequest += " ";
            }
        }
    }

    @Override
    public void onFilterClick() {
        mNavigator.goToFilterAct();
    }


    @Bindable
    public City getCurrentCity() {
        return mCurrentCity;
    }

    public void setCurrentCity(City mCurrentCity) {
        this.mCurrentCity = mCurrentCity;
    }

    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        mPlaceAdapter.setItemPlaceClick(itemPlaceClick);
    }

    @Bindable
    public PlaceAdapterForAllTabMap getPlaceAdapter() {
        return mPlaceAdapter;
    }

    public void setPlaceAdapter(PlaceAdapterForAllTabMap mPlaceAdapter) {
        this.mPlaceAdapter = mPlaceAdapter;
        this.mPlaceAdapter.setAdapterCallBack(this);
        notifyPropertyChanged(BR.placeAdapter);
    }

    public void setBinding(FragmentCityExploreBinding binding) {
        this.binding = binding;
        binding.rcPlayPlace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                if (mCurrentRecycleItem.get() != -1 && layoutManager.findViewByPosition(mCurrentRecycleItem.get()) != null) {
                    layoutManager.findViewByPosition(mCurrentRecycleItem.get())
                            .setBackgroundResource(R.drawable.shape_view_border_none);
                }

                if (isDrawItemBorder
                        && layoutManager.findViewByPosition(mCurrentRecycleItem.get()) != null) {

                    layoutManager.findViewByPosition(mCurrentRecycleItem.get()).setBackgroundResource(R.drawable.shape_view_border);
                    Log.e("draw","on scroll");
                }
            }
        });

    }

    @Override
    public void onPlaceClick(int pos) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rcPlayPlace.getLayoutManager());
        if (mCurrentRecycleItem.get() != -1 && layoutManager.findViewByPosition(mCurrentRecycleItem.get()) != null) {
            layoutManager.findViewByPosition(mCurrentRecycleItem.get())
                    .setBackgroundResource(R.drawable.shape_view_border_none);
        }
        mCurrentRecycleItem.set(pos);
        isDrawItemBorder = true;
        binding.rcPlayPlace.smoothScrollToPosition(mCurrentRecycleItem.get());

        if (layoutManager.findViewByPosition(pos) != null ) {
            layoutManager.findViewByPosition(pos).setBackgroundResource(R.drawable.shape_view_border);
            Log.e("draw","draw direct");
        }



    }

    @Override
    public void onMapClick() {
        deleteBorderItem();
    }

    public void deleteBorderItem() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rcPlayPlace.getLayoutManager());
        if (mCurrentRecycleItem.get() != -1 && layoutManager.findViewByPosition(mCurrentRecycleItem.get()) != null) {
            layoutManager.findViewByPosition(mCurrentRecycleItem.get())
                    .setBackgroundResource(R.drawable.shape_view_border_none);
        }
        isDrawItemBorder = false;
    }

    @Override
    public void onDrawComplete() {
        isDrawItemBorder = false;
    }


}
