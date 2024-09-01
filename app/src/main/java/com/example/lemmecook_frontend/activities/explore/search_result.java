package com.example.lemmecook_frontend.activities.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.adapter.FilterOptionAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class search_result extends Fragment {
    private RecyclerView rvOptionSearch;
    private HashMap<String, String> filterOptionMap = new HashMap<>();
    private List<String> filterSearchByIngredient = new ArrayList<>();
    public search_result() {
        // Required empty public constructor
    }

    public static search_result newInstance(HashMap<String, String> filterOptionMap, List<String> filterSearchByIngredient) {
        search_result fragment = new search_result();
        Bundle args = new Bundle();
        args.putSerializable("filterOptionMap", filterOptionMap);
        args.putSerializable("filterSearchByIngredient", (Serializable) filterSearchByIngredient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        if (getArguments() != null) {
            filterOptionMap = (HashMap<String, String>) getArguments().getSerializable("filterOptionMap");
            filterSearchByIngredient = (List<String>) getArguments().getSerializable("filterSearchByIngredient");
        }

        rvOptionSearch = view.findViewById(R.id.recyclerViewOptionSearch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvOptionSearch.setLayoutManager(layoutManager);
        FilterOptionAdapter adapter = new FilterOptionAdapter(requireContext(), filterOptionMap, filterSearchByIngredient);
        rvOptionSearch.setAdapter(adapter);
        return view;
    }
}