package com.example.swipenotes;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        // Define the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new MyRealmMigration()) // Your migration class
                .build();

        // Set the default Realm configuration
        Realm.setDefaultConfiguration(realmConfig);
    }
}
