databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1331310348954-1") {
        createTable(tableName: "pending_release") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pending_releaPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "plugin_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "plugin_version", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "pom", type: "mediumblob") {
                constraints(nullable: "false")
            }

            column(name: "xml", type: "mediumblob") {
                constraints(nullable: "false")
            }

            column(name: "zip", type: "mediumblob") {
                constraints(nullable: "false")
            }
        }
    }

}
