package fileio.parkinglot.utils;


public class StringUtils {

    public static boolean isEmpty(String string){

        if(ObjectUtils.isEmpty(string)) return true;

        return string.length() == 0;

    }

}
