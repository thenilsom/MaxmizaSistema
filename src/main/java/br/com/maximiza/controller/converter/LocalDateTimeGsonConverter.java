/**
 * 
 */
package br.com.maximiza.controller.converter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Samuel Oliveira
 *
 */
public class LocalDateTimeGsonConverter implements JsonSerializer<LocalDateTime> , JsonDeserializer<LocalDateTime> {
	
	private final DateTimeFormatter iso8601MilissegundosFormat;
	private final DateTimeFormatter FormatoData;

	/**
	 * Contrutor da classe.
	 *
	 */
	public LocalDateTimeGsonConverter() {
		this.iso8601MilissegundosFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		this.FormatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	}

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(LocalDateTime data, Type arg1, JsonSerializationContext arg2) {
		 
		 String d = data.format(this.iso8601MilissegundosFormat);
		 return new JsonPrimitive(d); 
	}

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context){
		
		String data = json.getAsString();
		
		LocalDateTime d = null;
		
		if(data.length()< 11){
			
			d = LocalDateTime.from(LocalDate.parse(data, FormatoData).atStartOfDay());
		
		}else{
			
			d = LocalDateTime.parse(data, iso8601MilissegundosFormat);
		}
		
		return d;
	}

}
