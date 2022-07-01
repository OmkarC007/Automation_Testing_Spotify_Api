package com.company.spotify;

import org.testng.Assert;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotifyApi {
	public String token;
    String track;
	String userId;
	String playlistId ="6jUQ03SNWLOzCt1YnX1mag";
	String trackId="0pFZ8npcNXuYd6ou9iNUl5";
	
	@BeforeTest
	public void getToken() {
		token = "Bearer BQAd184QCme2oMVn8wYvY8-0c4Yvg4XdncivFBIqnubew9te7NQGZSi1cmJhVgct5yp8GziFShm-5hlsO751yxvMfQPEIdTMxh4QjvsCyWPoe_mUMx8P28WP8BRvK8tm32nhmrZx9E3MiJjG0nMXdfWc4GyfR85bsos2-E0nvaK22lDfPZlb5BPct4w6t5RyFAQdN7OFRi702Ik4Ht-rQDN0sZTd39JBTbCKEBHnPUPZCVrYpHsTD97FzYIamkDgyJT6VktxVPnPT_hs9ton2YeNY5uNtoL8Qvbadv_tEy8HRSjjSP6-bijVJ5nIpyxujO9c4LGrsA";
	}
//	----------------Users profile starts----------------------------------------
	@Test (priority = 0)
	public void testGet_Current_Profile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get(" 	https://api.spotify.com/v1/me");
		response.prettyPrint();
		userId = response.path("id");
		System.out.println("USer Id is "+userId);
		response.then().assertThat().statusCode(200);
		Assert.assertEquals(userId, "31akz3zmqvibuczlkftupvkiz4aa");
		}
	
	@Test (priority = 1)
	public void testGet_UserProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/users/"+userId+"");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		System.out.println("status code " + statusCode);
		Assert.assertEquals(statusCode, 200);
	}
//	----------------------Users profile starts-------------------------------
	
//	----------------------Playlist starts------------------------------------
	@Test (priority = 2)
	public void testCreatePlaylist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.body("{\r\n"
						+ "  \"name\": \"Automated 2 Playlist\",\r\n"
						+ "  \"description\": \"New playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.post("https://api.spotify.com/v1/users/"+userId+"/playlists");
		response.prettyPrint();
		playlistId = response.path("id");
		System.out.println("Playlist Id is "+playlistId);
		response.then().assertThat().statusCode(201);
	}
	
	@Test (priority = 3)
	public void AddItemsToPlaylist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParams("uris", "spotify:track:45GXsEdPxpFLnqQMPR2Cyy")				
				.when()
				.post("https://api.spotify.com/v1/playlists/6jUQ03SNWLOzCt1YnX1mag/tracks");
       response.prettyPrint();
       response.then().assertThat().statusCode(201);
}
	@Test (priority = 4)
	public void ChangePlaylistDetails() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.body("{\r\n" 
						+ "  \"name\": \"my new auto Playlist\",\r\n"
						+ "  \"description\": \"Updated playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.put(" 	https://api.spotify.com/v1/playlists/"+playlistId+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 5)
	public void GetUsersPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get(" 	https://api.spotify.com/v1/users/"+userId+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 6)
	public void GetPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlistId+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	

	
	@Test (priority = 7)
	public void GetCurrentUsersPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get(" 	https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 8)
	public void GetPlaylistCoverImage() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get(" 	https://api.spotify.com/v1/playlists/"+playlistId+"/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}	
	
	
	
	@Test (priority = 9)
	public void UpdatePlaylistItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.body("{\r\n"
						+ "  \"range_start\": 1,\r\n"
						+ "  \"insert_before\": 0,\r\n"
						+ "  \"range_length\": 3\r\n"
						+ "}")	
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	
	@Test (priority = 10)
	public void RemovePlaylistItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "    \"tracks\": [{\"uri\": \"spotify:track:5pjSpt2mstf5JTf46FbT48\"}]\r\n"
						+ "}")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
//	------------------------playlists ends-----------------------------
	
//	------------------------search starts------------------------------
	@Test (priority = 11)
	public void SearchforItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParam("q", "Arijit singh")
				.queryParam("type", "track"	)
				.when()
				.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
//	------------------------search ends------------------------------
	
//	------------------------Tracks starts------------------------------
	
	@Test (priority = 12)
	public void GetTracksAudioAnalysis() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/audio-analysis/"+trackId+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 13)
	public void GetTracksAudioFeatures() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get(" 	https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 14)
	public void GetTracksAudioFeaturesWithId() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/audio-features/"+trackId+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 15)
	public void GetTrack() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/tracks/"+trackId+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 16)
	public void GetSeveralTracks() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get(" 	https://api.spotify.com/v1/tracks/"+trackId+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
//	------------------------track ends------------------------------

}
