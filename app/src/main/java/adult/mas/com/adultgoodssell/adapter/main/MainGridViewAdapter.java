package adult.mas.com.adultgoodssell.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainGridBean;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.imageload.ImageUtils;

/**
 * Created by sunmeng on 17/8/4.
 */

public class MainGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<MainGridBean> sortDatas = new ArrayList<>();

    public MainGridViewAdapter(Context context, List<MainGridBean> datas) {
        this.mContext = context;
        this.sortDatas = datas;
    }

    @Override
    public int getCount() {
        return CollectionUtils.isEmpty(sortDatas) ?  0 : sortDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (null == convertView) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_gridview_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.themeItemll = (LinearLayout) convertView.findViewById(R.id.themeItemll);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        MainGridBean bean = sortDatas.get(position);
        holder.name.setText(bean.getThemeName());
        ImageUtils.loadImageFromNormal(bean.getUrl(), holder.img, R.drawable.gridicon);
        return convertView;
    }
    private static class Holder {
        TextView name;
        ImageView img;
        LinearLayout themeItemll;
    }


}

