import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
        System.out.println("============================================");


        // 변환 Study
        // 1. map: 동기 처리 및 요소를 직접 변환
        Mono<String> mapStudyMono = Mono.just("map study");
        Mono<String> mapStudyMono2 = mapStudyMono.map(String::toUpperCase);
        mapStudyMono2 = mapStudyMono.map(str -> str.toUpperCase());
        mapStudyMono2.subscribe(System.out::println);

        Flux<Integer> mapStudyFlux = Flux.range(1,3);
        Flux<Integer> mapStudyFlux2 = mapStudyFlux.map(i -> i * i);
        mapStudyFlux2 = mapStudyFlux.map(i -> {
            int result;
            result = i * i;
            return result;
        });
        mapStudyFlux2.subscribe(System.out::println);

        // 2. flatmap: 비동기 처리 및 요소를 이용하여 Mono로 반환
        Mono<String> flatmapStudyMono = Mono.just("flatmap study");
        Mono<String> flatmapStudyMono2 = mapStudyMono.flatMap(str -> Mono.just(str.toUpperCase()));
        flatmapStudyMono2.subscribe(System.out::println);

        Flux<Integer> flatmapStudyFlux = Flux.range(1,3);
        Flux<Integer> flatmapStudyFlux2 = flatmapStudyFlux.flatMap(i -> Mono.just(i * i));
        flatmapStudyFlux2.subscribe(System.out::println);

        // 3. flatMapIterable: 요소를 이용하여 list와 같은 iterable을 만들고 iterable이 Flux로 변환
        Mono<String> flatmapIterableMono = Mono.just("flatmap iterable study");
        Flux<String> flatmapIterableMono2 = flatmapIterableMono.flatMapIterable(str -> List.of(str, "add: "+ str));
        flatmapIterableMono2.subscribe(System.out::println);

        Flux<Integer> flatmapIterableStudyFlux = Flux.range(1,3);
        Flux<Integer> flatmapIterableStudyFlux2 = flatmapIterableStudyFlux.flatMapIterable(i -> List.of(i, i * i));
        flatmapIterableStudyFlux2.subscribe(System.out::println);

        // 4. flatMapMany(Mono만 사용가능): 요소를 이용하여 Flux를 만들어서 변환
        Mono<Integer> flatmapManyStudyMono = Mono.just(1);
        Flux<Integer> flatmapManyStudyMono2 = flatmapManyStudyMono.flatMapMany(i -> Flux.range(1,3));
        flatmapManyStudyMono2.subscribe(System.out::println);

        // 5. flatMapMany 활용 (4의 Mono 활용)
        System.out.println("============================================");
        Flux<Integer> flatMapManyExam = flatmapManyStudyMono.flatMapMany(i -> Flux.range(1,3).map(e -> e * e));
        flatMapManyExam.subscribe(System.out::println); // 1, 4, 9

        Flux<Integer> flatMapManyExam2 = flatmapManyStudyMono.flatMapMany(i -> Flux.range(1,3).flatMap(e -> Mono.just(e * e)));
        flatMapManyExam2.subscribe(System.out::println); // 1, 4, 9

        Flux<Integer> flatMapManyExam3 = flatmapManyStudyMono.flatMapMany(i -> Flux.range(1,3).flatMapIterable(e -> List.of(e * e)));
        flatMapManyExam3.subscribe(System.out::println); // 1, 4, 9
    }
}
