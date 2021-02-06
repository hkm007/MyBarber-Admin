package barbar.mybarbar.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import barbar.mybarbar.HistoryAdopter.HistoryAdopter;
import barbar.mybarbar.HistoryAdopter.HistoryItems;
import barbar.mybarbar.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadApiData(view);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.notifyDataSetChanged();
                loadApiData(view);
            }
        });


        return view;
    }

    private void loadApiData(View view) {
        historyItems=new ArrayList<>();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://mybarber.herokuapp.com/shop/api/appointment/601a6fb155502621b4ace7f6", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("hkm", response.getString("msg"));
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
                        if (accepted||declined)
                            historyItems.add(new HistoryItems(id, date, time, description, customerName, customerPhone, accepted, declined));

                    }

                    mAdapter=new HistoryAdopter(historyItems,getContext());
                    recyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();

                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error.toString());
            }
        });


        requestQueue.add(jsonObjectRequest);
    }
}