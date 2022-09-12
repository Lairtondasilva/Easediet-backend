package com.gft.diet.convertion;

import org.springframework.stereotype.Service;

@Service
public class Conversor {

    public String snakeToCamel(String str) {
        // Capitalize first letter of string
        str = str.substring(0, 1).toLowerCase()
                + str.substring(1);

        // Convert to StringBuilder
        StringBuilder builder = new StringBuilder(str);

        // Traverse the string character by
        // character and remove underscore
        // and capitalize next letter
        for (int i = 0; i < builder.length(); i++) {

            // Check char is underscore
            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        // Return in String type
        return builder.toString();
    }
}
