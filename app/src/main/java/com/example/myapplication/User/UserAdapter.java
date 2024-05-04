package com.example.myapplication.User;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private ArrayList<User> userList;

    public UserAdapter(Context context) {
        this.context = context;
        this.userList = new ArrayList<>();
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewEmail;
        private TextView textViewMobile;
        private TextView textViewDob;
        private TextView textViewAddress;
        private TextView textViewCity;
        private TextView textViewGender;

        private ImageView imageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewMobile = itemView.findViewById(R.id.textViewMobile);
            textViewDob = itemView.findViewById(R.id.textViewDob);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewGender = itemView.findViewById(R.id.textViewGender);


            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(User user) {
            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewMobile.setText(user.getMobileNo());
            textViewDob.setText(user.getDob());
            textViewAddress.setText(user.getAddress());
            textViewCity.setText(user.getLivingCity());
            textViewGender.setText(user.getGender());

            byte[] imageBytes = user.getImage();
            if (imageBytes != null) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
            } else {
                imageView.setImageResource(R.drawable.image); // Default image if no image available
            }
        }
    }
}
