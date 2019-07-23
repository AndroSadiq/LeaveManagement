package com.example.dayoff.employeeViewActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dayoff.employeeViewActivity.EmpEditProfileActivity;

import com.example.dayoff.R;
import com.example.dayoff.model.EmpSignUp;
import com.example.dayoff.model.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import spencerstudios.com.bungeelib.Bungee;

public class EmpEditProfileActivity extends AppCompatActivity {
    CircleImageView editCircleImage;
    ImageView ivEdit;
    EditText edtName,edtUsername,edtPhone;
    Button btnSave;
    String tempPass,status;
    // DatabaseReference databaseReference;
    // SharedPreferences sharedPreferences;
    boolean flag = true;
    boolean setImage = false;
    boolean noChangeImg=false;
    Uri resultUri;
    String imageUrl;
    //SignUp signUp;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_edit_profile);

        editCircleImage = findViewById(R.id.circle_iv);
        ivEdit = findViewById(R.id.iv_edit);
        edtName = findViewById(R.id.edt_name);
        edtUsername = findViewById(R.id.edt_username);
        edtPhone = findViewById(R.id.edt_phone);
        btnSave = findViewById(R.id.btn_emp_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                   }
        });
        showData();
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag)
                {
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(EmpEditProfileActivity.this);
                }
                else
                {
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View view = layoutInflater.inflate(R.layout.dialog_ayout_remove,null);
                    final Dialog dialog = new Dialog(EmpEditProfileActivity.this);
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
                                    .start(EmpEditProfileActivity.this);
                            dialog.dismiss();
                        }
                    });
                    layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editCircleImage.setImageResource(R.drawable.ic_person_black_100dp);
                            setImage = false;
                            dialog.dismiss();
                            flag = true;
                        }
                    });
                }
            }
        });
    }
    private void updateData() {
        SharedPreferences sharedPreferences = getSharedPreferences(EmpLoginActivity.myEmpPref,MODE_PRIVATE);
        final String uid = sharedPreferences.getString(EmpLoginActivity.Empuid,null);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmpSignUp");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (setImage)
                {
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
                    final StorageReference filePath = storageReference.child(uid + ".jpg");
                    filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    imageUrl = task.getResult().toString();
                                    EmpSignUp empSignUp = new EmpSignUp(imageUrl, edtName.getText().toString(),edtUsername.getText().toString(),edtPhone.getText().toString(),tempPass,status);
                                    databaseReference.child(uid).setValue(empSignUp);
                                }
                            });
                        }
                    });
                }
                else
                {
                    if (noChangeImg)
                    {
                        imageUrl = img;
                        EmpSignUp empSignUp = new EmpSignUp(imageUrl,edtName.getText().toString(),edtUsername.getText().toString(),edtPhone.getText().toString(),tempPass,status);
                        databaseReference.child(uid).setValue(empSignUp);
                    }
                    else
                    {
                        imageUrl="";
                        EmpSignUp empSignUp = new EmpSignUp(imageUrl,edtName.getText().toString(),edtUsername.getText().toString(),edtPhone.getText().toString(),tempPass,status);
                        databaseReference.child(uid).setValue(empSignUp);
                    }
                }
                Toast.makeText(EmpEditProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                finish();
                Bungee.zoom(EmpEditProfileActivity.this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void showData() {
        SharedPreferences sharedPreferences = getSharedPreferences(EmpLoginActivity.myEmpPref,MODE_PRIVATE);
        String uid = sharedPreferences.getString(EmpLoginActivity.Empuid,null);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmpSignUp").child(uid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EmpSignUp empSignUp = dataSnapshot.getValue(EmpSignUp.class);
                if (empSignUp.getImageUrl().equals(""))
                {
                }
                else
                {
                    Picasso.get().load(empSignUp.getImageUrl()).into(editCircleImage);
                    flag = false;
                    img = empSignUp.getImageUrl();
                    noChangeImg = true;
                }
                edtName.setText(empSignUp.getName());
                edtName.setSelection(edtName.length());
                edtUsername.setText(empSignUp.getUsername());
                edtUsername.setSelection(edtUsername.length());
                edtPhone.setText(empSignUp.getPhone());
                edtPhone.setSelection(edtPhone.length());
                tempPass = empSignUp.getPassword();
                status = empSignUp.getStatus();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                editCircleImage.setImageURI(resultUri);
                flag = false;
                setImage = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
