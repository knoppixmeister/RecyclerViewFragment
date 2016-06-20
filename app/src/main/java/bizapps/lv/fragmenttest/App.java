package bizapps.lv.fragmenttest;

import android.app.Application;
import java.util.*;

public class App extends Application {
    public static List<String> results = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
