package cz.cvut.ass.lab02classloaders.test;

import java.util.Arrays;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Test {

    Inner inner = new Inner();

    public void print() {
        inner.print();
        System.out.println("public: " + this.getClass());
        System.out.println("enum: " + EnumClass.class);
    }

    private class Inner {

        public void print() {
            System.out.println("inner: " + this.getClass());
        }
    }

    private enum EnumClass {
    }

    public static void main(String[] args) {
        new Test().print();
        System.out.println("classes: " + Arrays.asList(new Test().getClass().getDeclaredClasses()));
    }
}