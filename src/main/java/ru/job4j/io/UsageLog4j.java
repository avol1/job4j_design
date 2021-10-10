package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        byte one = 1;
        short two = 2;
        int three = 3;
        double four = 4;
        float five = 5F;
        double six = 6D;
        char seven = '7';
        boolean yes = true;

        LOG.debug("{}, {}, {}, {}, {}, {}, {} are all numbers - {}", one, two, three, four, five, six, seven, yes);
    }
}