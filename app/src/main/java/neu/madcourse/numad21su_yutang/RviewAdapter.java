package neu.madcourse.numad21su_yutang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RviewAdapter extends RecyclerView.Adapter<RviewHolder> {

    private final ArrayList<LinkCard> linkList;
    private LinkClickListener listener;

    public RviewAdapter(ArrayList<LinkCard> linkList) {
        this.linkList = linkList;
    }

    public void setOnItemClickListener(LinkClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_collector_item,
                parent, false);
        return new RviewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RviewHolder holder, int position) {
        LinkCard currentItem = linkList.get(position);
        holder.name.setText(currentItem.getLinkName());
        holder.url.setText(currentItem.getLinkUrl());
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }
}
