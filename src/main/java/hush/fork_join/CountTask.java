package hush.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {


    public static final int threshold = 2;
    private int start;
    private int end;

    public CountTask(int start, int end)
    {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int sum = 0;

        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if(canCompute)
        {
            for (int i=start; i<=end; i++)
            {
                sum += i;
            }
        }
        else
        {
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end)/2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle+1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            //等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            //合并子任务
            sum = leftResult + rightResult;

        }
        return sum;
    }





   public static void main(String[] args) {

        ForkJoinPool forkjoinPool = new ForkJoinPool();

        CountTask task = new CountTask(1, 100);

        Future<Integer> result = forkjoinPool.submit(task);

        try{

            System.out.println(result.get());
        } catch (Exception e){
            e.printStackTrace();
        }


   }
}
