package com.muhammad_adi_yusuf.moviecatalogue.view.mainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;
import com.muhammad_adi_yusuf.moviecatalogue.view.adapter.AdapterRecyclerView;
import com.muhammad_adi_yusuf.moviecatalogue.view.detailPage.DetailActivity;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.ErrorNotificationLayout;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.IcsRecyclerView;
import com.muhammad_adi_yusuf.moviecatalogue.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TvFragment extends Fragment {

    private AdapterRecyclerView adapter;
    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private ErrorNotificationLayout errorView;

    private ArrayList<MovieCatalogueResultsItem> tvsItem = new ArrayList<>();

    public TvFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
        clickRecyclerView();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        viewModel.setTv(getString(R.string.idLanguage));
    }

    private void initialize(View view) {
        //viewModel
        viewModel.getTv().observe((LifecycleOwner) Objects.requireNonNull(getContext()), getData);
        //adapter
        adapter = new AdapterRecyclerView(getContext(), tvsItem);
        //recyclerView
        recyclerView = view.findViewById(R.id.rv_home);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
        //errorView
        errorView = new ErrorNotificationLayout(getContext());
        errorView.initializationInFragment(true, view);
    }

    private Observer<List<MovieCatalogueResultsItem>> getData = new Observer<List<MovieCatalogueResultsItem>>() {
        @Override
        public void onChanged(List<MovieCatalogueResultsItem> moviesItem) {
            errorView.settingNotification(getString(R.string.option3));
            if (moviesItem != null) {
                adapter.setData(moviesItem);
                errorView.notification(false);
            } else {
                errorView.notification(true);
            }
        }

    };

    private void clickRecyclerView() {
        IcsRecyclerView.addTo(recyclerView).setOnItemClickListener(new IcsRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                MovieCatalogueResultsItem iTem = tvsItem.get(position);
                int idItem = iTem.getId();
                Intent intentJump = new Intent(getContext(), DetailActivity.class);
                intentJump.putExtra("idItem", idItem);
                intentJump.putExtra("type", getString(R.string.tab2));
                startActivity(intentJump);
            }
        });
    }
}
