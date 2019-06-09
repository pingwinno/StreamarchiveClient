package com.pingwinno.streamarchive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<StreamMeta> streams;
    private View.OnClickListener onItemClickListener;

    public StreamAdapter(Context context, List<StreamMeta> streams) {
        this.streams = streams;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StreamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamAdapter.ViewHolder holder, int position) {
        StreamMeta streamMeta = streams.get(position);
        Glide.with(inflater.getContext())
                .load("https://storage.streamarchive.net/streams/olyashaa/" + streamMeta.getUuid() + "/preview.jpg")
                .into(holder.imageView);
        holder.titleView.setText(streamMeta.getTitle());

    }

    @Override
    public int getItemCount() {
        return streams.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // final ImageView imageView;
        final TextView titleView;
        final ImageView imageView;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.preview);
            titleView = view.findViewById(R.id.stream_title);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
