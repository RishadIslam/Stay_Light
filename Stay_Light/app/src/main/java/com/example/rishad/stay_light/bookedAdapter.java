package com.example.rishad.stay_light;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class bookedAdapter extends RecyclerView.Adapter<bookedAdapter.bookedViewHolder> {

    private Context CTX;
    private List<SearchModel> msearchModelList;

    private DatabaseReference databaseReference;
    private String house_key;


    public bookedAdapter(Context mCTX, List<SearchModel> searchModelList) {
        this.CTX = mCTX;
        this.msearchModelList = searchModelList;
    }

    @Override
    public void onBindViewHolder(@NonNull bookedViewHolder bookedViewHolder, int i) {
        final SearchModel searchModel = msearchModelList.get(i);

        bookedViewHolder.b_titleid.setText(searchModel.getid());
        Picasso.get()
                .load(searchModel.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(bookedViewHolder.b_imageurl);
        bookedViewHolder.checkingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        });
    }

    @NonNull
    @Override
    public bookedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(CTX);
        View view = layoutInflater.inflate(R.layout.bookrow, null);
        bookedViewHolder holder = new bookedViewHolder(view);

        return holder;
    }

    @Override
    public int getItemCount() {
        return msearchModelList.size();
    }

    public class bookedViewHolder extends RecyclerView.ViewHolder {

        ImageView b_imageurl;
        TextView b_titleid;
        Button checkingout;


        public bookedViewHolder(@NonNull View itemView) {
            super(itemView);

            b_imageurl = itemView.findViewById(R.id.bprofile_image);
            b_titleid = itemView.findViewById(R.id.bTitle);
            checkingout = itemView.findViewById(R.id.checkoutbtn);
        }
    }
}
