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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestFragment()).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.upcoming:
                        fragment=new RequestFragment();
                        break;
                    case R.id.history:
                        fragment=new HistoryFragment();
                        break;
                    case R.id.profile:
                        fragment=new ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });

    }

}