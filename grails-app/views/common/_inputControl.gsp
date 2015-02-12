<div class="control-group ${hasErrors(bean: bean, field: field, 'error')}">
    <label for="${field}">${title} <g:if test="${desc}"><small>(${desc})</small></g:if></label>

    <div class="col-sm-10">
        ${body}
        <g:if test="${bean?.errors?.getFieldErrorCount(field) > 0}">
            <p class="error-block">${bean.errors.getFieldError(field).defaultMessage}</p>
        </g:if>
    </div>
</div>