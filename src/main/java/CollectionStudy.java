import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CollectionStudy {

    static void listStudy() {
        List<String> arrList = new ArrayList<>();
        List<String> arrList2 = new ArrayList<>();
        List<String> lnkList = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();

        /********** Collection Test ***************/
        Collection<String> colList = new ArrayList<>();
        colList.add("collection test");
        //print(colList.get(0)); // Error: Collection은 list, queue, set과 같은 다양한 자료구조에 상속해야 하기 때문에 get이 구현되어 있지 않음.

        /********** ArrayList Test ***************/

        String[] strArr = {"dfs", "qewr", "zcv"};
        arrList2.add("dsfa");
        arrList2.add("qewr");
        arrList2.add("zccvxz");

        arrList.add("added String");
        //arrList.addAll(strArr); // Error: Object Collection만 가능
        arrList.addAll(arrList2);
        //arrList.add(10, "10번째"); // Error: 4번째 넣을 차례... 요소 사이에 빈공간을 허용하지 않아 삽입/삭제 할때마다 배열 이동이 일어난다
        arrList.add(4,"hoho");

        /********** LinkedList Test ***************/
        // list와 달리 특별한 함수 없음. 특징과 사용 목적 정리로 대체.
        // 삽입, 삭제가 빈번할 경우 빠른 성능을 보장한다.
        // 하지만 임의의 요소에 대한 접근 성능은 좋지 않다.
        // 자바의 LinkedList는 Doubly LinkedList(양방향 포인터 구조)로 이루어져 있다.
        // LinkedList는 리스트 용도 이외에도, 스택, 큐, 트리 등의 자료구조의 근간

        /********** Stack Test ***************/
        stack.push(30);
        stack.push(50);

        stack.pop(); // 50: LIFO
        stack.pop(); // 30: LIFO
    }

    // 우선순위 큐에 저장할 객체는 필수적으로 Comparable Interface를 구현
    static class Student implements Comparable<Student> {
        String name; // 학생 이름
        int priority; // 우선순위 값

        public Student(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Student user) {
            // Student의 priority 필드값을 비교하여 우선순위를 결정하여 정렬
            if (this.priority < user.priority) {
                return -1;
            } else if (this.priority == user.priority) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", priority=" + priority +
                    '}';
        }
    }

    static void queueStudy() {
        Queue<Integer> queue = new LinkedList<>();
        Queue<Student> priorityQueue = new PriorityQueue<>();
        Deque<Integer> deque = new ArrayDeque<>();

        /********** Queue Test ***************/
        queue.add(3); // add: 실패시 Exception
        queue.offer(2); // add: 실패시 false
        queue.offer(1);
        queue.offer(2);

        print(queue.poll()); // 3: 큐는 FIFO.
        print(queue.size()); // 3: poll은 head에 있는 값 삭제
        print(queue.peek()); // 2: peek - 공백이면 null
        print((queue.element())); // 2: element - 공백이면 Exception

        /********** PriorityQueue Test ***************/
        // Student Class 참고(Comparable를 구현)
        priorityQueue.offer(new Student("주몽", 5));
        priorityQueue.add(new Student("세종", 9));
        priorityQueue.offer(new Student("홍길동", 1));
        priorityQueue.add(new Student("임꺽정", 2));

        // 우선순위 대로 정렬되어 있음
        System.out.println(priorityQueue);
        // [Student{name='홍길동', priority=1}, Student{name='임꺽정', priority=2}, Student{name='주몽', priority=5}, Student{name='세종', priority=9}]

        // 우선순위가 가장 높은 값을 참조
        System.out.println(priorityQueue.peek()); // Student{name='홍길동', priority=1}

        // 차례대로 꺼내기
        System.out.println(priorityQueue.poll()); // Student{name='홍길동', priority=1}
        System.out.println(priorityQueue.poll()); // Student{name='임꺽정', priority=2}

        /********** Dequeue Test ***************/
        deque.offerLast(100); // [100]
        deque.offerFirst(10); // [10, 100]
        deque.offerFirst(20); // [20, 10, 100]
        deque.offerLast(30); // [20, 10, 100, 30]

        print(deque.pollFirst()); // 20 <- [10, 100, 30]
        print(deque.pollLast()); // [10, 100] -> 30
        print(deque.pollFirst()); // 10 <- [100]
        print(deque.pollLast()); // [] -> 100
    }
    enum Color {
        RED, YELLOW, GREEN, BLUE, BLACK, WHITE
    }

    static void setStudy() {
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        Set<Integer> treeSet = new TreeSet<>();

        /********** HashSet Test ***************/
        hashSet.add(10);
        hashSet.add(20);
        hashSet.add(30);
        hashSet.add(10); // 중복된 요소 추가 안됨. 중복 허용 X

        print(hashSet.size()); // 3 - 중복된건 카운트 X
        print(hashSet.toString()); // [20, 10, 30] - 자료 순서가 뒤죽박죽

        /********** LinkedHashSet Test ***************/
        linkedHashSet.add(10);
        linkedHashSet.add(20);
        linkedHashSet.add(30);
        linkedHashSet.add(10); // 중복된 요소 추가 안됨. 중복 허용 X

        print(linkedHashSet.size()); // 3 - 중복된건 카운트 X
        print(linkedHashSet.toString()); // [10, 20, 30] - 대신 자료가 들어온 순서대로 출력

        /********** TreeSet Test ***************/
        treeSet.add(7);
        treeSet.add(4);
        treeSet.add(9);
        treeSet.add(4); // 중복된 요소 추가 안됨. 중복 허용 X

        print(treeSet.size()); // 3 - 중복된건 카운트 X
        print(treeSet.toString()); // [4, 7, 9] - 자료가 알아서 정렬됨

        /********** EnumSet Test ***************/
        // 정적 팩토리 메서드를 통해 RegularEnumSet 혹은 JumboEnumSet 을 반환
        // 만일 enum 상수 원소 갯수가 64개 이하면 RegularEnumSet, 이상이면 JumboEnumSet 객체를 반환
        EnumSet<Color> enumSet = EnumSet.allOf(Color.class);

        print(enumSet.size()); // 6
        print(enumSet.toString()); // [RED, YELLOW, GREEN, BLUE, BLACK, WHITE]
    }


    static void synchronizedStudy() throws InterruptedException {
        // Collections.synchronizedList() 메서드를 이용해 ArrayList를 동기화 처리 가능
        List<Integer> syncArrList = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 16; i++)
            syncArrList.add(i);

        System.out.println(syncArrList);

        Runnable task = () -> {
            synchronized (syncArrList) {    //synchronized 블락 없으면 thread-safe하지 않음.
                ListIterator<Integer> itr = syncArrList.listIterator();
                while (itr.hasNext())
                    itr.set(itr.next() + 1);
            }
        };

        ExecutorService exsvc = Executors.newFixedThreadPool(3);
        exsvc.submit(task);
        exsvc.submit(task);
        exsvc.submit(task);

        exsvc.shutdown();
        exsvc.awaitTermination(100, TimeUnit.SECONDS);

        System.out.println(syncArrList);
    }

    static void mapStudy() {
        Map<String, Integer> mapEntries = new HashMap<>();

        /********** Map.Entry Test ***************/
        mapEntries.put("a", 1);
        mapEntries.put("b", 2);
        mapEntries.put("c", 3);
        // Map.Entry 인터페이스를 구현하고 있는 Key-Value 쌍을 가지고 있는 HashMap의 Node 객체들의 Set 집합을 반환
        Set<Map.Entry<String, Integer>> entry = mapEntries.entrySet();

        System.out.println(entry); // [a=1, b=2, c=3]
        // Set을 순회하면서 Map.Entry를 구현한 Node 객체에서 key와 value를 얻어 출력
        for (Map.Entry<String, Integer> e : entry) {
            System.out.printf("{ %s : %d }\n", e.getKey(), e.getValue());
        }

        /********** HashMap Test ***************/
        Map<Integer, String> hashMap = new HashMap<>();

        hashMap.put(10000000, "one");
        hashMap.put(20000000, "two");
        hashMap.put(30000000, "tree");

        for(Integer key : hashMap.keySet()) {
            System.out.println(key + " => " + hashMap.get(key));
            // HashMap 정렬 안됨
            // 20000000 => two
            // 10000000 => one
            // 30000000 => tree
        }

        /********** LinkedHashMap Test ***************/
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put(10000000, "one");
        linkedHashMap.put(20000000, "two");
        linkedHashMap.put(30000000, "tree");

        for(Integer key : linkedHashMap.keySet()) {
            System.out.println(key + " => " + linkedHashMap.get(key));
            // LinkedHashMap 정렬됨
            // 10000000 => one
            // 20000000 => two
            // 30000000 => tree
        }

        /********** TreeMap Test ***************/
        Map<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(10000000, "Toby");
        treeMap.put(20000000, "Ruth");
        treeMap.put(30000000, "jennifer");

        for(Integer key : treeMap.keySet()) {
            System.out.println(key + " => " + treeMap.get(key));
            // 정렬됨.
            // 10000000 => Toby
            // 20000000 => Ruth
            // 30000000 => jennifer
        }
    }

    static void 결론() {
//        ArrayList
//
//        리스트 자료구조를 사용한다면 기본 선택
//        임의의 요소에 대한 접근성이 뛰어남
//        순차적인 추가/삭제 제일 빠름
//        요소의 추가/삭제 불리
//
//
//        LinkedList
//
//        요소의 추가/삭제 유리
//        임의의 요소에 대한 접근성이 좋지 않음
//
//
//        HashMap / HashSet
//
//        해싱을 이용해 임의의 요소에 대한 추가/삭제/검색/접근성 모두 뛰어남
//        검색에 최고성능 ( get 메서드의 성능이 O(1) )
//
//
//        TreeMap / TreeSet
//
//        요소 정렬이 필요할때
//        검색(특히 범위검색)에 적합
//        그래도 검색 성능은 HashMap보다 떨어짐
//
//
//        LinkedHashMap / LinkedHashSet : HashMap과 HashSet에 저장 순서 유지 기능을 추가
//        Queue : 스택(LIFO) / 큐(FIFO) 자료구조가 필요하면 ArrayDeque 사용
//        Stack, Hashtable : 가급적 사용 X (deprecated)
    }

    static void print(String s) {
        System.out.println(s);
    }
    static void print(int i) {
        System.out.println(i);
    }

}
