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
        public TextView name, value;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.row_name);
            value = view.findViewById(R.id.row_value);
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
        holder.name.setText(treat.getName());
        holder.value.setText(treat.getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
