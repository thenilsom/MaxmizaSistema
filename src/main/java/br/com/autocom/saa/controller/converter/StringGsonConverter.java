//package br.com.autocom.saa.controller.converter;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Type;
//
//import javax.enterprise.context.Dependent;
//
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParseException;
//
///**
// * Classe parar conversão de {@link Json} para {@link String}.
// * 
// * Classe criada para resolver erro de Caracteres especiais com o WebView 
// * 
// * @author Samuel Oliveira
// */
//@Dependent 
//public class StringGsonConverter  implements JsonDeserializer<String> {
//	
//	private final String ENCODING_UTF8     = "UTF-8";
//	private final String ENCODING_ISO88591 = "ISO-8859-1";
//	
//	/**
//	 * Construtor da classe.
//	 */
//	public StringGsonConverter() {
//		
//		
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
//	 */
//	@Override
//	public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//		
//		
//		String texto = json.getAsString();
//		
//		try {
//		
//			byte[] stringBytesISO;
//	
//			/* Efetua a conversão para UTF-8*/
//			stringBytesISO = texto.getBytes(ENCODING_ISO88591);
//			
//			texto = new String(stringBytesISO, ENCODING_UTF8);
//			
//		} catch (UnsupportedEncodingException e) {
//			
//			return null;
//		}
//		
//		
// 		return texto;
//	}
//}