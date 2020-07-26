package com.codingspider.jantatransport.janttrans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import java.util.Collections;
import java.util.List;

public class AdapterBooked extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterView.OnItemClickListener  {

    private Context context;
    private LayoutInflater inflater;
    List<DataCate> data= Collections.emptyList();
    DataCate current;
    int currentPos=0;
    String S_Vname, S_CateId, S_from, S_to, S_dist, S_phone, S_date, S_time, S_estTime, S_price, S_bType, S_Status;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterBooked(Context context, List<DataCate> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.booked_container_item, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataCate current=data.get(position);

        myHolder.textFrom.setText(current.booked_loc_from);
        myHolder.textTo.setText(current.booked_loc_to);
        myHolder.textKM.setText(current.booked_distance);
        myHolder.textPrice.setText(current.booked_price);
        myHolder.textEst_time2.setText(current.booking_Esttime);
        myHolder.textDate1.setText(current.booked_bookDate);
        myHolder.textBooktime.setText(current.booked_bookTime);
        myHolder.textBookType.setText(current.booked_bookType);
        //myHolder.textBookStatus.setText(current.Share_status);
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

        TextView textShareId, textBookId, textCateId, textPhoneN, textFrom, textTo, textKM, textPrice, textEst_time2, textDate1, textBooktime, textBookType, textBookStatus ;
        Button bookNow;
        ImageView ivFish;

        //TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {

            super(itemView);

            //textCateName= (TextView) itemView.findViewById(R.id.S_textCateName);
            textBookType= (TextView) itemView.findViewById(R.id.booked_textBookTypeValue);
            textFrom= (TextView) itemView.findViewById(R.id.booked_from);
            textTo= (TextView) itemView.findViewById(R.id.booked_to);
            textKM= (TextView) itemView.findViewById(R.id.booked_KM);
            textDate1= (TextView) itemView.findViewById(R.id.booked_date);
            textBooktime= (TextView) itemView.findViewById(R.id.booked_time);
            textPrice= (TextView) itemView.findViewById(R.id.booked_price);
            textEst_time2= (TextView) itemView.findViewById(R.id.booked_Esttime);

            /*textShareId= (TextView) itemView.findViewById(R.id.S_textShareId);
            textBookId= (TextView) itemView.findViewById(R.id.S_textBookingId);
            textCateId= (TextView) itemView.findViewById(R.id.S_textCateId);
            textPhoneN= (TextView) itemView.findViewById(R.id.S_textPhoneN);
            textBookStatus= (TextView) itemView.findViewById(R.id.S_textStatusV);

            bookNow = (Button) itemView.findViewById(R.id.S_bookRecy);*/
            ivFish= (ImageView) itemView.findViewById(R.id.ivFish);
            itemView.setOnClickListener(this);
        }

        //cardOnclick
        @Override
        public void onClick(View v) {
        }
    }

}
