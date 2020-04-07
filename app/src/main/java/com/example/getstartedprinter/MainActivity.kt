package com.example.getstartedprinter
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.getstartedprinter.R
import com.example.getstartedprinter.base.PrinterBase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.receipt.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap = createBitmapFromView()
        imageView_preview.setImageBitmap(bitmap)

        button_print.setOnClickListener {
            val printBase = PrinterBase(this)
            printBase.setupPrinter()
            printReceipt(printBase, bitmap)
        }

    }

    private fun printReceipt(printBase: PrinterBase, bitmap: Bitmap) {
        Thread(Runnable {
            printBase.init()
            printBase.printBitmapWithMonoThreshold(bitmap, 200)
            printBase.step(70)
            printBase.start()
        }).start()
    }

    private fun createBitmapFromView(): Bitmap {
        val mInflate = LayoutInflater.from(this).inflate(R.layout.receipt, null)

        val constraintLayout = mInflate.constraintLayout as ConstraintLayout
        constraintLayout.measure(
            View.MeasureSpec.makeMeasureSpec(convertDpToPixels(650), View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        constraintLayout.layout(
            0,
            0,
            constraintLayout.measuredWidth,
            constraintLayout.measuredHeight
        )

        val bitmap = Bitmap.createBitmap(
            constraintLayout.measuredWidth,
            constraintLayout.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        constraintLayout.draw(canvas)

        return bitmap
    }

    private fun convertDpToPixels(dp: Int): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                Resources.getSystem().displayMetrics
            )
        )
    }

}