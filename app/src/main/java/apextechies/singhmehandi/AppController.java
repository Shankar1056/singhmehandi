package apextechies.singhmehandi;

import android.app.Application;


import java.time.LocalDateTime;


public class AppController extends Application {

    private static AppController mInstance;

    public LocalDateTime setDate, selectedDate;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /*getting the current week*/
    public LocalDateTime getDate() {
        return setDate;
    }

    /**
     * Set the current week date
     *
     * @param setDate
     */
    public void setDate(LocalDateTime setDate) {
        this.setDate = setDate;
    }

    /*getting the selected week*/

    public LocalDateTime getSelected() {
        return selectedDate;
    }

    /**
     * Setting selected week
     *
     * @param selectedDate
     */
    public void setSelected(LocalDateTime selectedDate) {
        this.selectedDate = selectedDate;
    }


}
