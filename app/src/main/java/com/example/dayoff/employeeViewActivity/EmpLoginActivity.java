package com.example.dayoff.employeeViewActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dayoff.Admin.HomePageActivity;
import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.R;
import com.example.dayoff.model.EmpSignUp;
import com.example.dayoff.model.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

import static com.example.dayoff.Admin.LoginActivity.myPref;

public class EmpLoginActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    Button btnLogin,btnSignUp;
    ImageView imageView;
    TextView tvForgotPass;
    int flag=0;
    ProgressDialog progressDialog;
    public static String myEmpPref="MyEmpPref";
    public static String Empusername="EmpUsername";
    public static String Empname="EmpName";
    public static String Empphone="EmpPhone";
    public static String Emppassword="Password";
    public static String EmpimageUrl="EmpImageUrl";
    public static String Empuid ="EmpUserId";
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> passwordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        edtUsername=findViewById(R.id.edt_empuser);
        edtPassword=findViewById(R.id.edt_emppassword);
        btnLogin=findViewById(R.id.btn_emp_login);
        btnSignUp = findViewById(R.id.btn_emp_sign_up);
        imageView=findViewById(R.id.iv_emp);
        tvForgotPass=findViewById(R.id.tv_emp_forgot);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmpSignUp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    EmpSignUp empSign = snapshot.getValue(EmpSignUp.class);
                    arrayList.add(empSign.getPhone());
                    passwordList.add(empSign.getPassword());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                 loginEmpData();
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_lay_forgot_pass,null);
                final Dialog dialog = new Dialog(EmpLoginActivity.this);
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
                        for (int i = 0;i<arrayList.size();i++)
                        {
                            if (edtPhone.getText().toString().equals(arrayList.get(i)))
                            {
                                f = 1;
                                Toast.makeText(EmpLoginActivity.this, "Password is :"+passwordList.get(i), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                        if (f == 0)
                        {
                            Toast.makeText(EmpLoginActivity.this, "Phone number is not correct", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(EmpLoginActivity.this,EmpSignUpActivity.class);
                startActivity(intent);
                Bungee.zoom(EmpLoginActivity.this);
            }
        });
    }
    private void loginEmpData() {
        final String user = edtUsername.getText().toString();
        final String pass = edtPassword.getText().toString();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("EmpSignUp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int f=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    EmpSignUp empSignUp= snapshot.getValue(EmpSignUp.class);
                    if (user.equals(empSignUp.getUsername()) && pass.equals(empSignUp.getPassword()))
                    {
                        SharedPreferences sharedPreferencesAutoLogin = getSharedPreferences(myEmpPref,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferencesAutoLogin.edit();
                        editor.putString(Empusername,empSignUp.getUsername());
                        editor.putString(Empname,empSignUp.getName());
                        editor.putString(EmpimageUrl,empSignUp.getImageUrl());
                        editor.putString(Empuid,snapshot.getKey());
                        editor.putString(Empphone,empSignUp.getPhone());
                        editor.putString(Emppassword,empSignUp.getPassword());
                        editor.apply();
                        Intent intent=new Intent(EmpLoginActivity.this, EmpHomePageActivity.class);
                        startActivity(intent);
                        /*progressDialog =new ProgressDialog(EmpLoginActivity.this);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                        */Bungee.zoom(EmpLoginActivity.this);
                        f=1;
                        finish();
                        break;
                    }
                }
                if (f==0)
                {
                    Toast.makeText(EmpLoginActivity.this, "Username or Password is incorrect!", Toast.LENGTH_SHORT).show();
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
        }
    }*/
}
