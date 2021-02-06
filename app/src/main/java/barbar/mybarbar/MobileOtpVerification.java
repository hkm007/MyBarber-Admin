package barbar.mybarbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class MobileOtpVerification extends AppCompatActivity {

    private TextView next,resend;
    final static String PHONE="phone";
    final static String CUNTERYCODE="cc";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private ProgressBar progressBar;
    private OtpTextView otpTextView;
    private SmsVerifyCatcher smsVerifyCatcher;
    String otp="";
    String phoneN0=null,referralCode=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp_verification);

        otpTextView = findViewById(R.id.otp_view);
        next=findViewById(R.id.next);
        resend=findViewById(R.id.resend);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

        phoneN0=getIntent().getStringExtra(CUNTERYCODE)+getIntent().getStringExtra(PHONE);

        SendVerificationCode(phoneN0);

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                otpTextView.setOTP(code);//set code in edit text
                //then you can send verification code to server
            }
        });
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String OTP) {
                // fired when user has entered the OTP fully.
                //Toast.makeText(MainActivity.this, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
                otp += OTP;
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(OtpPage.this,otp,Toast.LENGTH_LONG).show();

                if (otp.isEmpty()){
                    Toast.makeText(MobileOtpVerification.this,"Please Enter Your OTP",Toast.LENGTH_LONG).show();
                }
                else if (otp.replace(" ","").length()!=6){
                    Toast.makeText(MobileOtpVerification.this,"Please Enter 6 Digit OTP",Toast.LENGTH_LONG).show();
                }
                else {
                    // check correct otp entered?
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendVerificationCode(phoneN0);
            }
        });

    }

    private void SendVerificationCode(String phoneN0) {
        new CountDownTimer(5*60000, 1000) {

            @Override
            public void onTick(long l) {
                resend.setText("" + l / 1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);
            }
        }.start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
    public String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
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
        String text = sharedPreferences.getString(TEXT, "");
    }

}
