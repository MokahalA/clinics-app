package com.example.seg2105project;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import java.util.List;



public class AccountList extends ArrayAdapter<User> {

    private Activity context;
    List<User> users;

    public AccountList(Activity context, List<User> users){
        super(context, R.layout.layout_account_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem =inflater.inflate(R.layout.layout_account_list, null, true);

        TextView tv_UserName= (TextView) listViewItem.findViewById(R.id.tv_UserName);
        TextView tv_AccType = (TextView) listViewItem.findViewById(R.id.tv_AccType);

        User user = users.get(position);
        tv_UserName.setText(" Username: " + user.getUserName());
        tv_AccType.setText(" Account type: " + user.getType());
        return listViewItem;
    }
}
