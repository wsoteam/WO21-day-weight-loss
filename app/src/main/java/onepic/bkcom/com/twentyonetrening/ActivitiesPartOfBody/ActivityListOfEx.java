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
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import onepic.bkcom.com.twentyonetrening.POJOs.Ex;
import onepic.bkcom.com.twentyonetrening.POJOs.ExGroups;
import onepic.bkcom.com.twentyonetrening.R;

public class ActivityListOfEx extends AppCompatActivity {

    public static final String TAG = "ActivityListOfEx";
    private ExGroups exGroups;
    private RecyclerView recyclerView;
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
        setContentView(R.layout.activity_list_of_ex);
        recyclerView = findViewById(R.id.rvListOfAllEx);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exGroups = (ExGroups) getIntent().getSerializableExtra(TAG);
        recyclerView.setAdapter(new ListOfExAdapter((ArrayList<Ex>) exGroups.getExList()));

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.inter));
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(adRequest);


    }

    private class ListOfExViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvComplexity, tvBasicMuscle;

        public ListOfExViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_activity_list_of_ex, viewGroup, false));
            tvName = itemView.findViewById(R.id.tvNameItemActivityEx);
            tvName.setTypeface(Typeface.createFromAsset(ActivityListOfEx.this.getAssets()
                    , "asProgramMainScreen.ttf"));
            tvComplexity = itemView.findViewById(R.id.tvComplexityOfExItemActivityEx);
            tvBasicMuscle = itemView.findViewById(R.id.tvBasicMuscleExItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ActivityListOfEx.this, ActivityExDetail.class);
            intent.putExtra(ActivityExDetail.TAG, exGroups.getExList().get(getAdapterPosition()));
            startActivity(intent);
        }

        public void bind(Ex ex) {
            tvName.setText(ex.getTitle());
            tvComplexity.setText(ex.getComplexity());
            tvBasicMuscle.setText(ex.getBasic_muscle());
        }
    }

    private class ListOfExAdapter extends RecyclerView.Adapter<ListOfExViewHolder> {
        ArrayList<Ex> exArrayList;

        public ListOfExAdapter(ArrayList<Ex> exArrayList) {
            this.exArrayList = exArrayList;
        }

        @NonNull
        @Override
        public ListOfExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityListOfEx.this);//hardcode, need to fix
            return new ListOfExViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ListOfExViewHolder holder, int position) {
            holder.bind(exArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return exArrayList.size();
        }
    }
}
