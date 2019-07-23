package com.example.dayoff.Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dayoff.R;
import com.example.dayoff.model.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    Button btnLogin,btnSignUp;
    ImageView imageView;
    TextView tvForgotPass;
    int flag=0;
    ProgressDialog progressDialog;
    public static String myPref="MyPref";
    public static String username="Username";
    public static String companyName="CompanyName";
    public static String phone="Phone";
    public static String password="Password";
    public static String imageUrl="ImageUrl";
    public static String uid ="UserId";
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> passwordList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername=findViewById(R.id.edt_user);
        edtPassword=findViewById(R.id.edt_password);
        btnLogin=findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);
        imageView=findViewById(R.id.iv);
        tvForgotPass=findViewById(R.id.tv_forgot);
        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminSignUp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    SignUp sign = snapshot.getValue(SignUp.class);
                    list.add(sign.getPhone());
                    passwordList.add(sign.getPassword());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                {
                    imageView.setImageResource(R.drawable.ic_visibility_black_24dp);
                    edtPassword.setTransformationMethod(null);
                    flag=1;
                    edtPassword.setSelection(edtPassword.length());
                }
                else
                {
                    imageView.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    flag=0;
                    edtPassword.setSelection(edtPassword.length());
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginData();
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             View view = getLayoutInflater().inflate(R.layout.dialog_lay_forgot_pass,null);
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                final EditText edtPhone = view.findViewById(R.id.edt_phone_no);
                Button btnSendNo = view.findViewById(R.id.btn_send_no);
                ImageView ivClose = view.findViewById(R.id.iv_close_no);
                dialog.setContentView(view);
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                btnSendNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int f=0;
                        for (int i = 0;i<list.size();i++)
                        {
                            if (edtPhone.getText().toString().equals(list.get(i)))
                            {
                                f = 1;
                                Toast.makeText(LoginActivity.this, "Password is :"+passwordList.get(i), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                             }
                        }
                        if (f == 0)
                        {
                            Toast.makeText(LoginActivity.this, "Phone number is not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    Bungee.zoom(LoginActivity.this);
                }
        });
    }

    private void loginData()
    {
       final String user = edtUsername.getText().toString();
       final String pass = edtPassword.getText().toString();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("AdminSignUp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int f=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                   SignUp signUp= snapshot.getValue(SignUp.class);
                    if (user.equals(signUp.getUsername()) && pass.equals(signUp.getPassword()))
                    {
                       SharedPreferences sharedPreferencesAutoLogin = getSharedPreferences(myPref,MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferencesAutoLogin.edit();
                        editor.putString(username,signUp.getUsername());
                        editor.putString(companyName,signUp.getCompanyName());
                        editor.putString(imageUrl,signUp.getImageUrl());
                        editor.putString(uid,snapshot.getKey());
                        editor.putString(phone,signUp.getPhone());
                        editor.putString(password,signUp.getPassword());
                        editor.apply();
                        Intent intent=new Intent(LoginActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        /*progressDialog =new ProgressDialog(LoginActivity.this);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();*/
                        Bungee.zoom(LoginActivity.this);
                        f=1;
                        finish();
                        break;
                    }
                }
                if (f==0)
                {
                    Toast.makeText(LoginActivity.this, "Username or Password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    /*@Override
    protected void onStop() {
        super.onStop();
        if(progressDialog!=null){
            progressDialog.dismiss();
        }*/
    //}
}
