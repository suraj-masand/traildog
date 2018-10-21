package com.traildog.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.traildog.app.model.Treats;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {
    private List<Treats> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView value, something;
        public MyViewHolder(View view) {
            super(view);
            value = view.findViewById(R.id.row_value);
            something = view.findViewById(R.id.row_something);
        }
    }

    public WalletAdapter(List<Treats> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallet_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Treats treat = list.get(position);
        holder.value.setText(treat.getValue());
        holder.something.setText("something");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

