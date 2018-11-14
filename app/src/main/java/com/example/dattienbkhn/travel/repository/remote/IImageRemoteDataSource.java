package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public interface IImageRemoteDataSource {
    Observable<WrapperResponse<List<Image>>> getImagesOfCity(int idCity);

    Observable<WrapperResponse<List<Image>>> getImagesOfPlace(int idPlace);
}
