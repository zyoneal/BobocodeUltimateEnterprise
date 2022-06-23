package FunctionalProgramming;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

public class Main {
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        IntUnaryOperator abs = Math::abs; // 1. Static method reference (Class::staticMethodName)

        IntSupplier intSupplier = random::nextInt; // 2. Non-static method reference (obj::nonMethodName)

        // 3. Static reference of non-static method (Class::nonStaticMethodName)
        String hello = "hello";
        Supplier<String> stringSupplier = hello::toUpperCase;
        UnaryOperator<String> upperFunction = String::toUpperCase;
//        UnaryOperator<String> upperFunction2 = hello::toUpperCase; // won't work

        Function<Integer, String> helloSubstringFunction = hello::substring;
        var subHello = helloSubstringFunction.apply(1); // "ello"

        BiFunction<String, Integer, String> strIntSubstring = String::substring;
        var result = strIntSubstring.apply("Hello", 1); // "ello"

        //=======================HIGH ORDER FUNCTION==========================
        Function<Function<String, Integer>, Predicate<String>> highOrderFunction =
                strToIntFunction -> str -> strToIntFunction.apply(str) > 0;

        Predicate<String> positiveNumber = highOrderFunction.apply(Integer::parseInt);
        System.out.println(positiveNumber.test("-123"));

        //=======================FUNCTION COMPOSITION===========================
        //f(x), g(x) -> f(g(x))
        IntUnaryOperator square = x -> x * x;
        IntUnaryOperator increment = x -> x + 1;
        IntUnaryOperator incrementThenSquare = square.compose(increment);
        IntUnaryOperator squareThenIncrement = square.andThen(increment);
        incrementThenSquare.applyAsInt(3);
        squareThenIncrement.applyAsInt(3);

        // Comparator.comparing(...).thenComparing(...)
        Predicate<String> startsWithA = s -> s.startsWith("A");
        Predicate<String> bigWord = s -> s.length() > 16;
        Predicate<String> bigWordStartsWithA = bigWord.and(startsWithA);
    }
}






















