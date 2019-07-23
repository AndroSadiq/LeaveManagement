package com.example.dayoff.employeeViewActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dayoff.Admin.ChangePasswordActivity;
import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.R;
import com.example.dayoff.model.EmpSignUp;
import com.example.dayoff.model.SignUp;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import spencerstudios.com.bungeelib.Bungee;

public class EmpChangePasswordActivity extends AppCompatActivity {
    EditText edtCurrentPassword;
    EditText edtNewPassword,edtConfirmPassword;
    Button btnChange;
    ImageView ivCurrentPass,ivNewPass,ivConfirmPass;
    TextInputLayout tilCurrentPass,tilNewPass,tilConfirmPass;
    int f1=0,f2=0,f3=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_change_password);
        edtCurrentPassword = findViewById(R.id.edt_current_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        ivCurrentPass = findViewById(R.id.iv_current_pass);
        ivNewPass = findViewById(R.id.iv_new_pass);
        ivConfirmPass = findViewById(R.id.iv_confirm_pass);
        tilCurrentPass = findViewById(R.id.input_layout_current_pass);
        tilNewPass = findViewById(R.id.input_layout_new_password);
        tilConfirmPass = findViewById(R.id.input_layout_confirm_password);
        ivCurrentPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f1==0)
                {
                    ivCurrentPass.setImageResource(R.drawable.ic_visibility_black_24dp);
                    edtCurrentPassword.setTransformationMethod(null);
                    edtCurrentPassword.setSelection(edtCurrentPassword.length());
                    f1=1;
                }

                else
                {
                    ivCurrentPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    edtCurrentPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtCurrentPassword.setSelection(edtCurrentPassword.length());
                    f1=0;
                }
            }
        });

        ivNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f2 == 0)
                {
                    ivNewPass.setImageResource(R.drawable.ic_visibility_black_24dp);
                    edtNewPassword.setTransformationMethod(null);
                    edtNewPassword.setSelection(edtNewPassword.length());
                    f2=1;
                }

                else
                {
                    ivNewPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    edtNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtNewPassword.setSelection(edtNewPassword.length());
                    f2=0;
                }
            }
        });

        ivConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f3 == 0)
                {
                    ivConfirmPass.setImageResource(R.drawable.ic_visibility_black_24dp);
                    edtConfirmPassword.setTransformationMethod(null);
                    edtConfirmPassword.setSelection(edtConfirmPassword.length());
                    f3=1;
                }

                else
                {
                    ivConfirmPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtConfirmPassword.setSelection(edtConfirmPassword.length());
                    f3=0;
                }
            }
        });
        btnChange = findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(EmpLoginActivity.myEmpPref,MODE_PRIVATE);
                final String uid = sharedPreferences.getString(EmpLoginActivity.Empuid,null);
              final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmpSignUp");
                databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        EmpSignUp empSignUp = dataSnapshot.getValue(EmpSignUp.class);
                        String img = empSignUp.getImageUrl();
                        String name = empSignUp.getName();
                        String email = empSignUp.getUsername();
                        String phone = empSignUp.getPhone();
                        String pass = empSignUp.getPassword();
                        String status = empSignUp.getStatus();
                        if (pass.equals(edtCurrentPassword.getText().toString()))
                        {
                            tilCurrentPass.setError(null);
                            tilNewPass.setError(null);
                            tilConfirmPass.setError(null);
                            if (edtNewPassword.getText().toString().equals(""))
                            {
                                tilNewPass.setError("Enter New Password !!!");
                            }
                            else
                            {
                                tilNewPass.setError(null);
                                if (edtConfirmPassword.getText().toString().equals(""))
                                {
                                    tilConfirmPass.setError("Enter Confirm Password");
                                }
                                else
                                {
                                    tilConfirmPass.setError(null);
                                    if (edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString()))
                                    {
                                        tilConfirmPass.setError(null);
                                        EmpSignUp empSignUp1 = new EmpSignUp(img,name, email,phone, edtNewPassword.getText().toString(),status);
                                        databaseReference.child(uid).setValue(empSignUp1);
                                        View view = getLayoutInflater().inflate(R.layout.emp_dialog_change_password,null);
                                        final Dialog dialog = new Dialog(EmpChangePasswordActivity.this);
                                        Button btnOk = view.findViewById(R.id.btn_ok);
                                        dialog.setContentView(view);
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.show();
                                        btnOk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                                Bungee.zoom(EmpChangePasswordActivity.this);
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        tilConfirmPass.setError("Enter Correct Password !!!");
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (edtCurrentPassword.getText().toString().equals(""))
                            {
                                tilCurrentPass.setError("Enter Password !!!");
                            }
                            else
                            {
                                tilCurrentPass.setErrorEnabled(true);
                                tilCurrentPass.setError("Incorrect Password !!!");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }
}
