package lib;

import java.text.SimpleDateFormat;

public class DataGeneration {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+ timestamp + "@example.com";

    }
}
