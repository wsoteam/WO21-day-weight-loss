package wk.wildvso.com.twentyonetrening.ActivitiesProgramm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import wk.wildvso.com.twentyonetrening.ObjectHolder;
import wk.wildvso.com.twentyonetrening.POJOs.ArrayOfTiles;
import wk.wildvso.com.twentyonetrening.POJOs.ObjectLocalDatabase;
import wk.wildvso.com.twentyonetrening.POJOs.Tile;
import wk.wildvso.com.twentyonetrening.POJOs.Training;
import wk.wildvso.com.twentyonetrening.R;

public class ActivityWithTiles extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private AdView banner;

    private RecyclerView recyclerView;
    public static final String NUMBER_OF_PROGRAM = "NUMBER_OF_PROGRAM";
    public static final String NUMBER_OF_ITEM_FROM_LIST = "NUMBER_OF_ITEM_FROM_LIST";
    private Training training;
    private TextView titleCollapsing, textCollapsing;
    private FloatingActionButton fab;
    private int numberOfSelectedProgramm = 0;
    private int numberOfSelectedItemOfList = 0;

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



        numberOfSelectedProgramm = getIntent().getIntExtra(NUMBER_OF_PROGRAM, 0);
        numberOfSelectedItemOfList = getIntent().getIntExtra(NUMBER_OF_ITEM_FROM_LIST, 0);
        training = ObjectHolder.getGlobalObject()
                .getProgrammList()
                .get(numberOfSelectedProgramm)
                .getTrainingList()
                .get(numberOfSelectedItemOfList);

        if (training.getTilesList().size() == 1) {
            setContentView(R.layout.activity_with_tiles_if_list_empty);
        } else {
            setContentView(R.layout.activity_with_tiles);
            recyclerView = findViewById(R.id.rvTiles);
            recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
            GroupTilesAdapter groupTilesAdapter = new GroupTilesAdapter((ArrayList<ArrayOfTiles>) training.getTilesList());
            groupTilesAdapter.setHasStableIds(true);
            recyclerView.setAdapter(groupTilesAdapter);

            banner = findViewById(R.id.bannerFromActivityWithTiles);
        }


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.inter));
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);


        titleCollapsing = findViewById(R.id.tvCollapsingTitle);
        textCollapsing = findViewById(R.id.tvCollapsingText);
        fab = findViewById(R.id.fab);
        titleCollapsing.setText(training.getTitle());
        textCollapsing.setText(Html.fromHtml(training.getText()));


        if (ObjectLocalDatabase.isAddedInBase(numberOfSelectedProgramm, numberOfSelectedItemOfList)) {
            fab.setVisibility(View.GONE);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ActivityWithTiles.this, R.anim.animation_moving_out);
                ObjectLocalDatabase objectLocalDatabase =
                        new ObjectLocalDatabase(numberOfSelectedProgramm, numberOfSelectedItemOfList);
                objectLocalDatabase.save();
                Toast.makeText(ActivityWithTiles.this, R.string.add_favorite, Toast.LENGTH_SHORT).show();
                fab.startAnimation(animation);
                fab.setVisibility(View.GONE);
            }
        });

    }


    //Item Adapter
    private class ItemHolder extends RecyclerView.ViewHolder {
        ImageView topImage;
        TextView text;

        public ItemHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_activity_with_tiles, viewGroup, false));
            topImage = itemView.findViewById(R.id.ivTile);
            text = itemView.findViewById(R.id.tvNameTiles);
        }

        public void bind(Tile tile, int size) {
            text.setText(tile.getTitle());
            Glide.with(ActivityWithTiles.this)
                    .load(tile.getUrl_of_image())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_loading_tile))
                    .into(topImage);

        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        ArrayList<Tile> tileArrayList;

        public ItemAdapter(ArrayList<Tile> tileArrayList) {
            this.tileArrayList = tileArrayList;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityWithTiles.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.bind(tileArrayList.get(position), tileArrayList.size());
        }

        @Override
        public int getItemCount() {
            return tileArrayList.size();
        }
    }


    //Groups Adapter
    private class GroupTilesVH extends RecyclerView.ViewHolder {
        private RecyclerView recyclerViewGroupTiles;
        private TextView title;

        public GroupTilesVH(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_main_group_tile, viewGroup, false));
            recyclerViewGroupTiles = itemView.findViewById(R.id.rvMainGroupTiles);
            recyclerViewGroupTiles.setLayoutManager(new LinearLayoutManager(ActivityWithTiles.this, LinearLayoutManager.HORIZONTAL, false));
            title = itemView.findViewById(R.id.tvTitleOfGroupTiles);
        }

        public void bind(ArrayOfTiles arrayOfTiles) {
            title.setText(arrayOfTiles.getTitle());
            recyclerViewGroupTiles.setAdapter(new ItemAdapter((ArrayList<Tile>) arrayOfTiles.getTileList()));
        }
    }

    private class GroupTilesAdapter extends RecyclerView.Adapter<GroupTilesVH> {
        ArrayList<ArrayOfTiles> listOfList;

        public GroupTilesAdapter(ArrayList<ArrayOfTiles> listOfList) {
            this.listOfList = listOfList;
        }

        @NonNull
        @Override
        public GroupTilesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityWithTiles.this);
            return new GroupTilesVH(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GroupTilesVH holder, int position) {
            holder.bind(listOfList.get(position));
        }

        @Override
        public int getItemCount() {
            return listOfList.size();
        }
    }
}
