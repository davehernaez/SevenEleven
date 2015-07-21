package com.hernaez.seven_eleven.other.dagger;

import dagger.Module;

/**
 * Add all the other modules to this one.
 */
@Module(
        includes = {
                AndroidModule.class,
                MainModule.class,
        }
)
public class RootModule {


}
