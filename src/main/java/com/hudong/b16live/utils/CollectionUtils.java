package com.hudong.b16live.utils;

import java.util.Collection;

/**
 * 描        述：集合工具类
 * 创建时间：2016-8-22
 * @author Jibaole
 */
public class CollectionUtils {

	/**
	 * 判断集合对象是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection collection){
	    return (collection == null) || (collection.isEmpty());
	}
}
