/**
 * 
 */
package br.com.maximiza.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Samuel Oliveira
 *
 */
public class DataUtil {
	
	/**
	 * Retorna A Data Atual Em Local Date Time
	 * @return
	 */
	public static LocalDateTime getLocalDateTime(){
		return LocalDateTime.now();
	}
	
	/**
	 * Retorna A Data Formatada no Padrão dd/mm/aaaa
	 * @return
	 */
	public static String getDataFormatada(){
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return getLocalDateTime().format(formatoData);
	}
	
	/**
	 * Retorna A Data Formatada no Padrão dd/mm/aaaa hh:mm
	 * @return
	 */
	public static String getDataFormatada2(){
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		return getLocalDateTime().format(formatoData);
	}
	
	/**
	 * Retorna A Data Formatada no Padrão dd/mm/aaaa hh:mm
	 * @return
	 */
	public static String getDataFormatada3(){
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH.mm");
		
		return getLocalDateTime().format(formatoData);
	}
	
	/**
	 * Retorna O Primeiro Dia Do Mes Anterior
	 * @return
	 */
	public static LocalDateTime getPrimeiroDiaMes(){
		
		LocalDateTime data = getLocalDateTime();
		data = data.minusMonths(1);
		data = data.with(TemporalAdjusters.firstDayOfMonth());
		
		return data;
	}
	
	/**
	 * Retorna O Ultimo Dia Do Mes Anterior
	 * @return
	 */
	public static LocalDateTime getUltimoDiaMes(){
		
		LocalDateTime data = getLocalDateTime();
		data = data.minusMonths(1);
		data = data.with(TemporalAdjusters.lastDayOfMonth());
		
		return data;
	}
	
	/**
	 * Retorna O Primeiro Dia Da Semana Anterior
	 * @return
	 */
	public static LocalDateTime getPrimeiroDiaSemana(){
		LocalDateTime data = getLocalDateTime();
		data = data.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		
		return data;
	}
	
	/**
	 * Retorna O Ultimo Dia Da Semana Anterior
	 * @return
	 */
	public static LocalDateTime getUltimoDiaSemana(){
		
		LocalDateTime data = getLocalDateTime();
		data = data.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		
		return data;
	}	
	
	/**
	 * Retorna O Primeiro Dia Do Ano Anterior
	 * @return
	 */
	public static LocalDateTime getPrimeiroDiaAno(){
		
		LocalDateTime data = getLocalDateTime();
		data = data.minusYears(1);
		data = data.with(TemporalAdjusters.firstDayOfYear());
		
		return data;
	}	
	
	/**
	 * Retorna O Ultimo Dia Do Ano Anterior
	 * @return
	 */
	public static LocalDateTime getUltimoDiaAno(){
		
		LocalDateTime data = getLocalDateTime();
		data = data.minusYears(1);
		data = data.with(TemporalAdjusters.lastDayOfYear());
		
		return data;
	}	
	
	public static String CalculaTempo(LocalDateTime inicio, LocalDateTime fim){
		
		String tempo = "";

		LocalDateTime temp = LocalDateTime.from( inicio );

		long years = temp.until( fim, ChronoUnit.YEARS);
		temp = temp.plusYears( years );
		if(years>1){
			tempo = years + " Anos, ";
		}else if(years == 1){
			tempo = years + " Ano, ";
		}

		long months = temp.until( fim, ChronoUnit.MONTHS);
		temp = temp.plusMonths( months );
		if(months>1){
			tempo += months + " Meses, ";
		}else if(months==1){
			tempo += months + " Mes, ";
		}
		
		long days = temp.until( fim, ChronoUnit.DAYS);
		temp = temp.plusDays( days );
		if(days>1){
			tempo += days + " Dias, ";
		}else if(days==1){
			tempo += days + " Dia, ";
		}

		long hours = temp.until( fim, ChronoUnit.HOURS);
		temp = temp.plusHours( hours );
		if(hours>1){
			tempo += hours + " Horas, ";
		}else if(hours==1){
			tempo += hours + " Hora, ";
		}

		long minutes = temp.until( fim, ChronoUnit.MINUTES);
		temp = temp.plusMinutes( minutes );
		if(minutes>1){
			tempo += minutes + " Minutos";
		}else{
			tempo += minutes + " Minuto";
		}

		return tempo;
	}
	
	public static long calculaMinutos(LocalDateTime inicio, LocalDateTime fim ,LocalDateTime inicio1, LocalDateTime fim1){
		
		long minutes1 = inicio.until( fim, ChronoUnit.MINUTES);
		long minutes2 = 0; 
		if(inicio1 != null){
			minutes2 = inicio1.until( fim1, ChronoUnit.MINUTES);
		}
		
//		long horas = (minutes1 + minutes2)/60;
		long minutos = (minutes1 + minutes2); //- (horas * 60);
				
//		String tempo = String.valueOf(horas) +":"+String.valueOf(minutos);
//		
//		if(tempo.length() < 4){
//			tempo = tempo + '0';
//		}

		return minutos;
	}

}
