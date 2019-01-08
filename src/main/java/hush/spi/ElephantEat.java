package hush.spi;

public class ElephantEat implements Eatfood {


    @Override
    public void eat() {
        System.out.println("elephant eat gress");
    }

    public ElephantEat() {
    }
}
