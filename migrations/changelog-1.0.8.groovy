databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1311929798233-1") {
        createTable(tableName: "version_order") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "version_orderPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "base_version", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "order_index", type: "integer") {
                constraints(nullable: "false", unique: "true")
            }
        }

        createIndex(indexName: "order_index_unique_1311929797898", tableName: "version_order", unique: "true") {
            column(name: "order_index")
        }
    }
}
