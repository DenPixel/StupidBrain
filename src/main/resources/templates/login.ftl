<#import "parts/page.ftl" as p>
<#import "parts/login.ftl" as l>

<@p.page>
    <@l.login "/login"/>
    <a href="/registration">Registration</a>
</@p.page>