package com.example.dattienbkhn.travel.screen.vod;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.databinding.ItemVodBinding;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 29/04/2018.
 */

public class ItemVodViewModel extends BaseObservable {

    private String vodName;
    private IVodItemCLick itemCLick;
    private ItemVodBinding binding;
    private boolean isCanPlay;
    public final ObservableField<String> vodNameFormat = new ObservableField<>();
    public final ObservableField<Boolean> isLoadThumbnails = new ObservableField<>();

    public ItemVodViewModel(String vodName, IVodItemCLick itemCLick,ItemVodBinding binding) {
        this.vodName = vodName;
        this.itemCLick = itemCLick;
        this.binding = binding;
        isLoadThumbnails.set(Boolean.TRUE);
        isCanPlay = false;
        formatVodName();
        String nameVideoRequest = vodName.substring(0, vodName.lastIndexOf("."));
        getThumbnailsVod(nameVideoRequest);

    }

    private void getThumbnailsVod(final String nameVideoRequest) {
        AppRepository.getAccountRepo().getThumbnail(nameVideoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<String>>() {
                    @Override
                    public void accept(WrapperResponse<String> stringWrapperResponse) throws Exception {
                        if (stringWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.VODThumbnail.toString())) {
                            if (stringWrapperResponse.getData() != null) {
                                byte[] imrArr = Base64.decode(stringWrapperResponse.getData(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgThumbnails.setImageBitmap(bm);
                                isCanPlay = true;
                                isLoadThumbnails.set(Boolean.FALSE);
                            } else {
                                makeAndReloadThumbnails(nameVideoRequest);
                            }

                        } else {
                            isCanPlay = false;
                            isLoadThumbnails.set(Boolean.FALSE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isCanPlay = false;
                        isLoadThumbnails.set(Boolean.FALSE);
                    }
                });
    }

    private void makeAndReloadThumbnails(final String nameVideoRequest) {
        AppRepository.getAccountRepo().makeThumbnail(nameVideoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getData()
                                && booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.VODThumbnail.toString())) {
                            getThumbnailsVod(nameVideoRequest);
                        } else {
                            isCanPlay = false;
                            isLoadThumbnails.set(Boolean.FALSE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isCanPlay = false;
                        isLoadThumbnails.set(Boolean.FALSE);
                    }
                });
    }

    private void formatVodName() {
        int firstKey = vodName.indexOf("-");
        int lastKey = vodName.lastIndexOf("-");
        vodNameFormat.set(vodName.substring(firstKey + 1, lastKey));
    }

    @Bindable
    public String getVodName() {
        return vodName;
    }

    public void onVodClick() {
        if (isCanPlay) {
            itemCLick.onVodClick(vodName);
        } else {
            itemCLick.onVodClick("");
        }
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
        notifyPropertyChanged(BR.vodName);
    }

    public interface IVodItemCLick {
        void onVodClick(String vodName);
    }
}
