package com.lr.controller;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class text extends Thread {
    public static void main(String args[]) {
        MyRunnable myRunnable =new MyRunnable();
        new Thread(myRunnable).start();
       new Thread(new MyRunnable()).start();
        new Thread(()->{
            System.out.println("哈哈");
        }).start();
        MyCallable myCallable =new MyCallable();
        FutureTask<String> futureTask =new FutureTask<>(myCallable);
        new Thread(futureTask).start();

    }
   public static class MyRunnable implements Runnable{

       @Override
       public void run() {
           System.out.println("hello world");
       }
   }

   public static class MyCallable implements Callable<String>{

       @Override
       public String call() throws Exception {

           System.out.println("你好");
           return "success";
       }
   }

}


