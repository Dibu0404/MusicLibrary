package android.example.musiclibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class videoAdapter extends RecyclerView.Adapter<videoAdapter.Viewholder> {
    private List<Video> allVideos;
    private Context context;

    public videoAdapter(Context ctx, List<Video> videos) {
        this.allVideos = videos;
        this.context = ctx;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.title.setText(allVideos.get(position).getTitle());
        Picasso.get().load(allVideos.get(position).getImageUrl()).into(holder.videoImage);
        holder.vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("videoData", allVideos.get(position));
                Intent i = new Intent(context, Player.class);
                i.putExtras(b);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView videoImage;
        TextView title;
        View vv;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.videoThumbnail);
            title = itemView.findViewById(R.id.VideoLink);
            vv = itemView;
        }

    }
}
