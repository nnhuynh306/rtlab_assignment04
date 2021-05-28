package vn.edu.hcmus.fit.mssv18127014_18127208.map.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.edu.hcmus.fit.mssv18127014_18127208.map.Models.Coordinate;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.R;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.ViewModels.MapViewModel;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;

    private GoogleMap mMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModels(view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap = googleMap;

        mapViewModel.getNewCoordinateLiveData().observe(getViewLifecycleOwner(), new Observer<List<Coordinate>>() {
            @Override
            public void onChanged(List<Coordinate> coordinates) {
                for (Coordinate coordinate: coordinates) {
                    setMapMarker(coordinate);
                }
            }
        });
    }

    private void setupViewModels(View root) {
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        mapViewModel = viewModelProvider.get(MapViewModel.class);
    }

    private void setMapMarker(Coordinate coordinate) {
        LatLng newLatLng = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
        mMap.addMarker(new MarkerOptions().position(newLatLng).title(coordinate.getTitle()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
    }
}
