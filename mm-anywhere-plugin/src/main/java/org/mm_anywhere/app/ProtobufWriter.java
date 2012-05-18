package org.mm_anywhere.app;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.protobuf.Message;
//import com.googlecode.protobuf.format.HtmlFormat;
//import com.googlecode.protobuf.format.JsonFormat;
//import com.googlecode.protobuf.format.XmlFormat;

/**
 *
 */
@Provider
@Produces({ProtobufReader.XPROTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
public class ProtobufWriter implements MessageBodyWriter<Message> {

  private String _asString;

  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return Message.class.isAssignableFrom(type);
  }

  public long getSize(Message t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    if (ProtobufReader.XPROTO_TYPE.isCompatible(mediaType)) {
      return t.getSerializedSize();
    }
  
    if (MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType)) {
//      _asString = JsonFormat.printToString(t);
    } else if (MediaType.APPLICATION_XML_TYPE.isCompatible(mediaType)) {
//      _asString = XmlFormat.printToString(t);
    } else if (MediaType.TEXT_HTML_TYPE.isCompatible(mediaType)) {
//      _asString = HtmlFormat.printToString(t);
    } else if (MediaType.TEXT_PLAIN_TYPE.isCompatible(mediaType)) {
      _asString = t.toString();
    } else { //unsupported type
      return -1;
    }
    return _asString.length();
  }

  public void writeTo(final Message t, Class<?> type, Type genericType, Annotation[] annotations, final MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) 
  throws IOException, WebApplicationException 
  {
    for (Annotation annotation : annotations) {
    }
    
    if (ProtobufReader.XPROTO_TYPE.isCompatible(mediaType)) {
      entityStream.write(t.toByteArray());
    } else if (MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType) ||
        MediaType.APPLICATION_XML_TYPE.isCompatible(mediaType) ||
        MediaType.TEXT_HTML_TYPE.isCompatible(mediaType) ||
        MediaType.TEXT_PLAIN_TYPE.isCompatible(mediaType)) 
    {
      entityStream.write(_asString.getBytes());
    } else {
      throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
    }
  }

}
