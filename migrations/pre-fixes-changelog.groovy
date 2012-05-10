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
}
