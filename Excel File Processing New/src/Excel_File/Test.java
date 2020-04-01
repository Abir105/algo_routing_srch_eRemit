/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel_File;


import java.util.*;
import java.util.stream.*;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        list.add("baz");
        String result = list
            .stream()
            .map(s -> s+"AA")
            .collect(Collectors.joining());
        System.out.println(result); // fbb
    }
}
