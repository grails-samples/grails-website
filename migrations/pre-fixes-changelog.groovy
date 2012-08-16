databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "site-autobase"
    changeSet(id: "fixes", author: "pledbrook") {
        createTable(tableName: "blog_entry_comment") {
            column name: "blog_entry_comments_id", type: "BIGINT(20)"
            column name: "comment_id", type: "BIGINT(20)"
        }

        createIndex(indexName: "FK904109B52DEA8738", tableName: "blog_entry_comment", unique: "false") {
            column name: "comment_id"
        }

        createIndex(indexName: "FK904109B5D0A3E3EA", tableName: "blog_entry_comment", unique: "false") {
            column name: "blog_entry_comments_id"
        }

        createTable(tableName: "content_comment") {
            column name: "content_comments_id", type: "BIGINT(20)"
            column name: "comment_id", type: "BIGINT(20)"
        }

        createTable(tableName: "job") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "jobPK")
            }
            column(name: "version", type: "bigint") { constraints(nullable: "false") }
        }

        createTable(tableName: "payment") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "paymentPK")
            }
            column(name: "version", type: "bigint") { constraints(nullable: "false") }
        }

        createTable(tableName: "job_payment") {
            column name: "job_payments_id", type: "BIGINT(20)"
            column name: "payment_id", type: "BIGINT(20)"
        }

        createIndex(indexName: "FKB26867842990F5A9", tableName: "job_payment", unique: "false") {
            column name: "job_payments_id"
        }

        createIndex(indexName: "FKB26867848399F42C", tableName: "job_payment", unique: "false") {
            column name: "payment_id"
        }

        createTable(tableName: "plugin_tags") {
            column name: "plugin_tags_id", type: "BIGINT(20)"
            column name: "tag_id", type: "BIGINT(20)"
        }

        createTable(tableName: "plugin_comment") {
            column name: "plugin_comments_id", type: "BIGINT(20)"
            column name: "comment_id", type: "BIGINT(20)"
        }

        createTable(tableName: "tag") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pluginPK")
            }
            column(name: "version", type: "bigint") { constraints(nullable: "false") }
        }

        createIndex(indexName: "class_datecreated", tableName: "content") {
            column name: "class"
            column name: "date_created"
        }

        createIndex(indexName: "titleIndex", tableName: "content") {
            column name: "title"
        }
    }

    // All these changes are required to get subsequent refactorings to work on an empty
    // InnoDB database. Trust me. See the parent changelog for more information on why
    // this is necessary.
    changeSet(id: "CreatesForDropping", author: "pledbrook") {
        sql('ALTER TABLE content ENGINE=MyISAM')
        sql('ALTER TABLE download_file ENGINE=MyISAM')
        sql('ALTER TABLE mirror ENGINE=MyISAM')
        sql('ALTER TABLE plugin ENGINE=MyISAM')
        sql('ALTER TABLE rating ENGINE=MyISAM')
        sql('ALTER TABLE rating_link ENGINE=MyISAM')
        sql('ALTER TABLE screencast ENGINE=MyISAM')
        sql('ALTER TABLE screencast_mirror ENGINE=MyISAM')
        sql('ALTER TABLE user_info ENGINE=MyISAM')
        sql('ALTER TABLE user_role ENGINE=MyISAM')

        createTable(tableName: "test_domain") { column name: "id", type: "BIGINT(20)" }
        createTable(tableName: "test_rater") { column name: "id", type: "BIGINT(20)" }

        createIndex(tableName: "content", indexName: 'FK38B73479BC488994') { column name: "title" }
        createIndex(tableName: "plugin", indexName: 'FKC5476F339B7AA533') { column name: "id" }
        createIndex(tableName: "plugin", indexName: 'FKC5476F33DDE70EC0') { column name: "name" }
        createIndex(tableName: "plugin", indexName: 'FKC5476F3317DC9031') { column name: "title" }
        createIndex(tableName: "plugin", indexName: 'FKC5476F33B0CAFAD7') { column name: "grails_version" }
        createIndex(tableName: 'rating_link', indexName: 'FK1827315C45884E64') { column name: 'rating_id' }
        createIndex(tableName: 'screencast_mirror', indexName: 'FK326C9CD3A67C8B4A') { column(name: 'screencast_id') }

        addForeignKeyConstraint(constraintName: 'FK38B73479839030FB',
                baseTableName: 'content', baseColumnNames: 'current_id',
                referencedTableName: 'content', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK38B734797B9748B6',
                baseTableName: 'content', baseColumnNames: 'author_id',
                referencedTableName: 'user', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FKBFFD6BBF550BF0EF',
                baseTableName: 'mirror', baseColumnNames: 'file_id',
                referencedTableName: 'download_file', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FKC5476F33FA44DC5E',
                baseTableName: 'plugin', baseColumnNames: 'screenshots_id',
                referencedTableName: 'content', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FKC5476F33B7D872D1',
                baseTableName: 'plugin', baseColumnNames: 'installation_id',
                referencedTableName: 'content', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FKC5476F33343A5DCF',
                baseTableName: 'plugin', baseColumnNames: 'description_id',
                referencedTableName: 'content', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FKC5476F33CD28C875',
                baseTableName: 'plugin', baseColumnNames: 'faq_id',
                referencedTableName: 'content', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK1437D8A21ADE5676',
                baseTableName: 'user_info', baseColumnNames: 'user_id',
                referencedTableName: 'user', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK143BF46A16D0D038',
                baseTableName: 'user_role', baseColumnNames: 'user_roles_id',
                referencedTableName: 'user', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK143BF46A75B39296',
                baseTableName: 'user_role', baseColumnNames: 'role_id',
                referencedTableName: 'role', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK1827315C45884E64',
           baseTableName: 'rating_link', baseColumnNames: 'rating_id',
           referencedTableName: 'rating', referencedColumnNames: 'id')
    }
}
