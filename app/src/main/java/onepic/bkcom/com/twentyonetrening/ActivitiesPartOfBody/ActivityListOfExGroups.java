package onepic.bkcom.com.twentyonetrening.ActivitiesPartOfBody;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import onepic.bkcom.com.twentyonetrening.ObjectHolder;
import onepic.bkcom.com.twentyonetrening.POJOs.ExGroups;
import onepic.bkcom.com.twentyonetrening.POJOs.PartOfBody;
import onepic.bkcom.com.twentyonetrening.R;

public class ActivityListOfExGroups extends AppCompatActivity {

    private PartOfBody partOfBody;
    private RecyclerView recyclerView;
    public static final String TAG = "ActivityListOfExGroups";
    private int selectedNumber = 0;
    private InterstitialAd mInterstitialAd;

    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ex_groups);

        selectedNumber = getIntent().getIntExtra(TAG, 0);
        partOfBody = ObjectHolder.getGlobalObject().getExercises().getPartOfBodyList().get(selectedNumber);
        //partOfBody = (PartOfBody) getIntent().getSerializableExtra(TAG);

        recyclerView = findViewById(R.id.rvListOfExGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListOfGroupsAdapter((ArrayList<ExGroups>) partOfBody.getExGroupsList()));

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.inter));
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(adRequest);

    }

    private class ListOfGroupsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvCountOfExInside;
        ImageView imageView;

        public ListOfGroupsViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_activity_ex_groups, viewGroup, false));
            tvCountOfExInside = itemView.findViewById(R.id.tvCountOfExItemActivityExGroups);
            tvName = itemView.findViewById(R.id.tvNameItemActivityExGroups);
            tvName.setTypeface(Typeface.createFromAsset(ActivityListOfExGroups.this.getAssets()
                    , "asProgramMainScreen.ttf"));
            imageView = itemView.findViewById(R.id.ivItemActivityExGroups);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ActivityListOfExGroups.this, ActivityListOfEx.class);
            intent.putExtra(ActivityListOfEx.TAG, partOfBody.getExGroupsList().get(getAdapterPosition()));
            startActivity(intent);
        }

        public void bind(ExGroups exGroups) {
            tvCountOfExInside.setText(" " + String.valueOf(exGroups.getExList().size()));
            tvName.setText(exGroups.getName());
        }
    }

    private class ListOfGroupsAdapter extends RecyclerView.Adapter<ListOfGroupsViewHolder> {
        ArrayList<ExGroups> exGroupsArrayList;


        public ListOfGroupsAdapter(ArrayList<ExGroups> exGroupsArrayList) {
            this.exGroupsArrayList = exGroupsArrayList;
        }


        @NonNull
        @Override
        public ListOfGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityListOfExGroups.this);//hardcode, need to fix
            return new ListOfGroupsViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ListOfGroupsViewHolder holder, int position) {
            holder.bind(exGroupsArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return exGroupsArrayList.size();
        }


    }
}
