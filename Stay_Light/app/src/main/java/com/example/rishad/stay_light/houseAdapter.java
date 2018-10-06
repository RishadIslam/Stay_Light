package com.example.rishad.stay_light;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class houseAdapter extends RecyclerView.Adapter<houseAdapter.houseViewHolder> {

    private Context mCTX;
    private List<SearchModel> searchModelList;


    public houseAdapter(Context mCTX, List<SearchModel> searchModelList) {
        this.mCTX = mCTX;
        this.searchModelList = searchModelList;
    }

    @NonNull
    @Override
    public houseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCTX);
        View view = layoutInflater.inflate(R.layout.row, null);
        houseViewHolder holder = new houseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull houseViewHolder houseViewHolder, int i) {
        SearchModel searchModel = searchModelList.get(i);

        houseViewHolder.title_id.setText(searchModel.getId());
        Picasso.get()
                .load(searchModel.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(houseViewHolder.image_url);

    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    class houseViewHolder extends RecyclerView.ViewHolder {

        ImageView image_url;
        TextView title_id;


        public houseViewHolder(@NonNull View itemView) {
            super(itemView);

            image_url = itemView.findViewById(R.id.hprofile_image);
            title_id = itemView.findViewById(R.id.hTitle);
        }
    }
}
