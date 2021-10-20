package com.example.car_api_basic.home.model


// 차량 정보 조회 구조체
data class CarInfoData(
    val data: CarInfoDataArray,
    val errCode: String,
    val errMsg: String,
    val result: String
)
data class CarInfoDataArray (
    val BATTERYLIST: List<BATTERYLIST>,
    val CARNAME: String,
    val CARURL: String,
    val CARVENDER: String,
    val CARYEAR: String,
    val CC: String,
    val DRIVE: String,
    val EOILLITER: String,
    val ERRMSG: String,
    val FRONTTIRE: String,
    val FUEL: String,
    val FUELECO: String,
    val FUELTANK: String,
    val MISSION: String,
    val PRICE: String,
    val REARTIRE: String,
    val RESPONSE: String,
    val RESULT: String,
    val SEATS: String,
    val STATUS: String,
    val SUBMODEL: String,
    val UID: String,
    val VIN: String,
    val WIPER: String
)
data class BATTERYLIST(
    val BRAND: String,
    val MODEL: String,
    val TYPE: String
)

// 차량 세금(예상) 계산 구조체
data class CarTaxData(
    val data: CarTaxDataArray,
    val errCode: String,
    val errMsg: String,
    val result: String
)
data class CarTaxDataArray(
    val CARTAX: String,
    val ECODE: String,
    val ERRDOC: String,
    val ERRMSG: String,
    val ETRACK: String,
    val INCARNUMBER: String,
    val PREPAYMENTJAN: String,
    val PREPAYMENTJUN: String,
    val PREPAYMENTMAR: String,
    val PREPAYMENTSEP: String,
    val REDUCERATE: String,
    val RESULT: String
)