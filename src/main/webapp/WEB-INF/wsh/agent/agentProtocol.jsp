<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/extjsp/jsphead.jsp" %>
<title>代理商服务协议</title>
</head>
<body style="margin: 13px">
<p style="text-align:center;line-height:150%">
    <strong><span style="font-size:16px;line-height:150%;font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">代理商服务协议</span></strong>
</p>

<p>1、代理商享有维书会提供的相关服务与技术支持，及相关权益。</p>
<p>2、代理商有义务遵守维书会相关的商业秘密。</p>
<p>3、代理商需保证服务质量，不得从事其它损害维书会利益的行为。</p>
<p>4、代理商需要积极宣传推广相关业务及其增值服务，维护维书会的整体形象和服务品质。</p>
<p>5、代理商可以以“某某地区维书会代理”的名义，但不能以“独家代理”等具有排他性的名义进行宣传及商业活动。</p>
<p>6、代理商承诺不向与维书会构成商业竞争关系的企业、商业机构或者组织提供有关维书会的业务、技术等一切相关信息或者资料，否则承担相应的责任。</p>
<p>7、与其他代理商之间不得进行恶性竞争或者其它不正当竞争。</p>
<p>8、对因代理商违反本协议造成客户或维书会损失的，维书会有权终止本协议并有权要求代理商赔偿损失。</p>
<p>9、对于代理商与其客户之间的纠纷、争议、损失、侵权、违约责任等，均由代理商与客户自行解决，维书会不介入代理商与客户的纠纷、争议等，也不对客户的任何损失负责。</p>
<p>10、本协议的订立、效力、解释、履行和争议的解决均适用中华人民共和国法律，除法律本身有明确规定外，后继立法或法律变更对本协议不具有溯及力。</p>

<p>
    <br/>
</p>

<div class="wsh_vip_btn">
  <p class="buttons"><a href="javascript:;" class="btns" id="iamsure">确定遵守协议</a></p>
</div>

<script type="text/javascript">
	$("#iamsure").click(function(){
		window.location.href= "<%=basePath%>/agent/toAgentInvationPage ";
	});
</script>


</body>
</html>