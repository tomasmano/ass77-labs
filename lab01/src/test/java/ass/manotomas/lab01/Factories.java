package ass.manotomas.lab01;

import ass.manotomas.lab01.task01.helper.MyLinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Factories {

    private Factories() {
        // prevent instantiation
    }
    
    public static <E extends Comparable<E>> MyLinkedList<E> getEmptyMyLinkedList(){
        return new MyLinkedList<E>();
    }

    public static MyLinkedList<Integer> getMyLinkedList(){
        MyLinkedList list = new MyLinkedList<Integer>();
        list.add(new Integer(3));
        list.add(new Integer(19));
        list.add(new Integer(1));
        list.add(new Integer(20));
        list.add(new Integer(7));
        list.add(new Integer(10));
        return list;
    }

    public static MyLinkedList<Integer> getSortedMyLinkedList(){
        MyLinkedList list = new MyLinkedList<Integer>();
        list.add(new Integer(1));
        list.add(new Integer(3));
        list.add(new Integer(7));
        list.add(new Integer(10));
        list.add(new Integer(19));
        list.add(new Integer(20));
        return list;
    }
    
    public static List<Integer> getListWithRandomNubers(int n) {
        ArrayList<Integer> arrayRandom = new ArrayList<Integer>(n);

        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < n; i++) {
            Integer r = rand.nextInt() % 256;
            arrayRandom.add(r);
        }
        return arrayRandom;
    }
}
