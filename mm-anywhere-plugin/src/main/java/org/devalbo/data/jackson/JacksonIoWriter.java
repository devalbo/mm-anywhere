package org.devalbo.data.jackson;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 */
@Provider
@Produces({JacksonIoReader.XPROTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.WILDCARD})
public class JacksonIoWriter implements MessageBodyWriter<IJacksonIo> {

  private String _asString;
  private ObjectMapper _objectMapper = new ObjectMapper();

  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return IJacksonIo.class.isAssignableFrom(type);
  }

  public long getSize(IJacksonIo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

    try {
      _asString = _objectMapper.writeValueAsString(t);
    } catch (JsonGenerationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return -1;
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return -1;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return -1;
    }

    return _asString.length();
  }

  public void writeTo(final IJacksonIo t, Class<?> type, Type genericType, Annotation[] annotations, final MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) 
  throws IOException, WebApplicationException 
  {
    entityStream.write(_asString.getBytes());
  }

}
