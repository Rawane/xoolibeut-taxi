package com.xoolibeut.taxis;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
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
			pushPositionClient(points, "IB001",1);
			pushPositionClient(points, "IB002",2);
			pushPositionClient(points, "IB003",3);		
			pushPositionClient(points, "IB004",5);
			pushPositionClient(points, "IB005",6);
			pushPositionClient(points, "IB006",7);
			pushPositionClient(points, "IB007",8);
			pushPositionClient(points, "IB008",9);
			pushPositionClient(points, "IB009",10);
			pushPositionClient(points, "IB010",4);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void pushPositionClient(List<PointMap> points, String numeroClient,int indexPoint)
			throws JsonProcessingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		OkHttpClient client = new OkHttpClient();
		CourseTaxiDTO courseTaxiDTO = new CourseTaxiDTO();
		courseTaxiDTO.setC(numeroClient);
		courseTaxiDTO.setA(points.get(indexPoint).getLatitude());
		courseTaxiDTO.setO(points.get(indexPoint).getLongitude());
		String req = mapper.writeValueAsString(courseTaxiDTO);
		System.out.println("Request : " + req);
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), req);
		Request request = new Request.Builder().url("http://localhost:8080/clients/v3/res").post(body).build();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
	}

}
