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

        // Move documentation downloads down a level, making them children
        // of a 'Grails' download. So now a download has binary, documentation
        // and optionally source files/packages.
        grailsChange {
            change {
                def rows = sql.rows "SELECT * FROM download"
                for (r in rows.findAll { it.software_name == "Grails Documentation" }) {
                    def distributionId = rows.find { it.software_name == "Grails" && it.software_version == r.software_version }.id
                    def maxFileIndex = sql.firstRow "SELECT max(files_idx) FROM download_file WHERE download_id = ${distributionId}"
                    maxFileIndex = maxFileIndex[0].toInteger()

                    sql.executeUpdate "UPDATE download_file SET download_id = ${distributionId}, " +
                            "title = 'Documentation', files_idx = ${maxFileIndex + 1} WHERE download_id = ${r.id}"
                }

                sql.executeUpdate "DELETE FROM download WHERE software_name = 'Grails Documentation'"
            }
            confirm "Migrated Grails Documentation download files"
        }
        
        // Set file type to SOURCE for those identifiable as such.
        update(tableName: "download_file") {
            column name: "file_type", value: "1"
            where "title like 'Source%'"
        }

        // Set file type to DOCUMENTATION for those identifiable as such.
        update(tableName: "download_file") {
            column name: "file_type", value: "2"
            where "title = 'Documentation'"
        }
    }

    changeSet(author: "eberry (generated)", id: "1334328604860-3") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "release_date", tableName: "download")
    }

}