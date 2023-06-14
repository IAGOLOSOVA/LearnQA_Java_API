package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataGeneration {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+ timestamp + "@example.com";

    }

    public static Map<String,String> getRegistrationData() {
        Map<String,String> data = new HashMap<>();
        data.put("email",DataGeneration.getRandomEmail());
        data.put("password","123");
        data.put("username","lernqa");
        data.put("firstName","lernqa");
        data.put("lastName","lernqa");
        return data;
    }

    public static Map<String,String> getRegistrationData(Map<String,String> nonDefaultValues) {
        Map<String,String> defaultValues = DataGeneration.getRegistrationData();
        Map<String,String> userData = new HashMap<>();
        String[] keys = {"email","password","username","firstName","lastName"};
        for (String key:keys){
            if(nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            } else {
                userData.put(key,defaultValues.get(key));
            }
        }
        return userData;
        
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

 