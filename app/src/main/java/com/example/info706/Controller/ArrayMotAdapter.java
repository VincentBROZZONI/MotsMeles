package com.example.info706.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.info706.Model.Mot;
import com.example.info706.R;

import java.util.ArrayList;

public class ArrayMotAdapter extends ArrayAdapter<Mot> {

    public ArrayMotAdapter(Context context, ArrayList<Mot> listeMots){
        super (context,0,listeMots );
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent){
        if (convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.liste_view_mots,parent,false);
        }

        TextView mot = convertView.findViewById(R.id.choix_name);
        mot.setText(getItem(position).getChaineMot());
        return convertView;
    }
}