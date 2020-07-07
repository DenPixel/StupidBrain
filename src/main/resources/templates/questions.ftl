<#import "parts/page.ftl" as p>
<#import "parts/logout.ftl" as lout>
<#import "parts/submit.ftl" as s>

<@p.page>
    <div>
        <@lout.logout />
    </div>
    <div>
        <#if message??>
            <p>${message}</p>
        </#if>
        <form action="/questions" method="post">
            <div><label>Name Question: <input type="text" name="name"></label></div>
            <div><label>Description: <input type="text" name="description"></label></div>
            <@s.submit "Post question"/>
        </form>
    </div>
    <#list questionsList as question>
        <div>
            <p>
                <a href="/questions/${question.id}">Name: ${question.name}</a> Rating: ${question.rating}
            </p>
        </div>
    <#else>
        <p>No questions</p>
    </#list>
</@p.page>