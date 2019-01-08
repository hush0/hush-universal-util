package hush.spi;

public class MonkeyEat implements Eatfood {

    @Override
    public void eat() {

        System.out.println("monkey eat peach");
    }

    public MonkeyEat() {
    }
}
