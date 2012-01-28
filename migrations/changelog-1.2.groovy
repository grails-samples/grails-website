databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1327422140130-1") {
        createTable(tableName: "wiki_image") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "wiki_imagePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1327422140130-2") {
        createTable(tableName: "wiki_image_bi_image") {
            column(name: "wiki_image_bi_image_id", type: "bigint")

            column(name: "image_id", type: "bigint")

            column(name: "bi_image_idx", type: "varchar(255)")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1327422140130-3") {
        createIndex(indexName: "name_idx", tableName: "wiki_image") {
            column(name: "name")
        }
    }


    changeSet(author: "pledbrook", id: "multiple-plugin-authors") {
        createTable(tableName: "plugin_user_info") {
            column(name: "plugin_authors_id", type: "bigint")

            column(name: "user_info_id", type: "bigint")
        }

        createIndex(indexName: "FKA7111E96806B062", tableName: "plugin_user_info") {
            column(name: "plugin_authors_id")
        }

        createIndex(indexName: "FKA7111E962D69D3D0", tableName: "plugin_user_info") {
            column(name: "user_info_id")
        }

        addColumn(tableName: "user_info") {
            column(name: "email", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }

        addColumn(tableName: "user_info") {
            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }

        dropNotNullConstraint(columnDataType: "bigint", columnName: "user_id", tableName: "user_info")
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "author_email", tableName: "plugin")
    }

}
