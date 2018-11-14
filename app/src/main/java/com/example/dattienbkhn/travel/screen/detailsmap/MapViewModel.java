package com.example.dattienbkhn.travel.screen.detailsmap;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.Html;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.TravelApplication;
import com.example.dattienbkhn.travel.databinding.ActivityMapBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.network.message.map.DirectionResponse;
import com.example.dattienbkhn.travel.network.message.map.direction.Leg;
import com.example.dattienbkhn.travel.network.message.map.direction.Step;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.common.IMapDetailsEvent;
import com.example.dattienbkhn.travel.screen.common.IMapStepDetailsEvent;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.PolyUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 03/03/2018.
 */

public class MapViewModel extends BaseObservable implements MapContract.ViewModel, IMapDetailsEvent,
        com.google.android.gms.location.LocationListener, IMapStepDetailsEvent {
    private MapContract.View mNavigator;
    private DialogManager mDialogManager;
    private ActivityMapBinding binding;
    private Place mCurrentPlace;
    private PlaceInfor mPlaceInfor;
    private DirectionAdapter mDirectionAdapter;
    private DirectionResponse mDirectionResponse;
    private Location mCurrentLocation;

    //var for generate map
    private List<PolylineOptions> mLineOptions;
    private List<Leg> mLegDirect;

    //var for details step map
    private Polyline mCurStepPolyLine;
    private PolylineOptions mCurLineOptions;
    private Leg mCurLegDirect;
    //var save all latlgs of  polys direct
    private HashMap<Integer, List<LatLng>> mapLatlngPoly;
    //var save latlng of cur poly on road not one step -> get this var from hashMap
    private List<LatLng> mCurLatlngsPoly;
    //this var save latlngf of cur poly but not on road,it on details step <it is contained in mCurLatlngsPoly>
    // mCurStepLatlngsPoly + mCurStepLatlngsPoly +mCurStepLatlngsPoly+.... = mCurLatlngsPoly
    private List<List<LatLng>> mCurStepLatlngsPoly;
    //when map already we can draw step details
    //in first run,in location change,this var is false,it cannot draw because map is null
    //then alter map ready,it call back viewmodel via event,and we set this var is true
    //and step will be draw automatic in locationChange because this var is true
    private boolean isStepDetailsMapReady;
    //this var can use when map ready and callback to viewmodel <in callback map event method>
    private GoogleMap googleStepDetailsMap;

    private int currentRoad;
    private boolean isDirect;

    public final ObservableField<Boolean> isLocationTurnOn = new ObservableField<>();
    public final ObservableField<String> mCurrentDistance = new ObservableField<>();
    public final ObservableField<String> mDurationTime = new ObservableField<>();
    public final ObservableField<String> mCurrentLocationString = new ObservableField<>();
    public final ObservableField<String> mCurrentStepName = new ObservableField<>();
    public final ObservableField<String> mCurrentStepDescription = new ObservableField<>();
    public final ObservableField<Boolean> isFastRoad = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleDirection = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleListStep = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleDetailsStep = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleLocationContain = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleMap = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleMapSteps = new ObservableField<>();




    public MapViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        isStepDetailsMapReady = false;
        isDirect = false;
        isFastRoad.set(true);
        isVisibleDirection.set(false);
        isVisibleListStep.set(false);
        isVisibleDetailsStep.set(false);
        isVisibleLocationContain.set(false);
        isVisibleMapSteps.set(false);
        isVisibleMap.set(true);



    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (MapContract.View) viewNavigator;
    }

    @Bindable
    public Place getCurrentPlace() {
        return mCurrentPlace;
    }

    public void setCurrentPlace(Place mCurrentPlace) {
        this.mCurrentPlace = mCurrentPlace;
    }

    @Bindable
    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    public void setCurrentLocation(Location mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
        notifyPropertyChanged(BR.currentLocation);
    }

    @Bindable
    public DirectionAdapter getDirectionAdapter() {
        return mDirectionAdapter;
    }

    public void setDirectionAdapter(DirectionAdapter mDirectionAdapter) {
        this.mDirectionAdapter = mDirectionAdapter;
        notifyPropertyChanged(BR.directionAdapter);
    }

    @Override
    public void onMapClick() {

        //isVisibleDirection.set(false);

    }


    @Override
    public void onPolylineClick(int roadPos) {
        isVisibleDirection.set(true);

        currentRoad = roadPos;
        if (roadPos == 0) {
            isFastRoad.set(true);
        } else {
            isFastRoad.set(false);
        }

        mDurationTime.set(mDirectionResponse.getRoutes().get(roadPos)
                .getLegs().get(0).getDuration().getText());
        mCurrentDistance.set(" - " + mDirectionResponse.getRoutes().get(roadPos)
                .getLegs().get(0).getDistance().getText());

        //change list step when user reclick polyline
        mDirectionAdapter.setData(mDirectionResponse.getRoutes().get(currentRoad)
                .getLegs().get(0).getSteps());

        //reset var for details step map when user click
        setCurLatlngsPoly(mapLatlngPoly.get(roadPos));
        setCurLegDirect(mLegDirect.get(roadPos));
        setCurLineOptions(mLineOptions.get(roadPos));
        List<List<LatLng>> latlngsOfStepOnCheckRoad = new ArrayList<>();
        for (Step step : mCurLegDirect.getSteps()) {
            latlngsOfStepOnCheckRoad.add(PolyUtil.decode(step.getPolyline().getPoints()));
        }
        setCurStepLatlngsPoly(latlngsOfStepOnCheckRoad);

    }


    @Override
    public void onLocationChanged(Location location) {
        if (!isDirect) {
            directPolyline(location);
            loadCurrentLocationName(location);
            setCurrentLocation(location);
        }

        if (getCurStepLatlngsPoly() != null && isVisibleDetailsStep.get()) {
            if (!PolyUtil.isLocationOnPath(new LatLng(location.getLatitude(), location.getLongitude())
                    , mCurLatlngsPoly, true, 5)) {
                //if use is not on road or near road -> noti
                mCurrentStepName.set("You are far from this route !");
                mCurrentStepDescription.set("Please follow the instructions");
            } else {
                //case user on road or near road,then we find specifically step on that road
                for (int i = 0; i < mCurStepLatlngsPoly.size(); i++) {
                    if (PolyUtil.isLocationOnPath(new LatLng(location.getLatitude(), location.getLongitude())
                            , mCurStepLatlngsPoly.get(i), true, 5)) {
                        //draw step
                        drawStepDetails(i);

                        //update text UI
                        String s1 = "" + Html.fromHtml(mCurLegDirect.getSteps().get(i).getHtmlInstructions() + "");
                        String s2 = "";
                        String s3 = "";
                        for (int j = 0; j < s1.length(); j++) {
                            if (s1.charAt(j) == '\n') {
                                s2 += s1.substring(0, j);
                                s3 += s1.substring(j + 2);
                                break;
                            }
                        }
                        mCurrentStepName.set(s2);
                        mCurrentStepDescription.set(s3);
                        break;
                    }
                }
            }

        }

    }

    private void drawStepDetails(int stepPos) {
        if (isStepDetailsMapReady) {
            //remove pre step if visible
            if (mCurStepPolyLine != null) {
                mCurStepPolyLine.setColor(TravelApplication.getContext().getResources().getColor(android.R.color.darker_gray));
            }
            PolylineOptions lineOptions = new PolylineOptions();
            lineOptions.addAll(mCurStepLatlngsPoly.get(stepPos));
            lineOptions.width(18);
            lineOptions.color(TravelApplication.getContext().getResources().getColor(R.color.pink_FE007E));
            mCurStepPolyLine = googleStepDetailsMap.addPolyline(lineOptions);
        }
    }

    @Override
    public void directPolyline(final Location location) {
        AppRepository.getCommonRepo().getDirection(
                location.getLatitude() + "," + location.getLongitude(),
                mCurrentPlace.getLatitude() + "," + mCurrentPlace.getLongitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DirectionResponse>() {
                    @Override
                    public void accept(DirectionResponse directionResponse) throws Exception {
                        mDirectionResponse = directionResponse;
                        if (directionResponse.getRoutes().size() > 0) {
                            HashMap<Integer, List<LatLng>> latLngsOfPolylineTmp = new HashMap<>();
                            List<LatLng> latLngs = null;
                            PolylineOptions lineOptions = null;
                            List<PolylineOptions> listTmp = new ArrayList<>();
                            List<LatLng> polylineLatlng = new ArrayList<>();
                            List<Leg> legs = new ArrayList<>();
                            for (int j = 0; j < directionResponse.getRoutes().size(); j++) {
                                lineOptions = new PolylineOptions();

                                latLngs = PolyUtil.decode(directionResponse.getRoutes().get(j).getOverviewPolyline().getPoints());
                                lineOptions.addAll(latLngs);
                                latLngsOfPolylineTmp.put(j, latLngs);

                                lineOptions.width(18);
                                lineOptions.color(TravelApplication.getContext().getResources().getColor(android.R.color.darker_gray));
                                lineOptions.geodesic(true);
                                listTmp.add(lineOptions);

                                legs.add(directionResponse.getRoutes().get(j).getLegs().get(0));

                                //repair list step : clear data and addAll data after modify
                                List<Step> tmp = listStepDataRepair(directionResponse.getRoutes().get(j).getLegs().get(0).getSteps());
                                directionResponse.getRoutes().get(j).getLegs().get(0).setSteps(tmp);

                            }

                            //set var for generate direct map
                            setLineOptions(listTmp);
                            setMapLatlngPoly(latLngsOfPolylineTmp);
                            setLegDirect(legs);

                            //set var for cur step map <default is fast road , change alter user click poly>
                            setCurLatlngsPoly(latLngsOfPolylineTmp.get(0));
                            setCurLegDirect(legs.get(0));
                            setCurLineOptions(listTmp.get(0));
                            List<List<LatLng>> latlngsOfStepOnFastRoad = new ArrayList<>();
                            for (Step step : legs.get(0).getSteps()) {
                                latlngsOfStepOnFastRoad.add(PolyUtil.decode(step.getPolyline().getPoints()));
                            }
                            setCurStepLatlngsPoly(latlngsOfStepOnFastRoad);


                            isDirect = true;
                            mCurrentDistance.set(" - " + directionResponse.getRoutes().get(0).getLegs().get(0).getDistance().getText());
                            mDurationTime.set(directionResponse.getRoutes().get(0).getLegs().get(0).getDuration().getText());
                            currentRoad = 0;
                            mDirectionAdapter.setData(mDirectionResponse.getRoutes().get(currentRoad)
                                    .getLegs().get(0).getSteps());
                            isVisibleDirection.set(true);
                        } else {
                            isVisibleDirection.set(false);
                            isDirect = true;
                            mNavigator.onErrorMessage("Can not find road !");
                        }

                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        mNavigator.onErrorMessage("Error When Direct !");
                    }
                });
    }

    @Override
    public void loadCurrentLocationName(Location location) {
        Geocoder geocoder = new Geocoder(TravelApplication.getContext(), Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                Log.e("getNameOfCurLocation", "getNameOfCurLocation: " + returnedAddress.toString());
                StringBuilder strReturnedAddress = new StringBuilder("");

                //obj adress se chua nhieu line,moi line la cac chi tiet ve dia diem
               /* for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }*/

                //hien tai chi lay ra 1 dia chi chi tieets nhat
                strReturnedAddress.append(returnedAddress.getAddressLine(0));

                mCurrentLocationString.set(strReturnedAddress.toString());
                isVisibleLocationContain.set(true);
            }
        } catch (IOException e) {

        }

    }

    @Override
    public void startDetailsDirection() {
        isVisibleDirection.set(false);
        isVisibleListStep.set(false);
        isVisibleLocationContain.set(false);
        isVisibleMap.set(false);
        isVisibleMapSteps.set(true);
        isVisibleDetailsStep.set(true);

        mCurrentStepName.set("");
        mCurrentStepDescription.set("");
    }

    @Override
    public void onShowListStepClick() {
        if (isVisibleListStep.get()) {
            isVisibleListStep.set(false);
            binding.imgStepList.setImageResource(R.drawable.icons8_menu_24);
        } else {
            isVisibleListStep.set(true);
            binding.imgStepList.setImageResource(R.drawable.ab_map_ic);
        }
    }

    @Override
    public void onCameraCapture() {

        mNavigator.goToCameraAct();
    }

    @Override
    public void onNextDirectStep() {

    }

    @Override
    public void onCloseDetailsStep() {
        isVisibleDirection.set(true);
        isVisibleListStep.set(false);
        isVisibleDetailsStep.set(false);
        isVisibleLocationContain.set(true);
        isVisibleMapSteps.set(false);
        isVisibleMap.set(true);


    }

    @Override
    public void updateStateLocation(boolean isTurnOn) {
        isLocationTurnOn.set(isTurnOn);
    }

    @Override
    public void onTurnOnLocationClick() {
        mNavigator.goToAppSetting();
    }

    @Bindable
    public List<PolylineOptions> getLineOptions() {
        return mLineOptions;
    }

    public void setLineOptions(List<PolylineOptions> mLineOptions) {
        this.mLineOptions = mLineOptions;
        notifyPropertyChanged(BR.lineOptions);
    }

    @Bindable
    public List<Leg> getLegDirect() {
        return mLegDirect;
    }

    public void setLegDirect(List<Leg> mLegDirect) {
        this.mLegDirect = mLegDirect;
        notifyPropertyChanged(BR.legDirect);
    }

    @Bindable
    public PlaceInfor getPlaceInfor() {
        return mPlaceInfor;
    }

    public void setPlaceInfor(PlaceInfor mPlaceInfor) {
        this.mPlaceInfor = mPlaceInfor;
    }

    public void setBinding(ActivityMapBinding binding) {
        this.binding = binding;
    }

    public HashMap<Integer, List<LatLng>> getMapLatlngPoly() {
        return mapLatlngPoly;
    }

    public void setMapLatlngPoly(HashMap<Integer, List<LatLng>> mapLatlngPoly) {
        this.mapLatlngPoly = mapLatlngPoly;
    }

    @Bindable
    public PolylineOptions getCurLineOptions() {
        return mCurLineOptions;
    }

    public void setCurLineOptions(PolylineOptions mCurLineOptions) {
        this.mCurLineOptions = mCurLineOptions;
        notifyPropertyChanged(BR.curLineOptions);
    }

    @Bindable
    public Leg getCurLegDirect() {
        return mCurLegDirect;
    }

    public void setCurLegDirect(Leg mCurLegDirect) {
        this.mCurLegDirect = mCurLegDirect;
        notifyPropertyChanged(BR.curLegDirect);
    }

    @Bindable
    public List<LatLng> getCurLatlngsPoly() {
        return mCurLatlngsPoly;
    }

    public void setCurLatlngsPoly(List<LatLng> mCurLatlngsPoly) {
        this.mCurLatlngsPoly = mCurLatlngsPoly;
        notifyPropertyChanged(BR.curLatlngsPoly);
    }

    @Bindable
    public List<List<LatLng>> getCurStepLatlngsPoly() {
        return mCurStepLatlngsPoly;
    }

    public void setCurStepLatlngsPoly(List<List<LatLng>> mCurStepLatlngsPoly) {
        this.mCurStepLatlngsPoly = mCurStepLatlngsPoly;
        notifyPropertyChanged(BR.curStepLatlngsPoly);
    }

    @Bindable
    public Polyline getCurStepPolyLine() {
        return mCurStepPolyLine;
    }

    public void setCurStepPolyLine(Polyline mCurPolyLine) {
        this.mCurStepPolyLine = mCurPolyLine;
        notifyPropertyChanged(BR.curStepPolyLine);
    }

    /**
     * Because data get from google maybe doesn't have instruction string
     * we remove it
     */
    public List<Step> listStepDataRepair(List<Step> steps) {
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getHtmlInstructions() == null ||
                    !(steps.get(i).getHtmlInstructions().length() > 0)) {
                tmp.add(i);
            }
        }

        for (int i = 0; i < tmp.size(); i++) {
            steps.remove(tmp.get(i));
        }
        return steps;
    }

    @Override
    public void onStepDetailsMapReady(GoogleMap googleStepDetailsMap) {
        //draw step detail when map already
        isStepDetailsMapReady = true;
        this.googleStepDetailsMap = googleStepDetailsMap;
    }
}
