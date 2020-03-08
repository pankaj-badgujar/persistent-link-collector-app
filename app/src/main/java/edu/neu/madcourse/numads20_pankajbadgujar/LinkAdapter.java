package edu.neu.madcourse.numads20_pankajbadgujar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkHolder> {

    private List<Link> links = new ArrayList<>();
    private OnLinkItemClickListener listener;

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link_item, parent, false);
        return new LinkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkHolder holder, int position) {
        Link currentLink = links.get(position);
        holder.linkTitle.setText(currentLink.getLinkName());
        holder.linkUrl.setText(currentLink.getLinkURL());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public void setLinks(List<Link> links) {
        this.links = links;
        notifyDataSetChanged();
    }

    public Link getLinkAt(int position) {
        return links.get(position);
    }

    class LinkHolder extends RecyclerView.ViewHolder {
        private TextView linkTitle;
        private TextView linkUrl;

        public LinkHolder(@NonNull View itemView) {
            super(itemView);
            linkTitle = itemView.findViewById(R.id.linkTitle);
            linkUrl = itemView.findViewById(R.id.linkUrl);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onLinkItemClick(links.get(position));
                    }

                }
            });
        }
    }

    public interface OnLinkItemClickListener {
        void onLinkItemClick(Link link);
    }

    public void setOnItemClickListener(OnLinkItemClickListener listener) {
        this.listener = listener;
    }
}
