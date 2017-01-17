package com.wsh.base.wxpay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {

	public static Map<String, String> decodeXml(String content) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(content);
			Element root = doc.getRootElement();
			String returnCode = root.element("return_code").getTextTrim();
			Element ele = root.element("prepay_id");
			if (StringUtils.equals("SUCCESS", returnCode) && null != ele) {
				result.put("prepay_id", ele.getTextTrim());
			} else {
				ele = root.element("err_code_des");
				if (null != ele) {
					result.put("return_msg", ele.getTextTrim());
				} else {
					ele = root.element("return_msg");
					if (null != ele) {
						result.put("return_msg", ele.getTextTrim());
					}
				}

				result.put("return_code", "FAIL");
			}
		} catch (DocumentException e) {
			result.put("return_code", "FAIL");
			result.put("return_msg", e.getMessage());
		}

		return result;
	}

	public static Map<String, String> transferXml(String xml) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Element temp = null;
			for (Iterator<Element> iter = root.elementIterator(); iter
					.hasNext();) {
				temp = iter.next();
				result.put(temp.getName(), temp.getTextTrim());
			}
		} catch (DocumentException e) {
			result.put("return_code", "FAIL");
			result.put("return_msg", e.getMessage());
		}

		return result;
	}
}
