import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) throws Throwable {
        String[] strArr = new String[]{"a1", "a2", "a3", "a1", "a4", "a5", "a1"};
        //String[] strArr = new String[]{};
        count(strArr, "a1");
        //find(strArr, "a2");
        lastElement(strArr);
        remove(strArr, 2, 2);
        distinctAverageName(strArr);
        distinctSortDesc(strArr);
        sumNotEven(strArr);
    }

    private static void count(String[] strArr, String element) {
        Stream<String> stream = Arrays.stream(strArr);
        long count = stream.filter(i -> i == element).count();
        System.out.println(element + " найдено : " + count + " елементов");
    }

    private static void find(String[] strArr, String element) throws Throwable {
        Stream<String> stream = Arrays.stream(strArr);
        stream.filter(x -> x == element).findAny().orElseThrow(() -> new Exception(element + " не найден"));
        System.out.println(element + " найден");
    }

    private static void lastElement(String[] strArr) {
        Stream<String> stream = Arrays.stream(strArr);
        Optional<String> lastElement = Optional.of(stream
                .skip(strArr.length == 0 ? 0 : strArr.length - 1)
                .limit(1)
                .findAny()
                .orElse("<<empty>>"));
        System.out.println("Вернуть последний элемент коллекции или «empty», если коллекция пуста : " + lastElement.get());
    }

    private static void remove(String[] strArr, int skip, int limit) {
        if (skip > strArr.length && skip < 0) new Exception("Вышли за пределы массива");
        if (limit + skip > strArr.length) new Exception("Вышли за пределы массива");

        Stream<String> stream = Arrays.stream(strArr);
        List<String> list = stream.skip(skip).limit(limit).toList();
        System.out.println("Вернуть два элемента начиная со второго элемента коллекции : " + list);
    }

    private static void distinctAverageName(String[] strArr) {
        // TODO:: неправильная логика длина имен должна быть другой
        Stream<String> stream = Arrays.stream(strArr);
        AtomicLong count = new AtomicLong();
        AtomicLong sum = new AtomicLong();
        stream.distinct().mapToInt(x -> Integer.parseInt(String.valueOf(x.charAt(1)))).forEach(x -> {
            count.incrementAndGet();
            sum.addAndGet(x);
        });
        String average = count.get() == 0 ? "Делить на 0 нельзя" : String.valueOf(sum.get() / count.get());
        String result = String.format("%s / %s = %s", sum.get(), count.get(), average);
        System.out.println("Из коллекции имен убрать все повторения и найти среднюю длину имен : " + result);
    }

    private static void distinctSortDesc(String[] strArr) {
        Stream<String> stream = Arrays.stream(strArr);
        String str = stream.distinct().sorted((o1, o2) -> o2.compareTo(o1)).toList().toString();
        System.out.println("Отсортировать коллекцию строк по убыванию и убрать дубликаты : " + str);
    }

    private static void sumNotEven(String[] strArr) {
        Stream<String> stream = Arrays.stream(strArr);
        int sum = stream.mapToInt(x -> Integer.parseInt(String.valueOf(x.charAt(1)))).filter(x -> x % 2 != 0).sum();
        System.out.println("Вернуть сумму нечетных чисел или 0 : " + sum);
    }

}