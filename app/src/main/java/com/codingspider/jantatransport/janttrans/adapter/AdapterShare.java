package com.codingspider.jantatransport.janttrans.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.book.ShareActivity;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import java.util.Collections;
import java.util.List;

public class AdapterShare extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterView.OnItemClickListener  {

    private Context context;
    private LayoutInflater inflater;
    List<DataCate> data= Collections.emptyList();
    DataCate current;
    int currentPos=0;
    String S_Vname, S_CateId, S_from, S_to, S_dist, S_phone, S_date, S_time, S_estTime, S_price, S_bType, S_Status;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterShare(Context context, List<DataCate> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.book_share_container_item, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataCate current=data.get(position);

        myHolder.textCateName.setText(current.cate_Name);

        myHolder.textShareId.setText(current.Share_id);
        myHolder.textBookId.setText(current.Share_book_id);
        myHolder.textCateId.setText(current.Share_category_Id);
        myHolder.textPhoneN.setText(current.Share_phone);
        myHolder.textFrom.setText(current.Share_loc_from);
        myHolder.textTo.setText(current.Share_loc_to);
        myHolder.textKM.setText(current.Share_distance);
        myHolder.textPrice.setText(current.Share_price);
        myHolder.textEst_time2.setText(current.Share_est_time);
        myHolder.textDate1.setText(current.Share_bookDate);
        myHolder.textBooktime.setText(current.Share_bookTime);
        myHolder.textBookType.setText(current.Share_bookType);
        myHolder.textBookStatus.setText(current.Share_status);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textCateName, textShareId, textBookId, textCateId, textPhoneN, textFrom, textTo, textKM, textPrice, textEst_time2, textDate1, textBooktime, textBookType, textBookStatus ;
        Button bookNow;
        ImageView ivFish;

        //TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {

            super(itemView);

            textCateName= (TextView) itemView.findViewById(R.id.S_textCateName);

            textShareId= (TextView) itemView.findViewById(R.id.S_textShareId);
            textBookId= (TextView) itemView.findViewById(R.id.S_textBookingId);
            textCateId= (TextView) itemView.findViewById(R.id.S_textCateId);
            textPhoneN= (TextView) itemView.findViewById(R.id.S_textPhoneN);
            textFrom= (TextView) itemView.findViewById(R.id.S_from);
            textTo= (TextView) itemView.findViewById(R.id.S_to);
            textKM= (TextView) itemView.findViewById(R.id.S_KM1);
            textPrice= (TextView) itemView.findViewById(R.id.S_price);
            textEst_time2= (TextView) itemView.findViewById(R.id.S_Esttime2);
            textDate1= (TextView) itemView.findViewById(R.id.S_date);
            textBookType= (TextView) itemView.findViewById(R.id.S_textBookType);
            textBooktime= (TextView) itemView.findViewById(R.id.S_textBooktime);
            textBookStatus= (TextView) itemView.findViewById(R.id.S_textStatusV);

            bookNow = (Button) itemView.findViewById(R.id.S_bookRecy);
            bookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    S_Vname = textCateName.getText().toString();
                    System.out.println("S_Vname ="+S_Vname);

                    S_CateId = textCateId.getText().toString();
                    System.out.println("S_CateId ="+S_CateId);

                    S_from = textFrom.getText().toString();
                    System.out.println("S_from ="+S_from);

                    S_to = textTo.getText().toString();
                    System.out.println("S_to ="+S_to);

                    S_dist = textKM.getText().toString();
                    System.out.println("S_dist ="+S_dist);

                    S_phone = textPhoneN.getText().toString();
                    System.out.println("S_phone ="+S_phone);

                    S_date = textDate1.getText().toString();
                    System.out.println("S_date ="+S_date);

                    S_time = textBooktime.getText().toString();
                    System.out.println("S_Vname ="+S_time);

                    S_estTime = textEst_time2.getText().toString();
                    System.out.println("S_estTime ="+S_estTime);

                    S_price = textPrice.getText().toString();
                    System.out.println("S_price ="+S_price);

                    S_bType = textBookType.getText().toString();
                    System.out.println("S_bType ="+S_bType);

                    S_Status = textBookStatus.getText().toString();
                    System.out.println("S_Status ="+S_Status);

                    Intent i = new Intent(v.getContext(), ShareActivity.class);
                    i.putExtra("S_Vname",S_Vname);
                    i.putExtra("S_CateId",S_CateId);
                    i.putExtra("S_from",S_from);
                    i.putExtra("S_to",S_to);
                    i.putExtra("S_dist",S_dist);
                    i.putExtra("S_phone",S_phone);
                    i.putExtra("S_date",S_date);
                    i.putExtra("S_time",S_time);
                    i.putExtra("S_estTime",S_estTime);
                    i.putExtra("S_price",S_price);
                    i.putExtra("S_bType",S_bType);
                    i.putExtra("S_Status",S_Status);
                    i.putExtra("S_DataStatus","0");
                    context.startActivity(i);
                }
            });
            ivFish= (ImageView) itemView.findViewById(R.id.ivFish);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            S_Vname = textCateName.getText().toString();
            System.out.println("S_Vname ="+S_Vname);

            S_CateId = textCateId.getText().toString();
            System.out.println("S_CateId ="+S_CateId);

            S_from = textFrom.getText().toString();
            System.out.println("S_from ="+S_from);

            S_to = textTo.getText().toString();
            System.out.println("S_to ="+S_to);

            S_dist = textKM.getText().toString();
            System.out.println("S_dist ="+S_dist);

            S_phone = textPhoneN.getText().toString();
            System.out.println("S_phone ="+S_phone);

            S_date = textDate1.getText().toString();
            System.out.println("S_date ="+S_date);

            S_time = textBooktime.getText().toString();
            System.out.println("S_Vname ="+S_time);

            S_estTime = textEst_time2.getText().toString();
            System.out.println("S_estTime ="+S_estTime);

            S_price = textPrice.getText().toString();
            System.out.println("S_price ="+S_price);

            S_bType = textBookType.getText().toString();
            System.out.println("S_bType ="+S_bType);

            S_Status = textBookStatus.getText().toString();
            System.out.println("S_Status ="+S_Status);

            Intent i = new Intent(v.getContext(), ShareActivity.class);
            i.putExtra("S_Vname",S_Vname);
            i.putExtra("S_CateId",S_CateId);
            i.putExtra("S_from",S_from);
            i.putExtra("S_to",S_to);
            i.putExtra("S_dist",S_dist);
            i.putExtra("S_phone",S_phone);
            i.putExtra("S_date",S_date);
            i.putExtra("S_time",S_time);
            i.putExtra("S_estTime",S_estTime);
            i.putExtra("S_price",S_price);
            i.putExtra("S_bType",S_bType);
            i.putExtra("S_Status",S_Status);
            i.putExtra("S_DataStatus","0");
            context.startActivity(i);
        }
    }

}
