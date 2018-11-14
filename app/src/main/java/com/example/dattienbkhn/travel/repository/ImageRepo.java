package com.example.dattienbkhn.travel.repository;

import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.local.IImageLocalDataSource;
import com.example.dattienbkhn.travel.repository.remote.IImageRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class ImageRepo implements IImageRemoteDataSource,IImageLocalDataSource {

    private IImageLocalDataSource mImageLocalRepo;
    private IImageRemoteDataSource mImageRemoteRepo;

    public ImageRepo(IImageRemoteDataSource mImageRemoteRepo) {
        this.mImageRemoteRepo = mImageRemoteRepo;
    }
    @Override
    public Observable<WrapperResponse<List<Image>>> getImagesOfCity(int idCity) {
        return mImageRemoteRepo.getImagesOfCity(idCity);
    }

    @Override
    public Observable<WrapperResponse<List<Image>>> getImagesOfPlace(int idPlace) {
        return mImageRemoteRepo.getImagesOfPlace(idPlace);
    }
}
