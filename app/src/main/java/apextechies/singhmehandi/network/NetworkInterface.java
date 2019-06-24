package apextechies.singhmehandi.network;


import apextechies.singhmehandi.component.activity.CommonRequest;
import apextechies.singhmehandi.component.activity.CommonRequestWithDate;
import apextechies.singhmehandi.component.activity.login.model.LoginModel;
import apextechies.singhmehandi.component.activity.login.model.LoginModelVerifyModel;
import apextechies.singhmehandi.component.activity.login.model.OtpRequest;
import apextechies.singhmehandi.component.activity.login.model.OtpValidate;
import apextechies.singhmehandi.component.activity.order.model.*;
import apextechies.singhmehandi.component.activity.shop.model.*;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by anujgupta on 26/12/17.
 */

public interface NetworkInterface {

    @POST(BaseUrl.LOGINVALIDATEAPI)
    Observable<LoginModel> userLogin(@Body OtpRequest request);

    @POST(BaseUrl.LOGINVALIDATEAPI)
    Observable<LoginModelVerifyModel> verifyOtp(@Body OtpValidate request);

    @POST(BaseUrl.AREA_LIST)
    Observable<AreaListResponse> getAreaList(@Body CommonRequest request);

    @POST(BaseUrl.ROUTE_LIST)
    Observable<RouteListResponse> getRouteList(@Body RouteListRequest request);

    @POST(BaseUrl.DISTRIBUT_LIST)
    Observable<DistributorListResponse> getDistributorList(@Body CommonRequest request);

    @POST(BaseUrl.ADDSHOP)
    Observable<SaveShopResponse> addShop(@Body SaveShopDetailsRequest request);

  /*  @POST(BaseUrl.RETAILERLIST_SHOP)
    Observable<ShopRetailerList> getRetailerList(@Body CommonRequestWithDate request);*/

    @POST(BaseUrl.AUTHORISED_RETAILERS_LIST)
    Observable<AuthorizedRetailerList> getAuthorisedRetailer(@Body CommonRequest request);

    @POST(BaseUrl.ITEMLIST)
    Observable<ItemListResponse> getItemList(@Body CommonRequest request);

    @POST(BaseUrl.RETAILERLIST_SHOP)
    Observable<ShopListResponse> getShopList(@Body CommonRequestWithDate request);

    @POST(BaseUrl.ORDER_LIST)
    Observable<OrderListResponse> getOrderList(@Body CommonRequestWithDate request);

    @POST(BaseUrl.GETSHOPORDER_VISITSHOP)
    Observable<Response<Void>> visitOrGetShopOrder(@Body SaveShopOrder request);


}
