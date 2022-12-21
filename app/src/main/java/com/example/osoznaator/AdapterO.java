package com.example.osoznaator;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterO extends BaseAdapter  {
    Context mContext;
    List<Osozn> osoznList;

    public AdapterO(Context mContext, List<Osozn> osoznList) {
        this.mContext = mContext;
        this.osoznList = osoznList;
    }

    @Override
    public int getCount() {
        return osoznList.size();
    }

    @Override
    public Object getItem(int i) {
        return osoznList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return osoznList.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext,R.layout.item_osozn,null);
        TextView Cel = v.findViewById(R.id.Cel);
        TextView DRazum = v.findViewById(R.id.DRazum);
        TextView Prostr = v.findViewById(R.id.Prostr);
        TextView Ocenkaemotion = v.findViewById(R.id.Ocenkaemotion);
        TextView Emotion = v.findViewById(R.id.Emotion);
        TextView Doi = v.findViewById(R.id.Doi);
        TextView Think = v.findViewById(R.id.Think);
        TextView Time = v.findViewById(R.id.Time);
        Osozn osozn = osoznList.get(i);
        Cel.setText(osozn.getCel());
        DRazum.setText(osozn.getDRazum());
        Prostr.setText(osozn.getProstr());
        Ocenkaemotion.setText(Integer.toString(osozn.getOcenkaemotion()));
        Emotion.setText(osozn.getEmotion());
        Doi.setText(osozn.getDoi());
        Think.setText(osozn.getThink());
        Time.setText(osozn.getTime());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, First.class);
                intent.putExtra(Osozn.class.getSimpleName(), osozn);
                mContext.startActivity(intent);
            }
        });
        return v;
    }
}
