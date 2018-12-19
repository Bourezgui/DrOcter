package com.integration.sra.drocter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.integration.sra.srawebservicelibrary.Utile.OrdreFabrication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ItemFragment extends Fragment {
    final ArrayList<Map<String, String>> listItema=new ArrayList<>();
    private ListView List ;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historique, container, false);
        List= view.findViewById(R.id.list1);
        OrdreFabrication ordreFabrication=new OrdreFabrication();
        java.util.List<OrdreFabrication> o =OrdreFabrication.listAll(OrdreFabrication.class);
        for(final OrdreFabrication el : o){
            listItema.add(new HashMap<String, String>(){{
                put("numliste",el.getIdpalette());
                put("numfard",el.getArticle());
            }});
        }

        final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema,
                R.layout.item_fard, new String[]{"numliste", "numfard"}, new int[]{R.id.num1, R.id.num2});
        List.setAdapter(mSchedule);

        return view;
    }



}
