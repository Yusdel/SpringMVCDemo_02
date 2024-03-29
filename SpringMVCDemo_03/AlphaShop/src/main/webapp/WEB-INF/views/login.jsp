<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<div id="mainWrapper">
            <div class="login-container">
                <div class="login-card">
                    <div class="login-form">
                        <c:url var="loginUrl" value="/login" /> <!-- TODO Spring Security NOTE. Is the same name specified in Config file!-->
                        <form action="${loginUrl}" method="post" class="form-horizontal">
                        
                            <c:if test="${param.error != null}"> <!-- NOTE. Param in CONFIG class!-->
                                <div class="alert alert-danger">
                                    <p><spring:message code="login.form.errmsg"/></p>
                                </div>
                            </c:if>
                            
                            <c:if test="${param.forbidden != null}"> <!-- NOTE. Param in CCONFIG class!-->
                                <div class="alert alert-danger">
                                    <p><spring:message code="login.form.forbiddenmsg"/></p>
                                </div>
                            </c:if>
                            
                            <c:if test="${param.logout != null}"> <!-- NOTE. Param in CONFIG class!-->
                                <div class="alert alert-success">
                                    <p><spring:message code="login.form.logoutmsg"/></p>
                                </div>
                            </c:if>
                            <div class="input-group input-sm">
                                <label class="input-group-addon" for="idFidParam"><i class="fa fa-check-square"></i></label>
                                <input type="text" class="form-control" id="idFidParam" name="idFidParam" placeholder="Codice Fidelity" required>
                            </div>
                            <div class="input-group input-sm">
                                <label class="input-group-addon" for="userId"><i class="fa fa-user"></i></label>
                                <input type="text" class="form-control" id="userId" name="userId" placeholder="Nome Utente" required> <!-- NOTE. (id) Param in CCONFIG class!-->
                            </div>
                            <div class="input-group input-sm">
                                <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required> <!-- NOTE. (id) Param in CCONFIG class!-->
                            </div>
                            <div class="input-group input-sm">
                              <div class="checkbox">
                                <label class="mt-checkbox mt-checkbox-outline">
                                	<input type="checkbox" id="ricordami" name="ricordami">Ricordami
                                </label>  
                              </div>
                            </div>
                            <!-- NOTE. REQUIRED control for forms to avoid the csrf hacker attack -->
                            <input type="hidden" name="${_csrf.parameterName}"
                                value="${_csrf.token}" />
                                 
                            <div class="form-actions">
                                <input type="submit"
                                    class="btn btn-block btn-primary btn-default" value="Log in">
                            </div>
                        </form>
                    </div>
              </div>
        </div>
	</div>