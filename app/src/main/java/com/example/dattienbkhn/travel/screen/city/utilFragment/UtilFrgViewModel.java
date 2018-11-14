package com.example.dattienbkhn.travel.screen.city.utilFragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.location.Location;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.model.map.nearby.NearByWrapp;
import com.example.dattienbkhn.travel.model.map.nearby.NearyByPlace;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.common.IMapUtilEvent;
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

public class UtilFrgViewModel extends BaseObservable implements UtilFrgContract.ViewModel,
        IMapUtilEvent, com.google.android.gms.location.LocationListener {
    private City mCurrentCity;
    private UtilFrgContract.View mNavigator;
    private DialogManager mDialogManager;
    private Location mCurrentLocation;


    public ObservableField<List<NearyByPlace>> mNearyByPlaces = new ObservableField<>();
    public ObservableField<NearyByPlace> mCurNearyByPlaces = new ObservableField<>();
    public ObservableField<List<Integer>> mTypeIcon = new ObservableField<>();

    public final ObservableField<Boolean> isPoliceType = new ObservableField<>();
    public final ObservableField<Boolean> isAtmType = new ObservableField<>();
    public final ObservableField<Boolean> isGasStationType = new ObservableField<>();
    public final ObservableField<Boolean> isHospitalType = new ObservableField<>();
    public final ObservableField<Boolean> isHotelType = new ObservableField<>();
    public final ObservableField<Boolean> isResType = new ObservableField<>();

    public final ObservableField<Boolean> isFirstFind = new ObservableField<>();
    public final ObservableField<Boolean> isVisiblePlaceInfor = new ObservableField<>();
    public final ObservableField<Boolean> isLocationTurnOn = new ObservableField<>();

    public UtilFrgViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        isFirstFind.set(true);
        isAtmType.set(false);
        isGasStationType.set(false);
        isHospitalType.set(false);
        isHotelType.set(false);
        isResType.set(true);
        isPoliceType.set(false);
        isVisiblePlaceInfor.set(false);

    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (UtilFrgContract.View) viewNavigator;
    }


    @Bindable
    public City getCurrentCity() {
        return mCurrentCity;
    }

    public void setCurrentCity(City mCurrentCity) {
        this.mCurrentCity = mCurrentCity;
    }

    @Override
    public void onPlaceClick(int placePos) {
        mCurNearyByPlaces.set(mNearyByPlaces.get().get(placePos));
        isVisiblePlaceInfor.set(true);
    }

    @Override
    public void onMapClick() {
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void loadNearByPlace(final String type, String location) {
        AppRepository.getCommonRepo().getNearbyPlaces(type, location, 3000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<NearByWrapp>() {
                    @Override
                    public void accept(NearByWrapp nearByWrapp) throws Exception {
                        if (nearByWrapp.getResults().size() == 0 || nearByWrapp.getResults() == null) {
                            mNearyByPlaces.set(new ArrayList<NearyByPlace>());
                            mNavigator.onErrorMessage("Cannot find place type "+type+" neary by your location !");
                        }
                        else {
                            mNearyByPlaces.set(nearByWrapp.getResults());
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load near place !");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void onTypeRestaurantClick() {
        loadNearByPlace("restaurant", mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
        List<Integer> mapIcons = new ArrayList<>();
        mapIcons.add(R.drawable.map_pin_eat_default);
        mapIcons.add(R.drawable.map_pin_eat_default_active);
        mTypeIcon.set(mapIcons);
        isAtmType.set(false);
        isGasStationType.set(false);
        isHospitalType.set(false);
        isHotelType.set(false);
        isResType.set(true);
        isPoliceType.set(false);
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void onPlaceTypeHotelClick() {
        loadNearByPlace("hotel", mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
        List<Integer> mapIcons = new ArrayList<>();
        mapIcons.add(R.drawable.map_pin_hostel);
        mapIcons.add(R.drawable.map_pin_hostel_active);
        mTypeIcon.set(mapIcons);
        isAtmType.set(false);
        isGasStationType.set(false);
        isHospitalType.set(false);
        isHotelType.set(true);
        isResType.set(false);
        isPoliceType.set(false);
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void onPlaceTypeHospitalClick() {
        loadNearByPlace("hospital", mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
        List<Integer> mapIcons = new ArrayList<>();
        mapIcons.add(R.drawable.map_pin_hospital);
        mapIcons.add(R.drawable.map_pin_hospital_active);
        mTypeIcon.set(mapIcons);
        isAtmType.set(false);
        isGasStationType.set(false);
        isHospitalType.set(true);
        isHotelType.set(false);
        isResType.set(false);
        isPoliceType.set(false);
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void onPlaceTypeAtmClick() {
        loadNearByPlace("atm", mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
        List<Integer> mapIcons = new ArrayList<>();
        mapIcons.add(R.drawable.map_pin_courthouse);
        mapIcons.add(R.drawable.map_pin_courthouse_active);
        mTypeIcon.set(mapIcons);
        isAtmType.set(true);
        isGasStationType.set(false);
        isHospitalType.set(false);
        isHotelType.set(false);
        isResType.set(false);
        isPoliceType.set(false);
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void onPlaceTypeGasStationClick() {
        loadNearByPlace("gas_station", mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
        List<Integer> mapIcons = new ArrayList<>();
        mapIcons.add(R.drawable.map_pin_gas_station);
        mapIcons.add(R.drawable.map_pin_gas_station_active);
        mTypeIcon.set(mapIcons);
        isAtmType.set(false);
        isGasStationType.set(true);
        isHospitalType.set(false);
        isHotelType.set(false);
        isResType.set(false);
        isPoliceType.set(false);
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void onPlaceTypePoliceClick() {
        loadNearByPlace("police", mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
        List<Integer> mapIcons = new ArrayList<>();
        mapIcons.add(R.drawable.map_pin_police_station);
        mapIcons.add(R.drawable.map_pin_police_station_active);
        mTypeIcon.set(mapIcons);
        isAtmType.set(false);
        isGasStationType.set(false);
        isHospitalType.set(false);
        isHotelType.set(false);
        isResType.set(false);
        isPoliceType.set(true);
        isVisiblePlaceInfor.set(false);
    }

    @Override
    public void onPlaceInforClick() {
        Place place = new Place();
        place.setPlaceName(mCurNearyByPlaces.get().getName());
        place.setLatitude(mCurNearyByPlaces.get().getGeometry().getLocation().getLat());
        place.setLongitude(mCurNearyByPlaces.get().getGeometry().getLocation().getLng());

        PlaceInfor placeInfor = new PlaceInfor();
        placeInfor.setName(mCurNearyByPlaces.get().getVicinity());

        //mNavigator.goToMapAct(place,placeInfor);
    }

    @Override
    public void updateStateLocation(boolean state) {
        isLocationTurnOn.set(state);
    }

    @Override
    public void onTurnOnLocationClick() {
        mNavigator.goToAppSetting();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (isFirstFind.get()) {
            setCurrentLocation(location);
            List<Integer> mapIcons = new ArrayList<>();
            mapIcons.add(R.drawable.map_pin_eat_default);
            mapIcons.add(R.drawable.map_pin_eat_default_active);
            mTypeIcon.set(mapIcons);
            loadNearByPlace("restaurant", location.getLatitude() + "," + location.getLongitude());
            isFirstFind.set(false);
        }
    }

    @Bindable
    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    public void setCurrentLocation(Location mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
        notifyPropertyChanged(BR.currentLocation);
    }
}
