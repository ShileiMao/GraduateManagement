package io.github.xmchxup.latticy.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.talelin.autoconfigure.exception.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Component
public class GenericAndJson {

	private static ObjectMapper mapper;

	@Autowired
	public void setMapper(ObjectMapper mapper) {
		GenericAndJson.mapper = mapper;
	}

	public static <T> String objectToJson(T o) {
		try {
			return GenericAndJson.mapper.writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailedException(9999);
		}
	}

	public static <T> T jsonToObject(String s, TypeReference<T> tr) {
		if (null == s) {
			return null;
		}
		try {
			return GenericAndJson.mapper.readValue(s, tr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailedException(9999);
		}
	}


}
