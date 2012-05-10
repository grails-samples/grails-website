databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "site-autobase"

    changeSet(author: "pledbrook", id: "Rating resurrection") {
        createTable(tableName: "rating") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ratingPK")
            }

            column(name: "version", type: "bigint") { constraints(nullable: "false") }
            column(name: "date_created", type: "datetime") { constraints(nullable: "false") }
            column(name: "last_updated", type: "datetime") { constraints(nullable: "false") }
            column(name: "rater_class", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "rater_id", type: "bigint") { constraints(nullable: "false") }
            column(name: "stars", type: "decimal(19,2)") { constraints(nullable: "false") }
        }
    }
}
