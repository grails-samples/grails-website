package org.grails

import javax.servlet.http.HttpServletResponse

import pl.burningice.plugins.image.ast.Image

class DbContainerImageController extends pl.burningice.plugins.image.DbContainerImageController {

    def index = {
        Image image = imageService.get(params.imageId)

        if (!image){
            return response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }

        response.setHeader('Cache-Control', 'public, max-age=600')
        response.setContentType(getContentType(image))
        response.setContentLength(image.data.length)
        response.outputStream << new ByteArrayInputStream(image.data)
    }
}
