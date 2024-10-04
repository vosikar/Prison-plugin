package me.tox1que.prison.utils.ItemBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomChatColor {
    private static final Map<Character, CustomChatColor> BY_CHAR = new HashMap();
    private static final Map<String, CustomChatColor> BY_NAME = new HashMap();
    private static final LinkedHashMap<String, CustomChatColor> CUSTOM_BY_NAME = new LinkedHashMap();
    private static final Map<String, CustomChatColor> CUSTOM_BY_HEX = new HashMap();
    private static final TreeMap<String, CustomChatColor> CUSTOM_BY_RGB = new TreeMap();
    public static final String colorReplacerPlaceholder = "＆";
    public static final String colorHexReplacerPlaceholder = "{＆#";
    public static final String colorFontPrefix = "{@";
    public static final String colorCodePrefix = "{#";
    public static final String colorCodeSuffix = "}";
    public static final String hexColorRegex = "(\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})";
    public static final Pattern hexColorRegexPattern;
    public static final Pattern hexColorRegexPatternLast;
    public static final Pattern hexDeColorNamePattern;
    public static final String ColorNameRegex = "(\\{#)([a-zA-Z_]{3,})(\\})";
    public static final Pattern hexColorNamePattern;
    public static final Pattern hexColorNamePatternLast;
    public static final String ColorFontRegex = "(\\{@)([a-zA-Z_]{3,})(\\})";
    public static final Pattern gradientPattern;
    public static final String hexColorDecolRegex = "(&x)(&[0-9A-Fa-f]){6}";
    public static final Pattern postGradientPattern;
    public static final Pattern post2GradientPattern;
    public static final Pattern fullPattern;
    public static final Pattern formatPattern;
    public static final CustomChatColor BLACK;
    public static final CustomChatColor DARK_BLUE;
    public static final CustomChatColor DARK_GREEN;
    public static final CustomChatColor DARK_AQUA;
    public static final CustomChatColor DARK_RED;
    public static final CustomChatColor DARK_PURPLE;
    public static final CustomChatColor GOLD;
    public static final CustomChatColor GRAY;
    public static final CustomChatColor DARK_GRAY;
    public static final CustomChatColor BLUE;
    public static final CustomChatColor GREEN;
    public static final CustomChatColor AQUA;
    public static final CustomChatColor RED;
    public static final CustomChatColor LIGHT_PURPLE;
    public static final CustomChatColor YELLOW;
    public static final CustomChatColor WHITE;
    public static final CustomChatColor OBFUSCATED;
    public static final CustomChatColor BOLD;
    public static final CustomChatColor STRIKETHROUGH;
    public static final CustomChatColor UNDERLINE;
    public static final CustomChatColor ITALIC;
    public static final CustomChatColor RESET;
    public static final CustomChatColor HEX;
    private char c;
    private boolean color;
    private boolean isReset;
    private Pattern pattern;
    private int redChannel;
    private int greenChannel;
    private int blueChannel;
    private String hexCode;
    private String name;

    private static String charEscape(String s) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\b':
                    sb.append("\\b");
                    continue;
                case '\t':
                    sb.append("\\t");
                    continue;
                case '\n':
                    sb.append("\\n");
                    continue;
                case '\f':
                    sb.append("\\f");
                    continue;
                case '\r':
                    sb.append("\\r");
                    continue;
                case '"':
                    sb.append("\\\"");
                    continue;
                case '/':
                    sb.append("/");
                    continue;
                case '\\':
                    sb.append("\\\\");
                    continue;
            }

            if (ch >= 0 && ch <= 31 || ch >= 127 && ch <= 159 || ch >= 8192 && ch <= 8447) {
                String ss = Integer.toHexString(ch);
                sb.append("\\u");

                for(int k = 0; k < 4 - ss.length(); ++k) {
                    sb.append('0');
                }

                sb.append(ss.toUpperCase());
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    private static String escape(String text) {
        return text.replace("#", "\\#").replace("{", "\\{").replace("}", "\\}");
    }

    public CustomChatColor(String name, char c, int red, int green, int blue) {
        this(name, c, true, false, red, green, blue);
    }

    public CustomChatColor(String hex) {
        this((String)null, hex);
    }

    public CustomChatColor(String name, String hex) {
        this.color = true;
        this.isReset = false;
        this.pattern = null;
        this.hexCode = null;
        if (hex.startsWith("{#")) {
            hex = hex.substring("{#".length());
        }

        if (hex.endsWith("}")) {
            hex = hex.substring(0, hex.length() - "}".length());
        }

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        this.hexCode = hex;
        this.name = name;

        try {
            this.redChannel = Integer.valueOf(this.hexCode.substring(0, 2), 16);
            this.greenChannel = Integer.valueOf(this.hexCode.substring(2, 4), 16);
            this.blueChannel = Integer.parseInt(this.hexCode.substring(4, 6), 16);
        } catch (Throwable var4) {
            this.hexCode = null;
        }

    }

    public CustomChatColor(String name, char c, Boolean color) {
        this(name, c, color, false);
    }

    public CustomChatColor(String name, char c, Boolean color, Boolean reset) {
        this(name, c, color, reset, -1, -1, -1);
    }

    public CustomChatColor(String name, char c, Boolean color, Boolean reset, int red, int green, int blue) {
        this.color = true;
        this.isReset = false;
        this.pattern = null;
        this.hexCode = null;
        this.name = name;
        this.c = c;
        this.color = color;
        this.isReset = reset;
        this.pattern = Pattern.compile("(?i)(&[" + c + "])");
        this.redChannel = red;
        this.greenChannel = green;
        this.blueChannel = blue;
        if (!name.equalsIgnoreCase("Hex")) {
            BY_CHAR.put(c, this);
            BY_NAME.put(this.getName().toLowerCase().replace("_", ""), this);
        }

    }

    public static String processGradient(String text) {
        Matcher gradientMatch = gradientPattern.matcher(text);

        while(true) {
            String fullmatch;
            CustomChatColor c1;
            CustomChatColor c2;
            do {
                do {
                    if (!gradientMatch.find()) {
                        return text;
                    }

                    fullmatch = gradientMatch.group();
                    c1 = getColor("{#" + gradientMatch.group(2).replace("#", "") + "}");
                    c2 = getColor("{#" + gradientMatch.group(5).replace("#", "") + "}");
                } while(c1 == null);
            } while(c2 == null);

            String gtext = gradientMatch.group(3);
            boolean continuous = !gradientMatch.group(6).isEmpty();
            StringBuilder updated = new StringBuilder();
            Set<CustomChatColor> formats = getFormats(gtext);
            gtext = stripColor(gtext);

            for(int i = 0; i < gtext.length(); ++i) {
                char ch = gtext.charAt(i);
                int length = gtext.length();
                length = length < 2 ? 2 : length;
                double percent = (double)i * 100.0 / (double)(length - 1);
                CustomChatColor mix = mixColors(c1, c2, percent);
                updated.append("{#" + mix.getHex() + "}");
                if (!formats.isEmpty()) {
                    Iterator var16 = formats.iterator();

                    while(var16.hasNext()) {
                        CustomChatColor one = (CustomChatColor)var16.next();
                        updated.append("&" + one.getChar());
                    }
                }

                updated.append(String.valueOf(ch));
            }

            if (continuous) {
                updated.append("{#" + gradientMatch.group(5).replace("#", "") + ">}");
            }

            text = text.replace(fullmatch, updated.toString());
            if (continuous) {
                text = processGradient(text);
            }
        }
    }

    public static String getFinalMessage(String text) {
        text = translate(text);
        text = text.replace("{＆#", "{#");
        text = text.replace("＆", "&");
        return text;
    }

    public static String translate(String text) {
        if (text == null) {
            return null;
        } else {
            text = processGradient(text);
            if (!text.contains("{#")) {
                return ChatColor.translateAlternateColorCodes('&', text);
            } else {
                String strin;
                StringBuilder magi;
                Matcher nameMatch;
                for(nameMatch = hexColorRegexPattern.matcher(text); nameMatch.find(); text = text.replace(strin, magi.toString())) {
                    strin = nameMatch.group();
                    magi = new StringBuilder("§x");
                    char[] var7;
                    int var6 = (var7 = strin.substring(2, strin.length() - 1).toCharArray()).length;

                    for(int var5 = 0; var5 < var6; ++var5) {
                        char c = var7[var5];
                        magi.append('§').append(c);
                        if (strin.substring(2, strin.length() - 1).length() == 3) {
                            magi.append('§').append(c);
                        }
                    }
                }

                nameMatch = hexColorNamePattern.matcher(text);

                while(true) {
                    CustomChatColor cn;
                    String gex;
                    do {
                        if (!nameMatch.find()) {
                            return ChatColor.translateAlternateColorCodes('&', text);
                        }

                        gex = nameMatch.group(2);
                        cn = getByCustomName(gex.toLowerCase().replace("_", ""));
                    } while(cn == null);

                    gex = cn.getHex();
                    StringBuilder magic = new StringBuilder("§x");
                    char[] var10;
                    int var9 = (var10 = gex.toCharArray()).length;

                    for(int var8 = 0; var8 < var9; ++var8) {
                        char c = var10[var8];
                        magic.append('§').append(c);
                    }

                    text = text.replace(nameMatch.group(), magic.toString());
                }
            }
        }
    }

    public static String applyEqualGradient(String text, List<CustomChatColor> gradients) {
        if (gradients != null && !gradients.isEmpty()) {
            int size = text.length() / gradients.size();
            StringBuilder messageWithGradient = new StringBuilder();
            messageWithGradient.append(((CustomChatColor)gradients.get(0)).getFormatedHex(">"));

            for(int y = 0; y <= gradients.size() - 1; ++y) {
                if (y > 0 && size > 0) {
                    messageWithGradient.append(((CustomChatColor)gradients.get(y)).getFormatedHex("<>"));
                }

                for(int i = 0; i < size; ++i) {
                    messageWithGradient.append(text.charAt(0));
                    text = text.substring(1);
                }
            }

            messageWithGradient.append(text + ((CustomChatColor)gradients.get(gradients.size() - 1)).getFormatedHex("<"));
            return messageWithGradient.toString();
        } else {
            return text;
        }
    }

    /** @deprecated */
    @Deprecated
    public static String translateAlternateColorCodes(String text) {
        return translate(text);
    }

    public static String colorize(String text) {
        return text == null ? null : translate(text);
    }

    public static String flaten(String text) {
        return deColorize(text, true).replace("&", "＆").replace("{#", "{＆#");
    }

    public static String deColorize(String text) {
        return deColorize(text, true);
    }

    public static String deColorize(String text, boolean colorizeBeforeDe) {
        if (text == null) {
            return null;
        } else {
            if (colorizeBeforeDe) {
                text = translate(text);
            }

            text = text.replace("§", "&");
            if (text.contains("&x")) {
                Matcher match = hexDeColorNamePattern.matcher(text);

                while(match.find()) {
                    String reg = match.group(3).replace("&", "");
                    CustomChatColor custom = (CustomChatColor)CUSTOM_BY_HEX.get(reg.toLowerCase());
                    if (custom != null) {
                        text = text.replace(match.group(), "{#" + custom.getName().toLowerCase().replace("_", "") + "}");
                    } else {
                        text = text.replace(match.group(), "{#" + reg + "}");
                    }
                }
            }

            return text;
        }
    }

    public static List<String> deColorize(List<String> lore) {
        for(int i = 0; i < lore.size(); ++i) {
            lore.set(i, deColorize((String)lore.get(i)));
        }

        return lore;
    }

    public static String stripColor(String text) {
        if (text == null) {
            return null;
        } else {
            text = translate(text);
            return ChatColor.stripColor(text);
        }
    }

    public static String stripHexColor(String message) {
        message = translate(message);

        Matcher match;
        String string;
        for(match = hexColorRegexPattern.matcher(message); match.find(); message = message.replace(string, "")) {
            string = match.group();
        }

        if (message.contains("&x") || message.contains("§x")) {
            for(match = hexDeColorNamePattern.matcher(message); match.find(); message = message.replace(string, "")) {
                string = match.group();
            }
        }

        return message;
    }

    public static String getLastColors(String text) {
        if (text == null) {
            return null;
        } else {
            text = deColorize(text);
            Matcher match = hexColorRegexPatternLast.matcher(text);
            String colorByName;
            String[] split;
            String last;
            if (match.find()) {
                colorByName = match.group(0);
                if (text.endsWith(colorByName)) {
                    return colorByName;
                } else {
                    split = text.split(escape(colorByName), 2);
                    if (split == null) {
                        return colorByName;
                    } else {
                        last = getLastColors(split[1]);
                        return last != null && !last.isEmpty() ? last : colorByName;
                    }
                }
            } else {
                match = hexColorNamePatternLast.matcher(text);
                if (!match.find()) {
                    return ChatColor.getLastColors(translate(text));
                } else {
                    colorByName = match.group();
                    if (text.endsWith(colorByName)) {
                        return colorByName;
                    } else {
                        split = text.split(escape(colorByName), 2);
                        if (split == null) {
                            return colorByName;
                        } else {
                            last = getLastColors(split[1]);
                            return last != null && !last.isEmpty() ? last : colorByName;
                        }
                    }
                }
            }
        }
    }

    public String getColorCode() {
        return this.hexCode != null ? "{#" + this.hexCode + "}" : "&" + this.c;
    }

    public String getBukkitColorCode() {
        return this.hexCode != null ? translate("{#" + this.hexCode + "}") : "§" + this.c;
    }

    public String toString() {
        return this.getBukkitColorCode();
    }

    public char getChar() {
        return this.c;
    }

    public void setChar(char c) {
        this.c = c;
    }

    public boolean isColor() {
        return this.color;
    }

    public boolean isFormat() {
        return !this.color && !this.isReset;
    }

    public boolean isReset() {
        return this.isReset;
    }

    public ChatColor getColor() {
        return ChatColor.getByChar(this.getChar());
    }

    public static Set<CustomChatColor> getFormats(String text) {
        text = text.replace("§", "&");
        Set<CustomChatColor> formats = new HashSet();
        Matcher match = formatPattern.matcher(text);

        while(match.find()) {
            String string = match.group();
            CustomChatColor format = getFormat(string);
            if (format != null && format.isFormat()) {
                formats.add(format);
            }
        }

        return formats;
    }

    public static CustomChatColor getFormat(String text) {
        if (text == null) {
            return null;
        } else {
            String or = deColorize(text);
            text = text.replace("§", "&");
            if (text.length() > 1) {
                String formated = text.toLowerCase().replace("_", "");
                CustomChatColor got = (CustomChatColor)BY_NAME.get(formated);
                if (got != null) {
                    return got;
                }

                got = (CustomChatColor)CUSTOM_BY_NAME.get(formated);
                if (got != null) {
                    return got;
                }
            }

            if (or.length() > 1 && String.valueOf(or.charAt(or.length() - 2)).equalsIgnoreCase("&")) {
                text = text.substring(text.length() - 1, text.length());
                Iterator var5 = BY_CHAR.entrySet().iterator();

                while(var5.hasNext()) {
                    Map.Entry<Character, CustomChatColor> one = (Map.Entry)var5.next();
                    if (String.valueOf(one.getKey()).equalsIgnoreCase(text)) {
                        return ((CustomChatColor)one.getValue()).isFormat() ? (CustomChatColor)one.getValue() : null;
                    }
                }
            }

            return null;
        }
    }

    public static CustomChatColor getColor(String text) {
        if (text == null) {
            return null;
        } else {
            String or = deColorize(text);
            if (or.contains("{#")) {
                Matcher match = hexColorRegexPatternLast.matcher(or);
                if (match.find()) {
                    return new CustomChatColor(match.group(2));
                }

                match = hexColorNamePatternLast.matcher(or);
                if (match.find()) {
                    return getByCustomName(match.group(2));
                }
            }

            text = deColorize(text).replace("&", "");
            if (text.length() > 1) {
                String formated = text.toLowerCase().replace("_", "");
                CustomChatColor got = (CustomChatColor)BY_NAME.get(formated);
                if (got != null) {
                    return got;
                }

                got = (CustomChatColor)CUSTOM_BY_NAME.get(formated);
                if (got != null) {
                    return got;
                }
            }

            if (or.length() > 1 && String.valueOf(or.charAt(or.length() - 2)).equalsIgnoreCase("&")) {
                text = text.substring(text.length() - 1, text.length());
                Iterator var6 = BY_CHAR.entrySet().iterator();

                while(var6.hasNext()) {
                    Map.Entry<Character, CustomChatColor> one = (Map.Entry)var6.next();
                    if (String.valueOf(one.getKey()).equalsIgnoreCase(text)) {
                        return (CustomChatColor)one.getValue();
                    }
                }
            }

            return null;
        }
    }

    public static CustomChatColor getRandomColor() {
        List<CustomChatColor> ls = new ArrayList();
        Iterator var2 = BY_NAME.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, CustomChatColor> one = (Map.Entry)var2.next();
            if (((CustomChatColor)one.getValue()).isColor()) {
                ls.add((CustomChatColor)one.getValue());
            }
        }

        Collections.shuffle(ls);
        return (CustomChatColor)ls.get(0);
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public Color getRGBColor() {
        return this.blueChannel < 0 ? null : Color.fromRGB(this.redChannel, this.greenChannel, this.blueChannel);
    }

    public String getHex() {
        return this.hexCode;
    }

    public String getFormatedHex() {
        return this.getFormatedHex((String)null);
    }

    public String getFormatedHex(String subSuffix) {
        return "{#" + this.hexCode + (subSuffix == null ? "" : subSuffix) + "}";
    }

    public String getName() {
        return this.name;
    }

    public String getCleanName() {
        return this.name.replace("_", "");
    }

    public static CustomChatColor getByCustomName(String name) {
        if (name.startsWith("{#")) {
            name = name.substring("{#".length());
        }

        if (name.endsWith("}")) {
            name = name.substring(0, name.length() - "}".length());
        }

        if (name.equalsIgnoreCase("random")) {
            List<CustomChatColor> valuesList = new ArrayList(CUSTOM_BY_NAME.values());
            int randomIndex = (new Random()).nextInt(valuesList.size());
            return (CustomChatColor)valuesList.get(randomIndex);
        } else {
            return (CustomChatColor)CUSTOM_BY_NAME.get(name.toLowerCase().replace("_", ""));
        }
    }

    public static CustomChatColor getByHex(String hex) {
        if (hex.startsWith("{#")) {
            hex = hex.substring("{#".length());
        }

        if (hex.endsWith("}")) {
            hex = hex.substring(0, hex.length() - "}".length());
        }

        return (CustomChatColor)CUSTOM_BY_HEX.get(hex.toLowerCase().replace("_", ""));
    }

    public static Map<String, CustomChatColor> getByName() {
        return BY_NAME;
    }

    public static Map<String, CustomChatColor> getByCustomName() {
        return CUSTOM_BY_NAME;
    }

    public static String getHexFromCoord(int x, int y) {
        x = x < 0 ? 0 : (x > 255 ? 255 : x);
        y = y < 0 ? 0 : (y > 255 ? 255 : y);
        int blue = (int)(255.0 - (double)(y * 255) * (1.0 + Math.sin(6.3 * (double)x)) / 2.0);
        int green = (int)(255.0 - (double)(y * 255) * (1.0 + Math.cos(6.3 * (double)x)) / 2.0);
        int red = (int)(255.0 - (double)(y * 255) * (1.0 - Math.sin(6.3 * (double)x)) / 2.0);
        StringBuilder hex = (new StringBuilder()).append(Integer.toHexString((red << 16) + (green << 8) + blue & 16777215));

        while(hex.length() < 6) {
            hex.append("0" + hex);
        }

        return "#" + hex.toString();
    }

    public static String getHexRedGreenByPercent(int percentage, int parts) {
        float percent = (float)percentage * 33.0F / 100.0F / 100.0F;
        java.awt.Color color = java.awt.Color.getHSBColor(percent, 1.0F, 1.0F);
        StringBuilder hex = (new StringBuilder()).append(Integer.toHexString((color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue() & 16777215));

        while(hex.length() < 6) {
            hex.append("0" + hex);
        }

        return "#" + hex.toString();
    }

    public int getRed() {
        return this.redChannel;
    }

    public int getGreen() {
        return this.greenChannel;
    }

    public int getBlue() {
        return this.blueChannel;
    }

    public static CustomChatColor getClosest(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        CustomChatColor closest = (CustomChatColor)CUSTOM_BY_RGB.get(hex);
        if (closest != null) {
            return closest;
        } else {
            java.awt.Color c2 = null;

            try {
                c2 = new java.awt.Color(Integer.valueOf(hex.substring(0, 2), 16), Integer.valueOf(hex.substring(2, 4), 16), Integer.valueOf(hex.substring(4, 6), 16));
            } catch (Throwable var16) {
                return null;
            }

            double distance = Double.MAX_VALUE;
            Iterator var6 = CUSTOM_BY_HEX.entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry<String, CustomChatColor> one = (Map.Entry)var6.next();
                java.awt.Color c1 = new java.awt.Color(Integer.valueOf(((CustomChatColor)one.getValue()).hexCode.substring(0, 2), 16), Integer.valueOf(((CustomChatColor)one.getValue()).hexCode.substring(2, 4), 16), Integer.valueOf(((CustomChatColor)one.getValue()).hexCode.substring(4, 6), 16));
                int red1 = c1.getRed();
                int red2 = c2.getRed();
                int rmean = red1 + red2 >> 1;
                int r = red1 - red2;
                int g = c1.getGreen() - c2.getGreen();
                int b = c1.getBlue() - c2.getBlue();
                double dist = Math.sqrt((double)(((512 + rmean) * r * r >> 8) + 4 * g * g + ((767 - rmean) * b * b >> 8)));
                if (dist < distance) {
                    closest = (CustomChatColor)one.getValue();
                    distance = dist;
                }
            }

            if (closest != null) {
                CUSTOM_BY_RGB.put(hex, closest);
                return closest;
            } else {
                CUSTOM_BY_RGB.put(hex, null);
                return null;
            }
        }
    }

    public CustomChatColor mixColors(CustomChatColor color, double percent) {
        return mixColors(this, color, percent);
    }

    public static CustomChatColor mixColors(CustomChatColor color1, CustomChatColor color2, double percent) {
        percent /= 100.0;
        double inverse_percent = 1.0 - percent;
        int redPart = (int)((double)color2.getRed() * percent + (double)color1.getRed() * inverse_percent);
        int greenPart = (int)((double)color2.getGreen() * percent + (double)color1.getGreen() * inverse_percent);
        int bluePart = (int)((double)color2.getBlue() * percent + (double)color1.getBlue() * inverse_percent);
        String hexCode = String.format("#%02x%02x%02x", redPart, greenPart, bluePart);
        return new CustomChatColor(hexCode);
    }

    static {
        CustomColorNames[] var3;
        int var2 = (var3 = CustomColorNames.values()).length;

        for(int var1 = 0; var1 < var2; ++var1) {
            CustomColorNames one = var3[var1];
            CUSTOM_BY_NAME.put(one.name().toLowerCase().replace("_", ""), new CustomChatColor(one.toString(), one.getHex()));
            CUSTOM_BY_HEX.put(one.getHex().toLowerCase(), new CustomChatColor(one.toString(), one.getHex()));
        }

        for(float x = 0.0F; x <= 1.0F; x = (float)((double)x + 0.1)) {
            for(float z = 0.1F; z <= 1.0F; z = (float)((double)z + 0.1)) {
                for(float y = 0.0F; y <= 1.0F; y = (float)((double)y + 0.03)) {
                    java.awt.Color color = java.awt.Color.getHSBColor(y, x, z);
                    StringBuilder hex = (new StringBuilder()).append(Integer.toHexString((color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue() & 16777215));

                    while(hex.length() < 6) {
                        hex.append("0" + hex);
                    }

                    getClosest(hex.toString());
                }
            }
        }

        hexColorRegexPattern = Pattern.compile("(\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})");
        hexColorRegexPatternLast = Pattern.compile("(\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})(?!.*\\{#)");
        hexDeColorNamePattern = Pattern.compile("((&|§)x)(((&|§)[0-9A-Fa-f]){6})");
        hexColorNamePattern = Pattern.compile("(\\{#)([a-zA-Z_]{3,})(\\})");
        hexColorNamePatternLast = Pattern.compile("(\\{#)([a-zA-Z_]{3,})(\\})(?!.*\\{#)");
        gradientPattern = Pattern.compile("(\\{(#[^\\{]*?)>\\})(.*?)(\\{(#.*?)<(>?)\\})");
        postGradientPattern = Pattern.compile("((\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})|(\\{#)([a-zA-Z_]{3,})(\\}))(.)((\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})|(\\{#)([a-zA-Z_]{3,})(\\}))");
        post2GradientPattern = Pattern.compile("((\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})|(\\{#)([a-zA-Z_]{3,})(\\}))(.)(((\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})|(\\{#)([a-zA-Z_]{3,})(\\}))(.))+");
        fullPattern = Pattern.compile("(&[0123456789abcdefklmnorABCDEFKLMNOR])|(\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(\\})|(\\{#)([a-zA-Z_]{3,})(\\})|(\\{@)([a-zA-Z_]{3,})(\\})");
        formatPattern = Pattern.compile("(&[klmnorKLMNOR])");
        BLACK = new CustomChatColor("Black", '0', 0, 0, 0);
        DARK_BLUE = new CustomChatColor("Dark_Blue", '1', 0, 0, 170);
        DARK_GREEN = new CustomChatColor("Dark_Green", '2', 0, 170, 0);
        DARK_AQUA = new CustomChatColor("Dark_Aqua", '3', 0, 170, 170);
        DARK_RED = new CustomChatColor("Dark_Red", '4', 170, 0, 0);
        DARK_PURPLE = new CustomChatColor("Dark_Purple", '5', 170, 0, 170);
        GOLD = new CustomChatColor("Gold", '6', 255, 170, 0);
        GRAY = new CustomChatColor("Gray", '7', 170, 170, 170);
        DARK_GRAY = new CustomChatColor("Dark_Gray", '8', 85, 85, 85);
        BLUE = new CustomChatColor("Blue", '9', 85, 85, 255);
        GREEN = new CustomChatColor("Green", 'a', 85, 255, 85);
        AQUA = new CustomChatColor("Aqua", 'b', 85, 255, 255);
        RED = new CustomChatColor("Red", 'c', 255, 85, 85);
        LIGHT_PURPLE = new CustomChatColor("Light_Purple", 'd', 255, 85, 255);
        YELLOW = new CustomChatColor("Yellow", 'e', 255, 255, 85);
        WHITE = new CustomChatColor("White", 'f', 255, 255, 255);
        OBFUSCATED = new CustomChatColor("Obfuscated", 'k', false);
        BOLD = new CustomChatColor("Bold", 'l', false);
        STRIKETHROUGH = new CustomChatColor("Strikethrough", 'm', false);
        UNDERLINE = new CustomChatColor("Underline", 'n', false);
        ITALIC = new CustomChatColor("Italic", 'o', false);
        RESET = new CustomChatColor("Reset", 'r', false, true);
        HEX = new CustomChatColor("Hex", 'x', false, false);
    }
}
