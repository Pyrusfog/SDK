package com.example.getstartedprinter.base

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.exemplo.getstartedprinter.base.enum.StatusPrinter
import com.pax.dal.IDAL
import com.pax.dal.IPrinter
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.dal.exceptions.PrinterDevException
import com.pax.neptunelite.api.NeptuneLiteUser

class PrinterBase(private val context: Context){
    private var  printer: IPrinter? = null
    private var dal : IDAL? = null
    private var neptuneLiteUser : NeptuneLiteUser? = null

    public fun setupPrinter(){
        neptuneLiteUser = NeptuneLiteUser.getInstance()
        neptuneLiteUser?.let {
            dal = neptuneLiteUser!!.getDal(context)
        }
        dal?.let {
            printer = dal!!.printer
        }
    }

    public fun  print(bitmap: Bitmap, cutMode: Int, listener: IPrinter.IPinterListener){
        try {
            printer!!.print(bitmap, cutMode, listener )
        }catch (e: PrinterDevException){

        }
    }

    public fun  printBitmapWithMonoThreshold(bitmap: Bitmap, grayThreshold: Int){
        try {
            printer!!.printBitmapWithMonoThreshold(bitmap, grayThreshold )
        }catch (e: PrinterDevException){

        }
    }

    public fun init(){
        try {
            printer?.init()
        } catch (e: PrinterDevException) {
            e.printStackTrace()
            Log.d("init", e.toString())
        }
    }

    public  fun getStatus() : String{
        return try {
            val statusCode = printer!!.status
            statusCodeToString(statusCode)
        }catch (e: PrinterDevException){
            ""
        }
    }

    public fun setFont(asciiFontType: EFontTypeAscii, cFontType: EFontTypeExtCode){

        try {
            printer!!.fontSet(asciiFontType,cFontType )
        }catch (e : PrinterDevException){
            e.printStackTrace()
            Log.e("SETFONT", e.printStackTrace().toString())
        }
    }

    public fun setFontPath(path: String){

        try {
            printer!!.setFontPath(path )
        }catch (e : PrinterDevException){
            e.printStackTrace()
        }
    }

    public fun spaceSet(wordSpace: Byte, lineSpace: Byte){
        try {
            printer!!.spaceSet(wordSpace, lineSpace)
        }catch (e: PrinterDevException){

        }
    }

    public fun printText(text: String, charset: String?){
        try {
            printer!!.printStr(text, charset)
        }catch (e: PrinterDevException){

        }
    }

    public fun step(pixel : Int ){
        try {
            printer!!.step(pixel)
        }catch (e: PrinterDevException){

        }
    }

    public fun printImage(image : Bitmap){
        try{
            printer!!.printBitmap(image)
        }catch (e: PrinterDevException){

        }
    }

    public fun start() : String{
        return try {
            val code = printer!!.start()
            statusCodeToString(code)
        }catch (e: PrinterDevException){
            ""
        }
    }

    public fun leftIntent(intent : Int){
        try {
            printer!!.leftIndent(intent)
        }catch (e: PrinterDevException){

        }
    }

    public fun getDotLine(): Int{
        return try {
            printer!!.dotLine
        }catch (e: PrinterDevException){
            -2
        }
    }

    public fun setGray(level : Int){
        try {
            printer!!.setGray(level)
        }catch (e: PrinterDevException){

        }
    }

    public fun setDoubleWidth(isAscDouble: Boolean, isLocalDouble: Boolean){
        try {
            printer!!.doubleWidth(isAscDouble, isAscDouble)
        }catch (e: PrinterDevException){
            e.printStackTrace()
        }
    }

    public fun setDoubleHeight(isAscDouble: Boolean, isLocalDouble: Boolean){
        try {
            printer!!.doubleHeight(isAscDouble, isAscDouble)
        }catch (e: PrinterDevException){
            e.printStackTrace()
        }
    }

    public fun setInvert(isInvert : Boolean){
        try {
            printer!!.invert(isInvert)
        } catch (e: PrinterDevException) {
            e.printStackTrace()
        }
    }

    public  fun cutPaper(mode : Int): String{
        return try {
            printer!!.cutPaper(mode)
            "Cut paper sucessful"
        }catch (e: PrinterDevException){
            e.toString()
        }
    }

    public  fun getCutMode(): String{
        return try {

            return when(printer!!.cutMode){
                0 -> "Only support full paper cut"
                1 -> "Only support partial paper cutting"
                2 -> "support partial paper and full paper cutting "
                -1-> "No cutting knife,not support"
                else -> {
                    "unknown"
                }
            }
        }catch (e: PrinterDevException){
            e.toString()
        }
    }

    private fun statusCodeToString(code: Int): String{
        return when(code){
            StatusPrinter.SUCCESS.codeStatus -> "Success"
            StatusPrinter.PRINTER_IS_BUSY.codeStatus -> "Printer is busy"
            StatusPrinter.OUT_OF_PAPER.codeStatus -> "Out of paper"
            StatusPrinter.THE_FORMAT_OF_PRINT_DATA_PACKER_ERROR.codeStatus -> "The format of print data packet error"
            StatusPrinter.PRINTER_MALFUCTIONS.codeStatus -> "Printer malfunctions"
            StatusPrinter.PRINTER_OVER_HEATS.codeStatus -> "Success"
            StatusPrinter.VOLTAGE_IS_TOO_LOW.codeStatus -> "Printer voltage is too low"
            StatusPrinter.PRINTING_IS_UNFINISHED.codeStatus -> "Printing is unfinished "
            StatusPrinter.NOT_INSTALLED_FONT_LIBRARY.codeStatus -> "The printer has not installed font library"
            StatusPrinter.DATA_PACKAGE_IS_TOO_LONG.codeStatus -> "Data package is too long"
            else ->{
                ""
            }
        }
    }
}