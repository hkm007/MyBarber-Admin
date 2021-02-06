package barbar.mybarbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import barbar.mybarbar.Fragments.RequestFragment;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final String user=null;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (user==null){
                    Intent mainIntent = new Intent(SplashScreen.this,MobileAuthentication.class);
                    SplashScreen.this.startActivity(mainIntent);
                }else {
                    Intent mainIntent = new Intent(SplashScreen.this, RequestFragment.class);
                    SplashScreen.this.startActivity(mainIntent);
                }

                SplashScreen.this.finish();
            }
        }, 2000);
    }
}