package vn.edu.hcmus.fit.mssv18127014_18127208.map.ViewModels;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.hcmus.fit.mssv18127014_18127208.map.Models.Coordinate;

public class MapViewModel extends ViewModel {
    private MutableLiveData<List<Coordinate>> newCoordinateLiveData = new MutableLiveData<>();

    private HashMap<String, Coordinate> coordinates = new HashMap<>();

    public MapViewModel() {
    }

    public void setNewCoordinatesFromJSONArray(JSONArray jsonArray) {
        new SetNewCoordinatesAsyncTask().execute(jsonArray);
    }

    public void setNewCoordinate(ArrayList<Coordinate> coordinates) {
        List<Coordinate> newCoordinates = new ArrayList<>();

        for (Coordinate coordinate: coordinates) {
            if (!this.coordinates.containsKey(coordinate.getTitle())) {
                this.coordinates.put(coordinate.getTitle(), coordinate);
                newCoordinates.add(coordinate);
            }
        }

        this.newCoordinateLiveData.postValue(newCoordinates);
    }

    public MutableLiveData<List<Coordinate>> getNewCoordinateLiveData() {
        return newCoordinateLiveData;
    }
    private class SetNewCoordinatesAsyncTask extends AsyncTask<JSONArray, Void, Void> {

        @Override
        protected Void doInBackground(JSONArray... jsonArrays) {
            if (jsonArrays.length <= 0) {
                return null;
            }

            ArrayList<Coordinate> newCoordinates = getCoordinatesFromJSONArray(jsonArrays[0]);
            setNewCoordinate(newCoordinates);
            return null;
        }
    }

    private ArrayList<Coordinate> getCoordinatesFromJSONArray(JSONArray jsonArray) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                result.add(new Coordinate(Double.parseDouble(jsonObject.getString("latitude")),
                        Double.parseDouble(jsonObject.getString("longitude")),
                        jsonObject.getString("source_article")));
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
