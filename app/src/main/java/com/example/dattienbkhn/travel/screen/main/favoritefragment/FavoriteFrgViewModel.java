package com.example.dattienbkhn.travel.screen.main.favoritefragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.os.AsyncTask;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.realm.PlaceSave;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class FavoriteFrgViewModel extends BaseObservable implements FavoriteFrgContract.ViewModel {
    private DialogManager mDialogManager;
    private FavoriteFrgContract.View mNavigator;
    private FavoriteAdapter mFavoriteAdapter;

    public final ObservableField<Boolean> isHasData = new ObservableField<>();

    public FavoriteFrgViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        loadPlaceSave();
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (FavoriteFrgContract.View) viewNavigator;
    }

    @Bindable
    public FavoriteAdapter getFavoriteAdapter() {
        return mFavoriteAdapter;
    }

    public void setFavoriteAdapter(FavoriteAdapter mFavoriteAdapter) {
        this.mFavoriteAdapter = mFavoriteAdapter;
    }

    @Override
    public void loadPlaceSave() {
        new PlaceSaveAsyncTask().execute();
    }

    @Override
    public void deletePlaceSave(final int placeId) {
        Realm realm = Realm.getDefaultInstance();
        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                PlaceSave placeSave = bgRealm.where(PlaceSave.class).equalTo("id", placeId).findFirst();
                if (placeSave != null) {
                    placeSave.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                mFavoriteAdapter.removeItem(placeId);
            }
        });
    }


    public class PlaceSaveAsyncTask extends AsyncTask<Void, Void, List<Place>> {

        @Override
        protected List<Place> doInBackground(Void... voids) {
            Realm realm = Realm.getDefaultInstance();
            List<Place> placeSaves = new ArrayList<>();
            Place tmp;
            try {
                RealmResults<PlaceSave> results = realm.where(PlaceSave.class).findAll();
                if (results != null && results.size() > 0) {
                    isHasData.set(Boolean.TRUE);
                    for (int i = 0; i < results.size(); i++) {
                        tmp = new Place();
                        tmp.setPlaceId(results.get(i).getId());
                        tmp.setPlaceName(results.get(i).getName());
                        tmp.setPlaceImageUrl(results.get(i).getImage());
                        tmp.setTypeDetails(results.get(i).getType());
                        tmp.setPlaceReview(results.get(i).getView());
                        tmp.setPlaceRate(results.get(i).getRate());
                        placeSaves.add(tmp);
                    }
                } else {
                    isHasData.set(Boolean.FALSE);
                }
            } finally {
                realm.close();
            }
            return placeSaves;
        }

        @Override
        protected void onPostExecute(List<Place> placeSaves) {
            super.onPostExecute(placeSaves);
            if (placeSaves.size() > 0) {
                mFavoriteAdapter.setData(placeSaves);
            }
        }
    }
}
