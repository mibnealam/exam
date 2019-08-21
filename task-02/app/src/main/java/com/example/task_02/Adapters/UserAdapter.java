package com.example.task_02.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.task_02.Models.User;
import com.example.task_02.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder> {

    private List<User> userList;

    private Context context;

    /**
     * Default Constructor for UserAdapter
     */
    public UserAdapter() {
    }

    @NonNull
    @Override
    public UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_user;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new UserAdapter.UserAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterViewHolder holder, int position) {
        //Initialization and setting the user data into views.
        final User user = userList.get(position);

        if (user.getGender().equals("female")) {
            Glide.with(context).load("https://randomuser.me/api/portraits/women/" + user.getPhoto() + ".jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.userAvatarImageView);
        } else if (user.getGender().equals("male")){
            Glide.with(context).load("https://randomuser.me/api/portraits/men/" + user.getPhoto() + ".jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.userAvatarImageView);
        } else {
            holder.userLastNameTextView.setBackgroundResource(R.drawable.avatar_circle);
        }

        holder.userFirstNameTextView.setText(user.getFirstName());
        holder.userLastNameTextView.setText(user.getLastName());
        holder.userHomePhoneTextView.setText(user.getPhones().getHome());
        holder.userMobilePhoneTextView.setText(user.getPhones().getMobile());
    }

    @Override
    public int getItemCount() {
        if ( null == userList) return 0;
        return userList.size();
    }

    /**
     * This method is used to set the user data on a UserAdapter if we've already
     * created one. This is handy when we get new data from the api but don't want to create a
     * new UserAdapter to display it.
     *
     * @param userData The new weather data to be displayed.
     */
    public void setUserData(List<User> userData) {
        userList = userData;
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a user list item.
     */
    public class UserAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView userAvatarImageView;
        public final TextView userFirstNameTextView;
        public final TextView userLastNameTextView;
        public final TextView userHomePhoneTextView;
        public final TextView userMobilePhoneTextView;

        public UserAdapterViewHolder(View itemView) {
            super(itemView);
            userAvatarImageView = (ImageView) itemView.findViewById(R.id.user_avatar_image_view);
            userFirstNameTextView = (TextView) itemView.findViewById(R.id.user_first_name_text_view);
            userLastNameTextView = (TextView) itemView.findViewById(R.id.user_last_name_text_view);
            userHomePhoneTextView = (TextView) itemView.findViewById(R.id.home_phone_text_view);
            userMobilePhoneTextView = (TextView) itemView.findViewById(R.id.mobile_phone_text_view);
        }
    }
}
