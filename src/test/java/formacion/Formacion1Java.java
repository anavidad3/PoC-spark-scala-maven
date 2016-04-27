package formacion;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by navidad on 7/10/15.
 */
public class Formacion1Java {

    private static final Logger LOG = LoggerFactory.getLogger(Formacion1Java.class);
    private static final Integer NUM_INTERATIONS = 1000;


    private Double avg(Integer numIterations) {
        Long result = 0l;
        Integer count = 0;
        for (int i = 0; i < numIterations; i++) {
            result += i;
            count += 1;
        }
        return Double.valueOf(result / count);
    }

    @Test
    public void testAvg() {
        final Double avg = avg(NUM_INTERATIONS);
        Assert.assertEquals(499.0, avg, 0.0000000000000000000001);
    }


    private Double avgWithWhile(Integer numIterations) {
        Long result = 0l;
        Integer count = 0;
        while (count < numIterations) {
            result += count;
            count += 1;
        }
        return Double.valueOf(result / count);
    }

    @Test
    public void testAvgWithWhile() {
        final Double avgWithWhile = avgWithWhile(NUM_INTERATIONS);
        Assert.assertEquals(499.0, avgWithWhile, 0.0000000000000000000001);
    }

    private Set<String> toUpperCaseAndRemoveDuplicate() {
        List<String> namesLowerCase = Arrays.asList("a,b,c,a,b,c,q,w,e,r,t,y,u,i,o".split(","));
        Set<String> collect = new HashSet();
        for (String name : namesLowerCase) {
            collect.add(name.toUpperCase());
        }
        return collect;
    }

    @Test
    public void testToUpperCaseAndRemoveDuplicate() {
        Set<String> expectedSet = new HashSet<>(12);
        expectedSet.addAll(Arrays.asList("A,Q,B,R,C,T,E,U,W,Y,I,O".split(",")));
        final Set<String> actual = toUpperCaseAndRemoveDuplicate();
        Assert.assertEquals(expectedSet, actual);
    }

    private int[][] fillMatrix(int dimX, int dimY) {
        int[][] matrix = new int[dimX][dimY];
        Random rand = new Random(3l);
        for (int i = 0; i < dimX; i++)
            for (int j = 0; j < dimY; j++)
                matrix[i][j] = rand.nextInt();
        return matrix;
    }


    @Test
    public void testFillMatrix() {
        final int[][] matrix = fillMatrix(2, 2);
        Assert.assertEquals(matrix[0][0], -1155099828);
        Assert.assertEquals(matrix[1][1], -836442134);
    }

    private int[][][] fillCube(int dimX, int dimY, int dimZ) {
        int[][][] cube = new int[dimX][dimY][dimZ];
        Random rand = new Random(3l);
        for (int i = 0; i < dimX; i++)
            for (int j = 0; j < dimY; j++)
                for (int k = 0; k < dimZ; k++)
                    cube[i][j][k] = rand.nextInt();
        return cube;
    }

    @Test
    public void testFillCube() {
        final int[][][] cube = fillCube(3, 3, 3);
        Assert.assertEquals(cube[0][0][0], -1155099828);
        Assert.assertEquals(cube[1][1][1], 285587229);
        Assert.assertEquals(cube[2][2][2], -1975716582);
    }

    public void someOperationWithMap(String letterFilter){
        Map<String,String> map = new HashMap<String,String>(4) {{
            put("d", "Dog"); put("b", "Bird"); put("f", "Frog"); put("c", "Cat");
        }};
        Map<String,String> mapFilter = new HashMap<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (entry.getValue().contains(letterFilter)){
                mapFilter.put(entry.getKey(),entry.getValue());
            }
        }
        mapFilter.put("h", "horse");
        LOG.debug(mapFilter.toString());
        String zebraTmp = mapFilter.get("z");
        String zebra = zebraTmp == null ? "ZZZZZebra" : zebraTmp;
        LOG.warn("¡¡¡¡A Zebra: " + zebra + "!!!!");
    }

    @Test
    public void testSomeOperationsWithMap() {
        someOperationWithMap("o");
    }

    private String toYesOrNo(int choice) {
        switch (choice) {
            case 1:
            case 2:
            case 3: return "yes";
            case 0: return "no";
            default: return "error";
        }
    }

    @Test
    public void testToYesOrNo() {
        Assert.assertEquals("yes",toYesOrNo(1));
        Assert.assertEquals("yes", toYesOrNo(3));
        Assert.assertEquals("no",toYesOrNo(0));
        Assert.assertEquals("error",toYesOrNo(12));
    }

    public void isInstanceOf(Object obj) {
        if (obj == null) {
            LOG.debug("Null value. Game over");
            return;
        } else if (obj instanceof Double) {
            LOG.debug("This is a double number" + obj);
        } else if (obj instanceof String) {
            if (obj.equals("AQ_QA")) LOG.debug("Magic Word");
            else LOG.debug("This is a String");
        } else if (obj instanceof List) {
            LOG.debug("This is a List");
            List<?> list = (List<?>) obj;
            LOG.debug("Print list:" + list.toString());
        } else {
            LOG.debug("Not matching found");
        }
    }

    @Test
    public void testIsInstanceOf() {
        isInstanceOf("asd");
        isInstanceOf("AQ_QA");
        isInstanceOf(Arrays.asList(1,2,3));
        isInstanceOf(null);
    }

}
