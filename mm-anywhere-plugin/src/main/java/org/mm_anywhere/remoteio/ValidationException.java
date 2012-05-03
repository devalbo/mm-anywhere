/**
 * 
 */
package org.mm_anywhere.remoteio;

/**
 * @author ajb
 *
 */
@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

	private String _message;
	
	public ValidationException(String message) {
		_message = message;
	}
	
	@Override
	public String toString() {
		return _message;
	}
}
