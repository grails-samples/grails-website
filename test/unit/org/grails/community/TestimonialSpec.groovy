package org.grails.community

import org.grails.UnitSpecHelper
import org.grails.content.GenericApprovalResponse

import grails.test.mixin.Mock

import spock.lang.Unroll
import org.grails.common.ApprovalStatus

@Mock(Testimonial)
class TestimonialSpec extends UnitSpecHelper {

    @Unroll("testimonial constraints on #field with value: #value")
    def "testimonial all constraints"() {
        when:
        def testimonial = new Testimonial()
        testimonial[field] = value

        testimonial.validate()
        boolean fieldHasErrors = testimonial.errors[field]

        then:
        valid == !fieldHasErrors

        where:
        field           | value                 | valid
        'title'         | ''                    | false
        'title'         | null                  | false
        'title'         | word(50)              | true
        'title'         | word(51)              | false

        'body'          | ''                    | false
        'body'          | null                  | false
        'body'          | word(1)               | true
        'body'          | word(99999)           | true

        'companyName'   | ''                    | true
        'companyName'   | null                  | true
        'companyName'   | word(255)             | true
        'companyName'   | word(256)             | false

        'status'        | null                      | false
        'status'        | ApprovalStatus.APPROVED   | true

        'submittedBy'   | null                      | false

    }
}