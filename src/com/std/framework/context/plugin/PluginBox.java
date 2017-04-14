package com.std.framework.context.plugin;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Luox Context Plugins 插件箱，顺次执行置入其中的插件。
 */
public class PluginBox {

    private static List<ContextPlugin> plugins = new LinkedList<ContextPlugin>();

    public static void addPlugin (ContextPlugin plugin) {
        plugins.add(plugin);
    }

    public static void runPlugins () {
        Iterator<ContextPlugin> iterator = plugins.iterator();
        while (iterator.hasNext()) {
            ContextPlugin next = iterator.next();
            next.plugin();
        }
    }

}
