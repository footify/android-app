package eu.epitech.croucour.footify.Profile;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.FriendListEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.MyService;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by roucou_c on 27/09/2016.
 */
class ProfileService extends MyService{
    private final IProfileView _view;
    private final IFriendsView _iFiendsView;
    private IProfileService _api;

    interface IProfileService {
        @Headers("Content-Type: application/json")
        @GET("users/me")
        Call<UserEntity> getProfile();

        @Headers("Content-Type: application/json")
        @PATCH("users/me")
        Call<UserEntity> patchProfile(@Body Map<String, String> params);
//
//        @Headers("Content-Type: application/json")
//        @GET("users/check/email/{email}")
//        Call<String> checkEmail(@Path("email") String email);

        @Headers("Content-Type: application/json")
        @GET("users/me/friends")
        Call<FriendListEntity> getFriendsList();

        @Headers("Content-Type: application/json")
        @POST("users/me/friends/request")
        Call<FriendEntity> deleteAnswer(@Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @POST("users/me/friends/deny/{friend_id}")
        Call<FriendEntity> friendDeny(@Path("friend_id") String friend_id);


        @Headers("Content-Type: application/json")
        @POST("users/me/friends/accept/{friend_id}")
        Call<FriendEntity> friendAccept(@Path("friend_id") String friend_id);

        @Headers("Content-Type: application/json")
        @POST("users/me/friends/invite/{friend_id}")
        Call<Void> addFriend(@Path("friend_id") String friend_id);
    }

    ProfileService(IProfileView view, IFriendsView iFiendsView, Manager manager, TokenEntity tokenEntity) {
        super(manager);
        this._view = view;
        this._iFiendsView = iFiendsView;
        this._api = ServiceGeneratorApi.createService(IProfileService.class, "api", tokenEntity, manager);
    }

    void getProfile(final boolean populate) {
        Call<UserEntity> call = _api.getProfile();
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {

                if (!response.isSuccessful()) {

                }
                else {
                    UserEntity userEntity = response.body();
                    _view.setUserEntity(userEntity);
                    if (populate) {
                        _view.populateProfile(userEntity, null);
                    }
                }

            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }
        });
    }

    void patchProfile(HashMap<String, String> params) {
        Call<UserEntity> call = _api.patchProfile(params);
        call.enqueue(new Callback<UserEntity>() {

            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if (response.isSuccessful()) {
                    UserEntity userEntity = response.body();
                    _view.changeStep("profile");
                }
                else {
//                    ApiError apiError = new ApiError(response.errorBody(), response.code(), "pathProfile");
//                    _view.setError(apiError.get_target_message());
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {

            }
        });

    }

    public void getFriendsList(final String type_view) {
        Call<FriendListEntity> call = _api.getFriendsList();
        call.enqueue(new Callback<FriendListEntity>() {

            @Override
            public void onResponse(Call<FriendListEntity> call, Response<FriendListEntity> response) {
                if (response.isSuccessful()) {
                    FriendListEntity friendListEntity = response.body();

                    if (Objects.equals(type_view, "profile")) {
                        _view.setFriendList(friendListEntity);
                    }
                    else {
                        _iFiendsView.setFriendList(friendListEntity);
                    }
                }
                else {
                    FriendListEntity friendListEntity2 = new FriendListEntity();

                    List<FriendEntity> friendEntities_waiting_answer = new ArrayList<FriendEntity>();
                    List<FriendEntity> friendEntities_waiting_approval = new ArrayList<FriendEntity>();
                    List<FriendEntity> friendEntities_accpeted = new ArrayList<FriendEntity>();


                    FriendEntity friendEntity = new FriendEntity();
                    friendEntity.set_first_name("status");
                    friendEntity.set_last_name("accepted");
                    friendEntity.set_id("accepted");
                    friendEntities_accpeted.add(friendEntity);
                    friendListEntity2.set_accepted(friendEntities_accpeted);

                    FriendEntity friendEntity2 = new FriendEntity();
                    friendEntity2.set_first_name("status");
                    friendEntity2.set_last_name("waiting_answer");
                    friendEntity2.set_id("waiting_answer");
                    friendEntities_waiting_answer.add(friendEntity2);
                    friendListEntity2.set_waiting_answer(friendEntities_waiting_answer);


                    FriendEntity friendEntity3 = new FriendEntity();
                    friendEntity3.set_first_name("status");
                    friendEntity3.set_last_name("waiting_approval");
                    friendEntity3.set_id("waiting_approval");
                    friendEntities_waiting_approval.add(friendEntity3);
                    friendListEntity2.set_waiting_approval(friendEntities_waiting_approval);

                    if (Objects.equals(type_view, "profile")) {
                        _view.setFriendList(friendListEntity2);
                    }
                    else {
                        _iFiendsView.setFriendList(friendListEntity2);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendListEntity> call, Throwable t) {

            }
        });
    }



    void checkEmail(String email) {
//        IProfileService api = ServiceGeneratorApi.createService(IProfileService.class, "login", _manager);
//
//        Call<String> call = api.checkEmail(email);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (!response.isSuccessful()) {
////                    ApiError apiError = new ApiError(response.errorBody(), response.code(), "signUp");
////
////                    _view.setErrorEmail(apiError.get_target_message());
//                }
//                else {
//                    _view.setErrorEmail(0);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//            }
//        });
    }

    void follow(String user_id, final boolean isFollow) {
        Call<Void> call;

//        if (isFollow) {
//            call = _api.follow(user_id);
//        }
//        else {
//            call = _api.unfollow(user_id);
//        }

//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
////                if (response.isSuccessful()) {
////                    _view.follow(isFollow);
////                }
////                else {
////                    _view.follow(!isFollow);
////                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d("error", t.getMessage());
//            }
//        });
    }

    public void deleteAnswer(HashMap<String, String> params) {

        Call<FriendEntity> call = _api.deleteAnswer(params);
        call.enqueue(new Callback<FriendEntity>() {
            @Override
            public void onResponse(Call<FriendEntity> call, Response<FriendEntity> response) {

                if (response.isSuccessful()) {
                    getFriendsList("friendList");
                }
            }

            @Override
            public void onFailure(Call<FriendEntity> call, Throwable t) {
            }


        });
    }

    public void deny(String friend_id) {
        Call<FriendEntity> call = _api.friendDeny(friend_id);
        call.enqueue(new Callback<FriendEntity>() {
            @Override
            public void onResponse(Call<FriendEntity> call, Response<FriendEntity> response) {

                if (response.isSuccessful()) {
                    getFriendsList("friendList");
                }
            }

            @Override
            public void onFailure(Call<FriendEntity> call, Throwable t) {
            }
        });

    }

    public void accept(String friend_id) {
        Call<FriendEntity> call = _api.friendAccept(friend_id);
        call.enqueue(new Callback<FriendEntity>() {
            @Override
            public void onResponse(Call<FriendEntity> call, Response<FriendEntity> response) {

                if (response.isSuccessful()) {
                    getFriendsList("friendList");
                }
            }

            @Override
            public void onFailure(Call<FriendEntity> call, Throwable t) {
            }
        });
    }

    public void addFriend(String friend_id) {
        Call<Void> call = _api.addFriend(friend_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    _view.populateProfile(null, null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }


//    void getFollowing() {
//        Call<List<FollowEntity>> call = _api.getFollowing();
//        call.enqueue(new Callback<List<FollowEntity>>() {
//            @Override
//            public void onResponse(Call<List<FollowEntity>> call, Response<List<FollowEntity>> response) {
//
//                if (!response.isSuccessful()) {
//
//                }
//                else {
//                    List<FollowEntity> followEntities = response.body();
//                    _view.setFollowing(followEntities);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FollowEntity>> call, Throwable t) {
//            }
//        });
//    }
//
//    void getFriends() {
//        Call<List<FollowerEntity>> call = _api.getFriends();
//        call.enqueue(new Callback<List<FollowerEntity>>() {
//            @Override
//            public void onResponse(Call<List<FollowerEntity>> call, Response<List<FollowerEntity>> response) {
//
//                if (!response.isSuccessful()) {
//
//                }
//                else {
//                    List<FollowerEntity> followerEntities = response.body();
//                    _iFiendsView.setFollowers(followerEntities);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FollowerEntity>> call, Throwable t) {
//                Log.d("error", t.getMessage());
//            }
//        });
//    }

}
