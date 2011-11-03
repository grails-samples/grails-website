databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1310034469414-3") {
        createTable(tableName: "role_permissions") {
            column(name: "role_id", type: "bigint")

            column(name: "permissions_string", type: "varchar(255)")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-28") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "role_permissions", constraintName: "FKEAD9D23B75B39296", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-32") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_info", constraintName: "FK1437D8A21ADE5676", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-33") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A75B39296", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-34") {
        addForeignKeyConstraint(baseColumnNames: "user_roles_id", baseTableName: "user_role", constraintName: "FK143BF46A16D0D038", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }
}
