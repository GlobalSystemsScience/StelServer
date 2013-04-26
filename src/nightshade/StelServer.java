package nightshade;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class StelServer {
	static String[] stringContainer = new String[1];

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(51283);
		Socket s = ss.accept();
		final PrintWriter pw = new PrintWriter(s.getOutputStream());
		Scanner scan = new Scanner(s.getInputStream());
		while (scan.hasNext()) {
			String line = scan.nextLine();
			final Process p = Runtime.getRuntime().exec("./StelWrite.out " + line);
			new Thread() {
				public void run() {
					InputStream es = p.getErrorStream();
					Scanner eScan = new Scanner(es);
					while (eScan.hasNext()) {
						pw.println(eScan.nextLine());
						pw.flush();
					}
				}
			}.start();
			InputStream is = p.getInputStream();
			Scanner iScan = new Scanner(is);
			while (iScan.hasNext()) {
				pw.println(iScan.nextLine());
				pw.flush();
			}
		}
		scan.close();
		s.close();
		ss.close();
	}
}