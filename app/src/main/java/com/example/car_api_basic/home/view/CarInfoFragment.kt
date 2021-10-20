package com.example.car_api_basic.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.car_api_basic.R
import com.example.car_api_basic.home.adapter.ViewPagerAdapter
import com.example.car_api_basic.home.connect.Connect
import com.example.car_api_basic.home.model.CarInfoData
import com.example.car_api_basic.home.model.HomeInterface
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarInfoFragment : Fragment() {

    companion object{
        fun newInstance() : CarInfoFragment {
            return CarInfoFragment()
        }
    }

    fun Int.dp(): Int {
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
    }

    @SuppressLint("ResourceType", "CutPasteId", "SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_car_info, container, false)

        val viewPager_img = view.findViewById<ViewPager2>(R.id.viewPager_img)
        val spring_dots_indicator = view.findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)
        val car_info_progress_layout = view.findViewById<LinearLayout>(R.id.car_info_progress_layout)
        val car_info_web_view = view.findViewById<WebView>(R.id.car_info_web_view)
        val car_info_search_btn = view.findViewById<Button>(R.id.car_info_search_btn)

        val car_info_tag_btn = view.findViewById<LinearLayout>(R.id.car_info_tag_btn)
        val car_tax_tag_btn = view.findViewById<LinearLayout>(R.id.car_tax_tag_btn)
        val car_info_submit_btn = view.findViewById<Button>(R.id.car_info_submit_btn)
        val car_number_input = view.findViewById<EditText>(R.id.car_number_input)
        val owner_name_input = view.findViewById<EditText>(R.id.owner_name_input)
        val car_info_hidden_layout = view.findViewById<LinearLayout>(R.id.car_info_hidden_layout)
        val car_info_carvender = view.findViewById<TextView>(R.id.car_info_carvender)
        val car_info_carname = view.findViewById<TextView>(R.id.car_info_carname)
        val car_info_submodel = view.findViewById<TextView>(R.id.car_info_submodel)
        val car_info_caryear = view.findViewById<TextView>(R.id.car_info_caryear)
        val car_info_price = view.findViewById<TextView>(R.id.car_info_price)
        val car_info_seats = view.findViewById<TextView>(R.id.car_info_seats)
        val car_info_cc = view.findViewById<TextView>(R.id.car_info_cc)
        val car_info_fueleco = view.findViewById<TextView>(R.id.car_info_fueleco)
        val car_info_fueltank = view.findViewById<TextView>(R.id.car_info_fueltank)
        val car_info_status_message = view.findViewById<TextView>(R.id.car_info_status_message)

        car_info_search_btn.setOnClickListener {
            car_info_web_view.visibility = View.GONE
            car_info_web_view.apply {
                WebViewClient()
                settings.javaScriptEnabled = false
            }
            car_info_web_view.loadUrl("https://www.naver.com")
        }


        // API 셋팅
        val conn = Connect().connect()
        val home_api: HomeInterface = conn.create(HomeInterface::class.java)

        car_info_submit_btn.setOnClickListener {
            car_info_progress_layout.visibility = View.VISIBLE
            val temp_car_info = car_number_input.text.toString()
            val temp_owner_name = owner_name_input.text.toString()

            val hash:HashMap<String, String> = HashMap()
            hash["REGINUMBER"] = temp_car_info
            hash["OWNERNAME"] = temp_owner_name

            home_api.carInfo(hash).enqueue(object: Callback<CarInfoData> {
                override fun onResponse(call: Call<CarInfoData>, response: Response<CarInfoData>) {
                    val body = response.body()

                    if(body != null) {
                        if(body.errCode == "0000") {
                            car_info_hidden_layout.visibility = View.VISIBLE
                            car_info_progress_layout.visibility = View.GONE

                            car_info_carvender.text = body.data.CARVENDER
                            car_info_carname.text = body.data.CARNAME
                            car_info_submodel.text = body.data.SUBMODEL
                            car_info_caryear.text = body.data.CARYEAR
                            car_info_price.text = body.data.PRICE
                            car_info_seats.text = body.data.SEATS
                            car_info_cc.text = body.data.CC
                            car_info_fueleco.text = body.data.FUELECO
                            car_info_fueltank.text = body.data.FUELTANK
                            car_info_status_message.text = ""
                        } else {
                            car_info_hidden_layout.visibility = View.GONE
                            car_info_progress_layout.visibility = View.GONE
                            car_info_status_message.text = "차량 데이터가 존재하지 않습니다."
                        }

                    } else {
                        car_info_hidden_layout.visibility = View.GONE
                        car_info_progress_layout.visibility = View.GONE
                        car_info_status_message.text = "차량 데이터가 존재하지 않습니다."
                    }

                    Log.d("TEST", "home_api - carInfo 통신성공 바디 -> $body")
                }

                override fun onFailure(call: Call<CarInfoData>, t: Throwable) {
                    Log.d("TEST", "home_api - carInfo 통신실패 에러 -> " + t.message)
                    car_info_hidden_layout.visibility = View.GONE
                    car_info_progress_layout.visibility = View.GONE
                    car_info_status_message.text = "차량 데이터가 존재하지 않습니다."
                }
            })
        }

        car_tax_tag_btn.setOnClickListener {
            (activity as HomeActivity).changeFragment(1)
        }

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        viewPager_img.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        viewPager_img.offscreenPageLimit = 1
        viewPager_img.adapter = ViewPagerAdapter(getIconList())
        viewPager_img.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        spring_dots_indicator.setViewPager2(viewPager_img)
        spring_dots_indicator.setDotIndicatorColor(Color.parseColor("#FF7F00"))
        spring_dots_indicator.setStrokeDotsIndicatorColor(Color.parseColor("#999999"))

        return view
    }

    private fun getIconList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.car_1, R.drawable.car_2, R.drawable.car_3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}