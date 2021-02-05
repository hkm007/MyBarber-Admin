package barbar.mybarbar.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barbar.mybarbar.R;
import barbar.mybarbar.RequestAdopter.RequestAdopter;
import barbar.mybarbar.RequestAdopter.RequestItems;

import java.util.ArrayList;

public class RequestFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<RequestItems> requestItems;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestItems=new ArrayList<>();
        requestItems.add(new RequestItems("Pramod","9956118310","","","Pending","Hair Cut"));
        for (int i=1;i<6;i++)
        requestItems.add(new RequestItems("Himanshu","9956118310","","","Pending","Hair Cut"));
        mAdapter=new RequestAdopter(requestItems,getContext());
        recyclerView.setAdapter(mAdapter);






        return view;
    }
}