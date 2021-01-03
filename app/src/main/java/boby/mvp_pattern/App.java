package boby.mvp_pattern;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import boby.mvp_pattern.dagger.DaggerMainComponent;
import boby.mvp_pattern.dagger.MainComponent;
import boby.mvp_pattern.dagger.MainModule;

public class App extends Application {
    private static App mInstance;
    private static Resources res;
    private static Context appContext;
    public static MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        res = getResources();
        this.setAppContext(getApplicationContext());
        initDagger();
    }

    private void initDagger() {
        mainComponent = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }

    public static App getAppInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void setAppContext(Context mAppContext) {
        this.appContext = mAppContext;
    }

    public static Resources getAppResources() {
        return res;
    }

}

