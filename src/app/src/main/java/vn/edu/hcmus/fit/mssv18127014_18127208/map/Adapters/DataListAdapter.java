package vn.edu.hcmus.fit.mssv18127014_18127208.map.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import vn.edu.hcmus.fit.mssv18127014_18127208.map.R;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {

    private JSONArray jsonArray = new JSONArray();
    private ArrayList<Boolean> expandedList = new ArrayList<>();

    private int mExpandedPosition = -1;

    Context context;

    public DataListAdapter(Context context) {
        this.context = context;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        expandedList = new ArrayList<>(Arrays.asList(new Boolean[jsonArray.length()]));
        Collections.fill(expandedList, Boolean.FALSE);
        notifyDataSetChanged();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public DataListAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_data_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull DataListAdapter.ViewHolder holder, int position) {
        final boolean isExpanded = position==mExpandedPosition;
        holder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(position);
            }
        });

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.source_date_text_view.setText(jsonObject.getString("source_date"));
            holder.source_headline_text_view.setText(jsonObject.getString("source_headline"));
            holder.source_article_text_view.setText(jsonObject.getString("source_article"));
            holder.source_country_name_text_view.setText(jsonObject.getString("country"));
            holder.source_region_text_view.setText(jsonObject.getString("region"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout details;

        TextView source_date_text_view;
        TextView source_headline_text_view;
        TextView source_article_text_view;
        TextView source_country_name_text_view;
        TextView source_region_text_view;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            details = itemView.findViewById(R.id.details);
            source_date_text_view = itemView.findViewById(R.id.source_date);
            source_headline_text_view = itemView.findViewById(R.id.source_headline);
            source_article_text_view = itemView.findViewById(R.id.source_article);
            source_country_name_text_view = itemView.findViewById(R.id.country_name);
            source_region_text_view = itemView.findViewById(R.id.country_region);
        }
    }
}
