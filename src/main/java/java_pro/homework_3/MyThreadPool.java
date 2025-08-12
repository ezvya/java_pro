package java_pro.homework_3;


import java.util.LinkedList;

public class MyThreadPool {
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private final Worker[] workers;
    private volatile boolean isShutdown = false;

    public MyThreadPool(int capacity) {

        this.workers = new Worker[capacity];
        for (int i = 0; i < capacity; i++) {
            workers[i] = new Worker();
            workers[i].start();

        }
    }

    public void execute(Runnable task) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool is shut down, cannot accept new tasks");
            }
            taskQueue.addLast(task);
            taskQueue.notify();
        }
    }

    public void shutdown() {
        synchronized (taskQueue) {
            isShutdown = true;
            taskQueue.notifyAll();
        }
    }

    public void awaitTermination() {
        for (Worker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class Worker extends Thread {
        public void run() {
            while (true) {
                Runnable task;
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    if (taskQueue.isEmpty() && isShutdown) {
                        break;
                    }
                    task = taskQueue.removeFirst();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
