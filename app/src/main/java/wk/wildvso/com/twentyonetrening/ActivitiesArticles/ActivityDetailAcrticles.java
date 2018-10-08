package wk.wildvso.com.twentyonetrening.ActivitiesArticles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import wk.wildvso.com.twentyonetrening.ObjectHolder;
import wk.wildvso.com.twentyonetrening.POJOs.Article;
import wk.wildvso.com.twentyonetrening.POJOs.WholeArticle;
import wk.wildvso.com.twentyonetrening.R;


public class ActivityDetailAcrticles extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    public static final String TAG = "ActivityDetailAcrticles";
    private WholeArticle wholeArticle;
    private TextView collapsingTitle;
    private ImageView collapsingImage;
    private RecyclerView recyclerView;
    private int selectedNumber = 0;
    private AdView banner;

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
        setContentView(R.layout.activity_detail_articles);

        banner = findViewById(R.id.bannerFromArticle);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.inter));
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        banner.loadAd(adRequest);

        selectedNumber = getIntent().getIntExtra(TAG, 0);
        wholeArticle = ObjectHolder.getGlobalObject().getWholeArticles().getWholeArticleList().get(selectedNumber);

        collapsingTitle = findViewById(R.id.tvCollapsingTitleOfArticle);
        collapsingImage = findViewById(R.id.ivCollapsingImageArticle);
        collapsingTitle.setText(wholeArticle.getTitle());
        Glide.with(this).load(wholeArticle.getImg_url()).into(collapsingImage);

        recyclerView = findViewById(R.id.rvArticle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PartOfArticleAdapter((ArrayList<Article>) wholeArticle.getArticleList()));
    }

    private class PartOfArticleVH extends RecyclerView.ViewHolder {
        private TextView textOfOneItem;
        private ImageView imageOfOneItem;

        public PartOfArticleVH(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_activity_detail_arctiles_item, viewGroup, false));
            textOfOneItem = itemView.findViewById(R.id.tvTextDetailOfArticle);
            imageOfOneItem = itemView.findViewById(R.id.ivImageOfDetailArticle);
        }

        public void bind(Article article) {
            textOfOneItem.setText(Html.fromHtml(article.getText()));
            Glide.with(ActivityDetailAcrticles.this).load(article.getImg_url()).into(imageOfOneItem);
        }
    }

    private class PartOfArticleAdapter extends RecyclerView.Adapter<PartOfArticleVH> {
        ArrayList<Article> articles;

        public PartOfArticleAdapter(ArrayList<Article> articles) {
            this.articles = articles;
        }

        @NonNull
        @Override
        public PartOfArticleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityDetailAcrticles.this);
            return new PartOfArticleVH(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PartOfArticleVH holder, int position) {
            holder.bind(articles.get(position));
        }

        @Override
        public int getItemCount() {
            return articles.size();
        }
    }
}
