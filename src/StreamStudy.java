import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamStudy {
    static class Product {
        int amount;
        String name;

        public Product() {
            amount = 0;
            name = "Unknown";
        }

        public Product(int amount) {
            this.amount = amount;
            name = "Unknown";
        }
        public Product(int amount, String name) {
            this.amount = amount;
            this.name = name;
        }

        int getAmount() {
            return amount;
        }

        String getName() {
            return name;
        }
    }

    static void streamCreateStudy() {
        /* 1. Stream 생성하기 */
        /* 1.1. 배열 스트림 : Arrays.stream(배열, ) */
        String[] arr = new String[] { "a", "b", "c" };
        Stream<String> arrStream = Arrays.stream(arr);
        Stream<String> arrStreamOfArrPart = Arrays.stream(arr, 1, 3); // 1~2 요소 [b, c]
        // streamPrint(arrStream);
        // streamPrint(arrStreamOfArrPart);

        /* 1.2. 컬렉션 스트림 */
        List<String> list = new ArrayList<>();
        list.add("d");
        list.add("e");
        list.add("f");
        Stream<String> listStream = list.stream();
        // streamPrint(listStream);

        /* 1.3. builder */
        Stream<String> builderStream = Stream.<String>builder()
                .add("g").add("h").add("i")
                .build();
        // streamPrint(builderStream);

        /* 1.4. generate */
        Stream<String> generateStream = Stream.generate(() -> "j").limit(5);
        // streamPrint(generateStream);

        /* 1.5. Iterate */
        Stream<Integer> iterateStream = Stream.iterate(10, n -> n + 2).limit(5);
        // streamPrint(iterateStream);
    }

    static void streamTypeStudy() {
        /* 2. Stream Type */
        /* 2.1. 기본 타입형 스트림 */
        IntStream intStream = IntStream.range(1, 5); // [1, 2, 3, 4]
        LongStream longStream = LongStream.rangeClosed(1, 5); // [1, 2, 3, 4, 5]
        // 제네릭을 사용하지 않기 때문에 불필요한 오토박싱(auto-boxing)이 일어나지 않습니다. 필요한 경우 boxed 메소드를 이용해서
        // 박싱(boxing)할 수 있음
        Stream<Integer> boxedIntStream = IntStream.range(1, 5).boxed();
        // Java 8 의 Random 클래스는 난수를 가지고 세 가지 타입의 스트림(IntStream, LongStream,
        // DoubleStream)을 만들어낼 수 있음
        DoubleStream doubles = new Random().doubles(3); // 난수 3개 생성

        /* 2.2 문자열 스트림 */
        IntStream charsStream = "STREAM".chars();

        /* 2.3 파일 스트림 */
        /*
        try (Stream<String> lineStream = Files.lines(Paths.get("Main.java"), Charset.forName("UTF-8"))) {
            streamPrint(lineStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */

        /* 2.4 병렬 스트림 Parallel Stream */
        // 병렬 스트림 생성
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(100));
        productList.add(new Product(200));
        productList.add(new Product(300));

        Stream<Product> parallelStream = productList.parallelStream();
        IntStream intStream2 = IntStream.range(1, 150).parallel();

        // 병렬 여부 확인
        boolean isParallel = parallelStream.isParallel();
        boolean isParallel2 = intStream2.isParallel();

        // 다음 코드는 각 작업을 쓰레드를 이용해 병렬 처리
        boolean isMany = parallelStream
                .map(product -> product.getAmount() * 10)
                .anyMatch(amount -> amount > 200);

        // 다시 sequential로 돌리기
        Stream<Product> reSequentialProductStream = parallelStream.sequential();
        IntStream reSequentialIntStream = intStream2.sequential();
    }

    static void streamManufactureStudy() {
        /* 3. Stream 가공하기 */
        /* 3.1 Stream 연결(concat) */
        Stream<String> stream1 = Stream.of("Java", "Scala", "Groovy");
        Stream<String> stream2 = Stream.of("Python", "Go", "Swift");
        Stream<String> concat = Stream.concat(stream1, stream2);
        //streamPrint(concat);  // [Java, Scala, Groovy, Python, Go, Swift]

        /* 3.2 Filter: Stream<T> filter(Predicate<? super T> predicate); */
        List<String> names = Arrays.asList("Eric", "Elena", "Java");
        Stream<String> filterStream = names.stream()
                                            .filter(name -> name.contains("a"));
        //streamPrint(filterStream);   // [Elena, Java]

        /* 3.3 Mapping  <R> Stream<R> map(Function<? super T, ? extends R> mapper); */
        Stream<String> mapStream = names.stream()
                                        .map(String::toUpperCase);
        //streamPrint(mapStream); //

        /* 3.4 FlatMap <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper); */
//        List<List<String>> nestedList = Arrays.asList(Arrays.asList("a"), Arrays.asList("b"));
//        List<String> flatList =
//                nestedList.stream()
//                        .flatMap(Collection::stream)
//                        .collect(Collectors.toList());

        /* 3.5 Sorting */
        IntStream.of(14, 11, 20, 39, 23)
                .sorted()
                .boxed()
                .collect(Collectors.toList()); // [11, 14, 20, 23, 39]
        List<String> lang =
                Arrays.asList("Apple", "Zscale", "Groovy", "Python", "Go", "Swift");
        lang.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());  // [Swift, Scala, Python, Java, Groovy, Go]

        /* 3.6 Iterating(Peek): Stream<T> peek(Consumer<? super T> action); */
        int sum = IntStream.of(1, 3, 5, 7, 9)
                .peek(System.out::println)
                .sum(); // 25
    }

    static void streamDoneStudy() {
        /* 4. Stream 결과 만들기 */
        /* 4.1 Calculating: count, sum, min, max */
        OptionalInt min = IntStream.of(1, 3, 5, 7, 9).min();
        OptionalInt max = IntStream.of(1, 3, 5, 7, 9).max();
        DoubleStream.of(1.1, 2.2, 3.3, 4.4, 5.5)
                .average()
                .ifPresent(System.out::println);    //ifPresent 메소드를 이용해서 Optional 을 처리

        /* 4.2 Reduction: reduce 인자 3개: identity, accumulator, combiner  */
        // 인자 1개
        OptionalInt reduced =
                IntStream.range(1, 4) // [1, 2, 3]
                        .reduce((a, b) -> {
                            return Integer.sum(a, b);
                        }); // 6 = 1 + 2 + 3
        // 인자 2개
        int reducedTwoParams =
                IntStream.range(1, 4) // [1, 2, 3]
                        .reduce(10, Integer::sum);  //16 = 10 + 1 + 2 + 3
        // 인자 3개 (Combiner 는 병렬 처리 시 각자 다른 쓰레드에서 실행한 결과를 마지막에 합치는 단계)
        // 병렬 스트림에서만 동작
        // accumulator 는 총 세 번 동작
        // 초기값 10에 각 스트림 값을 더한 세 개의 값(10 + 1 = 11, 10 + 2 = 12, 10 + 3 = 13)을 계산
        // Combiner 는 identity 와 accumulator 를 가지고 여러 쓰레드에서 나눠 계산한 결과를 합치는 역할
        // 12 + 13 = 25, 25 + 11 = 36 이렇게 두 번 호출됩니다.
        Integer reducedParams = Stream.of(1, 2, 3).parallel()
                .reduce(10, // identity
                        Integer::sum, // accumulator
                        (a, b) -> {
                            System.out.println("combiner was called");
                            return a + b;
                        });
        //System.out.println(reducedParams);    //36

        /* 4.3 Collect: Collector 타입의 인자를 받아서 처리*/
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(100, "potato"));
        productList.add(new Product(200, "orange"));
        productList.add(new Product(300, "lemon"));
        // String으로 받기
        String listToString =
                productList.stream()
                        .map(Product::getName)
                        .collect(Collectors.joining(", ", "<", ">"));
        //System.out.println(listToString);// <potato, orange, lemon>
        // List로 받기
        List<String> collectorCollection =
                productList.stream()
                        .map(Product::getName)
                        .collect(Collectors.toList());
        // Collectors. averageingInt, summingInt, summarizingInt, groupingBy, partitioningBy
        // collectingAndThen
        // Collectors.toSet 을 이용해서 결과를 Set 으로 collect 한 후 수정불가한 Set 으로 변환하는 작업
        Set<Product> unmodifiableSet =
                productList.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toSet(),
                                Collections::unmodifiableSet));
        // 직접 collector 를 만들기
        /* public static<T, R> Collector<T, R, R> of(
            Supplier<R> supplier, // new collector 생성
            BiConsumer<R, T> accumulator, // 두 값을 가지고 계산
            BinaryOperator<R> combiner, // 계산한 결과를 수집하는 함수.
            Characteristics... characteristics) { ... } */
        Collector<Product, ?, LinkedList<Product>> toLinkedList =
                Collector.of(LinkedList::new,
                        LinkedList::add,
                        (first, second) -> {
                            first.addAll(second);
                            return first;
                        });
        LinkedList<Product> linkedListOfPersons =
                productList.stream()
                        .collect(toLinkedList);

        // 4.4 Matching: anyMatch, allMatch, noneMatch
        List<String> names = Arrays.asList("Eric", "Elena", "Java");

        boolean anyMatch = names.stream()
                .anyMatch(name -> name.contains("a"));  // true
        boolean allMatch = names.stream()
                .allMatch(name -> name.length() > 3);   // true
        boolean noneMatch = names.stream()
                .noneMatch(name -> name.endsWith("s")); // true

        // 4.5 Iterating: foreach
        names.stream().forEach(System.out::println);
    }

    static void advancedStreamStudy() {
        /* 성능 향상 */
        List<String> list = new ArrayList<>();
        list.add("Eric");
        list.add("Elena");
        list.add("Java");

        // 범위 줄이기: skip, filter, distinct
        // bad example
        list.stream()
                .map(el -> { // 3번 "map was called"
                    System.out.println("map was called");
                    return el.substring(0, 3);
                })
                .skip(2)
                .collect(Collectors.toList());
        // good example
        list.stream()
                .skip(2)
                .map(el -> { // 1번 "map was called"
                    System.out.println("map was called");
                    return el.substring(0, 3);
                })
                .collect(Collectors.toList());

        // Stream 재사용
        // bad example
        Stream<String> stream =
                Stream.of("Eric", "Elena", "Java")
                        .filter(name -> name.contains("a"));
        Optional<String> firstElement = stream.findFirst();
        // Optional<String> anyElement = stream.findAny(); // IllegalStateException: stream has already been operated upon or closed

        // good example: List에 저장하고 필요할 때 마다 꺼내 쓰기
        List<String> names =
                Stream.of("Eric", "Elena", "Java")
                        .filter(name -> name.contains("a"))
                        .collect(Collectors.toList());
        Optional<String> firstElement2 = names.stream().findFirst();
        Optional<String> anyElement2 = names.stream().findAny();

        // 지연 처리: Lazy Invocation
        // bad example
        list.stream()
                .filter(el -> { // LazyFilter1 출력 안됨.
                    System.out.println("LazyFilter1");
                    return el.contains("a");
                });
        // good example
        list.stream()
                .filter(el -> { // LazyFilter2 3번 출력 됨.
                    System.out.println("LazyFilter2");
                    return el.contains("a");
                }).collect(Collectors.toList());

        // Null-safe Stream
        List<String> nullList = null;
        // bad example
//        nullList.stream()
//                .filter(str -> str.contains("a"))
//                .map(String::length)
//                .forEach(System.out::println); // NPE!
        // good example
        collectionToStream(nullList)
                .filter(str -> str.contains("a"))
                .map(String::length)
                .forEach(System.out::println); // []
    }
    static <T> Stream<T> collectionToStream(Collection<T> collection) {
        return Optional
                .ofNullable(collection)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }

    static void streamSweetTips() {
        // 주의 사항
        // Collections.synchronizedList(...).stream().forEach() // not synchronizeD
        // Collections.synchronizedList(...).forEach()  // synchronized

        // collect(Collectors.maxBy()) // Optional
        // Stream.max() // NPE 발생 가능

//        collection.stream().forEach()
//  → collection.forEach()
//
//        collection.stream().toArray()
//  → collection.toArray()
//
//        Arrays.asList().stream()
//  → Arrays.stream() or Stream.of()
//
//        Collections.emptyList().stream()
//  → Stream.empty()
//
//        stream.filter().findFirst().isPresent()
//  → stream.anyMatch()
//
//        stream.collect(counting())
//  → stream.count()
//
//        stream.collect(maxBy())
//  → stream.max()
//
//        stream.collect(mapping())
//  → stream.map().collect()
//
//        stream.collect(reducing())
//  → stream.reduce()
//
//        stream.collect(summingInt())
//  → stream.mapToInt().sum()
//
//        stream.map(x -> {...; return x;})
//  → stream.peek(x -> ...)
//
//        !stream.anyMatch()
//  → stream.noneMatch()
//
//        !stream.anyMatch(x -> !(...))
//  → stream.allMatch()
//
//        stream.map().anyMatch(Boolean::booleanValue)
//  → stream.anyMatch()
//
//        IntStream.range(expr1, expr2).mapToObj(x -> array[x])
//  → Arrays.stream(array, expr1, expr2)
//
//        Collection.nCopies(count, ...)
//  → Stream.generate().limit(count)
//
//        stream.sorted(comparator).findFirst()
//  → Stream.min(comparator)


    }

    static void streamPrint(Stream<?> stream) {
        stream.forEach(System.out::println);

        Stream<String> stringStream = Pattern.compile(", ").splitAsStream("Eric, Elena, Java");
        // [Eric, Elena, Java]
    }


}