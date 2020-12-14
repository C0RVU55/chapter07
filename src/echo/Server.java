package echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {

		// 스레드로 여러 클라이언트가 서버에 접속하게 하기
		
		// 서버 소켓 객체 생성
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress("172.30.1.41", 10001));

		System.out.println("<서버 시작>");
		System.out.println("---------------");
		System.out.println("[연결을 기다리고 있습니다.]");

		// 서버는 새로운 클라이언트가 생길 때마다 소켓 accept 해야 함. 탈출조건없이 서버 꺼질 때까지 반복.
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("[클라이언트가 연결되었습니다.]");

			// 출장 보내고 각각 알아서 돌아가게 만들기 .start();
			Thread thread = new ServerThread(socket); // 어떤 클라이언트가 접속하는지 알려줘야 됨. --> ServerThread에 이거에 맞는 생성자 넣어야 됨.
			thread.start();
		}

		/*
		 * 서버 안 끌 거라 무한반복 돌려서 필요없는 부분. 서버 끌 때 쓰면 됨.
		 * System.out.println("---------------"); System.out.println("<서버 종료>");
		 * serverSocket.close();
		 */
	}

}
