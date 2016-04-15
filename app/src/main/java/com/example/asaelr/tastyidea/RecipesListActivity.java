package com.example.asaelr.tastyidea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.graphics.drawable.BitmapDrawable;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.RatingBar;
import android.widget.TextView;
import android.graphics.Bitmap;

public class RecipesListActivity extends AppCompatActivity {
    private String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        values = getResources().getStringArray(R.array.recipes);
        ListView listView = (ListView) findViewById(R.id.recepies_list);
        final RecipesAdapter adapter = new RecipesAdapter(this,values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: go to RecipeView Activity of the and display the screen of the chosen food
                Intent intent = new Intent(RecipesListActivity.this,RecipeViewActivity.class);

                String name = adapter.getName(position);
                RatingBar rating = (RatingBar)view.findViewById(R.id.rate);
                int ratingVal = (int)rating.getRating();
                TextView time = (TextView)view.findViewById(R.id.time);
                String timeStr = time.getText().toString();
                TextView difficulty = (TextView)view.findViewById(R.id.difficulty);
                String difficultStr = difficulty.getText().toString();
                TextView category = (TextView)view.findViewById(R.id.category);
                String categoryStr = category.getText().toString();


                ImageView pic = (ImageView)view.findViewById(R.id.pic);
                //intent.putExtra("imagePath", pathToImage);

                BitmapDrawable drawable = (BitmapDrawable) pic.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                /*pic.buildDrawingCache();
                Bitmap bmap = pic.getDrawingCache();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                */

                Recipe recipe = new Recipe(name,bitmap,ratingVal,timeStr,difficultStr,categoryStr);
                String recipeStr = recipe.toString();
                intent.putExtra("chosen_recipe", recipeStr);
                startActivity(intent);
            }
        });
    }
}
