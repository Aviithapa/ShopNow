package com.example.shopnow.Register.Fragments;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopnow.MainActivity;
import com.example.shopnow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private TextView alreadyHaveAccount;
    private FrameLayout parentframelayout;
    private EditText email;
    private EditText fullname;
    private EditText password;
    private EditText conform_password;
    private EditText contact_number;
    private Button sign_up;
    private FirebaseAuth firebaseAuth;
    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private FirebaseFirestore firebasefirestore;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAccount=view.findViewById(R.id.already_have_account);
        parentframelayout=getActivity().findViewById(R.id.framelayout_register);
        email=view.findViewById(R.id.sign_up_emaill);
        fullname=view.findViewById(R.id.sign_up_name);
        password=view.findViewById(R.id.sign_up_password);
        conform_password=view.findViewById(R.id.sign_up_confirm_password);
        contact_number=view.findViewById(R.id.sign_up_number);
        sign_up=view.findViewById(R.id.sign_up_button);
        firebaseAuth=FirebaseAuth.getInstance();
        firebasefirestore=FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
            setFragment(new SignInFragment());
        }

    });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
 
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
validateInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        contact_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateInputs();
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
                validateInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        conform_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //To save the data in database
                checkEmailAndPassword();
            }

        });
    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailPattern)){
            if (password.getText().toString().equals(conform_password.getText().toString())){
                sign_up.setEnabled(false);
                sign_up.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()){

                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("fullname",fullname.getText().toString());
                                    userdata.put("contact_number",contact_number.getText().toString());

                                    firebasefirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()){
                                                        Intent mainIntent= new Intent(getActivity(), MainActivity.class);
                                                        startActivity(mainIntent);
                                                        getActivity().finish();
                                                    }else {
                                                        sign_up.setEnabled(true);
                                                        sign_up.setTextColor(Color.rgb(255,255,255));
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error,Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });




                                }else{
                                    sign_up.setEnabled(true);
                                    sign_up.setTextColor(Color.rgb(255,255,255));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(), error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                conform_password.setError("Password doesn't match");
            }
        }else{
            email.setError("Invalid Email");
        }
    }

    private void validateInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(fullname.getText())){
                if(!TextUtils.isEmpty(contact_number.getText())){
                    if(!TextUtils.isEmpty(password.getText()) && password.length()>=8){
                        if (!TextUtils.isEmpty(conform_password.getText())){
                            sign_up.setEnabled(true);
                            sign_up.setTextColor(Color.rgb(255,255,255));
                        }else{
                            Toast.makeText(getActivity(),"Please fill all the above content",Toast.LENGTH_SHORT);
                        }

                    }else{
                        Toast.makeText(getActivity(),"Please fill all the above content",Toast.LENGTH_SHORT);
                    }
                }else{
                    Toast.makeText(getActivity(),"Please fill all the above content",Toast.LENGTH_SHORT);
                }
            }else{
                Toast.makeText(getActivity(),"Please fill all the above content",Toast.LENGTH_SHORT);

        }}else{
            Toast.makeText(getActivity(),"Please fill all the above content",Toast.LENGTH_SHORT);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
