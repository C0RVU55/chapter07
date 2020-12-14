package echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws IOException {

		// 소켓 생성
		Socket socket = new Socket();

		System.out.println("<클라이언트 시작>");
		System.out.println("-----------------");
		System.out.println("[서버에 연결을 요청합니다.]");

		// 접속 시도
		socket.connect(new InetSocketAddress("172.30.1.41", 10001));

		System.out.println("[서버에 연결되었습니다.]");

		// socket <---> socket 종이컵 전화기
		// 메세지 보내기용 스트림(피피티 그림 참고)
		OutputStream os = socket.getOutputStream(); // return형이 OutputStream임. socket이 내보낸 걸 받아옴.
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8"); // 포맷정해주기
		BufferedWriter bw = new BufferedWriter(osw);

		// 메세지 받기용 스트림
		InputStream is = socket.getInputStream(); // socket이 인풋 아웃풋 스트림 다 갖고 있음
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		// 스캐너 : 직접 키보드 입력
		Scanner sc = new Scanner(System.in);

		// 반복 구간(채팅처럼 주고받기) : 원하는 대로 반복이 되는지 검사 해보기(디버깅)
		while (true) {
			String str = sc.nextLine();
			if("/q".equals(str)) { 
				break;
			}

			// 메세지 보내기
			bw.write(str);
			bw.newLine();
			bw.flush(); // buffered로 글자수가 충분히 모이지 않아도 데이터를 강제로 보냄. close 없어도 가능하긴 한데 close 하는 게 좋음.

			// 메세지 받기
			String reMsg = br.readLine();
			System.out.println("server:[" + reMsg + "]");

		}

		// 자원 종료
		sc.close();
		bw.close(); // 문자수가 충분하지 않은데 데이터가 보내지는 건 경로 닫아야 되니까 데이터 그냥 다 보내버린 걸로 보임.
		br.close();

		System.out.println("-------------------");
		System.out.println("<클라이언트 종료>");

		socket.close(); // 노란색으로 표시돼 있음. 닫아주기.
	}

}
