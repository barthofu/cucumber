package org.cucumber.common.utils;

import org.cucumber.common.so.LoggerStatus;

/**
 * Logger class to have a uniformized logging across packages.
 */
public class Logger {

    public static void log(LoggerStatus stt, String msg) {
            System.out.printf("[%8s] : %s\n", stt.name(), msg);
            System.out.flush();
    }

}
