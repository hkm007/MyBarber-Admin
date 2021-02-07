package barbar.mybarbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import barbar.mybarbar.Fragments.RequestFragment;

import static barbar.mybarbar.MobileAuthentication.SHARED_PREFS;
import static barbar.mybarbar.MobileAuthentication.SHOP_ID;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final String shopId = sharedPreferences.getString(SHOP_ID, "");

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (shopId.isEmpty()){
                    Intent mainIntent = new Intent(SplashScreen.this,MobileAuthentication.class);
                    SplashScreen.this.startActivity(mainIntent);
                }else {
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                }

                SplashScreen.this.finish();
            }
        }, 2000);
    }
}