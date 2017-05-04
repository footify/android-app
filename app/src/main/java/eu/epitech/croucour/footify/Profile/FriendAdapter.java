package eu.epitech.croucour.footify.Profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.FriendListEntity;
import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 07/12/2016.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    private final Context _context;
    private final IFriendsView _iFriendsView;

    private FriendListEntity _friendListEntity;
    private List<FriendEntity> _friendEntities;


    public FriendAdapter(Context context, IFriendsView iFriendsView) {
        this._context = context;
        _iFriendsView = iFriendsView;
    }

    @Override
    public FriendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendAdapter.MyViewHolder holder, int position) {
        final FriendEntity friendEntity = _friendEntities.get(position);
        if (friendEntity != null) {
            String pseudo = "@"+friendEntity.get_pseudo();
            holder._pseudo.setText(pseudo);
            String name = friendEntity.get_last_name()+" "+friendEntity.get_first_name();
            holder._name.setText(name);
            _iFriendsView.setImage(friendEntity.get_picture_url(), holder._photo);

            String friend_type = null;

            for (FriendEntity friendEntity1 : _friendListEntity.get_accepted()) {
                if (Objects.equals(friendEntity1.get_id(), friendEntity.get_id())) {
                    holder._friend_status.setText("Accepted");
                    holder._friend_status.setBackgroundColor(_context.getColor(R.color.greeen));
                    friend_type = "Accepted";
                }
            }

            for (FriendEntity friendEntity1 : _friendListEntity.get_waiting_answer()) {
                if (Objects.equals(friendEntity1.get_id(), friendEntity.get_id())) {
                    holder._friend_status.setText("waiting answer");
                    holder._friend_status.setBackgroundColor(_context.getColor(R.color.red));
                    friend_type = "waiting answer";
                }
            }

            for (FriendEntity friendEntity1 : _friendListEntity.get_waiting_approval()) {
                if (Objects.equals(friendEntity1.get_id(), friendEntity.get_id())) {
                    holder._friend_status.setText("waiting approval");
                    holder._friend_status.setBackgroundColor(_context.getColor(R.color.orange));
                    friend_type = "waiting approval";
                }
            }
            if (!Objects.equals(friend_type, "Accepted")) {
                final String finalFriend_type = friend_type;
                holder._friend_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("id_friend", friendEntity.get_id());
                        _iFriendsView.showDialog(friendEntity.get_id(), finalFriend_type);
                    }
                });
            }

            holder._friend_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(_context,ProfileActivity.class);
                    intent.putExtra("friendEntity", friendEntity);
                    _context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return _friendEntities == null ? 0 : _friendEntities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView _friend_status;
        private final LinearLayout _friend_linearLayout;

        ImageView _photo;
        TextView _pseudo;
        TextView _name;

        MyViewHolder(View itemView) {
            super(itemView);

            _photo = (ImageView) itemView.findViewById(R.id.profile_photo);
            _pseudo = (TextView) itemView.findViewById(R.id.friend_pseudo);
            _name = (TextView) itemView.findViewById(R.id.friend_name);

            _friend_status = (TextView) itemView.findViewById(R.id.friend_status);

            _friend_linearLayout = (LinearLayout) itemView.findViewById(R.id.friend);

        }
    }

    public void set_friendListEntity(FriendListEntity _friendListEntity) {
        this._friendListEntity = _friendListEntity;
        List<FriendEntity> friendEntities = new ArrayList<>();

        friendEntities.addAll(_friendListEntity.get_waiting_approval());
        friendEntities.addAll(_friendListEntity.get_waiting_answer());
        friendEntities.addAll(_friendListEntity.get_accepted());

        _friendEntities = friendEntities;
        notifyDataSetChanged();
    }
}
