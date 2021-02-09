package barbar.mybarbar.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import barbar.mybarbar.R;
import barbar.mybarbar.RequestAdopter.RequestAdopter;
import barbar.mybarbar.RequestAdopter.RequestItems;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static barbar.mybarbar.MobileAuthentication.SHARED_PREFS;
import static barbar.mybarbar.MobileAuthentication.SHOP_ID;
import static barbar.mybarbar.MobileAuthentication.TEXT;

public class RequestFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<RequestItems> requestItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressbar);
        emptyMessage=view.findViewById(R.id.empty_message);


        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);

        loadData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadApiData(view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApiData(view);
                mAdapter.notifyDataSetChanged();

            }
        });





        return view;
    }

    private void loadApiData(final View view) {
        requestItems=new ArrayList<>();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String shopID = sharedPreferences.getString(SHOP_ID, "");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://mybarber.herokuapp.com/shop/api/appointment/new/"+shopID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    for(int i = 0; i < data.length(); i++)
                    {
                        JSONObject object = data.getJSONObject(i);

                        String id = object.getString("_id");
                        String date = object.getString("date");
                        String time = object.getString("time");
                        String description = object.getString("description");
                        String customerName = object.getJSONObject("customer").getString("name");
                        String customerPhone = object.getJSONObject("customer").getString("phone");
                        boolean accepted = object.getBoolean("accepted");
                        boolean declined = object.getBoolean("declined");

                        requestItems.add(new RequestItems(id, date, time, description, customerName, customerPhone, accepted, declined));
                    }
                    if (data.length()==0)
                        emptyMessage.setVisibility(View.VISIBLE);
                    mAdapter=new RequestAdopter(requestItems,getContext(),emptyMessage);
                    recyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getContext(),"Something went wrong! Swipe to refresh",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error.toString());
                loadApiData(view);
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Please wait working on it",Toast.LENGTH_LONG).show();
            }
        });


        requestQueue.add(jsonObjectRequest);


    }
    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString(SHOP_ID, "");
        Log.v("tag",text);
    }

}