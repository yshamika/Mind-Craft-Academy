package com.example.myapplication.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<Payment> payments;

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = payments.get(position);
        holder.bind(payment);
    }

    @Override
    public int getItemCount() {
        return payments != null ? payments.size() : 0;
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCost;
        private TextView textViewCourseName;
        private TextView textViewUserName;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCost = itemView.findViewById(R.id.textViewCost);
            textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
        }

        public void bind(Payment payment) {


            textViewUserName.setText("User Name: " + payment.getUserName());
            textViewCost.setText("Cost: " + payment.getCost());
        }
    }
}
