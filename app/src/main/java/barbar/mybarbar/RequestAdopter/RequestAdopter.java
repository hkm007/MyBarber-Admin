package barbar.mybarbar.RequestAdopter;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import barbar.mybarbar.R;

import static android.content.Context.MODE_PRIVATE;
import static barbar.mybarbar.MobileAuthentication.SHARED_PREFS;
import static barbar.mybarbar.MobileAuthentication.SHOP_ID;

public class RequestAdopter extends RecyclerView.Adapter<RequestAdopter.RequestViewHolder> {

    private ArrayList<RequestItems> requestItems;
    private Context context;
    ProgressDialog progressdialog;
    private TextView emptyMessage;


    public RequestAdopter(ArrayList<RequestItems> requestItems, Context context,TextView emptyMessage) {
        this.requestItems = requestItems;
        this.context = context;
        this.emptyMessage=emptyMessage;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_request_items, parent, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder, final int position) {
        final RequestItems currentItem=requestItems.get(position);
        holder.name.setText(currentItem.getCustomerName());
        holder.phoneNumber.setText(currentItem.getCustomerPhone());
        holder.date.setText(currentItem.getDate());
        holder.barberType.setText(currentItem.getDescription());
        holder.time.setText(currentItem.getTime());
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage("Please Wait...");
        progressdialog.setCanceledOnTouchOutside(false);



        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final String shopId=sharedPreferences.getString(SHOP_ID, "");
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Accept Request")
                        .setMessage("Are you sure you want to accept this request?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                progressdialog.show();
                                AcceptRequest(position,shopId,currentItem.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Decline Request")
                        .setMessage("Are you sure you want to decline this request?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                progressdialog.show();
                                DeclineRequest(position,shopId,currentItem.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();




            }
        });
    }

    private void DeclineRequest(final int position, String shopId, String id) {
        String url = "https://mybarber.herokuapp.com/shop/api/appointment/decline/"+shopId+"/"+id;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("msg", response.getString("msg"));
                            progressdialog.dismiss();
                            requestItems.remove(position);
                            notifyItemRemoved(position);
                            notifyItemChanged(position);
                            notifyItemRangeChanged(position, requestItems.size());
                            if (getItemCount()==0)
                                emptyMessage.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        queue.add(putRequest);
    }

    private void AcceptRequest(final int position, String shopId, String currentId) {
        String url = "https://mybarber.herokuapp.com/shop/api/appointment/accept/"+shopId+"/"+currentId;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("msg", response.getString("msg"));
                            progressdialog.dismiss();
                            requestItems.remove(position);
                            notifyItemRemoved(position);
                            if (getItemCount()==0)
                                emptyMessage.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        queue.add(putRequest);

    }

    @Override
    public int getItemCount() {
        return requestItems.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        private Button accept,decline;
        private TextView name,phoneNumber,date,time,barberType;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time);
            accept=itemView.findViewById(R.id.accept);
            decline=itemView.findViewById(R.id.decline);
            name=itemView.findViewById(R.id.name);
            phoneNumber=itemView.findViewById(R.id.phone_number);
            barberType=itemView.findViewById(R.id.barber_type);
            date=itemView.findViewById(R.id.date);
            barberType=itemView.findViewById(R.id.barber_type);




        }
    }
}
