package soa.utils;

public class Converter {
    public static Integer intConvert(String probablyNumber){
        try {
            return Integer.parseInt(probablyNumber);
        } catch (Exception ex){
            return null;
        }
    }

    public static Long longConvert(String probablyNumber){
        try {
            return Long.parseLong(probablyNumber);
        } catch (Exception ex){
            return null;
        }
    }

    public static Double doubleConvert(String probablyNumber){
        try {
            return Double.parseDouble(probablyNumber);
        } catch (Exception ex){
            return null;
        }
    }
}
