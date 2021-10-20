package com.example.car_api_basic.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.car_api_basic.R
import com.example.car_api_basic.home.adapter.ViewPagerAdapter
import com.example.car_api_basic.home.connect.Connect
import com.example.car_api_basic.home.model.CarInfoData
import com.example.car_api_basic.home.model.CarTaxData
import com.example.car_api_basic.home.model.HomeInterface
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarTaxFragment : Fragment() {

    companion object{
        fun newInstance() : CarTaxFragment {
            return CarTaxFragment()
        }
    }

    @SuppressLint("ResourceType", "CutPasteId", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_car_tax, container, false)

        val viewPager_img2 = view.findViewById<ViewPager2>(R.id.viewPager_img2)
        val spring_dots_indicator2 = view.findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator2)
        val car_tax_progress_layout = view.findViewById<LinearLayout>(R.id.car_tax_progress_layout)
        val car_tax_search_btn = view.findViewById<Button>(R.id.car_tax_search_btn)
        val car_tax_web_view = view.findViewById<WebView>(R.id.car_tax_web_view)

        val car_info_tag_btn2 = view.findViewById<LinearLayout>(R.id.car_info_tag_btn2)
        val car_tax_tag_btn2 = view.findViewById<LinearLayout>(R.id.car_tax_tag_btn2)
        val car_tax_submit_btn = view.findViewById<Button>(R.id.car_tax_submit_btn)
        val car_number_input2 = view.findViewById<EditText>(R.id.car_number_input2)
        val car_tax_status_message = view.findViewById<TextView>(R.id.car_tax_status_message)
        val car_tax_hidden_layout = view.findViewById<LinearLayout>(R.id.car_tax_hidden_layout)
        val car_tax_cartax = view.findViewById<TextView>(R.id.car_tax_cartax)
        val car_tax_jan = view.findViewById<TextView>(R.id.car_tax_jan)
        val car_tax_mar = view.findViewById<TextView>(R.id.car_tax_mar)
        val car_tax_jun = view.findViewById<TextView>(R.id.car_tax_jun)
        val car_tax_sep = view.findViewById<TextView>(R.id.car_tax_sep)

        car_tax_search_btn.setOnClickListener {
            car_tax_web_view.visibility = View.GONE
            car_tax_web_view.apply {
                WebViewClient()
                settings.javaScriptEnabled = false
            }
            car_tax_web_view.loadUrl("https://www.naver.com")
        }

        // API 셋팅
        val conn = Connect().connect()
        val home_api: HomeInterface = conn.create(HomeInterface::class.java)

        car_tax_submit_btn.setOnClickListener {
            val mInputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            car_tax_progress_layout.visibility = View.VISIBLE
            val temp_car_number = car_number_input2.text.toString()

            val hash:HashMap<String, String> = HashMap()
            hash["CARNUMBER"] = temp_car_number

            home_api.carTax(hash).enqueue(object: Callback<CarTaxData> {
                override fun onResponse(call: Call<CarTaxData>, response: Response<CarTaxData>) {
                    val body = response.body()

                    if(body != null) {
                        if(body.errCode == "0000") {
                            car_tax_hidden_layout.visibility = View.VISIBLE
                            car_tax_progress_layout.visibility = View.GONE

                            car_tax_cartax.text = body.data.CARTAX
                            car_tax_jan.text = body.data.PREPAYMENTJAN
                            car_tax_mar.text = body.data.PREPAYMENTMAR
                            car_tax_jun.text = body.data.PREPAYMENTJUN
                            car_tax_sep.text = body.data.PREPAYMENTSEP
                            car_tax_status_message.text = ""
                        } else {
                            car_tax_hidden_layout.visibility = View.GONE
                            car_tax_progress_layout.visibility = View.GONE
                            car_tax_status_message.text = "차량 데이터가 존재하지 않습니다."
                        }

                    } else {
                        car_tax_hidden_layout.visibility = View.GONE
                        car_tax_progress_layout.visibility = View.GONE
                        car_tax_status_message.text = "차량 데이터가 존재하지 않습니다."
                    }

                    Log.d("TEST", "home_api - carTax 통신성공 바디 -> $body")
                }

                override fun onFailure(call: Call<CarTaxData>, t: Throwable) {
                    Log.d("TEST", "home_api - carTax 통신실패 에러 -> " + t.message)
                    car_tax_hidden_layout.visibility = View.GONE
                    car_tax_progress_layout.visibility = View.GONE
                    car_tax_status_message.text = "차량 데이터가 존재하지 않습니다."
                }
            })
        }

        car_info_tag_btn2.setOnClickListener {
            (activity as HomeActivity).changeFragment(0)
        }

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        viewPager_img2.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        viewPager_img2.offscreenPageLimit = 1
        viewPager_img2.adapter = ViewPagerAdapter(getIconList())
        viewPager_img2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        spring_dots_indicator2.setViewPager2(viewPager_img2)
        spring_dots_indicator2.setDotIndicatorColor(Color.parseColor("#FF7F00"))
        spring_dots_indicator2.setStrokeDotsIndicatorColor(Color.parseColor("#999999"))

        return view
    }

    private fun getIconList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.car_4, R.drawable.car_5, R.drawable.car_6)
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