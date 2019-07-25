package mikuinc.multimuzic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by djmurusaki on 16 Dec 2017.
 */

public class ListMusicAdapter extends BaseAdapter{
    private Context context;
    private List<Musics> musicsList;

    public ListMusicAdapter(Context context, List<Musics> musicsList){
        this.context = context;
        this.musicsList = musicsList;
    }
    @Override
    public int getCount() {
        return musicsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.musicsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_music_list, null);
        TextView musicName = (TextView)view.findViewById(R.id.musicName);
        musicName.setText(musicsList.get(position).getMusicName());

        return view;
    }
}
