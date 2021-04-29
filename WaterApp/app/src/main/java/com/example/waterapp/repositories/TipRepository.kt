package com.example.waterapp.repositories

import com.example.waterapp.models.Tip

/**
 * This class simulates calls to an API to get random tips
 */
class TipRepository {

    private val tips: MutableList<Tip> = ArrayList()

    init {
        if (tips.isEmpty()) {
            tips.add(Tip("this is a tip"))
            tips.add(Tip("this is another tip"))
            tips.add(Tip("this is a tip about sun"))
            tips.add(Tip(("tip about water")))
            tips.add(Tip(("leaves are yellow")))
            tips.add(Tip(("leaves are brown")))
            tips.add(Tip(("some other tip")))
        }
    }

    fun fetchRandomTip(): Tip {
        return tips.random()
    }
}
