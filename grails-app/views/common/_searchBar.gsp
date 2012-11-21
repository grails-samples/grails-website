<form class="search" action="/${type}/search" name="${type}-search" method="GET">
    <input type="text" id="q" name="q" value="${params.q?.encodeAsHTML()}" placeholder="Search for a ${type}"/>
    <a href="#" id="search-button" class="btn btn-search"><i class="icon-search icon-white"></i></a>
</form>

<script type="text/javascript">
    $(function () {
        $('#search-button').click(function () {
            console.log('clicked');
            if ($('#q').val() != '' && $('#q').val() != undefined) {
                $('form[name="${type}-search"]').submit();
            } else {
                console.log("Didn't submit because the field is empty");
            }
        });
    })
</script>