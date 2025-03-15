/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset;

/**
 *
 * @author anawi
 */
public class StringFormatter {

    public static String formatString(String input) {
        String spaced = input.replace("_", " ");
        spaced = spaced.replaceAll("(?<=[a-z])([A-Z])", " $1");
        
        String[] words = spaced.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        
        return result.toString().trim();
    }
}

