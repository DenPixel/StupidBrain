<#import "parts/page.ftl" as p>
<#import "parts/logout.ftl" as lout>

<@p.page>
    <p>Welcome<#if user??>, ${user.getUsername()}</#if>!</p>
    <#if user??>
        <@lout.logout />
        <#if roles??>
            <div><a href="/questions">Go to questions</a></div>
        <#else>
            <p>You are baned</p>
        </#if>
    <#else>
        <div><a href="/login">Log in</a></div>
        <div><a href="/registration">Registration</a></div>
    </#if>
</@p.page>