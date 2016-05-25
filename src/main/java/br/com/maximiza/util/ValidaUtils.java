package br.com.maximiza.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe util para validações diversas na aplicação
 * 
 * @author Denilson Godinho
 *
 */
public final class ValidaUtils {
	
	private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	
	/**
	 * Construtor privado para garantir o Singleton.
	 */
	private ValidaUtils() {

	}

	
	/**
	 * valida o email passado como parametro
	 * 
	 * @param email
	 * @return true se for valido ou false se não for
	 */
	public static boolean isEmailValid(String email) {
		if ((email == null) || (email.trim().length() == 0))
			return false;

		String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		Pattern pattern = Pattern.compile(emailPattern,
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	
	private static int calcularDigito(String str, int[] peso) {
	      int soma = 0;
	      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	         digito = Integer.parseInt(str.substring(indice,indice+1));
	         soma += digito*peso[peso.length-str.length()+indice];
	      }
	      soma = 11 - soma % 11;
	      return soma > 9 ? 0 : soma;
	   }

	
	/**
	 * Verifica se o cpf passado é valido ou não (valida com caracteres especiais e sem)
	 * 
	 * @param cpf
	 * @return true se for valido false se não
	 */
	public static boolean isValidCPF(String cpf) {
		String cpfFormatado = cpf.replace(".", "").replace("-", "");
		cpf = cpfFormatado;
	      if ((cpf==null) || (cpf.length()!=11)) return false;

	      Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
	      Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
	      return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	   }

	/**
	 * Verifica se o cnpj passado é valido ou não (valida com caracteres especiais e sem)
	 * 
	 * @param cnpj
	 * @return true se for valido false se não
	 */	
	   public static boolean isValidCNPJ(String cnpj) {
		   String cnpjFormatado = cnpj.replace(".", "").replace("/", "").replace("-", "");
		   cnpj = cnpjFormatado;
	      if ((cnpj==null)||(cnpj.length()!=14)) return false;

	      Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
	      Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
	      return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	   }

}