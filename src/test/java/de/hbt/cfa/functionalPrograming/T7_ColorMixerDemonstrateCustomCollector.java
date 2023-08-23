package de.hbt.cfa.functionalPrograming;

import java.util.stream.IntStream;

public class T7_ColorMixerDemonstrateCustomCollector {

    public static void main(String[] args) {
        // mix a list of random colors but never manifest the list of colors
        final ColorMixer colorMixer = IntStream.generate(T7_ColorMixerDemonstrateCustomCollector::randomColor)
                .limit(10000)
                .parallel()
                .collect(ColorMixer::new, ColorMixer::accumulate, ColorMixer::combine);
        System.out.printf("I mixed a total of %s colors and now it's" +
                "\033[38;2;%02.0f;%02.0f;%02.0fm %s%n", colorMixer.totalAmount, colorMixer.r, colorMixer.g, colorMixer.b, colorMixer.toString());
    }

    private static int randomColor() {
        return (int) (Math.random() * 0xffffff);
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
            if (totalAmount == 0) return "ColorMix[empty]";
            return String.format("\033[38;2;%02.0f;%02.0f;%02.0fmColorMix[totalAmount=%d,r=%02.0f,g=%02.0f,b=%02.0f]", r, g, b, totalAmount, r, g, b);
        }

        public static void accumulate(ColorMixer colorMixer, int color) {
            if (colorMixer.totalAmount == 0) {
                colorMixer.r = r(color);
                colorMixer.g = g(color);
                colorMixer.b = b(color);
                colorMixer.totalAmount = 1;
                System.out.printf("\033[38;2;%02.0f;%02.0f;%02.0fmStarting with color %s%n", colorMixer.r, colorMixer.g, colorMixer.b, Integer.toHexString(color));
            } else {
                System.out.printf("\033[38;2;%02d;%02d;%02dmAdding color %s", r(color), g(color), b(color), Integer.toHexString(color));
                colorMixer.r = (colorMixer.r * colorMixer.totalAmount + r(color)) / (colorMixer.totalAmount + 1);
                colorMixer.g = (colorMixer.g * colorMixer.totalAmount + g(color)) / (colorMixer.totalAmount + 1);
                colorMixer.b = (colorMixer.b * colorMixer.totalAmount + b(color)) / (colorMixer.totalAmount + 1);
                colorMixer.totalAmount++;
                System.out.printf(" and got %s%n", colorMixer);

            }
        }

        public static void combine(ColorMixer colorMixer, ColorMixer colorMixer2) {
            System.out.printf("Combining %s with %s", colorMixer, colorMixer2);
            colorMixer.r = ((colorMixer.r * colorMixer.totalAmount) + (colorMixer2.r * colorMixer2.totalAmount)) / (colorMixer.totalAmount + colorMixer2.totalAmount);
            colorMixer.g = ((colorMixer.g * colorMixer.totalAmount) + (colorMixer2.g * colorMixer2.totalAmount)) / (colorMixer.totalAmount + colorMixer2.totalAmount);
            colorMixer.b = ((colorMixer.b * colorMixer.totalAmount) + (colorMixer2.b * colorMixer2.totalAmount)) / (colorMixer.totalAmount + colorMixer2.totalAmount);
            colorMixer.totalAmount += colorMixer2.totalAmount;
            System.out.printf(" and got %s%n", colorMixer);
        }
    }
}
