package barbar.mybarbar.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import barbar.mybarbar.R;

import static android.content.Context.MODE_PRIVATE;
import static barbar.mybarbar.MobileAuthentication.ADDRESS;
import static barbar.mybarbar.MobileAuthentication.OWNER_NAME;
import static barbar.mybarbar.MobileAuthentication.PHONE_NO;
import static barbar.mybarbar.MobileAuthentication.SHARED_PREFS;
import static barbar.mybarbar.MobileAuthentication.SHOP_NAME;
import static barbar.mybarbar.MobileAuthentication.TEXT;


public class ProfileFragment extends Fragment {

    private TextView shopName,ownerName,address,phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        shopName=view.findViewById(R.id.shop_name);
        ownerName=view.findViewById(R.id.owner_name);
        address=view.findViewById(R.id.address);
        phoneNumber=view.findViewById(R.id.phone_number);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        shopName.setText(sharedPreferences.getString(SHOP_NAME,""));
        ownerName.setText(sharedPreferences.getString(OWNER_NAME,""));
        address.setText(sharedPreferences.getString(ADDRESS,""));
        phoneNumber.setText(sharedPreferences.getString(PHONE_NO,""));









        return view;
    }
}