package adult.mas.com.thirdviewmodel.loopview;

/**
 * Created by sunmeng on 17/8/19.
 */

final class OnItemSelectedRunnable implements Runnable {
    final LoopView loopView;

    OnItemSelectedRunnable(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
    }
}

