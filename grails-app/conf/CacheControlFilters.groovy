class CacheControlFilters {
    def filters = {
        cacheDynamicImages(controller:'dbContainerImage', action:'index') {
            before = {
                response.setHeader('Cache-Control', 'public, max-age=600')
                true
            }
        }
    }
}
