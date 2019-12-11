package com.n.akhisarbelediyesikontrolpaneli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<ProjeVeriler> {


    public CustomAdapter(Context context, ArrayList<ProjeVeriler> chatList) {
        super(context, 0, chatList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProjeVeriler projeVeriler = getItem(position);


            convertView = LayoutInflater.from(getContext()).inflate(R.layout.proje_items, parent, false);

            TextView proje_adı_text_view = (TextView) convertView.findViewById(R.id.proje_adı_text_view);
            TextView proje_özeti_text_view = (TextView) convertView.findViewById(R.id.proje_özeti_text_view);

            proje_adı_text_view.setText(projeVeriler.getProje_adı());

        proje_özeti_text_view.setText(projeVeriler.getProje_özeti());



        return convertView;
    }
}
