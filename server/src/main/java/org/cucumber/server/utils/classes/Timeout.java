package org.cucumber.server.utils.classes;

/**
 * Asynchronous implementation of a setTimeout function (delay)
 */
public class Timeout {

    /**
     * Executes a runnable after a delay
     * @param runnable Runnable to execute
     * @param delay Delay in milliseconds
     */
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
