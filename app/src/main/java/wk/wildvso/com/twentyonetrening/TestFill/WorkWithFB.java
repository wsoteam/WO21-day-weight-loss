package wk.wildvso.com.twentyonetrening.TestFill;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import wk.wildvso.com.twentyonetrening.Conf;
import wk.wildvso.com.twentyonetrening.POJOs.AllPartOfBody;
import wk.wildvso.com.twentyonetrening.POJOs.AllWholeArticles;
import wk.wildvso.com.twentyonetrening.POJOs.ArrayOfTiles;
import wk.wildvso.com.twentyonetrening.POJOs.Article;
import wk.wildvso.com.twentyonetrening.POJOs.Ex;
import wk.wildvso.com.twentyonetrening.POJOs.ExGroups;
import wk.wildvso.com.twentyonetrening.POJOs.GlobalObject;
import wk.wildvso.com.twentyonetrening.POJOs.PartOfBody;
import wk.wildvso.com.twentyonetrening.POJOs.Programm;
import wk.wildvso.com.twentyonetrening.POJOs.Tile;
import wk.wildvso.com.twentyonetrening.POJOs.Training;
import wk.wildvso.com.twentyonetrening.POJOs.WholeArticle;

public class WorkWithFB {

    public static void fill() {

        Tile tile = new Tile("title", "url_of_image");
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        ArrayOfTiles arrayOfTiles = new ArrayOfTiles("title", tiles);
        ArrayList<ArrayOfTiles> arrayOfTilesArrayList = new ArrayList<>();
        arrayOfTilesArrayList.add(arrayOfTiles);
        Training training = new Training("title", "text", "url_of_image", arrayOfTilesArrayList);
        ArrayList<Training> trainingArrayList = new ArrayList<>();
        trainingArrayList.add(training);

        Programm programm = new Programm("title1", trainingArrayList, "img_of_url");
        Programm sec_programm = new Programm("title2", trainingArrayList, "img_of_url");
        Programm third_programm = new Programm("title3", trainingArrayList, "img_of_url");
        ArrayList<Programm> programmArrayList = new ArrayList<>();
        programmArrayList.add(programm);
        programmArrayList.add(sec_programm);
        programmArrayList.add(third_programm);


        Ex ex = new Ex("title", "basic_muscle", "additional_muscle"
                , "complexity", "img_url", "for_man", "for_woman"
                , "detail", "main_chips", "url_of_logo");
        ArrayList<Ex> exArrayList = new ArrayList<>();
        exArrayList.add(ex);
        ExGroups exGroups = new ExGroups("name", exArrayList);
        ArrayList<ExGroups> exGroupsArrayList = new ArrayList<>();
        exGroupsArrayList.add(exGroups);
        exGroupsArrayList.add(exGroups);
        exGroupsArrayList.add(exGroups);
        PartOfBody partOfBody = new PartOfBody("name", "url_of_image", exGroupsArrayList);
        ArrayList<PartOfBody> partOfBodyArrayList = new ArrayList<>();
        partOfBodyArrayList.add(partOfBody);
        AllPartOfBody allPartOfBody = new AllPartOfBody("name", "url_of_image", partOfBodyArrayList);

        Article article = new Article("text", "img_url");
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        WholeArticle wholeArticle = new WholeArticle("title", "img_url", articleList);
        List<WholeArticle> wholeArticles = new ArrayList<>();
        wholeArticles.add(wholeArticle);
        AllWholeArticles allWholeArticles = new AllWholeArticles("name", "img_url", wholeArticles);

        GlobalObject globalObject = new GlobalObject("name", programmArrayList, allPartOfBody, allWholeArticles);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Conf.NAME_OF_ENTITY_FOR_DB);

        myRef.setValue(globalObject);


    }
}
