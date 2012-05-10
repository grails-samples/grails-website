databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1333827938390-1") {
        createTable(tableName: "shirooauth_identity") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shirooauth_idPK")
            }

            column(name: "version", type: "bigint") { constraints(nullable: "false") }
            column(name: "provider", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "user_id", type: "bigint") { constraints(nullable: "false") }
            column(name: "username", type: "varchar(255)") { constraints(nullable: "false") }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333827938390-2") {
        createIndex(indexName: "identity_idx", tableName: "shirooauth_identity") {
            column(name: "provider")
            column(name: "username")
        }
    }

}
