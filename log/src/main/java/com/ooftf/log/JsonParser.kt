package com.ooftf.log

import java.lang.reflect.Type

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/7
 */
interface JsonParser {
    /**
     * Object to json
     *
     * @param instance obj
     * @return json string
     */
    fun object2Json(instance: Any?): String?

    /**
     * Parse json to object
     *
     * @param input json string
     * @param clazz object type
     * @return instance of object
     */
    fun <T> parseObject(input: String?, clazz: Type?): T
}