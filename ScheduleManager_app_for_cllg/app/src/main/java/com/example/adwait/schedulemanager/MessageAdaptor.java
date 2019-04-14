package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.MessageHolder> {


    private Context context;
    private List<Message> msgs;
    static Message sample;
    public MessageAdaptor(Context context,List <Message>msgs)
    {
        this.context=context;
        this.msgs=msgs;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message,null);
        MessageAdaptor.MessageHolder holder = new MessageAdaptor.MessageHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        sample = msgs.get(position);
        holder.message.setText(sample.getMessage());
        holder.title.setText(sample.getTitle());
        if(!sample.getImage().equals("none"))
        {
            MessageHolder.view_img.setVisibility(View.VISIBLE);

            MessageHolder.view_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Database.post_image=sample.getImage();
                    Intent intent = new Intent(context,View_image.class);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }


    static class MessageHolder extends RecyclerView.ViewHolder{
        TextView message,title;
        static Button view_img;

        public MessageHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.messagedata);
            title = (TextView) itemView.findViewById(R.id.Title);
            view_img = (Button) itemView.findViewById(R.id.view_image);


            view_img.setVisibility(View.INVISIBLE);


        }
    }
}


