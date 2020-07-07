<#macro submit value>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div><input type="submit" value="${value}"/></div>
</#macro>