package com.yurypotapov.sslnaviginecompose.service

import android.content.Context
import android.content.SharedPreferences

class PrefsStore(private val context: Context, private val sharedPreferencesKey: String): StoreInterface {

    private var store: SharedPreferences =
        context.getSharedPreferences(this.sharedPreferencesKey, 0);

    override fun setValue(key: String, value: String) {
        //TODO Checking datatype for any types
        this.store.edit().putString(key, value).apply();
    }

    override fun getValue(key: String): String? {
        //TODO Checking datatype for any types
        return this.store.getString(key, null);
    }

    override fun removeValue(key: String) {
        this.store.edit().remove(key).apply();
    }

    override fun isExist(key: String): Boolean {
        return null !== this.store.getString(key, null);
    }

    override fun save(key: String) {
       this.store.edit().apply();
    }

    override fun getStore(): Any {
        return this.store;
    }
}