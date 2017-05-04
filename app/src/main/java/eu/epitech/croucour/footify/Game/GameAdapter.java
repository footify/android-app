package eu.epitech.croucour.footify.Game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.FriendListEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.TeamEntity;
import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 07/12/2016.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private final Context _context;
    private final IGameView _iGameView;

    private List<GameEntity> _gameEntities;


    public GameAdapter(Context context, IGameView iGameView) {
        this._context = context;
        _iGameView = iGameView;
    }

    @Override
    public GameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameAdapter.MyViewHolder holder, int position) {
        final GameEntity gameEntity = _gameEntities.get(position);

        TeamEntity teamEntityBlue = gameEntity.get_teams().get(0);
        TeamEntity teamEntityRed = gameEntity.get_teams().get(1);

        holder._game_team1_user_1.setText(teamEntityBlue.get_players().get(0).get_pseudo());
        holder._game_team1_user_2.setText(teamEntityBlue.get_players().get(1).get_pseudo());
        holder._game_team1_score.setText(String.valueOf(gameEntity.get_scores().get(0)));

        holder._game_team2_user_1.setText(teamEntityRed.get_players().get(0).get_pseudo());
        holder._game_team2_user_2.setText(teamEntityRed.get_players().get(1).get_pseudo());
        holder._game_team2_score.setText(String.valueOf(gameEntity.get_scores().get(1)));

        if (Objects.equals(gameEntity.get_winner(), teamEntityBlue.get_id())) {
            holder._game_team1_score.setTextColor(Color.GREEN);
            holder._game_team2_score.setTextColor(Color.RED);
        }
        else {
            holder._game_team1_score.setTextColor(Color.RED);
            holder._game_team2_score.setTextColor(Color.GREEN);
        }

        holder._relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _iGameView.shareGame(gameEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _gameEntities == null ? 0 : _gameEntities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView _game_team1_user_1;
        private final TextView _game_team1_user_2;
        private final TextView _game_team1_score;
        private final TextView _game_team2_user_1;
        private final TextView _game_team2_user_2;
        private final TextView _game_team2_score;
        private final TextView _game_date;
        private final RelativeLayout _relativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);

            _game_team1_user_1 = (TextView) itemView.findViewById(R.id.game_team1_user_1);
            _game_team1_user_2 = (TextView) itemView.findViewById(R.id.game_team1_user_2);
            _game_team1_score = (TextView) itemView.findViewById(R.id.game_team1_score);
            _game_team2_user_1 = (TextView) itemView.findViewById(R.id.game_team2_user_1);
            _game_team2_user_2 = (TextView) itemView.findViewById(R.id.game_team2_user_2);
            _game_team2_score = (TextView) itemView.findViewById(R.id.game_team2_score);
            _game_date = (TextView) itemView.findViewById(R.id.game_date);

            _relativeLayout = (RelativeLayout) itemView.findViewById(R.id.game_RelativeLayout);
        }
    }

    public void setGameEntities(List<GameEntity> gameEntities) {
        _gameEntities = gameEntities;
        notifyDataSetChanged();
    }
}
