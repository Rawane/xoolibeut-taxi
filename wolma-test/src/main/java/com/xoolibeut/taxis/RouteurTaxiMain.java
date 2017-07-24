package com.xoolibeut.taxis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

public class RouteurTaxiMain {
	public static void main(String[] args) {
		try {System.out.println("-------------------  Routeur ------------------------------ ");
			ObjectMapper mapper = new ObjectMapper();
			OkHttpClient client = WolmaClientHttp.getSSLClient();
			Map<String, String> mapIn = new HashMap<>();
			mapIn.put("r", "01");
			mapIn.put("i", "localhost");
			mapIn.put("p", "4141");
			String req = mapper.writeValueAsString(mapIn);
			System.out.println("Request : " + req);
			RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), req);
			Request request = new Request.Builder().url("http://localhost:8443/registre/v1/rt").post(body).build();
			
			Runnable serverTask = new Runnable() {
				@Override
				public void run() {
					try {
						ServerSocket welcomeSocket = new ServerSocket(4141);
						while (true) {
							Socket connectionSocket = welcomeSocket.accept();
							BufferedReader inFromClient = new BufferedReader(
									new InputStreamReader(connectionSocket.getInputStream()));
							DataOutputStream sendToServer = new DataOutputStream(connectionSocket.getOutputStream());							
							String  inputContent=inFromClient.readLine();
							sendToServer.writeBytes("OK\n");	
							sendToServer.flush();
							System.out.println(inputContent);
							sendToServer.close();
							inFromClient.close();
							connectionSocket.close();
							CourseTaxiDTO courseTaxiDTO = mapper.readValue(inputContent, CourseTaxiDTO.class);
							if ("p".equals(courseTaxiDTO.getT())) {
								OkHttpClient client = new OkHttpClient();
								Map<String, String> mapIn = new HashMap<>();
								mapIn.put("i", courseTaxiDTO.getC());
								mapIn.put("t", courseTaxiDTO.getH());	
								mapIn.put("a", "14.427747");
								mapIn.put("o", "-16.974093");	
								String req = mapper.writeValueAsString(mapIn);
								System.out.println("Request : " + req);
								String endUrl="ref";
								if(Math.random()>0.5){
									endUrl="ref";
								}
								RequestBody body = RequestBody
										.create(MediaType.parse("application/json; charset=utf-8"), req);
								Request request = new Request.Builder().url("https://localhost:8443/root/taxis/v1/course/"+endUrl)
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
			Response response = client.newCall(request).execute();
			System.out.println("registry"+response.body().string());

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
