package t.masahide.android.croudia.api

import com.squareup.okhttp.RequestBody
import retrofit.http.*
import rx.Observable
import t.masahide.android.croudia.constant.CroudiaConstants
import t.masahide.android.croudia.entitiy.AccessToken
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.service.AccessTokenService

interface CroudiaAPIService {

    val accessTokenService: AccessTokenService
        get() = AccessTokenService()

    val authorization: String
        get() = " Bearer " + accessTokenService.getAccessToken().accessToken

    val refreshToken: String
        get() = accessTokenService.getAccessToken().refreshToken

    @FormUrlEncoded
    @POST("/oauth/token")
    fun createToken(
            @Field("code") code: String,
            @Field("client_id") clientId: String = CroudiaConstants.CONSUMER_KEY,
            @Field("client_secret") clientSecret: String = CroudiaConstants.CONSUMER_SECRET,
            @Field("grant_type") grantType: String = "authorization_code"
    ): Observable<AccessToken>

    @FormUrlEncoded
    @POST("/oauth/token")
    fun refreshToken(
            @Header("Authorization") authorization: String = authorization,
            @Field("refresh_token") refreshToken: String = refreshToken,
            @Field("client_id") clientId: String = CroudiaConstants.CONSUMER_KEY,
            @Field("client_secret") clientSecret: String = CroudiaConstants.CONSUMER_SECRET,
            @Field("grant_type") grantType: String = "refresh_token"
    ): Observable<AccessToken>

    @GET("/account/verify_credentials.json")
    fun verifyCredentials(
            @Header("Authorization") authorization: String = authorization
    ): Observable<User>

    @FormUrlEncoded
    @POST("/2/statuses/update.json")
    fun postStatus(
            @Header("Authorization") authorization: String = authorization,
            @Field("status") status: String,
            @Field("in_reply_to_status_id") replyStatusId: String = ""
    ): Observable<Status>

    @Multipart
    @POST("/2/statuses/update_with_media.json")
    fun postStatusWithImage(
            @Header("Authorization") authorization: String = authorization,
            @Part("media") media: RequestBody,
            @Part("status") status: RequestBody,
            @Part("in_reply_to_status_id") replyStatusId: RequestBody? = null
    ): Observable<Status>

    @FormUrlEncoded
    @POST("/2/statuses/spread/{status_id}.json")
    fun spreadStatus(
            @Header("Authorization") authorization: String = authorization,
            @Path("status_id") status: String,
            @Field("status_id") dummy: String = ""
    ): Observable<Status>

    @GET("/2/statuses/public_timeline.json")
    fun publicTimeLine(
            @Header("Authorization") authorization: String = authorization,
            @Query("since_id") sinceId: String = "",
            @Query("max_id") maxId: String = "",
            @Query("count") count: Int = 30
    ): Observable<MutableList<Status>>

    @GET("/2/statuses/home_timeline.json")
    fun homeTimeLine(
            @Header("Authorization") authorization: String = authorization,
            @Query("since_id") sinceId: String = "",
            @Query("max_id") maxId: String = "",
            @Query("count") count: Int = 30
    ): Observable<MutableList<Status>>

    @GET("/favorites.json")
    fun favoriteTimeLine(
            @Header("Authorization") authorization: String = authorization,
            @Query("since_id") sinceId: String = "",
            @Query("max_id") maxId: String = "",
            @Query("count") count: Int = 30
    ): Observable<MutableList<Status>>

    @GET("/2/statuses/mentions.json")
    fun mentionsTimeLine(
            @Header("Authorization") authorization: String = authorization,
            @Query("since_id") sinceId: String = "",
            @Query("max_id") maxId: String = "",
            @Query("count") count: Int = 30
    ): Observable<MutableList<Status>>

    @GET("/2/statuses/user_timeline.json")
    fun userTimeLine(
            @Header("Authorization") authorization: String = authorization,
            @Query("since_id") sinceId: String = "",
            @Query("max_id") maxId: String = "",
            @Query("count") count: Int = 30
    ): Observable<MutableList<Status>>

    @GET("/followers/list.json")
    fun followers(
            @Header("Authorization") authorization: String = authorization,
            @Query("count") count: Int = 100
    ): Observable<MutableList<User>>

    @GET("/friends/list.json")
    fun friends(
            @Header("Authorization") authorization: String = authorization,
            @Query("count") count: Int = 30
    ): Observable<MutableList<User>>

    @FormUrlEncoded
    @POST("/friendships/create.json")
    fun friendshipsCreate(
            @Header("Authorization") authorization: String = authorization,
            @Query("user_id") UserId: String
    ): Observable<User>

    @FormUrlEncoded
    @POST("/friendships/destroy.json")
    fun friendshipsDestroy(
            @Header("Authorization") authorization: String = authorization,
            @Field("user_id") UserId: String,
            @Field("user_id") dummy: String = ""
    ): Observable<User>

    @FormUrlEncoded
    @POST("/2/statuses/destroy/{status_id}.json")
    fun destroyStatus(
            @Header("Authorization") authorization: String = authorization,
            @Path("status_id") statusId: String,
            @Field("status_id") dummy: String = ""
    ): Observable<Status>

    @FormUrlEncoded
    @POST("/2/favorites/create/{status_id}.json")
    fun favoriteCreate(
            @Header("Authorization") authorization: String = authorization,
            @Path("status_id") statusId: String,
            @Field("status_id") dummy: String = ""
    ): Observable<Status>

    @FormUrlEncoded
    @POST("/2/favorites/destroy/{status_id}.json")
    fun favoriteDestroy(
            @Header("Authorization") authorization: String = authorization,
            @Path("status_id") statusId: String,
            @Field("status_id") dummy: String = ""
    ): Observable<Status>

    @FormUrlEncoded
    @POST("/account/update_profile.json")
    fun updateProfile(
            @Header("Authorization") authorization: String = authorization,
            @Field("name") name: String,
            @Field("description") description: String
    ): Observable<User>
}