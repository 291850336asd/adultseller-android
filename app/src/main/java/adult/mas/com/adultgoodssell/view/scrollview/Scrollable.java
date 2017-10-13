package adult.mas.com.adultgoodssell.view.scrollview;



import android.view.ViewGroup;

/**
 * Interface for providing common API for observable and scrollable widgets.
 */
public interface Scrollable {

    @Deprecated
    void setScrollViewCallbacks(ObservableScrollViewCallbacks listener);


    void addScrollViewCallbacks(ObservableScrollViewCallbacks listener);


    void removeScrollViewCallbacks(ObservableScrollViewCallbacks listener);


    void clearScrollViewCallbacks();

    /**
     * Scroll vertically to the absolute Y.<br>
     * Implemented classes are expected to scroll to the exact Y pixels from the top,
     * but it depends on the type of the widget.
     *
     * @param y Vertical position to scroll to.
     */
    void scrollVerticallyTo(int y);

    /**
     * Return the current Y of the scrollable view.
     *
     * @return Current Y pixel.
     */
    int getCurrentScrollY();

    /**
     * Set a touch motion event delegation ViewGroup.<br>
     * This is used to pass motion events back to parent view.
     * It's up to the implementation classes whether or not it works.
     *
     * @param viewGroup ViewGroup object to dispatch motion events.
     */
    void setTouchInterceptionViewGroup(ViewGroup viewGroup);
}
