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

public class TaxiMain {

	public static void main(String[] args) {

		try {
			PositionMap positionMap = new PositionMap();
			List<PointMap> points = positionMap.getPoints();
			int i=0;
			while (true) {
				pushPosition(points, "MB0001");
				pushPosition(points, "MB0002");
				pushPosition(points, "MB0003");
				pushPosition(points, "MB0004");
				pushPosition(points, "MB0005");
				pushPosition(points, "MB0006");
				pushPosition(points, "MB0007");
				pushPosition(points, "MB0008");
				pushPosition(points, "MB0009");
				pushPosition(points, "MB0010");
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void pushPosition(List<PointMap> points, String matricule)
			throws JsonProcessingException, IOException {
		int indexPoint = (int) (points.size() * Math.random());
		ObjectMapper mapper = new ObjectMapper();
		OkHttpClient client = new OkHttpClient();
		CourseTaxiDTO courseTaxiDTO = new CourseTaxiDTO();
		courseTaxiDTO.setC(matricule);
		courseTaxiDTO.setA(points.get(indexPoint).getLatitude());
		courseTaxiDTO.setO(points.get(indexPoint).getLongitude());
		if(matricule.equals("MB0001")){
			courseTaxiDTO.setIp("localhost");
			courseTaxiDTO.setP("5550");
		}
		if(matricule.equals("MB0002")){
			courseTaxiDTO.setIp("localhost");
			courseTaxiDTO.setP("5555");
		}
		String req = mapper.writeValueAsString(courseTaxiDTO);
		System.out.println("Request : " + req);
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), req);
		Request request = new Request.Builder().url("http://localhost:8080/taxis/v3/pos").post(body).build();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
	}

}
