package com.example.asaelr.tastyidea;

        import android.content.Context;
        import android.support.v4.app.FragmentActivity;
        import android.util.Pair;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import android.view.ViewGroup.LayoutParams;

        import java.util.List;

public class IngAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] ingredients_names;
    private final String[] ingredients_ammounts;

    public IngAdapter(Context context, String[] names,String[] ammounts) {
        super(context, R.layout.row_ingredients,names);
        this.context = context;
        this.ingredients_names = names;
        this.ingredients_ammounts = ammounts;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_ingredients, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.pic);
//        LayoutParams params = imageView.getLayoutParams();
//        params.width = 230;
//        params.height= 230;
//        imageView.setLayoutParams(params);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(ingredients_names[position]);
        TextView ammount = (TextView)rowView.findViewById(R.id.ammount);
        ammount.setText(" - " + ingredients_ammounts[position]);
        switch (position) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_flour);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_sugar);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_egg);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.ic_milk);
                break;
            case 4:
                imageView.setImageResource(R.mipmap.ic_oil);
                break;
        }
        return rowView;
    }
}