

package com.hernaez.seven_eleven.other;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.hernaez.seven_eleven.other.dagger.Injector;
import com.hernaez.seven_eleven.other.dagger.RootModule;

public class MainApplication extends Application {

    private static MainApplication instance;

    /**
     * Create main application
     */
    public MainApplication() {
    }

    /**
     * Create main application
     *
     * @param context
     */
    public MainApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // Perform injection
        Injector.init(getRootModule(), this);

    }

    private Object getRootModule() {
        return new RootModule();
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public MainApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
