package hush.fearure.simple.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitList {


    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("element1");
        list.add("element2");
        list.add("element3");


        List<String> list1 = Arrays.asList("lili","jery","pigpege");


        List<String> list2 = Stream.of("mon","tues","wedne","thur","Fri","satur","sun").collect(Collectors.toList());

        /*List<String> list3 = Lists.newArrayList("one", "two", "three");
        List<String> list4 = Lists.of("one", "two", "three");*/

        list1.stream().parallel().forEach(iteam->System.out.println(iteam));


    }

}
