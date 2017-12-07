package domain.hackathon.hackathon2017;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Raj Chandan on 11/11/2017.
 */

public class GridViewAdapter extends ArrayAdapter<PetInfo> {

    private Activity context;
    private int resource;
    private List<PetInfo> listImage;
    public GridViewAdapter(Context context, int resource, List<PetInfo> objects)
    {
        super(context, resource, objects);
        this.context = (Activity) context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(null == v)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.griditem,null);
        }
        PetInfo pet = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.petimg);
        TextView age =(TextView) v.findViewById(R.id.agetxt);

        Glide.with(context).load(listImage.get(position).getImageid()).into(img);
        age.setText(pet.getAge());

        return v;
    }
}
