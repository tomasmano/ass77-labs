package cz.cvut.ass.lab02classloaders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        CustomClassLoader loader = new CustomClassLoader();
        Class trezor = loader.loadClass("Trezor", true);
        Field f1 = trezor.getField("firstLocked");
        Field f2 = trezor.getDeclaredField("secondLocked");
        f2.setAccessible(true);
        Method m = trezor.getDeclaredMethod("open", null);
        Object o = trezor.newInstance();
        f1.set(o, false);
        f2.set(o, false);
        m.invoke(o, null);
        
        
    }
}
