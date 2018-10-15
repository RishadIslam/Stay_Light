package com.example.rishad.stay_light;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class houseAdapter extends RecyclerView.Adapter<houseAdapter.houseViewHolder> {

    private Context mCTX;
    private List<SearchModel> searchModelList;
    private ItemClickListener mItemClickListener;

    private DatabaseReference mDatabaseReference;
    private String house_key;


    public houseAdapter(Context mCTX, List<SearchModel> searchModelList) {
        this.mCTX = mCTX;
        this.searchModelList = searchModelList;
    }

    @NonNull
    @Override
    public houseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCTX);
        View view = layoutInflater.inflate(R.layout.row, null);
        houseViewHolder holder = new houseViewHolder(view, mItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull houseViewHolder houseViewHolder, int i) {
        final SearchModel searchModel = searchModelList.get(i);

        houseViewHolder.title_id.setText(searchModel.getid());
        Picasso.get()
                .load(searchModel.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(houseViewHolder.image_url);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Title Image");




        houseViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(mCTX, Details.class);
                intent.putExtra("HouseID", searchModel.getHouseID());
                mCTX.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    public class houseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image_url;
        TextView title_id;
        private ItemClickListener mItemClickListener;


        public houseViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            image_url = itemView.findViewById(R.id.hprofile_image);
            title_id = itemView.findViewById(R.id.hTitle);

            this.mItemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.mItemClickListener = itemClickListener ;
        }


        @Override
        public void onClick(View v) {
            this.mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
