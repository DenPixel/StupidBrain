<#import "submit.ftl" as s>

<#macro login path>
    <form action="${path}" method="post">
        <div><label> Username : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <#nested/>
        <@s.submit "Sign in"/>
    </form>
</#macro>