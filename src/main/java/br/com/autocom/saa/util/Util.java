package br.com.autocom.saa.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * @author Paulo Leonardo de O. Miranda.
 */
public final class Util {
	
	private static final String CHARSET_UFT_8 = "UTF-8";

	/**
	 * Construtor privado para garantir o Singleton.
	 */
	private Util() {

	}
	
    /** 
     * Verifica se a {@link String} informada não esta vazia, desconsiderando espaços no momento da validação.
     * 
     * @param valor
     * @return
     */
    public static boolean isBlank(final String valor){
    	return valor == null || valor.trim().length() == 0;
    }
   
    public static boolean isNull(final Object valor){
    	return valor == null;
    }
    
    public static boolean isNullOrBlank(final Object valor){
    	if (valor instanceof String) {
    		return isBlank((String)valor);
		}else{
			return valor == null;
		}  	
    }
    
    /**
	 * Retorna o valor informado criptografado segundo o algoritimo {@link MessageDigest} informado.
	 * 
	 * @param valor
	 * @param algoritmo
	 * @return
	 */
	private static String getValorCriptografado(final String valor, final String algoritmo) {  
		try {
			if(isBlank(algoritmo)) {
		  		throw new IllegalArgumentException("O algoritimo não foi especificado.");
		  	}
				
		  	if(isBlank(valor)){
				throw new IllegalArgumentException("O valor não foi especificado.");
			}
		  	
			MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
			
			byte[] digest = messageDigest.digest(valor.getBytes(CHARSET_UFT_8));
		    BigInteger hash = new BigInteger(1, digest);  
		    
		    String crypto = hash.toString(16);  
		    if (crypto.length() %2 != 0) {
		        crypto = "0" + crypto;  
		    }
		    return crypto;  
		} catch (GeneralSecurityException | IOException e) {
			throw new RuntimeException("Falha ao Criptografar o valor: " + valor + " em " + algoritmo + ".", e);
		}  
	}
	
	/**
	 * Retorna o valor Criptografado baseado no algoritimo de hash de 128bits unidirecional MD5.
	 * 
	 * @param valor
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 * @throws RuntimeException
	 */
	public static String getValorCriptografadoMD5(final String valor) {  
		return getValorCriptografado(valor, "MD5");
	}
	
	/**
	 * Retorna o valor Criptografado baseado no algoritimo de hash SHA-1.
	 * 
	 * @param valor
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 * @throws RuntimeException
	 */
	public static String getValorCriptografadoSHA1(final String valor) {  
		return getValorCriptografado(valor, "SHA1");
	}
	
}