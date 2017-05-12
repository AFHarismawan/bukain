package in.buka.app.libs.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.buka.app.R;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class ProjectUtils {

    public static int photoHeightFromWidthRatio(final int width) {
        return width * 9 / 16;
    }

    public static String deadlineCountdownDetail(final Long seconds, final Context context) {
        final Map<String, String> substitutions = new HashMap<String, String>() {
            {
                put("time_left", deadlineCountdownUnit(seconds, context));
            }
        };
        return replace(context.getString(R.string.time_left_to_go), substitutions);
    }

    private static String replace(final String string, final  Map<String, String> substitutions) {
        final StringBuilder builder = new StringBuilder();
        for (final String key : substitutions.keySet()) {
            if (builder.length() > 0) {
                builder.append("|");
            }
            builder
                    .append("(%\\{")
                    .append(key)
                    .append("\\})");
        }

        final Pattern pattern = Pattern.compile(builder.toString());
        final Matcher matcher = pattern.matcher(string);
        final StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            final String key = Pattern.compile("[^\\w]").matcher(matcher.group()).replaceAll("");
            final String value = substitutions.get(key);
            final String replacement = Matcher.quoteReplacement(value != null ? value : "");
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    public static String deadlineCountdownUnit(Long seconds, Context context) {
        if (seconds <= 1.0 && seconds > 0.0) {
            return context.getString(R.string.deadline_units_secs);
        } else if (seconds <= 120.0) {
            return context.getString(R.string.deadline_units_secs);
        } else if (seconds <= 120.0 * 60.0) {
            return context.getString(R.string.deadline_units_mins);
        } else if (seconds <= 72.0 * 60.0 * 60.0) {
            return context.getString(R.string.deadline_units_hours);
        }
        return context.getString(R.string.deadline_units_days);
    }

    /**
     * Returns time remaining until project reaches deadline in either seconds,
     * minutes, hours or days. A time unit is chosen such that the number is
     * readable, e.g. 5 minutes would be preferred to 300 seconds.
     *
     * @return the Integer time remaining.
     */
    public static String deadlineCountdownValue(Long seconds) {
        if (seconds <= 120.0) {
            return Integer.toString(seconds.intValue()); // seconds
        } else if (seconds <= 120.0 * 60.0) {
            return Integer.toString((int) Math.floor(seconds / 60.0)); // minutes
        } else if (seconds < 72.0 * 60.0 * 60.0) {
            return Integer.toString((int) Math.floor(seconds / 60.0 / 60.0)); // hours
        }
        return Integer.toString((int) Math.floor(seconds / 60.0 / 60.0 / 24.0)); // days
    }
}
