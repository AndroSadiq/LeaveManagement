package com.example.dayoff.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dayoff.R;
import com.example.dayoff.adapter.MySeconFragAdapter;
import com.example.dayoff.model.RequestHoliday;
import com.example.dayoff.model.SecondFrag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SecondFragment extends Fragment {
     ListView listView;
     MySeconFragAdapter mySeconFragAdapter;
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<String> reasonList = new ArrayList();
    ArrayList<String> starDateList = new ArrayList();
    ArrayList<String> endDateList = new ArrayList();
    ArrayList<String> statusList = new ArrayList<>();
    ArrayList<SecondFrag> secondFragArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_two,container,false);

         listView = view.findViewById(R.id.lv_secondFrag);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("RequestHoliday");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                secondFragArrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    RequestHoliday requestHoliday = snapshot.getValue(RequestHoliday.class);
                    keyList.add(snapshot.getKey());
                    starDateList.add(requestHoliday.getFromDate());
                    endDateList.add(requestHoliday.getToDate());
                    reasonList.add(requestHoliday.getReason());
                    statusList.add(requestHoliday.getStatus());
                    mySeconFragAdapter.notifyDataSetChanged();
                }
                for (int i=0;i<starDateList.size();i++)
                {
                   SecondFrag secondFrag = new SecondFrag(R.drawable.ic_event_busy_black_24dp,reasonList.get(i),starDateList.get(i),endDateList.get(i));
                   secondFragArrayList.add(secondFrag);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mySeconFragAdapter = new MySeconFragAdapter(secondFragArrayList,getContext(),keyList);
        listView.setAdapter(mySeconFragAdapter);
        mySeconFragAdapter.notifyDataSetChanged();
        return view;
    }
}
