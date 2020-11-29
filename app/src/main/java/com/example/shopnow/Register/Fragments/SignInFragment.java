package com.example.shopnow.Register.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopnow.ForgotPassword.ResetPasswordFragment;
import com.example.shopnow.MainActivity;
import com.example.shopnow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

private TextView dontHaveAccount;
private FrameLayout parentframelayout;
private EditText email;
private EditText password;
private Button  sign_in_btn;
private FirebaseAuth firebaseAuth;
private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
private ImageView close;
private TextView forgot_password;
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //we cannot directly assign the value in th
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAccount=view.findViewById(R.id.dont_have_a_account);
        parentframelayout=getActivity().findViewById(R.id.framelayout_register);
        email=view.findViewById(R.id.sign_up_emaill);
        password=view.findViewById(R.id.sign_up_password);
        sign_in_btn=view.findViewById(R.id.sign_in_button);
        close=view.findViewById(R.id.close);
        firebaseAuth=FirebaseAuth.getInstance();
        forgot_password=view.findViewById(R.id.sign_in_forgot_password);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setFragment(new SignUpFragment());

            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ResetPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication();
            }
        });
      close.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mainIntent();
          }
      });

    }

    private void mainIntent() {
        Intent mainIntent= new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }

    private void authentication() {
        if (email.getText().toString().matches(emailPattern)){
            if (password.getText().length()>=8) {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent mainIntent= new Intent(getActivity(), MainActivity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();
                                }else{
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT);
                                }
                            }
                        });

            }else{
                Toast.makeText(getActivity(),"Password length doesn't match",Toast.LENGTH_SHORT);
            }
            }else{
            Toast.makeText(getActivity(),"Email pattern doesn't match",Toast.LENGTH_SHORT);

        }

    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText().toString())){
            if(!TextUtils.isEmpty(password.getText().toString())){

            }else {

            }
        }else{

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
