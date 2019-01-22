package prochain.com.tronbox.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import prochain.com.tronbox.R;


/**
 * Created by hanzhiyao on 2018/9/4.
 */

public class SettingAdapter extends BaseAdapter {

    private Context mContext;

    private List<SettingListInfo> mList;

    private LayoutInflater mLayoutInflater;

    public SettingAdapter(Context context, List list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList != null && mList.size() > position) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.fragment_setting_item, viewGroup, false);

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.introduce = convertView.findViewById(R.id.introduce);
            viewHolder.icon = convertView.findViewById(R.id.icon);
            viewHolder.llcontainer = convertView.findViewById(R.id.llcontainer);
            viewHolder.line = convertView.findViewById(R.id.dividerline);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (position == 0) {
            viewHolder.llcontainer.setVisibility(View.VISIBLE);
        } else if(position!=mList.size()){
            viewHolder.llcontainer.setVisibility(View.GONE);
            viewHolder.line.setVisibility(View.VISIBLE);
        }else if(position==3){
            viewHolder.llcontainer.setVisibility(View.GONE);
            viewHolder.line.setVisibility(View.GONE);
        }

        SettingListInfo settingListInfo = mList.get(position);

        viewHolder.image.setBackgroundResource(settingListInfo.getImage());
        viewHolder.name.setText(settingListInfo.getName());
        viewHolder.introduce.setText(settingListInfo.getIntroduce());
        viewHolder.icon.setBackgroundResource(settingListInfo.getIcon());

        return convertView;
    }


    public static class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView introduce;
        public ImageView icon;
        public LinearLayout llcontainer;
        public View line;
    }
}
