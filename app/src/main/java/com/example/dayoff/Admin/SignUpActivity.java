package com.example.dayoff.Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dayoff.R;
import com.example.dayoff.model.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import spencerstudios.com.bungeelib.Bungee;

public class SignUpActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    ImageView ivEdit;
    EditText edtCompanyName, edtUsername, edtPassword,edtPhone, edtConfirmPassword;
    ImageView ivPassword, ivConfirmPass;
    Button btnSignUp,btnGotoLogin;
    int f1 = 0, f2 = 0;
    boolean flag = true,setImage = false;
    ProgressDialog progressDialog;
    String imageUrl;
    Uri resultUri;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        circleImageView = findViewById(R.id.circle_iv);
        ivEdit = findViewById(R.id.iv_edit);
        edtCompanyName = findViewById(R.id.edt_company_name);
        edtUsername = findViewById(R.id.edt_username);
        edtPhone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        ivPassword = findViewById(R.id.iv_pass);
        ivConfirmPass = findViewById(R.id.iv_confirm_pass);
        btnSignUp = findViewById(R.id.btn_sign);
        btnGotoLogin = findViewById(R.id.btn_goto_login);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminSignUp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot snapshot : dataSnapshot.getChildren())
                  {
                      SignUp sign = snapshot.getValue(SignUp.class);
                      list.add(sign.getUsername());
                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag)
                {
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(SignUpActivity.this);
                }
                else
                {
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View view = layoutInflater.inflate(R.layout.dialog_ayout_remove,null);
                    final Dialog dialog = new Dialog(SignUpActivity.this);
                    LinearLayout layout1 = view.findViewById(R.id.lay_1);
                    LinearLayout layout2 = view.findViewById(R.id.lay_2);
                    dialog.setContentView(view);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    layout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                                    .setAspectRatio(1, 1)
                                    .start(SignUpActivity.this);
                            dialog.dismiss();
                        }
                    });
                    layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            circleImageView.setImageResource(R.drawable.ic_person_black_100dp);
                            setImage = false;
                            dialog.dismiss();
                            flag = true;
                        }
                    });
                }
            }

        });
        ivPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f1 == 0) {
                    ivPassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                    edtPassword.setTransformationMethod(null);
                    edtPassword.setSelection(edtPassword.length());
                    f1 = 1;
                } else {
                    ivPassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtPassword.setSelection(edtPassword.length());
                    f1 = 0;
                }
            }
        });
        ivConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f2 == 0) {
                    ivConfirmPass.setImageResource(R.drawable.ic_visibility_black_24dp);
                    edtConfirmPassword.setTransformationMethod(null);
                    edtConfirmPassword.setSelection(edtConfirmPassword.length());
                    f2 = 1;
                } else {
                    ivConfirmPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtConfirmPassword.setSelection(edtConfirmPassword.length());
                    f2 = 0;
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDB();
            }
        });
        btnGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                Bungee.zoom(SignUpActivity.this);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                circleImageView.setImageURI(resultUri);
                flag = false;
                setImage = true;
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private void insertIntoDB () {
        final String companyName = edtCompanyName.getText().toString();
        final String username = edtUsername.getText().toString();
        final String phone = edtPhone.getText().toString();
        final String pass = edtPassword.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();
        TextInputLayout companyNameLayout = findViewById(R.id.tilay_company_name);
        final TextInputLayout usernameLayout = findViewById(R.id.tilay_username);
        TextInputLayout phoneLayout = findViewById(R.id.tilay_phone);
        TextInputLayout passLayout = findViewById(R.id.tilay_password);
        TextInputLayout confirmPassLayout = findViewById(R.id.tilay_confirm_password);
        if (companyName.equals("")) {
            companyNameLayout.setError("Enter Company Name !!!");
        } else {
            companyNameLayout.setError(null);
            if (username.equals("")) {
                usernameLayout.setError("Enter Username !!!");
            } else {
                usernameLayout.setError(null);
                if (phone.equals("") || phone.length() != 10) {
                    phoneLayout.setError("Enter Correct Phone Number !!!");
                } else {
                    phoneLayout.setError(null);
                    if (pass.equals("")) {
                        passLayout.setError("Enter Password !!!");
                    } else {
                        passLayout.setError(null);
                        if (confirmPass.equals("")) {
                            confirmPassLayout.setError("Enter Password");
                        } else {
                            confirmPassLayout.setError(null);
                            if (pass.equals(confirmPass)) {
                                int f=0;
                                for (int i = 0;i<list.size();i++)
                                {
                                    if (username.equals(list.get(i)))
                                    {
                                        Toast.makeText(this, "Username Already has been taken", Toast.LENGTH_SHORT).show();
                                        f = 1;
                                    }
                                }
                                        if (f == 0) {
                                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminSignUp");
                                            final String userId = databaseReference.push().getKey();
                                            if (setImage) {
                                                InputStream imageStream = null;
                                                try {
                                                    imageStream = getContentResolver().openInputStream(resultUri);
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }
                                                Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                                                byte[] byteArray = stream.toByteArray();
                                                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");
                                                final StorageReference filePath = storageReference.child(userId + ".jpg");
                                                filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Uri> task) {
                                                                imageUrl = task.getResult().toString();
                                                                SignUp signUp = new SignUp(imageUrl, companyName, username,phone, pass);
                                                                databaseReference.child(userId).setValue(signUp);
                                                            }
                                                        });
                                                    }
                                                });
                                            } else {
                                                imageUrl = "";
                                                SignUp signUp = new SignUp(imageUrl, companyName, username, phone, pass);
                                                databaseReference.child(userId).setValue(signUp);
                                            }
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            progressDialog =new ProgressDialog(SignUpActivity.this);
                                            progressDialog.setCanceledOnTouchOutside(false);
                                            progressDialog.setMessage("Loading...");
                                            progressDialog.show();
                                            finish();
                                            Bungee.zoom(SignUpActivity.this);
                                        }
                            }
                            else
                            {
                                confirmPassLayout.setError("Password is different !!!");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}

