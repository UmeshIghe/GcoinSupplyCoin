package utils;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import common.Logger;

@Component
public class ReflectionUtils {
	final static Logger logger = Logger.getLogger(ReflectionUtils.class);

	private ReflectionUtils() {

	}

	public static boolean setRequestField(Object object, String fieldName,
			Object fieldValue) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			} catch (NoSuchFieldException e) {
				logger.error(e);
				clazz = clazz.getSuperclass();
				assertNotNull("Field not found: ".concat(fieldName),
						e.getMessage());
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return false;
	}

	public static void setRequestClass(String className, String fieldName,
			String fieldValue) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
			Object instance = clazz.newInstance();
			setRequestField(instance, fieldName, fieldValue);

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			logger.error(e);
			assertNotNull("Request class not found: ".concat(className),
					e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public static <V> V getRequestField(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return (V) field.get(object);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
				logger.error(e);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return null;
	}

	public static String getRequestClass(String className, String fieldName) {
		Class<?> clazz;
		try {
			clazz = Class.forName(className);

			Object instance = clazz.newInstance();

			return getRequestField(instance, fieldName);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			assertNotNull("Request field not found: ".concat(fieldName),
					e.getMessage());
			logger.error(e);
		}
		return null;

	}

	public static Object getClass(String className) {

		try {
			Class<?> clazz = Class.forName("com.mastercard.requests."
					+ className);

			return clazz.newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			assertNotNull("Request class not found: ".concat(className),
					e.getMessage());
			logger.error(e);
		}
		return null;

	}
}
