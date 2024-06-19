package org.example.DZ2v1;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class RandomAnnotationProcessor {

    public static void processAnnotation(Object obj) {
        // найти все поля класса, над которыми стоит аннотация @Random
        // вставить туда рандомное число в диапазоне [0, 100)

        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            // obj.getClass().isAssignableFrom(AnnotationsMain.Person.class)

            if (field.isAnnotationPresent(Random.class) && field.getType().isAssignableFrom(int.class)) {
                Random annotation = field.getAnnotation(Random.class);
                int min = annotation.min();
                int max = annotation.max();

                try {
                    field.setAccessible(true); // чтобы можно было изменять финальные поля
                    field.set(obj, random.nextInt(min, max));
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }
        }

    }



    public static void processAnnotationForData(Object obj) {
        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomDate.class)) {
                RandomDate annotation = field.getAnnotation(RandomDate.class);
                long min = annotation.min();
                long max = annotation.max();
                ZoneId zone = ZoneId.of(annotation.zone());

                field.setAccessible(true); // чтобы можно было изменять финальные поля
                if (min < max) {
                    try {
                        long rnd = min + (long) (random.nextDouble() * (max - min));
                        if (field.getType().isAssignableFrom(long.class)) {
                            field.set(obj, rnd);
                        } else if (field.getType().isAssignableFrom(Instant.class)) {
                            Instant instant = Instant.ofEpochMilli(rnd);
                            field.set(obj, instant);
                        } else if (field.getType().isAssignableFrom(LocalDate.class)) {
                            LocalDate localDate = Instant.ofEpochMilli(rnd).atZone(zone).toLocalDate();
                            field.set(obj, localDate);
                        } else if (field.getType().isAssignableFrom(LocalDateTime.class)) {
                            LocalDateTime localDateTime = Instant.ofEpochMilli(rnd).atZone(zone).toLocalDateTime();
                            field.set(obj, localDateTime);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new IllegalArgumentException("min должно быть меньше max. min = " + min + ", max = " + max);
//                    System.out.println("min должно быть меньше max. min = ");
                }
            }
        }
    }

//    public static void processAnnotationForData(Object obj) {
//        // TODO: добавить проверку на min,max
//        java.util.Random random = new java.util.Random();
//        Class<?> objClass = obj.getClass();
//        for (Field field : objClass.getDeclaredFields()) {
//
//            if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(long.class)) {
//                RandomDate annotation = field.getAnnotation(RandomDate.class);
//                long min = annotation.min();
//                long max = annotation.max();
//
//                field.setAccessible(true); // чтобы можно было изменять финальные поля
//                if (min <= max) {
//                    try {
////                      long rnd = random.nextLong(min, max);
//                        long rnd = min + (long) (random.nextDouble() * (max - min));
//                        String format = String.format("%tc", rnd);
//                        field.set(obj, rnd);
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    throw new IllegalArgumentException("min должно быть меньше max. min = " + min + ", max = " + max);
//                }
//            }
//        }

//    }

//    public static void processAnnotationForData(Object obj) {
//        java.util.Random random = new java.util.Random();
//        Class<?> objClass = obj.getClass();
//        for (Field field : objClass.getDeclaredFields()) {
//            if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(long.class)) {
//                RandomDate annotation = field.getAnnotation(RandomDate.class);
//                long min = annotation.min();
//                long max = annotation.max();
//
//                field.setAccessible(true); // чтобы можно было изменять финальные поля
//                if (min < max) {
//                    try {
//                        long rnd = min + (long) (random.nextDouble() * (max - min));
//                        field.set(obj, rnd);
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    throw new IllegalArgumentException("min должно быть меньше max. min = " + min + ", max = " + max);
//                }
//            } else if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(Instant.class)) {
//                RandomDate annotation = field.getAnnotation(RandomDate.class);
//                long min = annotation.min();
//                long max = annotation.max();
//
//                field.setAccessible(true);
//                if (min < max) {
//                    try {
//                        long rnd = min + (long) (random.nextDouble() * (max - min));
//                        Instant instant = Instant.ofEpochMilli(rnd);
//                        field.set(obj, instant);
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    throw new IllegalArgumentException("min должно быть меньше max. min = " + min + ", max = " + max);
//                }
//            }
//        }
//    }
}



