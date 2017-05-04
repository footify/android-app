package eu.epitech.croucour.footify.Pub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 07/12/2016.
 */
public class BabyFootAdapter extends RecyclerView.Adapter<BabyFootAdapter.MyViewHolder> {

    private final Context _context;
    private final IPubView _iPubView;

    private List<BabyFootEntity> _babyFootEntities;


    public BabyFootAdapter(Context context, IPubView iPubView) {
        this._context = context;
        _iPubView = iPubView;
    }

    @Override
    public BabyFootAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.babyfoot_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BabyFootAdapter.MyViewHolder holder, int position) {
        final BabyFootEntity babyFootEntity = _babyFootEntities.get(position);

        holder._name.setText(babyFootEntity.get_name());
        holder._manufacturer.setText(babyFootEntity.get_manufacturer());

        _iPubView.setImage(babyFootEntity.get_picture_url(), holder._photo);
    }

    @Override
    public int getItemCount() {
        return _babyFootEntities == null ? 0 : _babyFootEntities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout _linearLayout;
        private final TextView _manufacturer;
        ImageView _photo;
        TextView _name;

        MyViewHolder(View itemView) {
            super(itemView);

            _photo = (ImageView) itemView.findViewById(R.id.babyfoot_photo);
            _name = (TextView) itemView.findViewById(R.id.babyfoot_name);
            _manufacturer = (TextView) itemView.findViewById(R.id.babyfoot_manufacturer);
            _linearLayout = (LinearLayout) itemView.findViewById(R.id.babyfoot);
        }
    }

    public void set_babyFootEntities(List<BabyFootEntity> _babyFootEntities) {
        this._babyFootEntities = _babyFootEntities;
        notifyDataSetChanged();
    }
}
