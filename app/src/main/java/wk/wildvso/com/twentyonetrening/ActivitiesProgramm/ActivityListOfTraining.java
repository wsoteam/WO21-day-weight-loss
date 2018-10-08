package wk.wildvso.com.twentyonetrening.ActivitiesProgramm;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import wk.wildvso.com.twentyonetrening.ActivitiesPartOfBody.ActivityListOfEx;
import wk.wildvso.com.twentyonetrening.ObjectHolder;
import wk.wildvso.com.twentyonetrening.POJOs.ObjectLocalDatabase;
import wk.wildvso.com.twentyonetrening.POJOs.Programm;
import wk.wildvso.com.twentyonetrening.POJOs.Training;
import wk.wildvso.com.twentyonetrening.R;

public class ActivityListOfTraining extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static final String TAG = "ActivityListOfTraining";
    private Programm programm;
    private int selectedNumber = 0;
    private TrainingAdapter trainingAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        fillRecycler();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        selectedNumber = getIntent().getIntExtra(TAG, 0);
        recyclerView = findViewById(R.id.rvTrainingList);
        fillRecycler();

    }

    private void fillRecycler() {
        programm = new Programm();
        programm = ObjectHolder.getGlobalObject().getProgrammList().get(selectedNumber);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainingAdapter = new TrainingAdapter((ArrayList<Training>) programm.getTrainingList());
        recyclerView.setAdapter(trainingAdapter);
        trainingAdapter.notifyDataSetChanged();
    }

    private class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitleOfProgramm;
        ImageView imageIsSave, backgroundImage;

        public TrainingViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.item_activity_training_list, parent, false));

            tvTitleOfProgramm = itemView.findViewById(R.id.tvTrainingListName);
            imageIsSave = itemView.findViewById(R.id.ivIsSaveProgrammList);
            backgroundImage = itemView.findViewById(R.id.ivListOfTrainingBackground);
            tvTitleOfProgramm.setTypeface(Typeface.createFromAsset(ActivityListOfTraining.this.getAssets()
                    , "asProgramMainScreen.ttf"));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ActivityListOfTraining.this, ActivityWithTiles.class);
            intent.putExtra(ActivityWithTiles.NUMBER_OF_PROGRAM, selectedNumber);
            intent.putExtra(ActivityWithTiles.NUMBER_OF_ITEM_FROM_LIST, getAdapterPosition());
            startActivity(intent);


        }

        public void bind(Training training) {
            imageIsSave.setVisibility(View.GONE);
            tvTitleOfProgramm.setText(training.getTitle());
            Glide.with(ActivityListOfTraining.this).load(training.getUrl_of_image()).into(backgroundImage);
            if(ObjectLocalDatabase.isAddedInBase(selectedNumber, getAdapterPosition())){
                imageIsSave.setVisibility(View.VISIBLE);
            }
        }
    }

    private class TrainingAdapter extends RecyclerView.Adapter<TrainingViewHolder> {
        ArrayList<Training> trainingArrayList;

        public TrainingAdapter(ArrayList<Training> trainingArrayList) {
            this.trainingArrayList = trainingArrayList;
        }

        @NonNull
        @Override
        public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityListOfTraining.this);
            return new TrainingViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
            holder.bind(trainingArrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return trainingArrayList.size();
        }
    }
}
