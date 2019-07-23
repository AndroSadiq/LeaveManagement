package com.example.dayoff.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dayoff.R;
import com.example.dayoff.adapter.MyEmpViewAdapter;
import com.example.dayoff.model.EmpList;
import com.example.dayoff.model.EmpSignUp;
import com.example.dayoff.model.Notifications;
import com.example.dayoff.model.SignUp;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FirstFragment extends Fragment {
    ArrayList<EmpList> empListArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    MaterialButton btnAddEmployee;
    MyEmpViewAdapter myEmpViewAdapter;
    ArrayList<String> keys = new ArrayList();
    ArrayList<String> usernameList = new ArrayList();
    ArrayList<String> statusList = new ArrayList();
    ArrayList<String> keyList = new ArrayList();
    ArrayList<String> nameList = new ArrayList();
    ArrayList<String> phoneList = new ArrayList();
    ArrayList<String> passwordList = new ArrayList();
    ArrayList<String> imageUrlList = new ArrayList();
    ArrayList<String> tokenList = new ArrayList<>();

    public FirstFragment()
    {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container,false);
        recyclerView = view.findViewById(R.id.rv_employee_list);
        btnAddEmployee = view.findViewById(R.id.btn_add_employee);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("EmpSignUp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empListArrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    EmpSignUp empSignUp = snapshot.getValue(EmpSignUp.class);
                    usernameList.add(empSignUp.getUsername());
                    statusList.add(empSignUp.getStatus());
                    keyList.add(snapshot.getKey());
                    nameList.add(empSignUp.getName());
                    phoneList.add(empSignUp.getPhone());
                    passwordList.add(empSignUp.getPassword());
                    imageUrlList.add(empSignUp.getImageUrl());
                    tokenList.add(empSignUp.getToken());
                    if (empSignUp.getStatus().equals("added"))
                    {
                        EmpList empList = new EmpList(empSignUp.getImageUrl(),empSignUp.getName(), empSignUp.getPhone());
                        empListArrayList.add(empList);
                        keys.add(snapshot.getKey());
                        myEmpViewAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myEmpViewAdapter = new MyEmpViewAdapter(getContext(), empListArrayList,keys);
        recyclerView.setAdapter(myEmpViewAdapter);
          btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = getLayoutInflater().inflate(R.layout.dialog_addemp_lay,null);
                final Dialog dialog = new Dialog(getActivity());
                final EditText edtEnterUsername = view1.findViewById(R.id.edt_enter_emp_username);
                Button btnAddEmp = view1.findViewById(R.id.btn_add_emp);
                ImageView ivClose = view1.findViewById(R.id.iv_close);
                dialog.setContentView(view1);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                btnAddEmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkIdIntoEmpTable(edtEnterUsername.getText().toString());
                        dialog.dismiss();
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

        return view;
    }

    private void checkIdIntoEmpTable(final String username) {
                          int f=0;
                         for (int i=0;i<usernameList.size();i++)
                         {
                         if (username.equals(usernameList.get(i))) {
                                if (statusList.get(i).equals("pending"))
                                {
                                    EmpSignUp empSignUp1 = new EmpSignUp(imageUrlList.get(i),nameList.get(i), usernameList.get(i), phoneList.get(i),passwordList.get(i),"added",tokenList.get(i));
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmpSignUp");
                                    databaseReference.child(keyList.get(i)).setValue(empSignUp1);
                                    String receiver = keyList.get(i);
                                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Notifications");
                                    databaseReference1.child(receiver).push().setValue(new Notifications("Admin"));

                                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                                    f = 1;
                                    break;

                                }
                            }
                        }
                         if (f == 0)
                         {
                             Toast.makeText(getActivity(), "First Create Employee Id", Toast.LENGTH_SHORT).show();
                         }
                     }
            }