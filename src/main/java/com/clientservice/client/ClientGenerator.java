package com.clientservice.client;

import java.util.concurrent.TimeUnit;

public class ClientGenerator extends Thread {

    private final TimeUnit timeUnit;

    public ClientGenerator(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    private void waitSomeTime() {

        try {
            timeUnit.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        while (true) {

            if (Math.random() > 0.7)
                new Client().start();

            waitSomeTime();

        }

    }
}
