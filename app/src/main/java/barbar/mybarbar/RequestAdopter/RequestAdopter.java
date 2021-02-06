package barbar.mybarbar.RequestAdopter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import barbar.mybarbar.R;

public class RequestAdopter extends RecyclerView.Adapter<RequestAdopter.RequestViewHolder> {

    private ArrayList<RequestItems> requestItems;
    private Context context;

    public RequestAdopter(ArrayList<RequestItems> requestItems, Context context) {
        this.requestItems = requestItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_request_items, parent, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, final int position) {
        final RequestItems currentItem=requestItems.get(position);
        holder.name.setText(currentItem.getCustomerName());
        holder.phoneNumber.setText(currentItem.getCustomerPhone());
        holder.date.setText(currentItem.getDate());
        holder.barberType.setText(currentItem.getDescription());



        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                currentItem.setDate(sdf.format(myCalendar.getTime()));
                Log.v("tag",sdf.format(myCalendar.getTime()));
                notifyItemChanged(position);
            }


        };

        holder.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,datePickerDialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return requestItems.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        private ImageView datePicker;
        private Button timePicker,accept,decline;
        private TextView name,phoneNumber,date,time,barberType;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            datePicker=itemView.findViewById(R.id.date_picker);
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
