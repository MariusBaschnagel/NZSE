package com.example.cwspace.ui.CoWorkerPackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cwspace.Adapter.RecyclerviewRoomsAdapter;
import com.example.cwspace.Datenklassen.Raum;
import com.example.cwspace.Datenklassen.RoomsArray;
import com.example.cwspace.R;

import java.util.ArrayList;


public class CwHomeFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_cw_home,container,false);
        //RoomsArray.getInstance().add(new Raum("test",23));
        //RoomsArray.getInstance().add(new Raum("test2",256));
        recyclerView = root.findViewById(R.id.show_all_roomlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerviewRoomsAdapter adapter = new RecyclerviewRoomsAdapter(getContext(),RoomsArray.getInstance());
        recyclerView.setAdapter(adapter);
        return root;
    }
}
