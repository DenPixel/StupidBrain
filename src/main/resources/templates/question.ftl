<#import "parts/page.ftl" as p>
<#import "parts/submit.ftl" as s>

<@p.page>
    <#if question??>
        <#if message??><p>${message}</p></#if>
        <p>Rating: ${question.getRating()}</p>
        <p>Name: ${question.getName()}</p>
        <p>Description: ${question.getDescription()}</p>
        <p>Your answers: </p>

        <#if user?? && user.getId().equals(question.getUser())>

            <#list trueAnswers as answer>
                <p>Answer: ${answer.getAnswer()}</p>
                <p>Description: ${answer.getDescription()}</p>
            </#list>

            <form action="/true-answers" method="post">
                <div><label>Add questions answer: <input type="text" name="answer"></label></div>
                <div><label>Description: <input type="text" name="description"></label></div>
                <input type="hidden" name="question" value="${question.getId()}">
                <@s.submit "Add answer"/>
            </form>

        <#else>

            <#list usersAnswers as answer>
                <p>${answer.getAnswer()}</p>
            </#list>

            <form action="/users-answer" method="post">
                <div><label>Answer: <input type="text" name="answer"></label></div>
                <input type="hidden" name="question" value="${question.id}">
                <@s.submit "Answer the question"/>
            </form>

        </#if>
    </#if>

    <a href="/questions">Questions</a>
</@p.page>
