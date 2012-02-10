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

        createIndex(indexName: "name_idx", tableName: "wiki_image") {
            column(name: "name")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1327422140130-2") {
        createTable(tableName: "wiki_image_bi_image") {
            column(name: "wiki_image_bi_image_id", type: "bigint")

            column(name: "image_id", type: "bigint")

            column(name: "bi_image_idx", type: "varchar(255)")
        }
    }

}
