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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class MobileOtpVerification extends AppCompatActivity {

    private TextView next,resend;
    final static String PHONE="phone";
    final static String CUNTERYCODE="cc";
    private ProgressBar progressBar;
    private OtpTextView otpTextView;
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


                    startActivity(new Intent(MobileOtpVerification.this,MainActivity.class));
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
        final String format="%02d:%02d";
        new CountDownTimer(5*60000, 1000) {

            @Override
            public void onTick(long l) {
                resend.setText(""+String.format(format,TimeUnit.MILLISECONDS.toMinutes(l),TimeUnit.MILLISECONDS.toSeconds(l)));
                resend.setEnabled(false);

            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);

            }
        }.start();
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


}
