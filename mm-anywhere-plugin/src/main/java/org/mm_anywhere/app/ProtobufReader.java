package org.mm_anywhere.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.ratatosk.mmrest.data.MmAnywhere.MmDevicesListing;

import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.JsonFormat;
import com.google.protobuf.Message;
import com.google.protobuf.XmlFormat;

/**
 *
 */
@Provider
@Consumes({ProtobufReader.XPROTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProtobufReader implements MessageBodyReader<Message>{

  private static Map<Type, Builder> _protobufReaderBuilderMap;
  public final static String XPROTO = "application/x-proto";
  public final static MediaType XPROTO_TYPE = new MediaType("application", "x-proto");

  {
    _protobufReaderBuilderMap = new HashMap<Type, Builder>();
    _protobufReaderBuilderMap.put(MmDevicesListing.class, 
        MmDevicesListing.newBuilder());
  }

  private static Builder getProtobufBuilder(Type c) {
    Builder builder = _protobufReaderBuilderMap.get(c);
    if (builder != null) {
      return builder.clone();
    }
    return null;
  }
  
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return getProtobufBuilder(type) != null;
  }

  public Message readFrom(Class<Message> type, Type genericType,
      Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
      InputStream entityStream) 
  throws IOException, WebApplicationException {

    final Builder builder = getProtobufBuilder(type);
    if (builder == null) {
      throw new IllegalStateException("Unable to process protobuf input for type: " + type);
    }          
    
    boolean inputOk = true;
    try {
      if (MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType)) {
        String data = toStringBuilder(new InputStreamReader(entityStream)).toString();
        System.out.println("Data from client: " + data);
        JsonFormat.merge(data, builder);

      } else if (XPROTO_TYPE.isCompatible(mediaType)) {
        builder.mergeFrom(entityStream);

      } else if (MediaType.APPLICATION_XML_TYPE.isCompatible(mediaType)) {
        XmlFormat.merge(new InputStreamReader(entityStream), builder);

      } else {
        inputOk = false;
      }

    } catch (Exception e) {
      inputOk = false;
      System.out.println("Exception reading protobuf: " + e.getLocalizedMessage());

    } finally {
      if (!inputOk) {
        throw new IllegalArgumentException("Error processing input for: " + mediaType + ":" + type);        
      }
    }

    return builder.build();
  }

  private static StringBuilder toStringBuilder(Readable input) throws IOException {
    StringBuilder text = new StringBuilder();
    CharBuffer buffer = CharBuffer.allocate(4096);
    while (true) {
        int n = input.read(buffer);
        if (n == -1) {
            break;
        }
        buffer.flip();
        text.append(buffer, 0, n);
    }
    return text;
}
}
