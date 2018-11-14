package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.service.TravelService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class ImageRemoteRepo implements IImageRemoteDataSource {

    private static TravelApi mApi;

    public ImageRemoteRepo() {
        mApi = TravelService.getTravelApiInstance();
    }

    @Override
    public Observable<WrapperResponse<List<Image>>> getImagesOfCity(int idCity) {
        return mApi.getImagesOfCity(idCity);
    }

    @Override
    public Observable<WrapperResponse<List<Image>>> getImagesOfPlace(int idPlace) {
        return mApi.getImagesOfPlace(idPlace);
    }
}
