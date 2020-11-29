package com.example.shopnow.ForgotPassword;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.PatternMatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopnow.R;
import com.example.shopnow.Register.Fragments.SignInFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {

private TextView reset_email;
private Button reset_button;
private TextView back_page;
private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FrameLayout parentframelayout;
    private FirebaseAuth firebaseAuth;
    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        reset_email=view.findViewById(R.id.reset_email);
        reset_button=view.findViewById(R.id.reset_password_button);
        back_page=view.findViewById(R.id.back_to_sign_in_page);
        parentframelayout=getActivity().findViewById(R.id.framelayout_register);
        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reset_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_button.setEnabled(false);

               firebaseAuth.sendPasswordResetEmail(reset_email.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Reset Link has been sucessfully sent to your email",Toast.LENGTH_SHORT).show();
                               }else{
                                   String error=task.getException().getMessage();
                                   Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT);
                               }
                           }
                       });
            }
        });
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void authentication() {
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(reset_email.getText())){
            reset_button.setEnabled(true);

        }else{
            reset_button.setEnabled(false);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
