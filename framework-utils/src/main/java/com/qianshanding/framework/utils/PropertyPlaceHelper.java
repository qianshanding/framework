package com.qianshanding.framework.utils;

import java.util.Properties;
import java.util.Stack;

/**
 * @author Fish
 */
public class PropertyPlaceHelper extends Properties {

    /**
     *
     */
    private static final long serialVersionUID = 667785506630277824L;

    public PropertyPlaceHelper(Properties defaults) {
        super(defaults);
    }

    @Override
    public String getProperty(String name) {
        String value = super.getProperty(name);
        return value != null ? resolve(value) : null;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        String value = getProperty(name);
        return value != null ? value : defaultValue;
    }

    public String resolve(String string) {
        StringBuffer sb = new StringBuffer();
        Stack<StringBuffer> stack = new Stack<StringBuffer>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            switch (c) {
                case '$':
                    if (i + 1 < string.length() && string.charAt(i + 1) == '{') {
                        stack.push(sb);
                        sb = new StringBuffer();
                        i++;
                    }
                    break;
                case '}':
                    if (stack.isEmpty())
                        throw new IllegalArgumentException("unexpected '}'");
                    String name = sb.toString();
                    sb = stack.pop();
                    sb.append(super.getProperty(name, null));
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        if (!stack.isEmpty())
            throw new IllegalArgumentException("expected '}'");
        return sb.toString();
    }
}
