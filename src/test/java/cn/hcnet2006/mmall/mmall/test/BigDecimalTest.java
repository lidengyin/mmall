package cn.hcnet2006.mmall.mmall.test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class BigDecimalTest {
    @Test
    public void test(){
//        System.out.println(0.05+0.01);
//        System.out.println(1.0-0.41);
//        System.out.println(4.015*100);
//        System.out.println(123.3/100);
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
        System.out.println(b1.min(b2));
        System.out.println(b1.max(b2));
        System.out.println(b1.divide(b2));


    }
    @Test
    public void test2(){
//        System.out.println(0.05+0.01);
//        System.out.println(1.0-0.41);
//        System.out.println(4.015*100);
//        System.out.println(123.3/100);
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
        System.out.println(b1.min(b2));
        System.out.println(b1.max(b2));
        System.out.println(b1.divide(b2,2, BigDecimal.ROUND_HALF_UP));


    }
    @Test
    public void test3(){
//        System.out.println(0.05+0.01);
//        System.out.println(1.0-0.41);
//        System.out.println(4.015*100);
//        System.out.println(123.3/100);
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
        System.out.println(b1.min(b2));
        System.out.println(b1.max(b2));
        System.out.println(b1.divide(b2,2, BigDecimal.ROUND_HALF_UP));


    }

    public static void main(String[] args) {

    }
}
