package adult.mas.com.adultgoodssell.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import adult.mas.com.adultgoodssell.R;

/**
 * Created by sunmeng on 17/8/2.
 */

public class CircleDialog  extends Dialog {
    private int mLoadingViewLayout;
    private boolean mCancelable;
    private boolean mOutsideCancelable;

    private CircleDialog(Context context) {
        this(context, 0);
    }

    private CircleDialog(Builder builder) {
        this(builder.mContext, 0);
        mLoadingViewLayout = builder.mLoadingViewLayout;
        mCancelable = builder.mCancelable;
        mOutsideCancelable = builder.mOutsideCancelable;
    }

    private CircleDialog(Context context, int themeResId) {
        super(context, R.style.Translucent_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLoadingViewLayout);
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mOutsideCancelable);
    }

    public static class Builder {
        private Context mContext;
        private int mLoadingViewLayout;
        private boolean mCancelable;
        private boolean mOutsideCancelable;

        public Builder(Context context, int loadingViewLayout) {
            mContext = context;
            mLoadingViewLayout = loadingViewLayout;
        }

        public Builder cancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder outsideCancelable(boolean outsideCancelable) {
            this.mOutsideCancelable = outsideCancelable;
            return this;
        }

        public CircleDialog build() {
            return new CircleDialog(this);
        }
    }

}

