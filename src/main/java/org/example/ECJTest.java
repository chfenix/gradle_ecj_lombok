package org.example;

public class ECJTest implements BaseInterface<TClass>{

    public static void main(String[] args) {
        TClass  s = new TClass();
        System.out.println(s.hashCode());

        LombokData lombokTest = new LombokData();
        System.out.println(lombokTest.getTest());

    }
}
