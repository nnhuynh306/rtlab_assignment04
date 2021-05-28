package vn.edu.hcmus.fit.mssv18127014_18127208.map.Views.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vn.edu.hcmus.fit.mssv18127014_18127208.map.Adapters.DataListAdapter;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.R;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.ViewModels.JSONViewModel;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.ViewModels.MapViewModel;

public class DataListFragment extends Fragment {

    private static final int limit = 20;

    private static final String TAG = "DATALISTFRAGMENT";

    private RecyclerView data_list_recycler_view;
    private DataListAdapter dataListAdapter;

    private JSONViewModel jsonViewModel;
    private MapViewModel mapViewModel;

    private LoadJSONHandler loadJSONHandler;

    private int currentPage = 1;
    private EditText currentPageEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setup(view);
    }

    private void setup(View root) {
        setupViewModels();

        getViews(root);
        setupAdapter(root);
        setupRecyclerView(root);

        this.loadJSONHandler = new LoadJSONHandler(currentPageEditText);

        this.jsonViewModel.loadJSONArray(10, 0, loadJSONHandler);

        setupListeners(root);
    }

    private void setupViewModels() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        this.jsonViewModel = viewModelProvider.get(JSONViewModel.class);
        this.mapViewModel = viewModelProvider.get(MapViewModel.class);
    }

    private void getViews(View root) {
        this.data_list_recycler_view = root.findViewById(R.id.data_list_recycler_view);
        this.currentPageEditText = root.findViewById(R.id.page_number);
    }

    private void setupAdapter(View root) {
        this.dataListAdapter = new DataListAdapter(requireContext(), this.mapViewModel);

        this.jsonViewModel.getJsonArrayMutableLiveData().observe(getViewLifecycleOwner(), new Observer<JSONArray>() {
            @Override
            public void onChanged(JSONArray jsonArray) {
                dataListAdapter.setJsonArray(jsonArray);
                mapViewModel.setNewCoordinatesFromJSONArray(jsonArray);
            }
        });
    }

    private void setupRecyclerView(View root) {
        data_list_recycler_view.setAdapter(this.dataListAdapter);
        data_list_recycler_view.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void setupListeners(View root) {
        root.findViewById(R.id.previous_page_btn).setOnClickListener(v -> {
            if (this.currentPage > 1) {
                jsonViewModel.loadJSONArray(limit, limit * (currentPage - 1 - 1), loadJSONHandler);
            }
        });

        root.findViewById(R.id.next_page_btn).setOnClickListener(v -> {
            jsonViewModel.loadJSONArray(limit, limit * (currentPage - 1 + 1), loadJSONHandler);
        });
    }

    private class LoadJSONHandler extends Handler {

        private EditText currentPageEditText;

        public LoadJSONHandler(EditText currentPageEditText) {
            this.currentPageEditText = currentPageEditText;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                {
                    break;
                }
                default: {
                    currentPageEditText.setText(String.valueOf(msg.what));
                    currentPage = msg.what;
                }
            }
        }
    }
}
