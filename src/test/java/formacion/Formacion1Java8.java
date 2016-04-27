package formacion;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Formacion1Java8 {

    private static final Logger LOG = LoggerFactory.getLogger(Formacion1Java8.class);


    @Test
    public void testAvgWithJava8() {
        Integer numIterations = 1000;
        final Double avgWithJava8 = avgWithJava8(numIterations);
        Assert.assertEquals(499.5, avgWithJava8, 0.0000000000000000000001);
    }

    public Double avgWithJava8(Integer numIterations) {
        return IntStream.range(0, numIterations).average().getAsDouble();
    }

    @Test
    public void testToUpperCaseAndRemoveDuplicateJava8() {
        Set<String> expectedSet = new HashSet<>(12);
        expectedSet.addAll(Arrays.asList("A,Q,B,R,C,T,E,U,W,Y,I,O".split(",")));
        final Set<String> actual = toUpperCaseAndRemoveDuplicateJava8();
        Assert.assertEquals(expectedSet, actual);
    }

    public Set<String> toUpperCaseAndRemoveDuplicateJava8() {
        List<String> namesLowerCase = Arrays.asList("a,b,c,a,b,c,q,w,e,r,t,y,u,i,o".split(","));
        final Set<String> collect = namesLowerCase.stream()
                .map(name -> name.toUpperCase())
                .collect(Collectors.toSet());
        return collect;
    }

    @Test
    public void testFillMatrix() {
        final int[][] matrix = fillMatrixJava8(2, 2);
        Assert.assertEquals(matrix[0][0], -1155099828);
        Assert.assertEquals(matrix[1][1], -836442134);
        final int[][] matrix2 = fillMatrixJava8WithPseudoFuntional(2, 2);
        Assert.assertEquals(matrix2[0][0], -1155099828);
        Assert.assertEquals(matrix2[1][1], -836442134);
    }

    public int[][] fillMatrixJava8(int dimX, int dimY) {
        int[][] matrix = new int[dimX][dimY];
        Random rand = new Random(3l);
        IntStream.range(0, dimX)
                .forEach(x -> IntStream.range(0, dimY)
                        .forEach(y -> matrix[x][y] = rand.nextInt()));
        return matrix;
    }

    public int[][] fillMatrixJava8WithPseudoFuntional(int dimX, int dimY) {
        int[][] matrix = new int[dimX][dimY];
        Random rand = new Random(3l);
        IntStream.range(0, dimX)
                .forEach(completeColumn(matrix, dimY, rand));
        return matrix;
    }

    /**
     * Pseudo-Funtional programming
     *
     * @param matrix
     * @param dimY
     * @param rand
     * @return
     */
    private IntConsumer completeColumn(int[][] matrix, int dimY, Random rand) {
        return x -> IntStream.range(0, dimY)
                .forEach(y -> matrix[x][y] = rand.nextInt());
    }

    public void someOperationWithMapJava8(String letterFilter) {
        Map<String, String> map = new HashMap<String, String>(4) {{
            put("d", "Dog");
            put("b", "Bird");
            put("f", "Frog");
            put("c", "Cat");
        }};
        final Map<String, String> mapFilter = map.entrySet().stream()
                .filter(entry -> entry.getValue().contains(letterFilter))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        mapFilter.put("h", "horse");
        LOG.debug(mapFilter.toString());
        String zebra = mapFilter.getOrDefault("z", "ZZZZZebra");
        LOG.warn("¡¡¡¡A Zebra: " + zebra + "!!!!");
    }

    @Test
    public void testSomeOperationsWithMapJava8() {
        someOperationWithMapJava8("o");
    }

}
