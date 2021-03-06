import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day4\\src\\main\\resources\\input.txt"));
        HashMap<String, Guard> guards = new HashMap<>();
        lines.sort(String.CASE_INSENSITIVE_ORDER);

        Guard currentGuard = null;
        int asleepMinute = 0;

        for (String line : lines) {
            String pattern = "(\\[.*\\])\\s(.*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(line);
            String dateTime = "";
            String action = "";

            if (m.find()) {
                dateTime = m.group(1).replaceAll("\\[|\\]", "");
                action = m.group(2);
            }

            if (action.contains("begins")) {
                String guardId = action.split(" ")[1].split("#")[1];
                guards.putIfAbsent(guardId, new Guard(guardId));
                currentGuard = guards.get(guardId);

            }

            if (action.contains("asleep")) {
                asleepMinute = Integer.parseInt(dateTime.split(" ")[1].split(":")[1]);
            }

            if (action.contains("wakes")) {
                int awakeMinute = Integer.parseInt(dateTime.split(" ")[1].split(":")[1]);
                for (int i = asleepMinute; i < awakeMinute; i++){
                    currentGuard.addAsleepMinute(i);
                }
            }
        }

        Pair<String, Integer>sleepiestGuard = new Pair<>("0", 0);
        for (Guard guard: guards.values()) {
            if (guard.getTotalSleepMinutes() > sleepiestGuard.getValue()) {
                sleepiestGuard = new Pair<>(guard.id,guard.getTotalSleepMinutes());
            }
        }

        System.out.println("Sleepiest Guard: " + sleepiestGuard.getKey());
        System.out.println("Sleepiest Guard's Sleepiest Minute: " + guards.get(sleepiestGuard.getKey()).getSleepiestMinute().getKey());
        System.out.println("Answer: " + Integer.parseInt(sleepiestGuard.getKey()) * guards.get(sleepiestGuard.getKey()).getSleepiestMinute().getKey());
    }
}
