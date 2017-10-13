package adult.mas.com.adultgoodssell.adapter.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.modelbean.mainview.MainBanner;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.StringUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.imageload.ImageUtils;

/**
 * Created by sunmeng on 17/8/3.
 */

public class MainViewPagerAdapter extends PagerAdapter {

    private List<MainBanner> bannerList;
    private Context context;
    private LinkedList<View> mCacheView = new LinkedList<View>();
    public MainViewPagerAdapter(Context context, List<MainBanner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }
    @Override
    public int getCount() {
        return  !CollectionUtils.isEmpty(bannerList) ? Integer.MAX_VALUE : bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mCacheView.poll();
        ViewHold viewHold;
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.home_banner_view, null);
            viewHold.viewPageImage = (ImageView) view.findViewById(R.id.banner_imageView);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        MainBanner banner = bannerList.get(position % bannerList.size());
        banner.setCurrentPosition(position + 1);
        String url = banner.getBannerPicUrl();
        if (!StringUtils.isEmpty(url)) {
            ImageUtils.loadImageFromNormal(url, viewHold.viewPageImage, R.drawable.no_data_icon);
        }
        view.setTag(R.id.banner_imageView, banner);
        addListenerForImageView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mCacheView.addLast((View) object);
    }

    private void addListenerForImageView(View view) {
        view.setOnClickListener(bannerClick);
    }


    View.OnClickListener bannerClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainBanner currentBanner = (MainBanner) v.getTag(R.id.banner_imageView);
            if (!TextUtils.isEmpty(currentBanner.getBannerChangeLink())) {
                ToastUtils.showToastLong(context, "url ok");
            } else {
                ToastUtils.showToastLong(context, "url null");
            }
        }
    };


    class ViewHold {
        ImageView viewPageImage;
    }
}
