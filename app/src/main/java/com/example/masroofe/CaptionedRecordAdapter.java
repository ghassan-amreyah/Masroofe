package com.example.masroofe;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaptionedRecordAdapter extends RecyclerView.Adapter<CaptionedRecordAdapter.ViewHolder> {
    private Context context;
    private List<Record> items;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.record_card,
                parent,
                false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Record record = items.get(position);
        CardView cardView = holder.cardView;
        TextView text = (TextView)cardView.findViewById(R.id.textView);
        text.setText("Account: "+record.getAccount_name()+", Amount: "+record.getAmount()+", Date: "+record.getDate());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

}
