package wk.wildvso.com.twentyonetrening.FragmentsOfMainScreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import wk.wildvso.com.twentyonetrening.ActivitiesArticles.ActivityDetailAcrticles;
import wk.wildvso.com.twentyonetrening.ObjectHolder;
import wk.wildvso.com.twentyonetrening.POJOs.AllWholeArticles;
import wk.wildvso.com.twentyonetrening.POJOs.WholeArticle;
import wk.wildvso.com.twentyonetrening.R;

public class FragmentsArticles extends Fragment {
    private static String TAG_OF_FRAGMENT = "FragmentsArticles";
    private RecyclerView listOfArticlesRecyclerView;
    private AllWholeArticles allWholeArticles;

    /*public static FragmentsArticles newInstance(AllWholeArticles allWholeArticles) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_OF_FRAGMENT, allWholeArticles);

        FragmentsArticles fragmentsArticles = new FragmentsArticles();
        fragmentsArticles.setArguments(bundle);
        return fragmentsArticles;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        //allWholeArticles = (AllWholeArticles) getArguments().getSerializable(TAG_OF_FRAGMENT);
        allWholeArticles = ObjectHolder.getGlobalObject().getWholeArticles();
        listOfArticlesRecyclerView = view.findViewById(R.id.rvListOfArticles);
        listOfArticlesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        listOfArticlesRecyclerView.setAdapter(new ArticleAdapter((ArrayList<WholeArticle>) allWholeArticles.getWholeArticleList()));

        return view;
    }

    private class ArticleVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleOfArticle;

        public ArticleVH(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_fragment_articles_main, viewGroup, false));
            titleOfArticle = itemView.findViewById(R.id.tvTitleOfArticleItem);
            titleOfArticle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "asProgramMainScreen.ttf"));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityDetailAcrticles.class);
            intent.putExtra(ActivityDetailAcrticles.TAG, getAdapterPosition());
            startActivity(intent);
        }

        public void bind(WholeArticle wholeArticle) {
            titleOfArticle.setText(wholeArticle.getTitle());
        }
    }

    private class ArticleAdapter extends RecyclerView.Adapter<ArticleVH> {
        ArrayList<WholeArticle> wholeArticles;

        public ArticleAdapter(ArrayList<WholeArticle> wholeArticles) {
            this.wholeArticles = wholeArticles;
        }

        @NonNull
        @Override
        public ArticleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ArticleVH(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ArticleVH holder, int position) {
            holder.bind(wholeArticles.get(position));
        }

        @Override
        public int getItemCount() {
            return wholeArticles.size();
        }
    }
}
