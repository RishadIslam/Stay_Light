package com.example.rishad.stay_light;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class bookedAdapter extends RecyclerView.Adapter<bookedAdapter.bookedViewHolder> {

    private Context CTX;
    private List<SearchModel> msearchModelList;

    private DatabaseReference databaseReference;
    private String house_key;
    public DatabaseReference database, reference,ref;
    TitleImage titleImage;
    public double latitude, longitude;


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
                try {

                    database = FirebaseDatabase.getInstance().getReference("No Title").child(searchModel.getHouseID());
                    ref = FirebaseDatabase.getInstance().getReference("Title Image").child(searchModel.getHouseID());

                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            titleImage = dataSnapshot.getValue(TitleImage.class);
                            ref.setValue(titleImage, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        database.setValue(null);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    reference = FirebaseDatabase.getInstance().getReference("Booked House").child(searchModel.getHouseID());
                    reference.removeValue();

                    GeoFire geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference("Not Available Host Location"));
                    geoFire.getLocation(searchModel.getHouseID(), new LocationCallback() {
                        @Override
                        public void onLocationResult(String key, GeoLocation location) {
                            if (location != null) {
                                latitude = location.latitude;
                                longitude = location.longitude;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    geoFire.removeLocation(searchModel.getHouseID(), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            GeoFire fire = new GeoFire(FirebaseDatabase.getInstance().getReference("Host Location"));
                            fire.setLocation(searchModel.getHouseID(), new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                                @Override
                                public void onComplete(String key, DatabaseError error) {
                                    Toast.makeText(CTX, "Not Available Transfered", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(CTX, e + "", Toast.LENGTH_SHORT).show();
                }

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

    public class bookedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView b_imageurl;
        TextView b_titleid;
        Button checkingout;


        public bookedViewHolder(@NonNull View itemView) {
            super(itemView);

            b_imageurl = itemView.findViewById(R.id.bprofile_image);
            b_titleid = itemView.findViewById(R.id.bTitle);
            checkingout = itemView.findViewById(R.id.checkoutbtn);

            checkingout.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.equals(checkingout)){
                removeAt(getAdapterPosition());
            }
        }
    }

    private void removeAt(int adapterPosition) {
        msearchModelList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, msearchModelList.size());
    }
}
