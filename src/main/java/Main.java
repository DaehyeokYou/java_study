import reactor.core.publisher.Flux;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //System.out.println("Hello World!!");

        /* Collection Study */
//        CollectionStudy.listStudy();
//        CollectionStudy.queueStudy();
//        CollectionStudy.setStudy();
//        try { CollectionStudy.synchronizedStudy(); }catch (InterruptedException ie) {}
//        CollectionStudy.mapStudy();

        /* Stream Study */
//        StreamStudy.streamCreateStudy();
//        StreamStudy.streamTypeStudy();
//        StreamStudy.streamManufactureStudy();
//        StreamStudy.streamDoneStudy();
//        StreamStudy.advancedStreamStudy();

        /* Lambda Study */
        //LambdaStudy.lambdaStudy();
        //LambdaStudy.functionalInterfaceStudy();

        /* Reactive(Reactor) Study */
//        ReactiveStudy.fluxStudy();
        ReactiveStudy.fluxExam();
    }
}