databaseChangeLog = {

    changeSet(author: "eberry (generated)", id: "1334328604860-1") {
        addColumn(tableName: "download") {
            column(name: "latest_release", type: "bit")
        }
    }

    changeSet(author: "eberry (generated)", id: "1334328604860-2") {
        addColumn(tableName: "download_file") {
            column(name: "file_type", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "eberry (generated)", id: "1334328604860-3") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "release_date", tableName: "download")
    }

}