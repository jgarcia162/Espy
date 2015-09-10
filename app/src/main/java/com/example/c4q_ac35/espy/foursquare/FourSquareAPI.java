package com.example.c4q_ac35.espy.foursquare;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by elvisboves on 8/15/15.
 */
public interface FourSquareAPI {

    public static final String CLIENT_ID = "1GRBSICWBKAFJKO1PL5SY3MK4MFIMJAZ2OS3U0O5RYM1WLWY";
    public static final String CLIENT_SECRET = "FXFOQD33LQO0PX2ZY1OWKZ3MVZELAXVSHYLVJBPJM4UPGNIL";

    @GET("/venues/search?v=20130815&client_id=1GRBSICWBKAFJKO1PL5SY3MK4MFIMJAZ2OS3U0O5RYM1WLWY&client_secret=FXFOQD33LQO0PX2ZY1OWKZ3MVZELAXVSHYLVJBPJM4UPGNIL&categoryId=4d4b7105d754a06374d81259,4d4b7105d754a06376d81259")
    public void getFeed (@Query("ll") String lat, Callback<ResponseAPI> response);

    @GET("/venues/search?v=20130815&client_id=1GRBSICWBKAFJKO1PL5SY3MK4MFIMJAZ2OS3U0O5RYM1WLWY&client_secret=FXFOQD33LQO0PX2ZY1OWKZ3MVZELAXVSHYLVJBPJM4UPGNIL&near=New York,NY")
    public void search (@Query("query") String query, @Query("limit") int limits, Callback<ResponseAPI> resposeLimit);


}
