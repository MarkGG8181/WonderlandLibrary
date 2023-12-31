package dev.monsoon.command.impl.yanchop;

import dev.monsoon.Monsoon;
import dev.monsoon.command.Command;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class YanchopCommand extends Command {

    public YanchopCommand() {
        super("Yanchop", "yanchop = cool", "yanchop <username>", "dox");
    }

    @Override
    public void onCommand(String[] args, String command) {
        YanchopUtil.instance.yanchop(args);
    }
}
