package lib;

import java.text.SimpleDateFormat;
import java.util.Random;

public class DataGeneration {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+ timestamp + "@example.com";

    }

    public static String getRandomName (String n) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 0;
        targetStringLength = Integer.parseInt(n);
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        return generatedString;

    }
}

 