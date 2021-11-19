package metro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static String getCommandFromInput(String input) {
        Pattern pattern = Pattern.compile("(/[a-zA-Z-]+)\\b");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Invalid command");
    }

    public static boolean isInputCorrect(String input) {
        String pattern = "/[a-zA-Z-]+( \"[a-zA-Z- \\d]+\"| [a-zA-Z-\\d]+){0,4}";
        return input.matches(pattern);
    }

    public static String[] splitParameters(String input) {
        String parameters = input.substring(input.indexOf(" "));
        String[] params = {null, null, null, null};
        Pattern splitter = Pattern.compile("(\"[a-zA-Z- \\d]+\"|\\b[a-zA-Z-\\d]+\\b)");
        Matcher matcher = splitter.matcher(parameters);
        int i = 0;
        while (matcher.find()) {
            String param = matcher.group(1).replace("\"", "");
            params[i++] = param;
        }
        return params;
    }
}
