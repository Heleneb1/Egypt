//package com.example.egypt.config;
//
//
//import com.fasterxml.jackson.core.JsonGenerator;
//        import com.fasterxml.jackson.databind.JsonSerializer;
//        import com.fasterxml.jackson.databind.SerializerProvider;
//        import org.springframework.security.core.GrantedAuthority;
//
//        import java.io.IOException;
//
//public class GrantedAuthoritySerializer extends JsonSerializer<GrantedAuthority> {
//    @Override
//    public void serialize(GrantedAuthority grantedAuthority, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        jsonGenerator.writeString(grantedAuthority.getAuthority());
//    }
//}
