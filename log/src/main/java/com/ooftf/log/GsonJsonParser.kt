package com.ooftf.log

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/6/4
 */
class GsonJsonParser : JsonParser {
    val gson by lazy {
        Gson()
    }

    override fun object2Json(instance: Any?): String? {
        return gson.toJson(instance)
    }

    override fun <T> parseObject(input: String?, clazz: Type?): T {
        return gson.fromJson(input, clazz)
    }
}