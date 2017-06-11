package com.xoolibeut.taxis;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaxiMain {

	public static void main(String[] args) {

		try {
			PositionMap positionMap=new PositionMap();
			List<PointMap> points = positionMap.getPoints();
			int indexPoint=(int)(points.size()*Math.random());
			ObjectMapper mapper = new ObjectMapper();
			OkHttpClient client = new OkHttpClient();
			CourseTaxiDTO courseTaxiDTO = new CourseTaxiDTO();
			courseTaxiDTO.setC("MB0001");
			courseTaxiDTO.setA(points.get(indexPoint).getLatitude());
			courseTaxiDTO.setO(points.get(indexPoint).getLongitude());
			String req=mapper.writeValueAsString(courseTaxiDTO);
			System.out.println("Request : "+req);
			RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
					req);
			Request request = new Request.Builder().url("http://localhost:8080/taxis/v3/pos").post(body).build();
			Response response = client.newCall(request).execute();			
			System.out.println(response.body().string());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
