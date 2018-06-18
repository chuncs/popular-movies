package com.udacity.classroom.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.classroom.popularmovies.R;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Pair<String, String>> {
    private Context mContext;
    private List<Pair<String, String>> mVideos;

    public VideoAdapter(@NonNull Context context, @NonNull List<Pair<String, String>> videos) {
        super(context, 0, videos);
        mContext = context;
        mVideos = videos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.video_list_item, parent, false);
        }

        TextView videoNameTv = convertView.findViewById(R.id.video_name_tv);
        videoNameTv.setText(getItem(position).second);

        return convertView;
    }
}
