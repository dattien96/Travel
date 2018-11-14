package com.example.dattienbkhn.travel.utils.binding;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.TravelApplication;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.map.nearby.NearyByPlace;
import com.example.dattienbkhn.travel.network.message.map.direction.Leg;
import com.example.dattienbkhn.travel.network.message.map.direction.Step;
import com.example.dattienbkhn.travel.screen.common.IMapDetailsEvent;
import com.example.dattienbkhn.travel.screen.common.IMapExploreEvent;
import com.example.dattienbkhn.travel.screen.common.IMapStepDetailsEvent;
import com.example.dattienbkhn.travel.screen.common.IMapUtilEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dattienbkhn on 31/01/2018.
 * Binding với 1 attr : @BindingAdapter({"imageUrl"})
 * hoặc @BindingAdapter("imageUrl")  cặp ngoặc nhọn dùng khi có nhiều attr
 * Binding với 2 attr : @BindingAdapter({"imageUrl","errorimg"})
 * Với custom adapter set ảnh cho ImageView này,thì bắt buộc ta phải set cả
 * 2 attr cho nó thì nó mới chạy : 1 là attr cho success img và 1 attr cho error img
 */

public class CustomBindingAdapter {

    @BindingAdapter({"recyclerAdapter"})
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
                                                 RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);

       /* AnimationSet set = new AnimationSet(true);

        // Fade in animation
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(100);
        fadeIn.setFillAfter(true);
        set.addAnimation(fadeIn);

        // Slide up animation from bottom of screen
        Animation slideUp = new TranslateAnimation(0, 0, 100, 0);
        slideUp.setInterpolator(new DecelerateInterpolator(4.f));
        slideUp.setDuration(800);
        set.addAnimation(slideUp);

        // Set up the animation controller              (second parameter is the delay)
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        recyclerView.setLayoutAnimation(controller);*/
    }

    /*@BindingAdapter({"layoutManager"})
    public static void setLayoutManager(RecyclerView recyclerView,
                                        LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }*/

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (url != null && url.length() > 0) {
        Picasso.with(view.getContext()).load(url).into(view);
        }
    }

    @BindingAdapter({"imageResource"})
    public static void loadImageResource(ImageView view, int res) {
        view.setImageResource(res);
    }

    @BindingAdapter({"imageUrl", "progressBar"})
    public static void loadImageWithProgressBar(final ImageView view, String url, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        if (url != null && url.length() > 0) {

            Picasso.with(view.getContext()).load(url).into(view, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progressBar.setVisibility(View.GONE);
                    view.setImageResource(R.drawable.no_image);
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            view.setImageResource(R.drawable.no_image);
        }
    }

    /**
     * use div 2 event scroll of recycleView and NestedScroll
     *
     * @param view
     * @param b
     */
    @BindingAdapter({"scroll"})
    public static void setScroll(RecyclerView view, boolean b) {
        view.setNestedScrollingEnabled(b);
    }

    @BindingAdapter({"imgSelected"})
    public static void setImgSelected(ImageView view, boolean b) {
        view.setSelected(b);
    }

    @BindingAdapter("viewSelected")
    public static void setSelectedView(View v, boolean b) {
        v.setSelected(b);
    }

    @BindingAdapter({"viewPagerAdater"})
    public static void setAdapterForViewPager(final ViewPager view, FragmentStatePagerAdapter pagerAdapter) {
        // final Wallet adapter = new MainActionsAdapter(view.getContext(), activity.getSupportFragmentManager());
        view.setAdapter(pagerAdapter);
        view.setOffscreenPageLimit(3);
    }

    @BindingAdapter({"slidePagerAdapter"})
    public static void setSlidePagerAdapter(ViewPager v, PagerAdapter adapter) {
        v.setAdapter(adapter);
    }

    @BindingAdapter({"vpCurrentTab"})
    public static void setVpCurrentTab(ViewPager view, int pos) {
        view.setCurrentItem(pos, true);
    }

    @BindingAdapter("viewPager")
    public static void setViewPagerForTablayout(TabLayout tablayout, ViewPager viewPager) {
        tablayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter("customFont")
    public static void setCustomFont(TextView v, String fontPath) {
        Typeface typeface = Typeface.createFromAsset(TravelApplication.getContext().getAssets(), "fonts/" + fontPath);
        v.setTypeface(typeface);
    }

    @BindingAdapter("cbInit")
    public static void checkBoxInit(CheckBox view, boolean b) {
        view.setChecked(b);

    }

    @BindingAdapter({"initMapExplore", "mapEvent"})
    public static void setGoogleMap(MapView view, final List<Place> places, final IMapExploreEvent mapEvent) {
        final int[] curMarkerSelected = {-1};
        if (view != null && places != null && places.size() > 0) {
            view.onCreate(null);
            view.onResume();
            view.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    LatLng latLng = null;
                    final List<Marker> markers = new ArrayList<>();
                    Place placeCoordinate = places.get(0);

                    for (int i = 0; i < places.size(); i++) {
                        placeCoordinate = places.get(i);
                        latLng = new LatLng(placeCoordinate.getLatitude(), placeCoordinate.getLongitude());

                        markers.add(googleMap.addMarker(new MarkerOptions().position(latLng)
                                .title(placeCoordinate.getPlaceName())
                                .icon(BitmapDescriptorFactory.fromResource(placeCoordinate.getMapPin()))));

                    }
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(placeCoordinate.getLatitude(), placeCoordinate.getLongitude())));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                    //map event

                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            mapEvent.onMapClick();
                            if (curMarkerSelected[0] != -1) {
                                markers.get(curMarkerSelected[0])
                                        .setIcon(BitmapDescriptorFactory.fromResource(places.get(curMarkerSelected[0]).getMapPin()));
                            }
                        }
                    });

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            if (curMarkerSelected[0] != -1) {
                                markers.get(curMarkerSelected[0]).setIcon(BitmapDescriptorFactory.fromResource(places.get(curMarkerSelected[0]).getMapPin()));
                            }
                            for (int i = 0; i < markers.size(); i++) {
                                if (marker.equals(markers.get(i))) {
                                    markers.get(i).setIcon(BitmapDescriptorFactory.fromResource(places.get(i).getMapPinActive()));
                                    curMarkerSelected[0] = i;
                                    break;
                                }
                            }
                            for (int i = 0; i < markers.size(); i++) {
                                if (markers.get(i).equals(marker)) {
                                    mapEvent.onPlaceClick(i);
                                    break;
                                }
                            }

                            return false;
                        }
                    });

                }
            });

        }
    }

    @BindingAdapter({"initMapDetails", "mapEvent", "drawDirect", "mapLeg"})
    public static void setMapDetails(MapView view, final Place place, final IMapDetailsEvent mapEvent,
                                     final List<PolylineOptions> polylineOptions, final List<Leg> legs) {
        if (view != null && place != null) {
            view.onCreate(null);
            view.onResume();
            view.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("MissingPermission")
                @Override
                public void onMapReady(final GoogleMap googleMap) {

                    googleMap.setMyLocationEnabled(true);
                    //padding left-top-right-bottom
                    googleMap.setPadding(0, 250, 0, 0);

                    //add maker of place and move camera to it
                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());

                    final Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(latLng)
                            .title(place.getPlaceName())
                            .icon(BitmapDescriptorFactory.fromResource(place.getMapPin())));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(place.getLatitude(), place.getLongitude())));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                    final List<Polyline> polylines = new ArrayList<>();
                    //add maker with transparen icon on polyline to show time
                    final List<Marker> makerPolylines = new ArrayList<>();

                    //if availble road
                    if (polylineOptions != null && legs != null) {
                        //need draw poly maker before draw poly to hide this maker click event
                        //because user can click in to marker on poly
                        //then map only show time box and poly listener not active
                        List<Step> steps = legs.get(0).getSteps();
                        LatLng latLngTmp = new LatLng(steps.get((int) steps.size() / 2).getStartLocation().getLat(),
                                steps.get((int) steps.size() / 2).getStartLocation().getLng());

                        Marker markerPolyline = googleMap.addMarker(new MarkerOptions().position(latLngTmp)
                                .title("" + legs.get(0).getDuration().getText())
                                .anchor((float) 0.5, (float) 0.5)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dot_transparen)));
                        markerPolyline.showInfoWindow();
                        makerPolylines.add(markerPolyline);


                        //draw polyline direct
                        for (int i = 0; i < polylineOptions.size(); i++) {
                            polylines.add(googleMap.addPolyline(polylineOptions.get(i)));
                        }

                        polylines.get(0).remove();
                        polylines.set(0, googleMap.addPolyline(polylineOptions.get(0)));

                        //set click for all polyline to listen click event
                        for (int i = 0; i < polylines.size(); i++) {
                            polylines.get(i).setClickable(true);
                            if (i == 0) {
                                //set color choose for fast road
                                polylines.get(i).setColor(TravelApplication.getContext().getResources().getColor(R.color.pink_FE007E));
                            }
                        }


                    }


                    //map event

                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            if (makerPolylines != null && makerPolylines.size()>0) {
                                makerPolylines.get(0).showInfoWindow();
                                //viewmodel event
                                mapEvent.onMapClick();
                            }



                        }
                    });

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            if (makerPolylines != null && makerPolylines.size()>0) {
                                makerPolylines.get(0).showInfoWindow();
                            }
                            return true;
                        }
                    });

                    googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                        @Override
                        public void onPolylineClick(Polyline polyline) {
                            //reset icon place marker if it is choosed
                            placeMarker.setIcon(BitmapDescriptorFactory.fromResource(place.getMapPin()));

                            //set unchoose color for all poly before set choose color for poly of click
                            for (int i = 0; i < polylines.size(); i++) {
                                polylines.get(i).setColor(TravelApplication.getContext().getResources().getColor(android.R.color.darker_gray));

                            }

                            // find poly is clicked and set color,show time on it
                            for (int i = 0; i < polylines.size(); i++) {
                                if (polylines.get(i).equals(polyline)) {
                                    //need draw poly maker before draw poly to hide this maker click event
                                    //because user can click in to marker on poly
                                    //then map only show time box and poly listener not active
                                    List<Step> steps = legs.get(i).getSteps();
                                    LatLng latLngTmp = new LatLng(steps.get((int) steps.size() / 2).getStartLocation().getLat(),
                                            steps.get((int) steps.size() / 2).getStartLocation().getLng());

                                    Marker markerPolyline = googleMap.addMarker(new MarkerOptions().position(latLngTmp)
                                            .title("" + legs.get(i).getDuration().getText())
                                            .anchor((float) 0.5, (float) 0.5)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.dot_transparen)));
                                    markerPolyline.showInfoWindow();
                                    makerPolylines.clear();
                                    makerPolylines.add(markerPolyline);

                                    //redraw poly after draw poly marker
                                    polylines.get(i).remove();
                                    polylines.set(i, googleMap.addPolyline(polylineOptions.get(i)));
                                    polylines.get(i).setClickable(true);
                                    polylines.get(i).setColor(TravelApplication.getContext().getResources().getColor(R.color.pink_FE007E));

                                    //event modelView
                                    mapEvent.onPolylineClick(i);
                                    break;
                                }
                            }

                        }
                    });

                }
            });

        }
    }

    @BindingAdapter({"initMapSteps", "location","curLeg","curLineOptions","placeDestination","event"})
    public static void setMapSteps(MapView view, Boolean b, final Location location, Leg leg,
                                   final PolylineOptions polylineOptions, final Place place, final IMapStepDetailsEvent event) {
        if (view != null && location != null && leg != null && polylineOptions != null) {
            view.onCreate(null);
            view.onResume();
            view.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("MissingPermission")
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    Polyline curPoly = null;
                    googleMap.clear();
                    googleMap.setMyLocationEnabled(true);
                    //padding left-top-right-bottom
                    googleMap.setPadding(0, 250, 0, 0);

                    //add maker of cur location and move camera to it
                    LatLng curLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                            curLatLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    //add maker of place destination
                    LatLng placeDeslatLng = new LatLng(place.getLatitude(), place.getLongitude());

                    final Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeDeslatLng)
                            .title(place.getPlaceName())
                            .icon(BitmapDescriptorFactory.fromResource(place.getMapPin())));


                    curPoly = googleMap.addPolyline(polylineOptions);

                    //callback to viewmodel to draw step details
                    event.onStepDetailsMapReady(googleMap);
                }
            });

        }
    }

    @BindingAdapter({"initMapUtil", "mapEvent", "typeIcon", "location"})
    public static void setMapUtil(MapView view, final List<NearyByPlace> places,
                                  final IMapUtilEvent mapEvent, final List<Integer> typeIcon,
                                  final Location location) {
        if (view != null) {

            view.onCreate(null);
            view.onResume();
            view.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("MissingPermission")
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    googleMap.clear();
                    googleMap.setMyLocationEnabled(true);

                    if (location != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                                new LatLng(location.getLatitude(),
                                        location.getLongitude())));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                    }

                    LatLng latLng = null;
                    final List<Marker> markers = new ArrayList<>();
                    NearyByPlace nearByPlace = null;
                    if (places != null && places.size() > 0) {

                        for (int i = 0; i < places.size(); i++) {
                            nearByPlace = places.get(i);
                            latLng = new LatLng(nearByPlace.getGeometry().getLocation().getLat(),
                                    nearByPlace.getGeometry().getLocation().getLng());

                            markers.add(googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .title(nearByPlace.getName())
                                    .icon(BitmapDescriptorFactory.fromResource(typeIcon.get(0)))));

                        }
                    }

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            int curPlacePos = -1;
                            for (int i = 0; i < markers.size(); i++) {
                                markers.get(i).setIcon(BitmapDescriptorFactory.fromResource(typeIcon.get(0)));
                                if (markers.get(i).equals(marker)) {
                                    curPlacePos = i;
                                }
                            }
                            marker.setIcon(BitmapDescriptorFactory.fromResource(typeIcon.get(1)));
                            mapEvent.onPlaceClick(curPlacePos);
                            return false;
                        }
                    });

                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            for (int i = 0; i < markers.size(); i++) {
                                markers.get(i).setIcon(BitmapDescriptorFactory.fromResource(typeIcon.get(0)));
                            }

                            mapEvent.onMapClick();
                        }
                    });


                }
            });

        }
    }
}
