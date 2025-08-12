package java_pro.homework_3;

public class TestPool {

    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int taskNum = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " running task " + taskNum);
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            });
        }

        pool.shutdown();
        pool.awaitTermination();
        System.out.println("All tasks complete");
    }
}
