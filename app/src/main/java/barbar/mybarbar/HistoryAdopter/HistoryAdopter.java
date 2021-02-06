package barbar.mybarbar.HistoryAdopter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import barbar.mybarbar.R;


public class HistoryAdopter extends RecyclerView.Adapter<HistoryAdopter.HistoryViewHolder> {
    private ArrayList<HistoryItems> items;
    private Context context;

    public HistoryAdopter(ArrayList<HistoryItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_items,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        final HistoryItems currentItems=items.get(position);
        holder.name.setText(currentItems.getCustomerName());
        holder.phoneNumber.setText(currentItems.getCustomerPhone());
        holder.date.setText(currentItems.getDate());
        holder.callMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = currentItems.getCustomerPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });
        if (currentItems.isAccepted()){
            holder.staus.setText("Accepted");
            holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.green_dot,0);
            holder.staus.setBackground(ContextCompat.getDrawable(context, R.drawable.hollw_circle_background));
        }
        if (currentItems.isDeclined()){
            holder.staus.setText("Declined");
            holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.red_dot,0);
            holder.staus.setBackground(ContextCompat.getDrawable(context, R.drawable.hollw_circle_red_background));
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView name,phoneNumber,date,staus,barberType,time;
        private Button callMe;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            callMe=itemView.findViewById(R.id.call_me);
            name=itemView.findViewById(R.id.name);
            phoneNumber=itemView.findViewById(R.id.phone_number);
            barberType=itemView.findViewById(R.id.barber_type);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            staus=itemView.findViewById(R.id.status);

        }
    }
}
