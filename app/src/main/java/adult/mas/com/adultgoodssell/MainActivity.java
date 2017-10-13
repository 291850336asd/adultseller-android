package adult.mas.com.adultgoodssell;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import adult.mas.com.adultgoodssell.activity.fragment.BaseFragment;
import adult.mas.com.adultgoodssell.activity.fragment.MainPageFragment;
import adult.mas.com.adultgoodssell.activity.fragment.MinePageFragment;
import adult.mas.com.adultgoodssell.activity.fragment.SortPageFragment;
import adult.mas.com.adultgoodssell.activity.fragment.VideosPageFragment;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.view.MainIconTextLinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final int EXIT_TIME = 2000;
    private long exitTime;
    private int currentSelect = 1;

    @BindView(R.id.mainBottomLL)
    LinearLayout mainBottomLL;

    private MainPageFragment mainPageFragment;
    private SortPageFragment sortPageFragment;
    private VideosPageFragment videosPageFragment;
    private MinePageFragment minePageFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdultApplication.addActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialBottomView();
        initialfragment();
    }

    private void initialfragment() {
        mainPageFragment = new MainPageFragment();
        sortPageFragment = new SortPageFragment();
        videosPageFragment = new VideosPageFragment();
        minePageFragment = new MinePageFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, mainPageFragment, null)
                .add(R.id.frame_container,sortPageFragment,null)
                .add(R.id.frame_container, videosPageFragment, null)
                .add(R.id.frame_container, minePageFragment, null)
                .hide(sortPageFragment)
                .hide(videosPageFragment)
                .hide(minePageFragment)
                .show(mainPageFragment)
                .commitAllowingStateLoss();
    }


    private void showFragment(BaseFragment baseFragment, BaseFragment hideFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(hideFragment)
                .show(baseFragment)
                .commit();
    }


    private void initialBottomView() {
        MainIconTextLinearLayout mainPageItem = new MainIconTextLinearLayout(this);
        setBottomClickEvent(mainPageItem, 1);
        mainPageItem.setIconText("\ue6cb");
        mainPageItem.setText(getResources().getString(R.string.mainpage));
        mainPageItem.setSelectTextColor(getResources().getColor(R.color.color_fd7530));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.F);
        mainPageItem.setLayoutParams(lp);
        mainBottomLL.addView(mainPageItem);

        MainIconTextLinearLayout sortPageItem = new MainIconTextLinearLayout(this);
        setBottomClickEvent(sortPageItem, 2);
        sortPageItem.setIconText("\ue618");
        sortPageItem.setText(getResources().getString(R.string.sort));
        sortPageItem.setLayoutParams(lp);
        mainBottomLL.addView(sortPageItem);

        MainIconTextLinearLayout videopageItem = new MainIconTextLinearLayout(this);
        setBottomClickEvent(videopageItem, 3);
        videopageItem.setIconText("\ue820");
        videopageItem.setText(getResources().getString(R.string.videos));
        videopageItem.setLayoutParams(lp);
        mainBottomLL.addView(videopageItem);

        MainIconTextLinearLayout personPageItem = new MainIconTextLinearLayout(this);
        setBottomClickEvent(personPageItem, 4);
        personPageItem.setIconText("\ue600");
        personPageItem.setText(getResources().getString(R.string.mine));
        personPageItem.setLayoutParams(lp);
        mainBottomLL.addView(personPageItem);
    }

    private void setBottomClickEvent(final MainIconTextLinearLayout itemBottom, final int index){
        itemBottom.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                int count = mainBottomLL.getChildCount();
                if( count> 0 && count >= index){
                    if(!itemBottom.getIsSelect()){
                        MainIconTextLinearLayout item = (MainIconTextLinearLayout) mainBottomLL.getChildAt(currentSelect-1);
                        item.setDefaultTextColor(getResources().getColor(R.color.color_999));
                        BaseFragment currentFragment = null;
                        if(currentSelect == 1){
                            currentFragment = mainPageFragment;
                        }else if(currentSelect == 2){
                            currentFragment = sortPageFragment;
                        }else if(currentSelect == 3){
                            currentFragment = videosPageFragment;
                        }else if(currentSelect == 4){
                            currentFragment = minePageFragment;
                        }
                        currentSelect = index;
                        itemBottom.setSelectTextColor(getResources().getColor(R.color.color_fd7530));
                        if(index == 1){
                            videosPageFragment.onDetachFromWindow();
                            showFragment(mainPageFragment, currentFragment);
                        }else if(index == 2){
                            videosPageFragment.onDetachFromWindow();
                            showFragment(sortPageFragment, currentFragment);
                        }else if(index == 3){
                            showFragment(videosPageFragment, currentFragment);
                        }else if(index == 4){
                            videosPageFragment.onDetachFromWindow();
                            showFragment(minePageFragment, currentFragment);
                        }
                    }
                }
            }
        });
    }

    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > EXIT_TIME) {
            exitTime = System.currentTimeMillis();
        } else {
            AdultApplication.exitApp();
        }
    }



}
