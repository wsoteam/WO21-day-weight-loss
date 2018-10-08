package wk.wildvso.com.rtrening.FragmentsOfMainScreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import wk.wildvso.com.rtrening.ActivitiesProgramm.ActivityListOfTraining;
import wk.wildvso.com.rtrening.ObjectHolder;
import wk.wildvso.com.rtrening.POJOs.GlobalObject;
import wk.wildvso.com.rtrening.POJOs.Programm;
import wk.wildvso.com.rtrening.R;

public class FragmentProgramms extends Fragment {
    private static String TAG_OF_FRAGMENT = "FragmentProgramms";
    private RecyclerView rvListOfProgramm;
    private GlobalObject globalObject;

    /*public static FragmentProgramms newInstance(GlobalObject globalObject) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_OF_FRAGMENT, globalObject);

        FragmentProgramms fragmentProgramms = new FragmentProgramms();
        fragmentProgramms.setArguments(bundle);
        return fragmentProgramms;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programms, container, false);
        rvListOfProgramm = view.findViewById(R.id.rvListOfHomeFragment);
        //globalObject = (GlobalObject) getArguments().getSerializable(TAG_OF_FRAGMENT);
        globalObject = ObjectHolder.getGlobalObject();
        rvListOfProgramm.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListOfProgramm.setAdapter(new ProgrammAdapter((ArrayList<Programm>) globalObject.getProgrammList()));

        return view;
    }

    private class ProgrammViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitleOfProgramm, tvCountOfProgrammInside;
        ImageView imageView;

        public ProgrammViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.item_fragment_programms_main_screen, parent, false));

            tvTitleOfProgramm = itemView.findViewById(R.id.tvNameHomeList);
            tvCountOfProgrammInside = itemView.findViewById(R.id.tvCountOfProgrammInside);
            tvTitleOfProgramm.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "asProgramMainScreen.ttf"));
            imageView = itemView.findViewById(R.id.ivBackgroundItemListOfProgrammMainScreen);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityListOfTraining.class);
            intent.putExtra(ActivityListOfTraining.TAG, getAdapterPosition());
            startActivity(intent);
        }

        public void bind(Programm programm) {
            Glide.with(getActivity()).load(programm.getImg_url()).into(imageView);
            tvTitleOfProgramm.setText(programm.getTitle());
            tvCountOfProgrammInside.setText(String.valueOf(programm.getTrainingList().size()));
        }
    }

    private class ProgrammAdapter extends RecyclerView.Adapter<ProgrammViewHolder> {
        ArrayList<Programm> programmArrayList;

        public ProgrammAdapter(ArrayList<Programm> programmArrayList) {
            this.programmArrayList = programmArrayList;
        }

        @NonNull
        @Override
        public ProgrammViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProgrammViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProgrammViewHolder holder, int position) {
            holder.bind(programmArrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return programmArrayList.size();
        }
    }
}
