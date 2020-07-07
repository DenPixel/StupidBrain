<#import "parts/page.ftl" as p>
<#import  "parts/login.ftl" as l>

<@p.page>
    <h1>Add new user</h1>
    <#if message??>
        <p>${message}</p>
    </#if>
    <@l.login "/registration">
        <div><label> RePassword: <input type="password" name="rePassword"> </label></div>
        <div><label> Email: <input type="email" name="email"> </label></div>
    </@l.login>
</@p.page>