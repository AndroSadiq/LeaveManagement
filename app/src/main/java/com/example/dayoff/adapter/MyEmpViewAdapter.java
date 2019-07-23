package com.example.dayoff.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayoff.R;
import com.example.dayoff.model.EmpList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyEmpViewAdapter extends RecyclerView.Adapter<MyEmpViewAdapter.MyHolder> {

  private Context context;

   private ArrayList<EmpList> arrayList;
    ArrayList<String> keys;

    public MyEmpViewAdapter(Context context, ArrayList<EmpList> arrayList,ArrayList<String> keys) {
        this.context = context;
        this.arrayList = arrayList;
        this.keys = keys;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.emp_list_view,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.textView1.setText(arrayList.get(position).getName());
        if (arrayList.get(position).getImageUrl().equals(""))
        {
        }
        else
        {
            Picasso.get().load(arrayList.get(position).getImageUrl()).into(holder.imageView);
        }

        //        holder.textView2.setText(arrayList.get(position).getPhone());

        holder.fbtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   holder.temp=arrayList.get(position).getPhone();
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse("tel:"+holder.temp));
                   v.getContext().startActivity(intent);
                }
            });
        holder.fbtnMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.temp=arrayList.get(position).getPhone();
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("sms:"+holder.temp));
                    v.getContext().startActivity(intent);
                    }
            });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String key = keys.get(position);
                        arrayList.remove(position);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("EmpSignUp").child(key).removeValue();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.setTitle("Are You Sure ?");
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });
}
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class  MyHolder extends RecyclerView.ViewHolder
     {
         ImageView imageView;
         TextView textView1;
         TextView textView2;
         String temp;
         FloatingActionButton fbtnCall;
         FloatingActionButton fbtnMsg;
         CardView cardView;
    public MyHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_emp);
        textView1 = itemView.findViewById(R.id.tv_emp_name);
        //textView2   = itemView.findViewById(R.id.tv_temp);
        fbtnCall = itemView.findViewById(R.id.fbtn_call);
        fbtnMsg = itemView.findViewById(R.id.fbtn_msg);
        cardView = itemView.findViewById(R.id.card_view);
    }
     }
}
