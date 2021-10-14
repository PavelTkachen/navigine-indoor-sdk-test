package com.yurypotapov.sslnaviginecompose.service

interface StoreInterface {
    public fun setValue(key: String, value: String);
    public fun getValue(key: String): Any?;
    public fun removeValue(key: String)
    public fun isExist(key: String): Boolean
    public fun save(key: String)
    public fun getStore(): Any;
    public fun clearStore();
}