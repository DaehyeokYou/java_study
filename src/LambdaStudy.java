import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaStudy {

    static void lambdaStudy() {
        /* Lambda 생성하기 */
        //함수를 하나의 식(expression)으로 표현
        //메소드의 이름이 필요 없기 때문에
        //익명 함수(Anonymous Function)의 한 종류 (일급 객체: https://mangkyu.tistory.com/111)

        //람다식(Lambda Expression) 의 장점
        //코드를 간결하게 만들 수 있다.
        //식에 개발자의 의도가 명확히 드러나 가독성이 높아진다.
        //함수를 만드는 과정없이 한번에 처리할 수 있어 생산성이 높아진다.
        //병렬프로그래밍이 용이하다.

        //람다식(Lambda Expression) 의 단점
        //람다를 사용하면서 만든 무명함수는 재사용이 불가능하다.
        //디버깅이 어렵다.
        //람다를 남발하면 비슷한 함수가 중복 생성되어 코드가 지저분해질 수 있다.
        //재귀로 만들경우에 부적합하다.

        //함수형 인터페이스(Functional Interface)
        //Java는 기본적으로 객체지향 언어이기 때문에 순수 함수와 일반 함수를 다르게 취급하고 있으며, 이를 구분하기 위해 함수형 인터페이스가 등장
        //함수형 인터페이스란 함수를 1급 객체처럼 다룰 수 있게 해주는 어노테이션으로, 인터페이스에 선언하여 단 하나의 추상 메소드만을 갖도록 제한하는 역할

        // 기존의 익명함수
        /*
        System.out.println(new MyLambdaFunction() {
            public int max(int a, int b) {
                return a > b ? a : b;
            }
        }.max(3, 5));
        */
        // 람다식을 이용한 익명함수
        MyLambdaFunction lambdaFunction = (int a, int b) -> a > b ? a : b;
        System.out.println(lambdaFunction.max(3, 5));   // 5

    }
    @FunctionalInterface
    interface MyLambdaFunction {
        int max(int a, int b);
    }

    static void functionalInterfaceStudy() {
        //자바에서 제공하는 함수형 인터페이스
        //자주 사용될 것 같은 함수형 인터페이스가 이미 정의되어 있으며, 총 4가지 함수형 인터페이스를 지원하고 있다.
        //Supplier<T>     : 매개변수: X, 반환값: T    / 추상 메소드: T get()
        //Consumer<T>     : 매개변수: T, 반환값: X    / 추상 메소드: void accept(T t)  + default 함수: andThen
        //Function<T, R>  : 매개변수: T, 반환값: R    / 추상 메소드: R apply(T t)      + default 함수: compose, andThen, identity
        //Predicate<T>    : 매개변수: T, 반환값: Bool / 추상 메소드: boolean test(T t) + dafault 함수: and, negate, or, isEqual

        // Supplier Example
        Supplier<String> supplier = () -> "Supplier World!";
        System.out.println(supplier.get()); // Supplier World!

        // Consumer Example
        Consumer<String> consumer = (str) -> System.out.println(str.split(" ")[0]);
        consumer.andThen(System.out::println).accept("Consumer World!");
        // Consumer \n Consumer World!

        // Function Example
        Function<String, Integer> function = str -> str.length();
        Function<String, Integer> function2 = String::length;   // 메소드 참조로 간소화 가능(String::length;)
        function.apply("function world1");  //15
        function2.apply("function world2"); //15

        // Predicate Example
        Predicate<String> predicate = (str) -> str.equals("Predicate World");
        predicate.test("Predicate World");  //true
    }

    static void methodReferenceStudy() {
        //메소드 참조
        //함수형 인터페이스를 람다식이 아닌 일반 메소드를 참조시켜 선언하는 방법
        //조건 3개 만족해야 함. (매개변수 타입과 개수, 반환형)
        //참조가능한 메소드는 일반 메소드, Static 메소드, 생성자가 있으며 클래스이름::메소드이름 으로 참조


        //예시1.
        Supplier<String> supplier = String::new;

        //예시2.
        Consumer<String> consumer = System.out::println;
        consumer.accept("Hello World!!");

        //예시3.
        Function<String, Integer> function = str -> str.length();
        Function<String, Integer> function2 = String::length;   // 메소드 참조로 간소화 가능(String::length;)
        function.apply("function world1");  //15
        function2.apply("function world2"); //15

        //예시4.
        Predicate<Boolean> predicate = Objects::isNull;


    }
    static class MyClass {}
}
