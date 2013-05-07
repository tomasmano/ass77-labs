package ass.manotoma.lab01.helper;

import ass.manotoma.ReversibleLinkedList;
import ass.manotoma.lab01.task01.helper.MyLinkedList;
import ass.manotoma.lab01.Factories;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class MyLinkedListTest {

    @Test
    public void test_add_elems_to_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        assertFalse("Elements added, list returned that is empty", list.isEmpty());
        assertEquals(3, list.size());
        System.out.println(MyLinkedList.toList(list));
    }

    @Test
    public void test_iterating_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));

        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(Int(1), it.next());
        assertEquals(Int(2), it.next());
        assertEquals(Int(3), it.next());
        assertEquals(Int(4), it.next());
        assertEquals(Int(5), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void test_contains_elem_in_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        assertTrue(list.contains(Int(1)));
        assertTrue(list.contains(Int(2)));
        assertFalse(list.contains(Int(5)));
        assertFalse(list.contains(Int(6)));
    }

    @Test
    public void test_isEmpty_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        assertTrue(list.isEmpty());
        list.add(new Integer(1));
        assertFalse(list.isEmpty());
        list.add(new Integer(2));
        list.add(new Integer(3));
        assertFalse(list.isEmpty());
    }

    @Test
    public void test_remove_elem_from_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));
        assertTrue(list.contains(Int(3)));
        assertTrue(list.size() == 5);
        list.remove(Int(3));
        assertTrue(list.size() == 4);
        assertFalse(list.contains(Int(3)));
    }

    @Test
    public void test_remove_elem_from_small_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        assertTrue(list.contains(Int(1)));
        assertTrue(list.size() == 1);
        list.remove(Int(1));
        assertTrue(list.isEmpty());
        assertFalse(list.contains(Int(1)));
    }

    @Test
    public void test_remove_all_elem_from_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        assertTrue(list.contains(Int(1)));
        assertTrue(list.contains(Int(2)));
        assertTrue(list.size() == 2);
        list.remove(Int(1));
        list.remove(Int(2));
        assertTrue(list.isEmpty());
        assertFalse(list.contains(Int(1)));
        assertFalse(list.contains(Int(2)));
    }

    @Test
    public void test_descending_iterating_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));

        Iterator<Integer> it = list.descendingIterator();
        assertTrue(it.hasNext());
        assertEquals(Int(5), it.next());
        assertEquals(Int(4), it.next());
        assertEquals(Int(3), it.next());
        assertEquals(Int(2), it.next());
        assertEquals(Int(1), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void test_descending_iterating_small_linked_list() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));

        Iterator<Integer> it = list.descendingIterator();
        assertTrue(it.hasNext());
        assertEquals(Int(1), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void test_add_all() {
        MyLinkedList<Integer> list1 = Factories.getEmptyMyLinkedList();
        list1.add(new Integer(1));
        list1.add(new Integer(2));
        list1.add(new Integer(3));
        list1.add(new Integer(4));
        list1.add(new Integer(5));

        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(new Integer(6));
        list2.add(new Integer(7));
        list2.add(new Integer(8));
        list2.add(new Integer(9));
        list2.add(new Integer(10));

        list1.addAll(list2);
        assertTrue(list1.size() == 10);
        list1.contains(Int(2));
        list1.contains(Int(6));
        list1.contains(Int(10));
        
    }
    
    @Test
    public void test_sorting() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(5));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(2));
        list.sort();
        Iterator<Integer> it = list.iterator();
        assertEquals(Int(1), it.next());
        assertEquals(Int(2), it.next());
        assertEquals(Int(3), it.next());
        assertEquals(Int(4), it.next());
        assertEquals(Int(5), it.next());
    }

    @Test
    public void test_huge_sorting() {
        MyLinkedList<Integer> myList = Factories.getEmptyMyLinkedList();
        List<Integer> javaList = Factories.getListWithRandomNubers(1000);
        myList.addAll(javaList);
        Collections.sort(javaList);
        assertEquals(javaList.size(), myList.size());
        myList.sort();
        Iterator<Integer> it = myList.iterator();
        int count = 0;
        while(it.hasNext()) {
            assertEquals(javaList.get(count), it.next());
            count++;
        }
    }

    @Test
    public void test_huge_sorting_in_high_level() {
        ReversibleLinkedList<Integer> myList = new ReversibleLinkedList<Integer>();
        List<Integer> javaList = Factories.getListWithRandomNubers(100);
        myList.addAll(javaList);
        Collections.sort(javaList);
        assertEquals(javaList.size(), myList.size());
        myList.sort();
        Iterator<Integer> it = myList.iterator();
        int count = 0;
        while(it.hasNext()) {
            assertEquals(javaList.get(count), it.next());
            count++;
        }
    }

    @Test
    public void test_getting_node_by_index() {
        MyLinkedList<Integer> list = Factories.getEmptyMyLinkedList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));
        assertEquals(Int(3), list.node(2).getElement());
        assertEquals(Int(5), list.node(4).getElement());
        
    }


    private static Integer Int(int i) {
        return new Integer(i);
    }
}
