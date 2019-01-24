package prochain.com.tronbox.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import prochain.com.tronbox.R;
import prochain.com.tronbox.Views.RoundedImagView;
import prochain.com.tronbox.utils.loader.ImageLoaderUtils;


/**
 * Created by hanzhiyao on 2018/10/17.
 */

public class homeDiscoverAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<homeBean> mList;

    public homeDiscoverAdapter(Context context, List<homeBean> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_discover_eos, parent, false);
            holder.logo = convertView.findViewById(R.id.logo);
            holder.title = convertView.findViewById(R.id.title);
            holder.description = convertView.findViewById(R.id.description);
            holder.image = convertView.findViewById(R.id.image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        homeBean dataBean = mList.get(position);
        holder.title.setText(dataBean.title);
        ImageLoaderUtils.getImageLoader().loadImage(convertView.getContext(), dataBean.logo, holder.logo,R.mipmap.aboutme, R.mipmap.aboutme);
        holder.description.setText(dataBean.info);

        return convertView;
    }


    static class ViewHolder {
        private RoundedImagView logo;
        private TextView title;
        private TextView description;
        private ImageView image;
    }
}
