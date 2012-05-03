/**
 * 
 */
package org.mm_anywhere.remoteio;

import mmcorej.PropertyType;



/**
 * @author ajb
 *
 */
public class RemoteIoTypes {

	public final static String RIO_UNDEFINED = "undefined";
	public final static String RIO_BOOLEAN = "boolean";
	public final static String RIO_INTEGER = "integer";
	public final static String RIO_FLOAT = "float";
	public final static String RIO_STRING = "string";

	public static String getRioTypeForClass(Class<?> type) {
		if (type.isPrimitive()) {
			if (type == Boolean.TYPE) {
				return RIO_BOOLEAN;
			} else if (type == Byte.TYPE || type == Short.TYPE || 
					type == Integer.TYPE || type == Long.TYPE) 
			{
				return RIO_INTEGER;
			} else if (type == Float.TYPE || type == Double.TYPE) {
				return RIO_FLOAT;
			} else if (type == Character.TYPE) {
				return RIO_STRING;
			}

		} else {
			if (type == String.class) {
				return RIO_STRING;
			}
		}

		return RIO_UNDEFINED;
	}

	public static String getRioTypeForMmType(PropertyType type) {
		if (type == PropertyType.Float) {
			return RIO_FLOAT;
		} else if (type == PropertyType.Integer) {
			return RIO_INTEGER;
		} else if (type == PropertyType.String) {
			return RIO_STRING;
		} else {
			return RIO_UNDEFINED;
		}
	}

	public static Object convertStringtoRioType(String value, Class<?> type) {
		if (type == String.class) {
			return value;
		}	else if (type == Boolean.TYPE) {
			return Boolean.parseBoolean(value);
		} else if (type == Long.TYPE) {
			return Long.parseLong(value);
		} else if (type == Double.TYPE) {
			return Double.parseDouble(value);
		} else if (type == Integer.TYPE) {
			return Integer.parseInt(value);
		} else if (type == Float.TYPE) {
			return Float.parseFloat(value);
		}	else if (type == Byte.TYPE) {
			return Byte.parseByte(value);
		} else if (type == Short.TYPE) {
			return Short.parseShort(value);
		} else
			return null;
	}
}
