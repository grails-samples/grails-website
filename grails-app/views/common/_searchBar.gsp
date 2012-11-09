<section class="search">
    <form action="/${type}/search" name="${type}-search" method="GET">
        <input type="search" id="q" name="q" value="${params.q?.encodeAsHTML()}" placeholder="Search for a ${type}"/>
        <a href="#" id="search-button" class="zoom">Search</a>
    </form>
</section>

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