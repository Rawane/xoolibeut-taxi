package com.xoolibeut.taxis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClientMain {

	public static void main(String[] args) {

		try {
			PositionMap positionMap = new PositionMap();
			List<PointMap> points = positionMap.getPointsClients();
			pushPositionClient(points, "IB001", 1);
			pushPositionClient(points, "IB002", 2);
			pushPositionClient(points, "IB003", 3);
			pushPositionClient(points, "IB004", 5);
			// pushPositionClient(points, "IB005", 6);
			pushPositionClient(points, "IB006", 7);
			// pushPositionClient(points, "IB007", 8);
			// pushPositionClient(points, "IB008", 9);
			// pushPositionClient(points, "IB009", 10);
			pushPositionClient(points, "IB010", 4);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void pushPositionClient(List<PointMap> points, String numeroClient, int indexPoint)
			throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		OkHttpClient client = WolmaClientHttp.getSSLClient();
		CourseTaxiDTO courseTaxiDTO = new CourseTaxiDTO();
		courseTaxiDTO.setC(numeroClient);
		courseTaxiDTO.setA(points.get(indexPoint).getLatitude());
		courseTaxiDTO.setO(points.get(indexPoint).getLongitude());
		courseTaxiDTO.setH("759652314");
		if (numeroClient.equals("IB004")) {
			courseTaxiDTO.setIp("localhost");
			courseTaxiDTO.setP("3333");
			Runnable serverTask = new Runnable() {
				@Override
				public void run() {
					try {
						ServerSocket welcomeSocket = new ServerSocket(3333);
						while (true) {
							Socket connectionSocket = welcomeSocket.accept();
							BufferedReader inFromClient = new BufferedReader(
									new InputStreamReader(connectionSocket.getInputStream()));
							String inputContent = inFromClient.readLine();
							System.out.println(inputContent);
							ObjectMapper mapper = new ObjectMapper();
							CourseTaxiDTO courseTaxiDTO = mapper.readValue(inputContent, CourseTaxiDTO.class);
							if ("dat".equals(courseTaxiDTO.getTy())) {
								CourseTaxiDTO courseTaxiAnn = new CourseTaxiDTO();
								courseTaxiAnn.setC(numeroClient);
								courseTaxiAnn.setI(courseTaxiDTO.getCi());
								String req = mapper.writeValueAsString(courseTaxiAnn);
								System.out.println("Request : " + req);
								RequestBody body = RequestBody
										.create(MediaType.parse("application/json; charset=utf-8"), req);
								Request request = new Request.Builder().url("https://localhost:8443/clients/v3/ann")
										.post(body).build();
								Response response = client.newCall(request).execute();
								System.out.println(response.body().string());
							}
						}
					} catch (Exception exception) {
						exception.printStackTrace();
					}

				}
			};
			Thread serverThread = new Thread(serverTask);
			serverThread.start();

		}
		if (numeroClient.equals("IB002")) {
			courseTaxiDTO.setIp("localhost");
			courseTaxiDTO.setP("3335");
			Runnable serverTask = new Runnable() {
				@Override
				public void run() {
					try {
						ServerSocket welcomeSocket = new ServerSocket(3335);
						while (true) {
							Socket connectionSocket = welcomeSocket.accept();
							BufferedReader inFromClient = new BufferedReader(
									new InputStreamReader(connectionSocket.getInputStream()));
							String inputContent = inFromClient.readLine();
							System.out.println(inputContent);
							// ObjectMapper mapper = new ObjectMapper();
							// CourseTaxiDTO courseTaxiDTO = mapper.readValue(inputContent,
							// CourseTaxiDTO.class);
							if ("p".equals(courseTaxiDTO.getT())) {

							}
						}
					} catch (Exception exception) {
						exception.printStackTrace();
					}

				}
			};
			Thread serverThread = new Thread(serverTask);
			serverThread.start();

		}
		if (numeroClient.equals("IB006")) {
			courseTaxiDTO.setIp("localhost");
			courseTaxiDTO.setP("3336");
			Runnable serverTask = new Runnable() {
				@Override
				public void run() {
					try {
						ServerSocket welcomeSocket = new ServerSocket(3336);
						while (true) {
							Socket connectionSocket = welcomeSocket.accept();
							BufferedReader inFromClient = new BufferedReader(
									new InputStreamReader(connectionSocket.getInputStream()));
							String inputContent = inFromClient.readLine();
							System.out.println(inputContent);
							// ObjectMapper mapper = new ObjectMapper();
							// CourseTaxiDTO courseTaxiDTO = mapper.readValue(inputContent,
							// CourseTaxiDTO.class);
							if ("p".equals(courseTaxiDTO.getT())) {

							}
						}
					} catch (Exception exception) {
						exception.printStackTrace();
					}

				}
			};
			Thread serverThread = new Thread(serverTask);
			serverThread.start();

		}
		String req = mapper.writeValueAsString(courseTaxiDTO);
		System.out.println("Request : " + req);
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), req);
		Request request = new Request.Builder().url("https://localhost:8443/clients/v3/res").post(body).build();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
	}

}
