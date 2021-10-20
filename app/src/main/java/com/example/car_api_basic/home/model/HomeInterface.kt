package com.example.car_api_basic.home.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeInterface {
    // 차량 정보 조회
    @POST("/assist/common/carzen/CarAllInfoInquiry")
    fun carInfo(
        @Body parameters: HashMap<String, String>
    ): Call<CarInfoData>

    // 차량 세금(예상) 계산
    @POST("/scrap/common/car365/CarTaxCalculation")
    fun carTax(
        @Body parameters: HashMap<String, String>
    ): Call<CarTaxData>
}