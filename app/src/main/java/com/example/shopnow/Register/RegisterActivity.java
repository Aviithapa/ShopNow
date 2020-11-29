package com.example.shopnow.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.shopnow.R;
import com.example.shopnow.Register.Fragments.SignInFragment;

public class RegisterActivity extends AppCompatActivity {
//variable for framelayout declaration
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Assigning the value of the framelayout to the variable

        frameLayout=findViewById(R.id.framelayout_register);
        //creating method and passing upcomming fragment  as parameter
        setFragment(new SignInFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        //replace the current framelayout and added the fragment of the parameter
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
