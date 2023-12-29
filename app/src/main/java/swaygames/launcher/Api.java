package swaygames.launcher;

public class Api {
    public static String cache_link = "";
    public static String client_link = "";
    public static String update_link = "";
    public static int version_cache = 0;
    public static int version_client = 0;
    public static eTypeLoad typeLoad = eTypeLoad.NONE;

    public enum eTypeLoad {
        NONE,
        FILES,
        UPDATE,
        CLIENT
    }

    public static void setAllValues(String cache, String client, String update, int vers1, int vers2) {
        cache_link = cache;
        client_link = client;
        update_link = update;
        version_cache = vers1;
        version_client = vers2;
    }
}
