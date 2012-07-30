package org.grails.db

import org.hibernate.dialect.MySQL5InnoDBDialect

class Utf8InnodbDialect extends MySQL5InnoDBDialect {
    @Override
    String getTableTypeString() { super.getTableTypeString() + ' DEFAULT CHARSET=utf8' }
}
