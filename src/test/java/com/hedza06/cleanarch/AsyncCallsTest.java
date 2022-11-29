package com.hedza06.cleanarch;

import com.hedza06.cleanarch.customer.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CleanarchApplication.class)
class AsyncCallsTest {
    
    @Test
    void doSomethingNonBlocking() throws ExecutionException, InterruptedException 
    {
        ExecutorService service = Executors.newFixedThreadPool(10);
        
//        Future<Customer> future1 = service.submit(() -> {
//            System.out.println("Thread: " + Thread.currentThread().getName());
////            delay(5);
//            return new Customer(1);
//        });
//
//        Future<Customer> future2 = service.submit(() -> {
//            System.out.println("Thread: " + Thread.currentThread().getName());
//            return new Customer(2);
//        });
//
//        Future<Customer> future3 = service.submit(() -> {
//            System.out.println("Thread: " + Thread.currentThread().getName());
//            return new Customer(3);
//        });

        //        future1.get();
//        future2.get();
//        future3.get();

        CompletableFuture<Customer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating customer with id 1");
            System.out.println("Thread: " + Thread.currentThread().getName());
            return new Customer(1);
        });

        CompletableFuture<Customer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating customer with id 2");
            System.out.println("Thread: " + Thread.currentThread().getName());
            return new Customer(2);
        });

        CompletableFuture<Customer> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating customer with id 3");
            System.out.println("Thread: " + Thread.currentThread().getName());
            return new Customer(3);
        });

        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(future1, future2, future3);
        completableFuture.get();

        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());
    }

    private void delay(int seconds) throws InterruptedException
    {
        TimeUnit.SECONDS.sleep(seconds);
    }
}
