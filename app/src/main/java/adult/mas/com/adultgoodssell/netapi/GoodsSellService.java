package adult.mas.com.adultgoodssell.netapi;


import java.util.List;

import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsComments;
import adult.mas.com.adultgoodssell.modelbean.mainview.GoodsInfo;
import adult.mas.com.adultgoodssell.modelbean.mainview.confirm.ConfirmDealResponse;
import adult.mas.com.adultgoodssell.modelbean.mainview.goodssort.GoodsSortList;
import adult.mas.com.adultgoodssell.modelbean.mainview.marketshop.MarketShop;
import adult.mas.com.adultgoodssell.modelbean.mainview.videos.VideoData;
import adult.mas.com.adultgoodssell.netapi.response.MainPageResponse;
import adult.mas.com.adultgoodssell.netapi.response.UserResponse;
import adult.mas.com.httpmodel.RequestModel;
import adult.mas.com.httpmodel.ResponseModel;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sunmeng on 17/8/1.
 */

public interface GoodsSellService {

    /*
        @POST("customer/update11")
        <T> Observable<ResponseModel<T>> userRequest(@Body RequestModel request);
        这样写错误，retrofit2不支持此类写法，会抛出
        Method return type must not include a type variable or wildcard错误
     */

    @POST("customer/update11")
    Observable<ResponseModel<UserResponse>> userRequest(@Body RequestModel request);

    @POST("api/mainlist")
    Observable<ResponseModel<MainPageResponse>> mainPageRequest(@Body RequestModel request);


    @POST("api/goodssort")
    Observable<ResponseModel<List<GoodsSortList>>> mainGoodsSortRequest(@Body RequestModel request);

    @POST("api/videosinfosPage")
    Observable<ResponseModel<List<VideoData>>> mainVideosRequest(@Body RequestModel request);

    @POST("api/goodsDetailInfo")
    Observable<ResponseModel<GoodsInfo>> gooodsDetailInfo(@Body RequestModel request);

    @POST("api/goodsComments")
    Observable<ResponseModel<List<GoodsComments>>> goodsComments(@Body RequestModel request);

    @POST("api/goodsMarketShop")
    Observable<ResponseModel<MarketShop>> marketShopDetail(@Body RequestModel request);

    @POST("api/confirmDeal")
    Observable<ResponseModel<ConfirmDealResponse>> confirmDeal(@Body RequestModel request);

    @POST("api/goodsinfosSort")
    Observable<ResponseModel<List<GoodsInfo>>> sortList(@Body RequestModel request);

    @POST("api/createDeal")
    Observable<ResponseModel> createDeal(@Body RequestModel request);

}
