package in.co.moove.moovelibrary.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Reader;

import in.co.moove.moovelibrary.analytics.AnalyticsConstant;
import in.co.moove.moovelibrary.api.ApiConstants;
import in.co.moove.moovelibrary.api.MooveService;
import in.co.moove.moovelibrary.api.ServiceFactory;
import in.co.moove.moovelibrary.api.models.ErrorBody;
import in.co.moove.moovelibrary.api.models.ForgotPassword;
import in.co.moove.moovelibrary.api.models.ResetPassword;
import in.co.moove.moovelibrary.api.models.SignInModel;
import in.co.moove.moovelibrary.utils.PreferenceHelper;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by boss1088 on 10/7/16.
 */

public abstract class MooveRepository implements MooveDataSourse {


    private MooveService mService;

    private MooveService put in anotherbranch;


    public MooveRepository() {
        mService = ServiceFactory.createRetrofitService(
                MooveService.class, ApiConstants.getApiUrl());
    }

    public MooveService getService() {
        return mService;
    }

    @Override
    public void login(final String email, final String password, final String appId, final BaseCallback callback) {
        final MooveService serviceAxiscades = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.TEST_API_URL);
        final MooveService serviceTechmBlr = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.STAGE_API_URL);
        final MooveService serviceJindal = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.BLAMO_API_URL);
        serviceAxiscades.signIn(email, password, appId).enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                if (response.code() == ApiConstants.SUCCESS_CODE) {
                    updateTimestamp(response.headers());
                    PreferenceHelper.setAccessToken(response.headers().get(ApiConstants.ACCESS_TOKEN));
                    PreferenceHelper.setClient(response.headers().get(ApiConstants.CLIENT));
                    PreferenceHelper.setUid(response.headers().get(ApiConstants.UID));
                    PreferenceHelper.setUserId(response.body().id);
                    PreferenceHelper.setEmail(response.body().email);
                    PreferenceHelper.setName(response.body().f_name + " " + response.body().l_name);
                    PreferenceHelper.setPhone(response.body().phone);
                    PreferenceHelper.setUsername(email);
                    PreferenceHelper.setApiUrl(MooveService.TEST_API_URL);
                    PreferenceHelper.setAmplitudeId(AnalyticsConstant.TEST_AMPLITUDE_KEY);
                    PreferenceHelper.setFirebasePrefix(ApiConstants.FIREBASE_TEST);
                    PreferenceHelper.setServerUrl(ApiConstants.TEST_URL);
                    mService = serviceAxiscades;
                    callback.onSuccess();
                } else {
                    serviceTechmBlr.signIn(email, password, appId).enqueue(new Callback<SignInModel>() {
                        @Override
                        public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                            if (response.code() == ApiConstants.SUCCESS_CODE) {
                                updateTimestamp(response.headers());
                                PreferenceHelper.setAccessToken(response.headers().get(ApiConstants.ACCESS_TOKEN));
                                PreferenceHelper.setClient(response.headers().get(ApiConstants.CLIENT));
                                PreferenceHelper.setUid(response.headers().get(ApiConstants.UID));
                                PreferenceHelper.setUserId(response.body().id);
                                PreferenceHelper.setEmail(response.body().email);
                                PreferenceHelper.setName(response.body().f_name + " " + response.body().l_name);
                                PreferenceHelper.setPhone(response.body().phone);
                                PreferenceHelper.setUsername(email);
                                PreferenceHelper.setApiUrl(MooveService.STAGE_API_URL);
                                PreferenceHelper.setAmplitudeId(AnalyticsConstant.STAGE_AMPLITUDE_KEY);
                                PreferenceHelper.setFirebasePrefix(ApiConstants.FIREBASE_STAGING);
                                PreferenceHelper.setServerUrl(ApiConstants.STAGING_URL);
                                mService = serviceTechmBlr;
                                callback.onSuccess();
                            } else {
                                serviceJindal.signIn(email, password, appId).enqueue(new Callback<SignInModel>() {
                                    @Override
                                    public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                                        if (response.code() == ApiConstants.SUCCESS_CODE) {
                                            updateTimestamp(response.headers());
                                            PreferenceHelper.setAccessToken(response.headers().get(ApiConstants.ACCESS_TOKEN));
                                            PreferenceHelper.setClient(response.headers().get(ApiConstants.CLIENT));
                                            PreferenceHelper.setUid(response.headers().get(ApiConstants.UID));
                                            PreferenceHelper.setUserId(response.body().id);
                                            PreferenceHelper.setEmail(response.body().email);
                                            PreferenceHelper.setName(response.body().f_name + " " + response.body().l_name);
                                            PreferenceHelper.setPhone(response.body().phone);
                                            PreferenceHelper.setUsername(email);
                                            PreferenceHelper.setApiUrl(MooveService.BLAMO_API_URL);
                                            PreferenceHelper.setAmplitudeId(AnalyticsConstant.BLAMO_AMPLITUDE_KEY);
                                            PreferenceHelper.setFirebasePrefix(ApiConstants.FIREBASE_BLAMO);
                                            PreferenceHelper.setServerUrl(ApiConstants.BLAMO_URL);
                                            mService = serviceJindal;
                                            callback.onSuccess();
                                        } else {
                                            callback.onError(handleError(response.errorBody().charStream()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SignInModel> call, Throwable t) {
                                        callback.onError("Something goes wrong");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<SignInModel> call, Throwable t) {
                            callback.onError("Something goes wrong");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                callback.onError("Something goes wrong");
            }
        });
    }

    @Override
    public void register(final String email, final BaseCallback callback) {
        final MooveService serviceAxiscades = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.TEST_API_URL);
        final MooveService serviceTechmBlr = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.STAGE_API_URL);
        final MooveService serviceJindal = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.BLAMO_API_URL);
        serviceAxiscades.register(email).enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                if (response.code() == ApiConstants.SUCCESS_CODE) {
                    updateTimestamp(response.headers());
                    PreferenceHelper.setUserId(response.body().id);
                    PreferenceHelper.setEmail(response.body().email);
                    PreferenceHelper.setName(response.body().f_name + " " + response.body().l_name);
                    PreferenceHelper.setPhone(response.body().phone);
                    PreferenceHelper.setUserStatus(response.body().status);
                    PreferenceHelper.setUsername(email);
                    PreferenceHelper.setApiUrl(MooveService.TEST_API_URL);
                    PreferenceHelper.setAmplitudeId(AnalyticsConstant.TEST_AMPLITUDE_KEY);
                    PreferenceHelper.setFirebasePrefix(ApiConstants.FIREBASE_TEST);
                    PreferenceHelper.setServerUrl(ApiConstants.TEST_URL);
                    mService = serviceAxiscades;
                    callback.onSuccess();
                } else {
                    serviceTechmBlr.register(email).enqueue(new Callback<SignInModel>() {
                        @Override
                        public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                            if (response.code() == ApiConstants.SUCCESS_CODE) {
                                updateTimestamp(response.headers());
                                PreferenceHelper.setUserId(response.body().id);
                                PreferenceHelper.setEmail(response.body().email);
                                PreferenceHelper.setName(response.body().f_name + " " + response.body().l_name);
                                PreferenceHelper.setPhone(response.body().phone);
                                PreferenceHelper.setUserStatus(response.body().status);
                                PreferenceHelper.setUsername(email);
                                PreferenceHelper.setApiUrl(MooveService.STAGE_API_URL);
                                PreferenceHelper.setAmplitudeId(AnalyticsConstant.STAGE_AMPLITUDE_KEY);
                                PreferenceHelper.setFirebasePrefix(ApiConstants.FIREBASE_STAGING);
                                PreferenceHelper.setServerUrl(ApiConstants.STAGING_URL);
                                mService = serviceTechmBlr;
                                callback.onSuccess();
                            } else {
                                serviceJindal.register(email).enqueue(new Callback<SignInModel>() {
                                    @Override
                                    public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                                        if (response.code() == ApiConstants.SUCCESS_CODE) {
                                            updateTimestamp(response.headers());
                                            PreferenceHelper.setUserId(response.body().id);
                                            PreferenceHelper.setEmail(response.body().email);
                                            PreferenceHelper.setName(response.body().f_name + " " + response.body().l_name);
                                            PreferenceHelper.setPhone(response.body().phone);
                                            PreferenceHelper.setUserStatus(response.body().status);
                                            PreferenceHelper.setUsername(email);
                                            PreferenceHelper.setApiUrl(MooveService.BLAMO_API_URL);
                                            PreferenceHelper.setAmplitudeId(AnalyticsConstant.BLAMO_AMPLITUDE_KEY);
                                            PreferenceHelper.setFirebasePrefix(ApiConstants.FIREBASE_BLAMO);
                                            PreferenceHelper.setServerUrl(ApiConstants.BLAMO_URL);
                                            mService = serviceJindal;
                                            callback.onSuccess();
                                        } else {
                                            callback.onError(handleError(response.errorBody().charStream()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SignInModel> call, Throwable t) {
                                        callback.onError("Something goes wrong");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<SignInModel> call, Throwable t) {
                            callback.onError("Something goes wrong");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                callback.onError("Something goes wrong");
            }
        });
    }

    @Override
    public void logout(final BaseCallback callback) {
        mService.logOut(PreferenceHelper.getAccessToken(), PreferenceHelper.getClient(), PreferenceHelper.getUid()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onSuccess();
            }
        });
    }

    public void updateTimestamp(Headers headers) {
        long serverTimestamp = Long.valueOf(headers.get(ApiConstants.SERVER_TIMESTAMP)) * 1000;
        PreferenceHelper.setTimestampDiff(serverTimestamp);
    }

    @Override
    public void forgotPassword(final String email, final BaseObjectCallback callback) {
        final MooveService serviceAxiscades = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.TEST_API_URL);
        final MooveService serviceTechmBlr = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.STAGE_API_URL);
        final MooveService serviceJindal = ServiceFactory.createRetrofitService(
                MooveService.class, MooveService.BLAMO_API_URL);
        serviceAxiscades.forgotPassword(email).enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                if (response.code() == ApiConstants.SUCCESS_CODE) {
                    callback.onSuccess(response.body().message);
                } else {
                    serviceTechmBlr.forgotPassword(email).enqueue(new Callback<ForgotPassword>() {
                        @Override
                        public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                            if (response.code() == ApiConstants.SUCCESS_CODE) {
                                callback.onSuccess(response.body().message);
                            } else {
                                serviceJindal.forgotPassword(email).enqueue(new Callback<ForgotPassword>() {
                                    @Override
                                    public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                                        if (response.code() == ApiConstants.SUCCESS_CODE) {
                                            callback.onSuccess(response.body().message);
                                        } else {
                                            callback.onError(handleError(response.errorBody().charStream()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ForgotPassword> call, Throwable t) {
                                        callback.onError("Something goes wrong.");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ForgotPassword> call, Throwable t) {
                            callback.onError("Something goes wrong.");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                callback.onError("Something goes wrong.");
            }
        });
    }

    @Override
    public void resetPassword(String password, String confirmPassword, final BaseObjectCallback callback) {
        mService.resetPassword(PreferenceHelper.getUserId(), password, confirmPassword).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.code() == ApiConstants.SUCCESS_CODE) {
                    callback.onSuccess(response.body().message);
                } else {
                    callback.onError(handleError(response.errorBody().charStream()));
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                callback.onError("Something goes wrong.");
            }
        });
    }

    protected String handleError(Reader reader) {
        Gson gson = new Gson();
        try {
            ErrorBody errorBody = gson.fromJson(reader, ErrorBody.class);
            if (errorBody.errors.size() > 0) {
                return errorBody.errors.get(0);
            }
        } catch (Exception ex) {
            return "Something goes wrong.";
        }


        return "Something goes wrong.";
    }

    protected void logoutAndStartLogin() {
        PreferenceHelper.clear();
        startLoginActivity();
    }

    protected abstract void startLoginActivity();
}
