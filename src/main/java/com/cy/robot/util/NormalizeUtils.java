package com.cy.robot.util;

import org.apache.commons.lang3.CharUtils;
import org.springframework.util.StringUtils;

import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS;
import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;


/**
 * 语句规范化转换工具类。
 *
 */
public class NormalizeUtils {

    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    static final char SBC_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    static final char DBC_SPACE = 32; // 半角空格

    /**
     * 把字符串中半角字符转成全角
     *
     * @param src 输入字符串
     * @return 转换后的字符串
     */
    public static String toFullWidth(String src) {

        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (ca[i] + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca[i]);
            }
        }
        return buf.toString();

    }

    /**
     * 把字符串中全角字符转成半角
     *
     * @param src 输入字符串
     * @return 转换后的字符串
     */
    public static String toHalfWidth(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 把字符串中标点符号转成全角
     *
     * @param src 输入字符串
     * @return 转换后的字符串
     */
    public static String punctuatioToFullWidth(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (isAsciiPunctuation(ca[i])) { // 如果是半角标点符号
                buf.append((char) (ca[i] + CONVERT_STEP));
            } else { // 不处理半角标点符号外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 移除非英文字母中间的空格
     *
     * @param src 输入字符串
     * @return 转换后的字符串
     */
    public static String removeBlank(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (i != 0 && i != ca.length - 1) {
                if (ca[i] == ' ') {
                    if (CharUtils.isAsciiAlpha(ca[i - 1]) && CharUtils.isAsciiAlpha(ca[i + 1])) {
                        buf.append(ca[i]);
                    }
                } else {
                    buf.append(ca[i]);
                }
            } else {
                buf.append(ca[i]);
            }

        }
        return buf.toString();

    }

    /**
     * 判断是否是半角标点符号
     *
     * @param ch 字符
     * @return 是否是半角标点符号
     */
    public static boolean isAsciiPunctuation(final char ch) {
        switch (ch) {
            case '!':
            case '"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case '{':
            case '|':
            case '}':
            case '~':
                return true;
            default:
                return false;
        }

    }

    /**
     * 判断是否是标点符号
     *
     * @param ch 字符
     * @return 是否是标点符号
     */
    public static boolean isPunctuation(final char ch) {
        // return (ch >= DBC_CHAR_START && ch <= DBC_CHAR_END)
        //   || (ch >= SBC_CHAR_START && ch <= SBC_CHAR_END);

        if (isAsciiPunctuation(ch)) {
            return true;
        }
        final int type = Character.getType(ch);
        switch (type) {
            case Character.DASH_PUNCTUATION:
            case Character.START_PUNCTUATION:
            case Character.END_PUNCTUATION:
            case Character.CONNECTOR_PUNCTUATION:
            case Character.OTHER_PUNCTUATION:
            case Character.INITIAL_QUOTE_PUNCTUATION:
            case Character.FINAL_QUOTE_PUNCTUATION:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPunctuationOrSpace(final char ch) {
        return ch == DBC_SPACE || DBC_SPACE == SBC_SPACE || isPunctuation(ch);
    }

    /**
     * 替换标点符号
     *
     * @param text 输入字符串
     * @return 转换后的字符串
     */
    public static String normalizePunctuation(String text) {
        text = text.replaceAll("[,、;；]", "，");
        text = text.replaceAll("[!！?？]", "。");
        text = text.replaceAll("[\"“”'‘’]", "");
        return text;
    }


    /**
     * 检查字符串是否包含中文。
     *
     * @param checkStr 字符串
     * @return 是否包含中文
     */
    public static boolean checkStringContainChinese(String checkStr) {
        if (!StringUtils.isEmpty(checkStr)) {
            char[] checkChars = checkStr.toCharArray();
            for (int i = 0; i < checkChars.length; i++) {
                char checkChar = checkChars[i];
                if (checkCharContainChinese(checkChar)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查字符是否是中文。
     *
     * @param checkChar 字符
     * @return 是否是中文
     */
    public static boolean checkCharContainChinese(char checkChar) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);
        if (CJK_UNIFIED_IDEOGRAPHS == ub
                || CJK_COMPATIBILITY_IDEOGRAPHS == ub
                || CJK_COMPATIBILITY_FORMS == ub
                || CJK_RADICALS_SUPPLEMENT == ub
                || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub
                || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub) {
            return true;
        }
        return false;
    }

    /**
     * 规则化名字，去除中间的空格。
     *
     * @param str 字符串
     * @return 规则化后的字符串
     */
    public static String normalizeName(String str) {
        return toHalfWidth(str.toLowerCase().replaceAll("\\s", ""));
    }

}

