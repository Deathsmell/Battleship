package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static void helper(String s) {
        int i = 0;
        Pattern pattern = Pattern.compile("(\\d\\,?\\s?){10}");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            if (i == 9){
                System.out.println("{" + matcher.group() + "}};//" + i++);
                break;
            }
                System.out.println("{" + matcher.group() + "}, //" + i++);
        }
    }


}
