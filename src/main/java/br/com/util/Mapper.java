package br.com.util;

import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;

public class Mapper {

    public static final String PATTERN_FULL = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_SIMPLE = "dd/MM/yyyy";
    public static final String PATTERN_SHORT = "yyyy-MM";

    public static <S, T> T map(S source, Class<T> classTarget) {
        T target = createInstanceOf(classTarget);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <S, T> T map(S source, Class<T> classTarget, Consumer<T> postMap) {
        T target = map(source, classTarget);
        postMap.accept(target);
        return target;
    }

    public static LocalDate parseLocalDate(String value, String pattern){
        return Optional.ofNullable(value)
                .map( strDate -> LocalDate.parse(strDate, DateTimeFormatter.ofPattern(pattern)))
                .orElse(null);
    }

    public static Instant parseToInstant(String value, String pattern){
        return Optional.ofNullable(value)
                .map ( strDate ->
                LocalDateTime.parse(
                        strDate,
                        DateTimeFormatter.ofPattern(pattern)
                ).atZone(ZoneId.systemDefault()).toInstant()
                ).orElse(null);
    }

    @SuppressWarnings("deprecation")
	private static <T> T createInstanceOf(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception ex) {

        }
        return t;
    }
}