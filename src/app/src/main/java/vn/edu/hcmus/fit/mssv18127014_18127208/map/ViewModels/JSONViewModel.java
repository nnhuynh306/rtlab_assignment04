package vn.edu.hcmus.fit.mssv18127014_18127208.map.ViewModels;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONViewModel extends ViewModel {
    MutableLiveData<JSONArray> jsonArrayMutableLiveData = new MutableLiveData<>();

    public void loadJSONArray(int limit, int offset, Handler handler) {
        new LoadJSONArrayAsyncTask(handler, limit, offset).execute(this.getDataUrl(limit, offset));
    }

    public MutableLiveData<JSONArray> getJsonArrayMutableLiveData() {
        return jsonArrayMutableLiveData;
    }

    private class LoadJSONArrayAsyncTask extends AsyncTask<String, Void, JSONArray> {

        private Handler handler;

        private int offset;

        private int limit;

        public LoadJSONArrayAsyncTask(Handler handler, int limit, int offset) {
            this.handler = handler;
            this.limit = limit;
            this.offset = offset;
        }

        protected JSONArray doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder receivedDataStringBuilder = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    receivedDataStringBuilder.append(line).append("\n");
                }

                return new JSONArray(receivedDataStringBuilder.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            handler.sendEmptyMessage(0);
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            if (jsonArray != null) {
                //send loaded page number;
                handler.sendEmptyMessage((offset / limit) + 1);
                jsonArrayMutableLiveData.setValue(jsonArray);
            }
        }
    }

    private String getDataUrl(int limit, int offset) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://rtlab02.rtworkspace.com/api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=")
                .append(limit)
                .append("&offset=")
                .append(offset);
        return stringBuilder.toString();
    }
}
