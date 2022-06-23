package JavaNetworking;

import lombok.Data;

public class CustomJackson {

    public static void main(String[] args) {

        var json = "{\n" +
                "            \"firstName\": \"Viktor\",\n" +
                "            \"lastName\": \"Piven\",\n" +
                "            \"email\": \"viktor.piven@eleks.com\"\n" +
                "        }";

        var user = jsonToObject(json, User.class);
        System.out.println(user);
    }

    private static <T> T jsonToObject(String json,Class<T> userClass) {
        return null;
    }

    @Data
    static class User {

        private String firstName;

        private String lastName;

        private String email;
    }

}
