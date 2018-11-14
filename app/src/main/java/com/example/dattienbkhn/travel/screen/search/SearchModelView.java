package com.example.dattienbkhn.travel.screen.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.widget.SearchView;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivitySearchBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.Region;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemTopCityViewModel;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class SearchModelView extends BaseObservable implements SearchContract.ViewModel,
        ItemRegionViewModel.IItemRegionClick, ItemTopCityViewModel.IItemCityClick,
        ItemFamousPlaceCountryViewModel.IItemPlaceClick {
    private SearchContract.View mNavigator;
    private DialogManager mDialogManager;
    private ActivitySearchBinding binding;

    private RegionAdapter mRegionAdapter;
    private PlaceSearchAdapter mPlaceSearchAdapter;
    private CitySearchAdapter mCitySearchAdapter;

    public final ObservableField<Boolean> isPlaceSearchVisible = new ObservableField<>();
    public final ObservableField<Boolean> isCitySearchVisible = new ObservableField<>();

    public final ObservableField<Boolean> isFilterByAmerica = new ObservableField<>();
    public final ObservableField<Boolean> isFilterByAsia = new ObservableField<>();
    public final ObservableField<Boolean> isFilterByAfrica = new ObservableField<>();
    public final ObservableField<Boolean> isFilterByAustralia = new ObservableField<>();
    public final ObservableField<Boolean> isFilterByEurope = new ObservableField<>();
    public final ObservableField<Boolean> isFilterByCaribbean = new ObservableField<>();

    public SearchModelView(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        isPlaceSearchVisible.set(false);
        isCitySearchVisible.set(false);
        isFilterByAmerica.set(false);
        isFilterByAustralia.set(false);
        isFilterByCaribbean.set(false);
        isFilterByAsia.set(false);
        isFilterByEurope.set(false);
        isFilterByAfrica.set(false);

        loadRegionFilter();
        loadResultForSearch();


    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (SearchContract.View) viewNavigator;
    }

    @Bindable
    public RegionAdapter getRegionAdapter() {
        return mRegionAdapter;
    }

    public void setRegionAdapter(RegionAdapter mRegionAdapter) {
        this.mRegionAdapter = mRegionAdapter;
        this.mRegionAdapter.setItemRegionClick(this);
    }

    @Bindable
    public PlaceSearchAdapter getPlaceSearchAdapter() {
        return mPlaceSearchAdapter;
    }

    public void setPlaceSearchAdapter(PlaceSearchAdapter mPlaceSearchAdapter) {
        this.mPlaceSearchAdapter = mPlaceSearchAdapter;
        this.mPlaceSearchAdapter.setItemPlaceClick(this);
    }

    @Bindable
    public CitySearchAdapter getCitySearchAdapter() {
        return mCitySearchAdapter;
    }

    public void setCitySearchAdapter(CitySearchAdapter mCitySearchAdapter) {
        this.mCitySearchAdapter = mCitySearchAdapter;
        this.mCitySearchAdapter.setItemCityClick(this);
    }

    public void setBinding(ActivitySearchBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onBackClick() {
        mNavigator.goFinish();
    }

    @Override
    public void loadRegionFilter() {
        List<Region> regions = new ArrayList<>();
        regions.add(new Region("America", R.drawable.america));
        regions.add(new Region("Asia", R.drawable.asia));
        regions.add(new Region("Australia", R.drawable.australia));
        regions.add(new Region("Africa", R.drawable.africa));
        regions.add(new Region("Europe", R.drawable.europe));
        regions.add(new Region("Caribbean", R.drawable.caribbean));
        mRegionAdapter.setData(regions);

    }

    @Override
    public Observable<String> listenSearch() {
        final PublishSubject<String> subject = PublishSubject.create();

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                return true;
            }
        });

        return subject;
    }

    /**
     * debounce: sẽ chờ đợi time đc cung cấp để làm bất cứ điều j
     * Ví dụ ở đây,người dùng muốn search city : newyork nhưng nếu k có debounce
     * mà user gõ quá nhanh thì sẽ có 7 call liên tiếp tới network
     * debounce sẽ giảm số call bằng cách cứ cách 300ms mới call 1 lần
     * ví dụ ta search : n --> call method lấy về city n.....
     * sau đó gõ nhanh : ew -->thì chỉ call lấy về new mà k có ne
     * vì cứ sau 300ms nó mới cho phép chạy 1 lần,những request ở khoảng giữa sẽ k đc exe
     * -Ví dụ khi gõ n,nó bắt đầu search cho 'n' và tính time 300ms sau mới đc search tiếp
     * -Giả sử search n ở giây 300ms,thì phải đến giây thứ 600ms nó mới search tiếp
     * -Tuy nhiên trong khoảng tg từ 301ms --> 599ms ta gõ thêm 'ew'
     * -Thì nó sẽ hủy bỏ call cho việc search 'n' và đợi cho đến 600ms để call 'new'
     * -Rồi trong 300ms tiếp nếu k viết thêm j thì nó sẽ tiếp tục process cho call 'new'
     * -Đại loại từ đầu đến h thực chất nó chỉ call 1 lần cho việc search 'new' mà k call
     * 'n' hay 'ne'
     * -
     * filter: lọc các item k mong muốn,trong th này lọc ra string empty để tránh lỗi khi request network
     * distinctUntilChanged: ngăn chặn các call trùng lặp
     * ví dụ khi search 'halong' --> đang call cho 'halong'
     * sau đó ta xóa chữ 'g' ->còn 'halon' ->xong gõ thêm 'g' ngay
     * cuối cùng thì vẫn là 'halong',nếu k có cái này thì sẽ lại call
     * method với city y hệt lần trước
     * SwitchMap:
     */
    @Override
    public void loadResultForSearch() {
        listenSearch().debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) throws Exception {
                        if (text.isEmpty()) {
                            isPlaceSearchVisible.set(false);
                            isCitySearchVisible.set(false);
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        String regionName = getStringRegionChoosed() + "";
                        findPlace(s, regionName);
                        findCity(s, regionName);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isPlaceSearchVisible.set(false);
                        isCitySearchVisible.set(false);
                        mNavigator.onErrorMessage("No search results!");
                    }
                });
    }

    @Override
    public String getStringRegionChoosed() {
        String result = "";
        if (isFilterByAmerica.get()) {
            result += " ";
            result += "America";
        }

        if (isFilterByAsia.get()) {
            result += " ";
            result += "Asia";
        }

        if (isFilterByAfrica.get()) {
            result += " ";
            result += "Africa";
        }

        if (isFilterByEurope.get()) {
            result += " ";
            result += "Europe";
        }

        if (isFilterByCaribbean.get()) {
            result += " ";
            result += "Caribbean";
        }

        if (isFilterByAustralia.get()) {
            result += " ";
            result += "Australia";
        }

        if (!isFilterByAmerica.get() && !isFilterByAfrica.get() && !isFilterByAsia.get()
                && !isFilterByAustralia.get() && !isFilterByCaribbean.get() && !isFilterByEurope.get()) {
            result += " ";
            result += "America";
            result += " ";
            result += "Asia";
            result += " ";
            result += "Africa";
            result += " ";
            result += "Europe";
            result += " ";
            result += "Caribbean";
            result += " ";
            result += "Australia";

        }
            return result;
    }

    @Override
    public void findPlace(String placeName, String regionName) {

        AppRepository.getPlaceRepo().findPlaceByName(placeName, regionName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Place>> listWrapperResponse) throws Exception {
                        if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListPlace.toString())) {
                            mPlaceSearchAdapter.setData(listWrapperResponse.getData());
                            isPlaceSearchVisible.set(true);
                        } else {
                            isPlaceSearchVisible.set(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isPlaceSearchVisible.set(false);
                        mNavigator.onErrorMessage("No search results!");
                    }
                });

    }

    @Override
    public void findCity(String cityName, String regionName) {
        AppRepository.getCityRepo().findCityByName(cityName, regionName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<City>>>() {
                    @Override
                    public void accept(WrapperResponse<List<City>> listWrapperResponse) throws Exception {
                        if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.ListCity.toString())) {
                            mCitySearchAdapter.setData(listWrapperResponse.getData());
                            isCitySearchVisible.set(true);
                        } else {
                            isCitySearchVisible.set(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isCitySearchVisible.set(false);
                        mNavigator.onErrorMessage("No search results!");
                    }
                });
    }


    @Override
    public void onItemRegionClick(Region obj, boolean b) {
        String regionName = obj.getRegionName();
        if (regionName.equalsIgnoreCase("America")) {
            isFilterByAmerica.set(b);
        } else if (regionName.equalsIgnoreCase("Asia")) {
            isFilterByAsia.set(b);
        } else if (regionName.equalsIgnoreCase("Africa")) {
            isFilterByAfrica.set(b);
        } else if (regionName.equalsIgnoreCase("Europe")) {
            isFilterByEurope.set(b);
        } else if (regionName.equalsIgnoreCase("Caribbean")) {
            isFilterByCaribbean.set(b);
        } else {
            isFilterByAustralia.set(b);
        }
    }

    @Override
    public void onItemCityClick(City obj) {
        mNavigator.goToCityAct(obj);
    }


    @Override
    public void onItemPlaceClick(Place obj) {
        mNavigator.goToPlaceDetailsAct(obj);
    }
}
