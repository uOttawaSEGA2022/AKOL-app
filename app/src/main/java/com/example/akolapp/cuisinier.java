package com.example.akolapp;

import android.os.Bundle;

import com.example.akolapp.databinding.ActivityClientBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.akolapp.databinding.ActivityCuisinierBinding;
import com.google.android.material.tabs.TabLayout;

public class cuisinier extends AppCompatActivity {

    ActivityCuisinierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCuisinierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Homefrag());

        binding.bottomNav.setOnItemSelectedListener( item -> {

            switch (item.getItemId()){

                case R.id.HomeCuis:
                    replaceFragment(new Homefrag());
                    break;
                case R.id.Orders:
                    replaceFragment(new OrdersFrag());
                    break;
                case R.id.Profile:
                    replaceFragment(new Profilefrag());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}