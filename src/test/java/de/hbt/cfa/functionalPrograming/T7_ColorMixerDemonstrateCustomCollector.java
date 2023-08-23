package de.hbt.cfa.functionalPrograming;

import java.awt.*;
import java.util.List;

public class T7_ColorMixerDemonstrateCustomCollector {

    public static void main(String[] args) {
        List<Integer> colors = List.of(Color.BLUE.getRGB(), Color.RED.getRGB(), Color.GREEN.getRGB(), Color.YELLOW.getRGB(), Color.CYAN.getRGB(), Color.MAGENTA.getRGB(), Color.ORANGE.getRGB(), Color.PINK.getRGB(), Color.WHITE.getRGB(), Color.BLACK.getRGB());
        final ColorMixer colorMixer = colors.stream()
                .parallel()
                .collect(ColorMixer::new, ColorMixer::accumulate, ColorMixer::combine);
        System.out.printf("I mixed a total of %s colors and now it's" +
                "\033[38;2;%02.0f;%02.0f;%02.0fm %s \n", colorMixer.totalAmount, colorMixer.r, colorMixer.g, colorMixer.b, colorMixer.toString());
    }

    private static int r(int hex) {
        return (hex & 0xFF0000) >> 16;
    }

    private static int g(int hex) {
        return (hex & 0x00FF00) >> 8;
    }

    private static int b(int hex) {
        return (hex & 0x0000FF);
    }

    public static class ColorMixer {
        private int totalAmount = 0;

        private double r = 0;
        private double g = 0;
        private double b = 0;

        @Override
        public String toString() {
            return String.format("mix (totalAmount=%d,r=%02.0f,g=%02.0f,b=%02.0f)", totalAmount, r, g, b);
        }

        public static void accumulate(ColorMixer colorMixer, int color) {
            if (colorMixer.totalAmount == 0) {
                System.out.printf("Starting with color %s\n", Integer.toHexString(color));
                colorMixer.r = r(color);
                colorMixer.g = g(color);
                colorMixer.b = b(color);
                colorMixer.totalAmount = 1;
            } else {
                System.out.printf("Adding color %s\n", Integer.toHexString(color));
                colorMixer.r = (colorMixer.r * colorMixer.totalAmount + r(color)) / (colorMixer.totalAmount + 1);
                colorMixer.g = (colorMixer.g * colorMixer.totalAmount + g(color)) / (colorMixer.totalAmount + 1);
                colorMixer.b = (colorMixer.b * colorMixer.totalAmount + b(color)) / (colorMixer.totalAmount + 1);
                colorMixer.totalAmount++;
            }
        }

        public static void combine(ColorMixer colorMixer, ColorMixer colorMixer2) {
            System.out.println("Combining " + colorMixer + " with " + colorMixer2);
            colorMixer.r = ((colorMixer.r * colorMixer.totalAmount) + (colorMixer2.r * colorMixer2.totalAmount)) / (colorMixer.totalAmount + colorMixer2.totalAmount);
            colorMixer.g = ((colorMixer.g * colorMixer.totalAmount) + (colorMixer2.g * colorMixer2.totalAmount)) / (colorMixer.totalAmount + colorMixer2.totalAmount);
            colorMixer.b = ((colorMixer.b * colorMixer.totalAmount) + (colorMixer2.b * colorMixer2.totalAmount)) / (colorMixer.totalAmount + colorMixer2.totalAmount);
            colorMixer.totalAmount += colorMixer2.totalAmount;
        }
    }
}
