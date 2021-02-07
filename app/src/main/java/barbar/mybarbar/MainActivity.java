package barbar.mybarbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import barbar.mybarbar.Fragments.HistoryFragment;
import barbar.mybarbar.Fragments.ProfileFragment;
import barbar.mybarbar.Fragments.RequestFragment;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmoothBottomBar bottomNav = findViewById(R.id.bottom_navigation_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestFragment()).commit();



        bottomNav.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                Fragment fragment=null;
                switch (i){
                    case 0:
                        fragment=new RequestFragment();
                        break;
                    case 1:
                        fragment=new HistoryFragment();
                        break;
                    case 2:
                        fragment=new ProfileFragment();
                        break;
                }
                if (fragment!=null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                return true;
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