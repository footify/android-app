package eu.epitech.croucour.footify.Ranking;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.TeamEntity;
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 07/12/2016.
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {

    private final Context _context;
    private final IRankingView _iRankingView;

    private List<LigueRankingEntity> _ligueRankingEntities;


    public RankingAdapter(Context context, IRankingView iRankingView) {
        this._context = context;
        _iRankingView = iRankingView;
    }

    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingAdapter.MyViewHolder holder, int position) {
        final LigueRankingEntity ligueRankingEntity = _ligueRankingEntities.get(position);

        TeamEntity teamEntity = ligueRankingEntity.get_team();

        holder._ranking_position.setText(String.valueOf(position + 1));
        holder._ranking_team.setText(teamEntity.get_players().get(0)+" & "+teamEntity.get_players().get(1));
        holder._ranking_points.setText(String.valueOf(ligueRankingEntity.get_point()));
    }

    @Override
    public int getItemCount() {
        return _ligueRankingEntities == null ? 0 : _ligueRankingEntities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView _ranking_team;
        private final TextView _ranking_position;
        private final TextView _ranking_points;

        MyViewHolder(View itemView) {
            super(itemView);

            _ranking_position = (TextView) itemView.findViewById(R.id.ranking_position);
            _ranking_team = (TextView) itemView.findViewById(R.id.ranking_team);
            _ranking_points = (TextView) itemView.findViewById(R.id.ranking_points);
        }
    }

    public void set_ligueRankingEntities(List<LigueRankingEntity> _ligueRankingEntities) {
        this._ligueRankingEntities = _ligueRankingEntities;
        notifyDataSetChanged();
    }
}
