package hush.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class test {

    //测试时，将jar 引入到调用的工程中，用serviceLoader 加载执行
    public static void main(String[] args) {


       /* Eatfood Eeat = new ElephantEat();
        Eatfood  Meat = new MonkeyEat();
        Eeat.eat();
        Meat.eat();*/

        ServiceLoader<Eatfood> eatBreakfirst = ServiceLoader.load(Eatfood.class);
        Iterator<Eatfood> eatBreakfirstIterator = eatBreakfirst.iterator();

        //System.out.println("classpalth: "+System.getProperty("java.class.path"));

        while(eatBreakfirstIterator.hasNext()){

            Eatfood  eatfood = eatBreakfirstIterator.next();

            eatfood.eat();
        }

    }


}
