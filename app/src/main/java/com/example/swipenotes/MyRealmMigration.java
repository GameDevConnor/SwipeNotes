package com.example.swipenotes;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MyRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            // If the old schema version is 0, perform the migration
            schema.get("Note")
                    .removePrimaryKey()
                    .addField("timeCreated", String.class)
                    .addPrimaryKey("timeCreated");
            oldVersion++;
        }
        // Add more migration steps for other schema changes if needed
    }

}