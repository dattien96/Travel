package com.example.dattienbkhn.travel.screen.main.favoritefragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.FragmentFavoriteBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class FavoriteFragment extends Fragment implements
        FavoriteFrgContract.View, ItemFamousPlaceCountryViewModel.IItemPlaceClick,
        ItemFavoriteViewModel.IItemPopupMenu, PopupMenu.OnMenuItemClickListener {
    public static final String FAVORITE_FRAGMENT_TAG = "FavoriteFragmentTag";
    private FavoriteFrgViewModel mViewModel;
    private FragmentFavoriteBinding binding;
    private DialogManager mDialogManager;
    private FavoriteAdapter mFavoriteAdapter;
    private int mCurPlaceChooseId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewmodel
        mDialogManager = new DialogManager(getActivity());
        mViewModel = new FavoriteFrgViewModel(mDialogManager);
        mViewModel.setViewNavigator(this);
        mFavoriteAdapter = new FavoriteAdapter(getActivity(), new ArrayList<Place>(), this,this);
        mViewModel.setFavoriteAdapter(mFavoriteAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
                R.layout.fragment_favorite,
                container, false);


        binding.setViewModel(mViewModel);


        onInitViewModel();

        return binding.getRoot();
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(getActivity(), "" + mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemPlaceClick(Place obj) {
        goToPlaceDetailsAct(obj);
    }

    @Override
    public void goToPlaceDetailsAct(Place obj) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place", obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void onShowPopupMenu(int placeId,View v) {
        mCurPlaceChooseId = placeId;
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.place_save_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                mViewModel.deletePlaceSave(mCurPlaceChooseId);
                return true;
            default:
                return false;
        }
    }
}
