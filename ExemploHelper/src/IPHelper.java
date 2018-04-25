import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

public class IPHelper {

	public static void main(String args[]) {
		System.out.println(isIpValido("172.4.200.18"));
	}

	/*
	 * Testa se um ip está na rede ou não
	 */
	public static boolean isOnLine(String host) {
		boolean ping = false;

		if (isIpValido(host)) {

			String comando = "ping -c 1 " + host;

			try {
				String s = "";
				Process process = Runtime.getRuntime().exec(comando);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				int c = 0;

				while ((s = stdInput.readLine()) != null) {

					if (c == 1) {
						String outProcess = s.toString();

						if (outProcess.contains("64 bytes") || outProcess.contains("40 bytes")) {
							ping = true;

						} else {
							ping = false;
						}
					}

					c++;
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Endereço Ip Inválido: " + host, "Validação do IP",
					JOptionPane.WARNING_MESSAGE);

		}

		return ping;
	}

	/*
	 * Realiza um ping dado determinado IP
	 */
	public static String ping(String ip) {
		String resposta = "";
		String comando = "ping -c 1 " + ip;
		try {
			Scanner s = new Scanner(Runtime.getRuntime().exec(comando).getInputStream());

			while (s.hasNextLine()) {
				resposta += s.nextLine() + "\n";
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return resposta;
	}

	/*
	 * Cria uma comunicacao com a porta desejada, se a porta estiver disponivel
	 * returna true, caso contrário uma exception ira ocorrer e retornara false
	 */
	public static boolean criarComunicacaoComPorta(int port) {
		try {
			ServerSocket srv = new ServerSocket(port);
			srv.close();
			srv = null;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/*
	 * Verifica se o IP é valido
	 */
	public static boolean isIpValido(String ip) {
		if (ip == null || ip.isEmpty())
			return false;
		ip = ip.trim();
		if ((ip.length() < 6) & (ip.length() > 15))
			return false;

		try {
			Pattern pattern = Pattern.compile(
					"^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
			Matcher matcher = pattern.matcher(ip);
			return matcher.matches();

		} catch (PatternSyntaxException ex) {
			return false;
		}
	}
}
