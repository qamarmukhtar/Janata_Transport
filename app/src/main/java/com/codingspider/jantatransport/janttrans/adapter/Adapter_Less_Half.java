package com.codingspider.jantatransport.janttrans.adapter;

/**
 * Created by NikhilRaichur on 06-01-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.activities.Book_Less_Share;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import java.util.Collections;
import java.util.List;

public class Adapter_Less_Half extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    List<DataCate> data= Collections.emptyList();
    DataCate current;
    int currentPos=0;

    String getID, getName, getFrom, getTo, getKm, getPrice, getDate, getTime, getBookId,getest_time;

    // create constructor to innitilize context and data sent from MainActivity
    public Adapter_Less_Half(Context context, List<DataCate> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.fragment_two_eat, parent,false);
        Adapter_Less_Half.MyHolder holder=new Adapter_Less_Half.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataCate current=data.get(position);

        myHolder.textCateId.setText(current.two_cate_Id);
        myHolder.bookid.setText(current.two_book_id);
        myHolder.textCateName.setText(current.two_fname1);

        myHolder.textBookType.setText(current.two_bookType);

        myHolder.from.setText(current.two_loc_from);
        myHolder.to.setText(current.two_loc_to);
        myHolder.est_time.setText(current.two_est_time);
        myHolder.KM.setText(current.two_distance);
        myHolder.price.setText(current.two_price);
        myHolder.date1.setText(current.two_bookDate);
        myHolder.time2.setText(current.two_bookTime);

        System.out.println("Two_two_cate_Id" +current.two_cate_Id);
        System.out.println("Two_two_book_id"+current.two_book_id);
        System.out.println("Two_two_fname1"+current.two_fname1);
        System.out.println("Two_two_bookType"+current.two_bookType);
        System.out.println("Two_two_loc_from"+current.two_loc_from);
        System.out.println("Two_two_loc_to"+current.two_loc_to);
        System.out.println("Two_two_est_time"+current.two_est_time);
        System.out.println("Two_two_distance"+current.two_distance);
        System.out.println("Two_two_price"+current.two_price);
        System.out.println("Two_two_bookDate"+current.two_bookDate);
        System.out.println("Two_two_bookTime"+current.two_bookTime);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        
        TextView textCateId;
        TextView bookid;
        TextView textCateName;
        TextView textBookType;

        TextView from;
        TextView to;

        TextView est_time;
        TextView KM;
        TextView price;
        TextView date1;
        TextView time2;

        Button bookNow;
        View ivFish;
        //TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {

            super(itemView);
            
            textCateId= (TextView) itemView.findViewById(R.id.textTwoCateId);
            bookid= (TextView) itemView.findViewById(R.id.textTwobookId);
            textCateName= (TextView) itemView.findViewById(R.id.textTwoCateName);
            textBookType= (TextView) itemView.findViewById(R.id.textTwoType);

            from= (TextView) itemView.findViewById(R.id.textTwofrom);
            to= (TextView) itemView.findViewById(R.id.textTwoto);

            KM= (TextView) itemView.findViewById(R.id.textTwodistance);
            price= (TextView) itemView.findViewById(R.id.textTwoprice);

            date1= (TextView) itemView.findViewById(R.id.textTwodate);
            time2= (TextView) itemView.findViewById(R.id.textTwotime);
            est_time= (TextView) itemView.findViewById(R.id.textTwoEst_time);

            bookNow = (Button) itemView.findViewById(R.id.textTwoPhTwobook);
            bookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getID = textCateId.getText().toString();
                    getName = textCateName.getText().toString();
                    getFrom = from.getText().toString();
                    getTo = to.getText().toString();
                    getKm = KM.getText().toString();
                    getDate = date1.getText().toString();
                    getPrice = price.getText().toString();
                    getTime = time2.getText().toString();
                    getBookId = textBookType.getText().toString();
                    getest_time = est_time.getText().toString();

                    System.out.println("getID =" + getID);
                    System.out.println("getName =" + getName);
                    System.out.println("getFrom =" + getFrom);
                    System.out.println("getTo =" + getTo);
                    System.out.println("getKm =" + getKm);
                    System.out.println("getDate =" + getDate);
                    System.out.println("getPrice =" + getPrice);
                    System.out.println("getTime =" + getTime);
                    System.out.println("getBookId =" + getBookId);
                    System.out.println("getest_time =" + getest_time);

                    Intent i = new Intent(v.getContext(), Book_Less_Share.class);

                    i.putExtra("putID", getID);
                    i.putExtra("putName", getName);
                    i.putExtra("putFrom", getFrom);
                    i.putExtra("putTo", getTo);
                    i.putExtra("putKm", getKm);
                    i.putExtra("putDate", getDate);
                    i.putExtra("putPrice", getPrice);
                    i.putExtra("putTime", getTime);
                    i.putExtra("putType", getBookId);
                    i.putExtra("putest_time", getest_time);

                    context.startActivity(i);
                }
            });

            ivFish= (View) itemView.findViewById(R.id.ivFish);

            //textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            //Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

        }
    }

}