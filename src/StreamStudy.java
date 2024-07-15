import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamStudy {

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

        // 병렬 여부 확인
        boolean isParallel = parallelStream.isParallel();

        // 다음 코드는 각 작업을 쓰레드를 이용해 병렬 처리
        boolean isMany = parallelStream
                .map(product -> product.getAmount() * 10)
                .anyMatch(amount -> amount > 200);
    }

    static void streamPrint(Stream<?> stream) {
        stream.forEach(System.out::println);

        Stream<String> stringStream = Pattern.compile(", ").splitAsStream("Eric, Elena, Java");
        // [Eric, Elena, Java]
    }

    static class Product {
        int amount;

        public Product() {
            amount = 0;
        }

        public Product(int amount) {
            this.amount = amount;
        }

        int getAmount() {
            return amount;
        }
    }
}