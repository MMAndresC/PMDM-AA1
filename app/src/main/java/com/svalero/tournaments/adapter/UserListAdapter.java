package com.svalero.tournaments.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.UserData;
import com.svalero.tournaments.util.ParseUtil;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    private final List<UserData> usersList;
    private final List<String> regions = List.of("North America", "South America", "Europe", "Asia", "Oceania");

    public UserListAdapter(List<UserData> userslist){
        this.usersList = userslist;
    }

    @NonNull
    @Override
    public UserListAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_user_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserHolder holder, int position) {
        if (position < 0 || position >= usersList.size()) return;
        String alias = usersList.get(position).getAlias();
        if(alias.isBlank()) alias = usersList.get(position).getUsername();
        holder.itemAlias.setText(alias);
        int codeRegion = usersList.get(position).getRegion();
        String region = "";
        if(codeRegion != 0) region = regions.get(codeRegion - 1);
        holder.itemRegion.setText(region);
        byte[] image = usersList.get(position).getImage();
        if( image != null){
            holder.itemImage.setImageBitmap(ParseUtil.byteArrayToBitmap(image));
        }else
            holder.itemImage.setImageResource(R.drawable.no_photos);
        String role = usersList.get(position).getMainRole();
        if(role != null){
            if(role.equals("tank")) holder.itemRole.setImageResource(R.drawable.tank_role);
            else if(role.equals("dps")) holder.itemRole.setImageResource(R.drawable.dps_role);
            else if(role.equals("support")) holder.itemRole.setImageResource(R.drawable.support_role);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView itemAlias;
        private TextView itemRegion;
        private ImageView itemImage;
        private ImageView itemRole;
        public UserHolder(@NonNull View itemView) {
            super(itemView);

            this.itemAlias = itemView.findViewById(R.id.aliasUserItem);
            this.itemImage = itemView.findViewById(R.id.imageUserItem);
            this.itemRegion = itemView.findViewById(R.id.regionUserItem);
            this.itemRole = itemView.findViewById(R.id.roleUserItem);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
