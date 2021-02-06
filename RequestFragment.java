package barbar.mybarbar.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        requestItems = new ArrayList<>();

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://mybarber.herokuapp.com/shop/api/appointment/new/601a6fb155502621b4ace7f6", null, new Response.Listener<JSONObject>() {
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

        mAdapter = new RequestAdopter(requestItems, getContext());
        recyclerView.setAdapter(mAdapter);


        return view;
    }
}