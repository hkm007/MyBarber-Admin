package barbar.mybarbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

public class MobileAuthentication extends AppCompatActivity {
    CountryCodePicker ccp;
    private EditText phoneNo,shopName,ownerName,address;
    private Button continue_no;
    final static String PHONE="phone";
    final static String CUNTERYCODE="cc";
    final static String REF_CODE="RF";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SHOP_ID = "shopId";
    public static final String SHOP_NAME = "shop_name";
    public static final String OWNER_NAME = "owner_name";
    public static final String ADDRESS = "address";
    public static final String PHONE_NO = "phone_no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        shopName=findViewById(R.id.shop_name);
        ownerName=findViewById(R.id.owner_name);
        address=findViewById(R.id.address);
        continue_no=findViewById(R.id.continue_button);
        phoneNo=findViewById(R.id.mobile_no);




        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                //Toast.makeText(MobileAthuntication.this, "Updated " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //Log.v("tag",ccp.getSelectedCountryCodeWithPlus());
        continue_no.setText("Create Shop");

        continue_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressdialog = new ProgressDialog(getApplicationContext());
                progressdialog.setMessage("Creating Shop....");
                progressdialog.setCanceledOnTouchOutside(false);
                progressdialog.show();
                if (phoneNo.getText().toString().isEmpty()||!ValidatedData()){
                    Toast.makeText(MobileAuthentication.this,"Plese fill all places",Toast.LENGTH_LONG).show();
                }
                else {


                    String postUrl = "https://mybarber.herokuapp.com/shop/api/new";
                    RequestQueue requestQueue = Volley.newRequestQueue(MobileAuthentication.this);

                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("name",shopName.getText().toString());
                        postData.put("owner", ownerName.getText().toString());
                        postData.put("phone", phoneNo.getText().toString());
                        postData.put("address", address.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("hkm", response.getString("msg"));
                                Toast.makeText(MobileAuthentication.this, response.getString("msg"),Toast.LENGTH_SHORT).show();

                                String shop_id = response.getJSONObject("data").getString("_id");
                                String shop_name = response.getJSONObject("data").getString("name");
                                String shop_owner = response.getJSONObject("data").getString("owner");
                                String shop_phone = response.getJSONObject("data").getString("phone");
                                String shop_address = response.getJSONObject("data").getString("address");

                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(SHOP_ID, shop_id);
                                editor.putString(SHOP_NAME, shop_name);
                                editor.putString(OWNER_NAME, shop_owner);
                                editor.putString(ADDRESS, shop_address);
                                editor.putString(PHONE_NO, shop_phone);
                                editor.apply();
                                Intent intent=new Intent(MobileAuthentication.this,MainActivity.class);
                                //intent.putExtra(PHONE,phoneNo.getText().toString());
                                //intent.putExtra(CUNTERYCODE,ccp.getSelectedCountryCodeWithPlus());
                                progressdialog.dismiss();
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.d("err", error.toString());
                            Toast.makeText(MobileAuthentication.this,"Something went wrong! Swipe to refresh",Toast.LENGTH_LONG).show();
                        }
                    });

                    requestQueue.add(jsonObjectRequest);



                }
            }
        });

    }

    private boolean ValidatedData() {
        if (shopName.getText().toString().isEmpty()||ownerName.getText().toString().isEmpty()||address.getText().toString().isEmpty())
            return false;


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, "");
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String shopId = sharedPreferences.getString(SHOP_ID, "");
        Log.v("tag",shopId);
    }
}