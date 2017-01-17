<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en" ng-app>
<head>
<title>绑定银行卡</title>
<%@include file="/extjsp/jsphead.jsp"%>
</head>
<script type="text/javascript" src="<%=basePath %>/myjs/bindBank.js"></script>
<input id="basePaths" value="<%=basePath %>" type="hidden">
<body class="yue_bg">
<div class="input_area">
<form>
  <ul class="input_ul">
  <li>
  	<p align="center"><font color="red">请绑卡</font></p>
  </li>
    <li>
      <input type="text" placeholder="银行卡姓名" class="input" value="${userInfo.userName}" id="name" maxlength="10">
    </li>
    <li>
      <input type="text" placeholder="银行卡号" class="input" value="${userInfo.bankNo}" id="bankno" maxlength="20">
    </li>
    <li>
    
     <div class="borrow_button custom_select">
        <select name="banktype" id="banktype">
          <option value="ABC_DEBIT" <c:if test="${userInfo.banktype=='ABC_DEBIT'}">selected="selected"</c:if> >农业银行(借记卡)</option>
          <option value="CCB_DEBIT" <c:if test="${userInfo.banktype=='CCB_DEBIT'}">selected="selected"</c:if> >建设银行(借记卡)</option>
          <option value="CMB_DEBIT" <c:if test="${userInfo.banktype=='CMB_DEBIT'}">selected="selected"</c:if> >招商银行(借记卡)</option>
          <option value="ICBC_DEBIT" <c:if test="${userInfo.banktype=='ICBC_DEBIT'}">selected="selected"</c:if> >工商银行(借记卡)</option>
        </select>
        <span class="pos_bg"></span>
      </div>
      
    </li>
	
	<li>
		<input type="text" placeholder="开户行，如：北京分行朝阳青年路支行" class="input" value="${userInfo.subBank}" id="subBank" maxlength="15">
    </li>
    
    <li>
      <p class="buttons"><a href="javascript:pushtheinfo()" class="btns">保存</a></p>
    </li>
  </ul>
  </form>
  <ul class="user_notice">
    <li class="tit">温馨提示：</li>
    <li>(1)提现目前支持中国农业银行、中国建设银行、招商银行、中国工商银行4家银行；</li>
    <li>(2)请注意填写银行信息，如有错误，会影响到你的提现时间；</li>
  </ul>
</div>
</body>
</html>