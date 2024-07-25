import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


public class ReactiveStudy {

    static void fluxStudy() throws InterruptedException {
        Flux<Integer> f = Flux.range(1,50);

        System.out.println("Flux Start");

        f.map(i -> {
            System.out.println("Mapping!! " + "Thread Name: " + Thread.currentThread());
            return i * 2;
                })
                .filter( i -> {
                    System.out.println("Filter!! " + "Thread Name: " + Thread.currentThread());
                    return i % 2 == 0;
                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .publishOn(Schedulers.boundedElastic())
                .subscribe((i) -> System.out.println("Subsc:" + i + "!! Thread Name: " + Thread.currentThread()),
                        Throwable::printStackTrace,
                        ()-> System.out.println("Completed!! " + "Thread Name: " + Thread.currentThread()));
        //Thread.sleep(1000);

        System.out.println("Flux End");

        Stream<Integer> s = IntStream.range(1,50).boxed();

        System.out.println("Stream Start");
        s.map(i -> {
                    System.out.println("Stream Mapping!! " + "Thread Name: " + Thread.currentThread());
                    return i * 2;
                })
                .filter( i -> {
                    System.out.println("Stream Filter!! " + "Thread Name: " + Thread.currentThread());
                    return i % 2 == 0;
                })
                .forEach((i) -> System.out.println("Stream Subsc:" + i + "!! Thread Name: " + Thread.currentThread()));

        System.out.println("Stream End");


    }
}
