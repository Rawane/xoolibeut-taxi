package com.xoolibeut.taxis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TCPTaxiAcceptCourseMain {
	public static void main(String[] args) {
		try {System.out.println("-------------------  TAXI MB0001------------------------------ ");

			Runnable serverTask = new Runnable() {
				@Override
				public void run() {
					try {
						ServerSocket welcomeSocket = new ServerSocket(5550);
						while (true) {
							Socket connectionSocket = welcomeSocket.accept();
							BufferedReader inFromClient = new BufferedReader(
									new InputStreamReader(connectionSocket.getInputStream()));
							String  inputContent=inFromClient.readLine();
							System.out.println(inputContent);
							ObjectMapper mapper = new ObjectMapper();							
							CourseTaxiDTO courseTaxiDTO = mapper.readValue(inputContent, CourseTaxiDTO.class);							
							if ("p".equals(courseTaxiDTO.getT())) {
								OkHttpClient client = new OkHttpClient();
								Map<String, String> mapIn = new HashMap<>();
								mapIn.put("i", courseTaxiDTO.getC());
								mapIn.put("c", "MB0001");		
								mapIn.put("a", "14.427747");
								mapIn.put("o", "-16.974093");	
								String req = mapper.writeValueAsString(mapIn);
								System.out.println("Request : " + req);
								RequestBody body = RequestBody
										.create(MediaType.parse("application/json; charset=utf-8"), req);
								Request request = new Request.Builder().url("http://localhost:8080/taxis/v1/course/ref")
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

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
