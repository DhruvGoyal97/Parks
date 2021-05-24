package com.example.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parks.adapter.ViewPagerAdapter;
import com.example.parks.model.ParkViewModel;

public class details_fragment extends Fragment {
    private ParkViewModel parkViewModel;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;



    public details_fragment() {
        // Required empty public constructor
    }

    public static details_fragment newInstance() {
        details_fragment fragment = new details_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.detail_viewPager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        TextView parName = view.findViewById(R.id.details_parkName);
        TextView parDesignation = view.findViewById(R.id.details_parkDsignation);




        parkViewModel.getSelectedPark().observe(this,park -> {
            parName.setText(park.getName());
            parDesignation.setText(park.getDesignation());
            viewPagerAdapter = new ViewPagerAdapter(park.getImages());
            viewPager.setAdapter(viewPagerAdapter);

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_fragment, container, false);
    }
}