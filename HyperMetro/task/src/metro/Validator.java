package metro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern COMMAND = Pattern.compile("(/[a-zA-Z-]+)\\b");
    private static final Pattern CORRECT_INPUT = Pattern.compile("/[a-zA-Z-]+( \"[a-zA-Z- \\d.]+\"| [a-zA-Z-\\d.]+){0,4}");
    private static final Pattern ARGUMENTS_DELIMITERS = Pattern.compile("(\"[a-zA-Z- \\d.]+\"|\\b[a-zA-Z-\\d.]+\\b)");

    public static String getCommandFromInput(String input) {
        Matcher matcher = COMMAND.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Invalid command");
    }

    public static boolean isInputCorrect(String input) {
        Matcher matcher = CORRECT_INPUT.matcher(input);
        return matcher.matches();
    }

    public static String[] splitParameters(String input) {
        String[] params = {null, null, null, null};
        String command = getCommandFromInput(input);
        String parameters = input.replace(command, "");
        if (parameters.isEmpty()) {
            return params;
        }
        Matcher matcher = ARGUMENTS_DELIMITERS.matcher(parameters);
        int i = 0;
        while (matcher.find()) {
            String param = matcher.group(1).replace("\"", "");
            params[i++] = param;
        }
        return params;
    }
}
