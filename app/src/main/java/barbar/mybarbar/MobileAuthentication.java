package barbar.mybarbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class MobileAuthentication extends AppCompatActivity {
    CountryCodePicker ccp;
    private EditText phoneNo,refCode;
    private Button continue_no;
    final static String PHONE="phone";
    final static String CUNTERYCODE="cc";
    final static String REF_CODE="RF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);


        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                //Toast.makeText(MobileAthuntication.this, "Updated " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //Log.v("tag",ccp.getSelectedCountryCodeWithPlus());
        phoneNo=findViewById(R.id.mobile_no);
        continue_no=findViewById(R.id.continue_button);
        continue_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNo.getText().toString().isEmpty()){
                    phoneNo.setError("Please enter mobile number");
                }
                else {
                    Intent intent=new Intent(MobileAuthentication.this,MobileOtpVerification.class);
                    intent.putExtra(PHONE,phoneNo.getText().toString());
                    intent.putExtra(CUNTERYCODE,ccp.getSelectedCountryCodeWithPlus());

                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}