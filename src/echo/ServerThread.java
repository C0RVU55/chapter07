package echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	// 필드
	private Socket socket;

	// 생성자
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	// 메소드 겟셋

	// 메소드 일반
	@Override
	public void run() { // 오류나서 던지면 Server에서 처리해야 되니까 여기서 간단하게 처리함.
		try {
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			// 메세지 보내기용 스트림
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			// 반복 구간
			while (true) {
				// 메세지 받기
				String msg = br.readLine();

				if (msg == null) {
					break;
				}
				System.out.println("받은메세지:" + msg);

				// 메세지 보내기 : 서버에서 '메세지 보내기'는 클라이언트에서 볼 수 있도록(출력될 수 있도록) 보낸다는 의미. 
				bw.write(msg);
				bw.newLine();
				bw.flush();
			}

			bw.close();
			br.close();
		} catch (IOException e) { // 빨간줄에 무슨 오류인지 보고 그거 넣으면 됨.
			e.printStackTrace(); // 오류나면 오류내용 출력한다는 뜻.
		}

	}

}
