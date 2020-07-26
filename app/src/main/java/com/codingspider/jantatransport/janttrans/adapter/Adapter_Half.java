package com.codingspider.jantatransport.janttrans.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.activities.BookShare;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import java.util.Collections;
import java.util.List;

/**
 * Created by CS-001 on 06-Jan-17.
 */
public class Adapter_Half extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataCate> data= Collections.emptyList();
    DataCate current;
    int currentPos=0;

    String getID, getName, getFrom, getTo, getKm, getPrice, getDate, getTime, getBookId,getest_time;

    // create constructor to innitilize context and data sent from MainActivity
    public Adapter_Half(Context context, List<DataCate> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_fish, parent,false);
        Adapter_Half.MyHolder holder=new Adapter_Half.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataCate current=data.get(position);

        myHolder.textCateId.setText(current.one_cate_Id);
        myHolder.bookid.setText(current.one_book_id);
        myHolder.textCateName.setText(current.one_fname1);

        myHolder.textBookType.setText(current.one_bookType);

        myHolder.from.setText(current.one_loc_from);
        myHolder.to.setText(current.one_loc_to);
        myHolder.est_time.setText(current.one_est_time);
        myHolder.KM.setText(current.one_distance);
        myHolder.price.setText(current.one_price);
        myHolder.date1.setText(current.one_bookDate);
        myHolder.time2.setText(current.one_bookTime);

        System.out.println("one_one_cate_Id" +current.one_cate_Id);
        System.out.println("one_one_book_id"+current.one_book_id);
        System.out.println("one_one_fname1"+current.one_fname1);
        System.out.println("one_one_bookType"+current.one_bookType);
        System.out.println("one_one_loc_from"+current.one_loc_from);
        System.out.println("one_one_loc_to"+current.one_loc_to);
        System.out.println("one_one_est_time"+current.one_est_time);
        System.out.println("one_one_distance"+current.one_distance);
        System.out.println("one_one_price"+current.one_price);
        System.out.println("one_one_bookDate"+current.one_bookDate);
        System.out.println("one_one_bookTime"+current.one_bookTime);
    }

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
        ImageView ivFish;

        // create constructor to get widget reference
        public MyHolder(View itemView) {

            super(itemView);

            textCateId= (TextView) itemView.findViewById(R.id.textOneCateId);
            bookid= (TextView) itemView.findViewById(R.id.textOnebookId);
            textCateName= (TextView) itemView.findViewById(R.id.textOneCateName);
            textBookType= (TextView) itemView.findViewById(R.id.textOneType);

            from= (TextView) itemView.findViewById(R.id.textOnefrom);
            to= (TextView) itemView.findViewById(R.id.textOneto);

            KM= (TextView) itemView.findViewById(R.id.textOnedistance);
            price= (TextView) itemView.findViewById(R.id.textOneprice);

            date1= (TextView) itemView.findViewById(R.id.textOnedate);
            time2= (TextView) itemView.findViewById(R.id.textOnetime);
            est_time= (TextView) itemView.findViewById(R.id.textOneEst_time);

            bookNow = (Button) itemView.findViewById(R.id.textOnePhonebook);
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
                    getBookId = bookid.getText().toString();
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

                    Intent i = new Intent(v.getContext(), BookShare.class);

                    i.putExtra("putID", getID);
                    i.putExtra("putName", getName);
                    i.putExtra("putFrom", getFrom);
                    i.putExtra("putTo", getTo);
                    i.putExtra("putKm", getKm);
                    i.putExtra("putDate", getDate);
                    i.putExtra("putPrice", getPrice);
                    i.putExtra("putTime", getTime);
                    i.putExtra("getBookId", getBookId);
                    i.putExtra("putest_time", getest_time);

                    context.startActivity(i);

                }
            });
          /*  ivFish= (ImageView) itemView.findViewById(R.id.ivFish);

            itemView.setOnClickListener(this);*/
        }


        @Override
        public void onClick(View view) {

            //Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

           /* getID = textCateId.getText().toString();
            getName = textCateName.getText().toString();
            getFrom = from.getText().toString();
            getTo = to.getText().toString();
            getKm = KM.getText().toString();
            getDate = date1.getText().toString();
            getPrice = price.getText().toString();
            getTime = time2.getText().toString();
            getBookId = bookid.getText().toString();
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

            Intent i = new Intent(view.getContext(), BookShare.class);

            i.putExtra("putID", getID);
            i.putExtra("putName", getName);
            i.putExtra("putFrom", getFrom);
            i.putExtra("putTo", getTo);
            i.putExtra("putKm", getKm);
            i.putExtra("putDate", getDate);
            i.putExtra("putPrice", getPrice);
            i.putExtra("putTime", getTime);
            i.putExtra("getBookId", getBookId);
            i.putExtra("putest_time", getest_time);

            context.startActivity(i);*/

        }
    }

}