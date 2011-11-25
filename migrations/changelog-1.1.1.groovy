databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1320944813799-1") {
        createTable(tableName: "license") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "licensePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-2") {
        createTable(tableName: "plugin_license") {
            column(name: "plugin_licenses_id", type: "bigint")

            column(name: "license_id", type: "bigint")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-3") {
        addColumn(tableName: "plugin") {
            column(name: "organization", type: "varchar(255)")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-4") {
        addColumn(tableName: "plugin") {
            column(name: "organization_url", type: "varchar(255)")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-15") {
        createIndex(indexName: "FK5A8AB7355E175809", tableName: "plugin_license") {
            column(name: "license_id")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-16") {
        createIndex(indexName: "FK5A8AB735B49DE880", tableName: "plugin_license") {
            column(name: "plugin_licenses_id")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-26") {
        addForeignKeyConstraint(baseColumnNames: "license_id", baseTableName: "plugin_license", constraintName: "FK5A8AB7355E175809", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "license", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-27") {
        addForeignKeyConstraint(baseColumnNames: "plugin_licenses_id", baseTableName: "plugin_license", constraintName: "FK5A8AB735B49DE880", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "plugin", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1320952425659-1") {
        addColumn(tableName: "plugin") {
            column(name: "usage", type: "decimal(19,2)")
        }
    }

        //--------------------------------------------
        // Random leftover stuff
        //
    changeSet(author: "pledbrook (generated)", id: "1320944813799-5") {
        addNotNullConstraint(columnDataType: "longtext", columnName: "body", tableName: "comment")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-6") {
        addNotNullConstraint(columnDataType: "datetime", columnName: "date_created", tableName: "comment")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-7") {
        addNotNullConstraint(columnDataType: "datetime", columnName: "last_updated", tableName: "comment")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-8") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "poster_class", tableName: "comment")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-9") {
        addNotNullConstraint(columnDataType: "bigint", columnName: "poster_id", tableName: "comment")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-10") {
        addNotNullConstraint(columnDataType: "bit", columnName: "beta_release", tableName: "download")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-11") {
        addNotNullConstraint(columnDataType: "bit", columnName: "featured", tableName: "plugin")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-12") {
        addNotNullConstraint(columnDataType: "bit", columnName: "official", tableName: "plugin")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-13") {
        addNotNullConstraint(columnDataType: "bit", columnName: "zombie", tableName: "plugin")
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-14") {
        createIndex(indexName: "name_unique_1320944812248", tableName: "plugin", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320944813799-36") {
        dropColumn(columnName: "url", tableName: "mirror")
    }
}
