package com.example.dattienbkhn.travel.screen.friendlist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.databinding.ActivityFriendListBinding;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.main.mefragment.FriendAdapter;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by tiendatbkhn on 25/04/2018.
 */

public class FriendListViewModel extends BaseObservable implements FriendListContract.ViewModel {
    private int mCurAccId;
    private FriendListContract.View mNavigator;
    private DialogManager mDialogManager;
    private FriendAdapter mAdapter;
    private FriendsSearchAdapter mSearchAdapter;
    private ActivityFriendListBinding binding;


    public final ObservableField<Boolean> isFriendsSearchVisible = new ObservableField<>();

    public FriendListViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        isFriendsSearchVisible.set(Boolean.FALSE);
        getAllFriend(mCurAccId);
        loadResultForSearch();
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (FriendListContract.View) viewNavigator;
    }

    @Override
    public void getAllFriend(int accId) {

        AppRepository.getAccountRepo().getFriends(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<Friend>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Friend>> friendsWrapperResponse) throws Exception {
                        if (friendsWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Friend.toString())) {
                            Log.e("friend", " get friend : " + friendsWrapperResponse.getMessage());
                            mAdapter.setData(friendsWrapperResponse.getData());
                            Log.e("friend", " get friend : " + friendsWrapperResponse.getData().size());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("friend", " get friend : " + throwable.getMessage());
                        mNavigator.onErrorMessage("Error when get friends");
                    }
                });
    }

    @Override
    public void onBackClick() {
        mNavigator.finishView();
    }

    @Override
    public Observable<String> listenSearch() {
        final PublishSubject<String> subject = PublishSubject.create();

        binding.searchFriendView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @Override
    public void loadResultForSearch() {
        listenSearch().debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) throws Exception {
                        if (text.isEmpty()) {
                            isFriendsSearchVisible.set(false);
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
                        findFriends(s);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("No search results!");
                    }
                });
    }

    @Override
    public void findFriends(String s) {
        AppRepository.getAccountRepo().findFriendByName(mCurAccId, s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                }).subscribe(new Consumer<WrapperResponse<List<UserSearch>>>() {
            @Override
            public void accept(WrapperResponse<List<UserSearch>> listWrapperResponse) throws Exception {
                if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.UserSearch.toString())) {
                   if (listWrapperResponse.getData() != null) {
                       mSearchAdapter.setData(listWrapperResponse.getData());
                       isFriendsSearchVisible.set(Boolean.TRUE);
                   }
                   else {
                       mSearchAdapter.setData(new ArrayList<UserSearch>());
                       isFriendsSearchVisible.set(Boolean.FALSE);
                   }
                }else {
                    mSearchAdapter.setData(new ArrayList<UserSearch>());
                    isFriendsSearchVisible.set(Boolean.FALSE);
                    mNavigator.onErrorMessage("No Search Results!");
                }
                mDialogManager.dismissProgressDialog();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mDialogManager.dismissProgressDialog();
            }
        });
    }

    public int getCurAccId() {
        return mCurAccId;
    }

    public void setCurAccId(int mCurAccId) {
        this.mCurAccId = mCurAccId;
    }

    @Bindable
    public FriendAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(FriendAdapter mAdapter) {
        this.mAdapter = mAdapter;
        notifyPropertyChanged(BR.adapter);
    }

    public void setBinding(ActivityFriendListBinding binding) {
        this.binding = binding;
    }

    @Bindable
    public FriendsSearchAdapter getSearchAdapter() {
        return mSearchAdapter;
    }

    public void setSearchAdapter(FriendsSearchAdapter mSearchAdapter) {
        this.mSearchAdapter = mSearchAdapter;
        notifyPropertyChanged(BR.searchAdapter);
    }

    public void onHideResultSearch() {
        isFriendsSearchVisible.set(false);
        binding.searchFriendView.setQuery(null,false);
    }
}
