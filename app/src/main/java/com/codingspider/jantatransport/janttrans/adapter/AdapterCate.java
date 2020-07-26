package com.codingspider.jantatransport.janttrans.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.book.BookVehicle;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import java.util.Collections;
import java.util.List;

public class AdapterCate extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterView.OnItemClickListener{

    private Context context;
    private LayoutInflater inflater;
    List<DataCate> data= Collections.emptyList();
    DataCate current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterCate(Context context, List<DataCate> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.main_container_cate, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataCate current=data.get(position);
        myHolder.textCateId.setText(current.cate_Id);
        myHolder.textCateName.setText(current.cate_Name);
        myHolder.textphoneId.setText(current.phones);
        myHolder.textnameId.setText(current.names);

        System.out.println("ADPT_ID" +current.cate_Id);
        System.out.println("ADPT_NAME"+current.cate_Name);
        System.out.println("ADPT_PHONES"+current.phones);
        System.out.println("ADPT_NAMES"+current.names);

        // load image into imageview using glide
        Glide.with(context).load("http://jt.codingspider.com/" + current.cate_Image)
                .placeholder(R.drawable.ic_error)
                .error(R.drawable.ic_error)
                .into(myHolder.ivFish);
        System.out.println("Glide_IMAGE" + current.cate_Image);
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

        TextView textCateId;
        TextView textCateName, textphoneId, textnameId;
        ImageView ivFish;
        String CateId,phone1,CateName,name1;

        //TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {

            super(itemView);
            textCateId= (TextView) itemView.findViewById(R.id.textCateId);
            //CateId = String.valueOf(textCateId);
            textphoneId = (TextView)itemView.findViewById(R.id.textphoneId);
            textnameId = (TextView)itemView.findViewById(R.id.textnameId);
            textCateName= (TextView) itemView.findViewById(R.id.textCateName);
            ivFish= (ImageView) itemView.findViewById(R.id.ivFish);

            //textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            CateId = textCateId.getText().toString();
            System.out.println("CateId"+CateId);

            phone1 = textphoneId.getText().toString();
            System.out.println("phone1"+phone1);

            CateName = textCateName.getText().toString();
            System.out.println("CateName"+CateName);

            name1 = textnameId.getText().toString();
            System.out.println("name1"+name1);

           // Intent i = new Intent(v.getContext(), BookActivity.class);
            Intent i = new Intent(v.getContext(), BookVehicle.class);
            i.putExtra("key", CateId);
            i.putExtra("key1",phone1);
            i.putExtra("key2",CateName);
            i.putExtra("key3",name1);

            System.out.println("key"+CateId);
            System.out.println("key1"+phone1);
            System.out.println("key2"+CateName);
            System.out.println("key3"+name1);

            context.startActivity(i);
        }

    }

}
