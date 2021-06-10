package cf.nquan.util;

public class Strings {
    public static String capitalizeOnlyFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase();
    }

}
