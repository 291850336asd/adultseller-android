package adult.mas.com.thirdviewmodel.marqueeview.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import adult.mas.com.thirdviewmodel.R;
import adult.mas.com.thirdviewmodel.marqueeview.MarqueeFactory;

public class ComplexViewMF extends MarqueeFactory<RelativeLayout, ComplexItemEntity> {
    private LayoutInflater inflater;

    public ComplexViewMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RelativeLayout generateMarqueeItemView(ComplexItemEntity data) {
        RelativeLayout mView = (RelativeLayout) inflater.inflate(R.layout.marquee_complex_view, null);
        ((TextView) mView.findViewById(R.id.title)).setText(data.getTitle());
        ((TextView) mView.findViewById(R.id.time)).setText(data.getTime());
        return mView;
    }
}