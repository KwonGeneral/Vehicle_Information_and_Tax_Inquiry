package com.example.car_api_basic.home.view

import android.Manifest
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.example.car_api_basic.R
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    private lateinit var car_info_fragment: CarInfoFragment
    private lateinit var car_tax_fragment: CarTaxFragment

    val bundle = Bundle()
    var mBackWait:Long = 0
    var frag_tag = 0

    // 권한 체크 : 인터넷, 네트워크
    val permission_list = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        requestPermissions(permission_list, 0)

        changeFragment(0)
    }

    fun changeFragment(int: Int) {
        if (int == 0) {
            car_info_fragment = CarInfoFragment.newInstance()
            car_info_fragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.home_frag, car_info_fragment).commit()
            frag_tag = 0
        } else if (int == 1) {
            car_tax_fragment = CarTaxFragment.newInstance()
            car_tax_fragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.home_frag, car_tax_fragment).commit()
            frag_tag = 1
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mBackWait >= 1000) {
            mBackWait = System.currentTimeMillis()
            val snack: Snackbar = Snackbar
                .make(
                    findViewById<RelativeLayout>(R.id.home_frag),
                    "뒤로가기 버튼을 한번 더 누르면 종료됩니다",
                    1000
                )
                .setBackgroundTint(Color.parseColor("#666666"))
                .setTextColor(Color.parseColor("#ffffff"))

            val snack_view = snack.view
            val params = snack_view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.CENTER_HORIZONTAL
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            snack_view.layoutParams = params
            snack.show()

        } else {
            finish()
        }
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