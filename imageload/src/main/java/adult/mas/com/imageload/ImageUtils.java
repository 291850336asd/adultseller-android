package adult.mas.com.imageload;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by sunmeng on 17/8/4.
 *
 * https://github.com/androidstarjack/MyGlideFactoryView
 */

public class ImageUtils {

    public static void loadImage(String url, GlideImageView imageView, int holderIcon){
        imageView.loadImageDefaultDra(url, holderIcon);
    }



    public static void loadImageFromNormal(String url, ImageView imageView, int holderIcon){
        Glide.with(imageView.getContext().getApplicationContext())
                .load(url)
                .apply(new RequestOptions().centerCrop()
                        .placeholder(imageView.getContext().getApplicationContext().getResources().getDrawable(holderIcon)
                        ).error(imageView.getContext().getApplicationContext().getResources().getDrawable(holderIcon)))
                .into(imageView);
    }
}
