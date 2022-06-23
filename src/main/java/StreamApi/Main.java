package StreamApi;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        var list = List.of(1, 2, 3, 4, 5, 2, 34, 3, 2, 1, 3, 5, 67, 7, 4, 8);

        // Main operations with stream:
        list.stream()
                .forEach(e -> System.out.println(e)); // Consume each element of the stream

        list.stream()
                .filter(e -> e.equals(2)) // Filter each element of the stream
                .collect(Collectors.toList()); // Collect each element into the list

        list.stream()
                .map(e -> e.toString()) // transform each element into the list
                .collect(Collectors.toList());

        // reduce
        var anyInt = list.stream()
                .filter(e -> e.equals(2))
                .reduce((e1, e2) -> e1);

        // flatMap
        var flatMap = list.stream()
                .flatMap(e -> Stream.of(e + 1, e + 2));

        // work with primitives Stream<Double> -> DoubleStream. DoubleStream - new functions like sum, statistics, avg, min, max
        var primitives = list.stream()
                .mapToDouble(e -> Double.parseDouble(e.toString()));

        var toDoubleBoxedFromPrimitive = list.stream()
                .mapToDouble(e -> Double.parseDouble(e.toString())).boxed();

        Stream.generate(() -> list).limit(5).forEach(System.out::println);
        Stream.iterate("A", s -> s + s).limit(10).forEach(System.out::println);

        // Collector - Collectors
        var toList = list.stream()
                .filter(e -> e.equals(2))
                .collect(Collectors.toList());

        var toSet = list.stream()
                .filter(e -> e.equals(2))
                .collect(Collectors.toSet());

        var toMap = list.stream()
                .filter(e -> e.equals(2))
                .collect(Collectors.toMap(e -> e.hashCode(), e -> e.toString()));

        // grouping by
//        var grouping = list.stream()
//                .filter(e -> e.equals(2))
//                .collect(Collectors.groupingBy(e -> e.hashCode()), Collectors.toList());

        // Optional
        var integer = findInteger(list).orElseThrow(() -> new RuntimeException("Error"));

        // OrElseGet -> когда нужно сделать логику (например поход в базу данных). Если оно уже есть, то использовать orElse
        Supplier<Object> defaultObjectSupplier = () -> 0;
        var integerGet = findInteger(list).orElseGet(defaultObjectSupplier);

        // .map + .flatMap -> Optional<Optional<...>> -> Optional<...>
        var string = findInteger(list).map(e -> e.toString());
    }

    static Optional<Object> findInteger(List list) {
        return ThreadLocalRandom.current().nextBoolean() ? Optional.of(list.get(0)) : Optional.empty();
    }
}








