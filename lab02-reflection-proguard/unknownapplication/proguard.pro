-injars       unknownApplication.jar
-outjars      unknownApplication-obf.jar
-libraryjars  JAVA_HOME/lib/rt.jar
-printmapping proguard.map

-keep public class ass.Main {
    public static void main(java.lang.String[]);
}