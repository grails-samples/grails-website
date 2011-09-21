databaseChangeLog = {
    changeSet(author: "pledbrook", id: "cleanup") {
        dropIndex(indexName: "FK904109B52DEA8738", tableName: "blog_entry_comment")
        dropIndex(indexName: "FK904109B5D0A3E3EA", tableName: "blog_entry_comment")
        dropIndex(indexName: "class_datecreated", tableName: "content")
        dropIndex(indexName: "titleIndex", tableName: "content")
        dropIndex(indexName: "FKB26867842990F5A9", tableName: "job_payment")
        dropIndex(indexName: "FKB26867848399F42C", tableName: "job_payment")
        dropTable(tableName: "blog_entry_comment")
        dropTable(tableName: "job")
        dropTable(tableName: "job_payment")
        dropTable(tableName: "payment")
    }

    changeSet(author: "pledbrook", id: "1314257761835-1") {
        addColumn(tableName: "mirror") {
            column(name: "url_string", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }

        dropNotNullConstraint tableName: "mirror", columnName: "url", columnDataType: "blob"

        addForeignKeyConstraint(
                baseColumnNames: "file_id",
                baseTableName: "mirror",
                constraintName: "FKBFFD6BBF550BF0EF",
                deferrable: "false",
                initiallyDeferred: "false",
                referencedColumnNames: "id",
                referencedTableName: "download_file",
                referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook", id: "1314257761835-2") {
        createIndex(tableName: "content", indexName: "title_idx") {
            column(name: "title")
        }
    }
}
