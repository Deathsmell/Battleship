package by.deathsmell.battleship.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        for (int i = 0; i < 10; i++) {
            sb.append("[").append(++num).append(", ");
            for (int j = 0; j < 9; j++) {
                sb.append(++num);
                if (j != 8){
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i != 9){
                sb.append(",\n");
            }
        }
        System.out.println(sb.toString());
    }


    public static void helper(String s) {
        int i = 0;
        Pattern pattern = Pattern.compile("(\\d,?\\s?){10}");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            if (i == 9){
                System.out.println("{" + matcher.group() + "}};//" + i);
                break;
            }
                System.out.println("{" + matcher.group() + "}, //" + i++);
        }
    }


}
