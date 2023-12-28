package swaygames.launcher;

public class Api {
    public static eTypeLoad typeLoad = eTypeLoad.NONE;

    public enum eTypeLoad {
        NONE,
        FILES,
        UPDATE,
        CLIENT
    }
}
