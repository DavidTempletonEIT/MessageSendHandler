package com.raywenderlich.mcwenderlich

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import java.util.*

class OrderHandlerThread(private var uiHandler: MainActivity.UiHandler) :
        HandlerThread("OrderHandlerThread") {

    private var handler: Handler? = null
    private val random = Random()

    private fun convertCurrency(foodPriceInDollars: Float): Float {
        return foodPriceInDollars * 68.45f
    }

    private fun attachSideOrder(): String {

        val randomOrder = random.nextInt(3)
        return when (randomOrder) {
            0 -> "Chips"
            1 -> "Salad"
            else -> "Nachos"
        }
    }

    private fun getHandler(looper: Looper): Handler {
        //1
        return object : Handler(looper) {
            //2
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                //3
                val foodOrder = msg?.obj as FoodOrder
                //4
                foodOrder.foodPrice = convertCurrency(foodOrder.foodPrice)
                //5
                foodOrder.sideOrder = attachSideOrder()
                //6
                val processedMessage = Message()
                //7
                processedMessage.obj = foodOrder
                //8
                uiHandler.sendMessage(processedMessage)
            }
        }

    }

    fun sendOrder(foodOrder: FoodOrder) {
        //1
        val message = Message()
        //2
        message.obj = foodOrder
        //3
        handler?.sendMessage(message)
    }


    override fun onLooperPrepared() {
            super.onLooperPrepared()
            handler = getHandler(looper)
        }


    }
