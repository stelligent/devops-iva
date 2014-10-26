package com.singlestoneconsulting.util;

/**
 * Provides an instance of T
 */
public interface Provider<T> {

    T provide();
}
