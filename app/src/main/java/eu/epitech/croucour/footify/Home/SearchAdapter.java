package eu.epitech.croucour.footify.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 13/12/2016.
 */
class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private final IHomeView _iHomeView;
    private Context _context;

    private List<String> _searchList;

    void set_searchList(List<String> _searchList) {
        this._searchList = _searchList;
    }

    SearchAdapter(IHomeView iHomeView, Context context) {
        _iHomeView = iHomeView;
        _context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_single_row, parent, false);
        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String string = _searchList.get(position);
        try {
            JSONObject json = new JSONObject(string);
            final String id = json.getString("id");
            final String type = json.getString("type");
            holder.name.setText(json.getString("name"));
            String url = null;
            String tag = null;
            switch (type) {
                case "pub":
                    holder.type.setText(R.string.PubTag);
                    holder.type.setBackgroundColor(_context.getColor(R.color.greeen));
                    break;
                case "user":
                    holder.type.setText(R.string.userTag);
                    holder.type.setBackgroundColor(_context.getColor(R.color.orange));
                    break;
            }

            final String finalUrl = url;
            final String finalTag = tag;
            holder.search_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (type) {
                        case "pub":
                            _iHomeView.getPubAndShow(id);
                            break;
                        case "user":
                            _iHomeView.getUserAndShow(id);
                            break;
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return _searchList == null ? 0 : _searchList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        LinearLayout search_linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.search_name);
            type = (TextView) itemView.findViewById(R.id.search_type);
            search_linearLayout = (LinearLayout) itemView.findViewById(R.id.search_linearLayout);
        }
    }

}
